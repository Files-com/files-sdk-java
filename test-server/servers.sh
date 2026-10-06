#!/usr/bin/env bash
#
# Starts and stops the WireMock servers FilesApiTest runs against: one for the API and a second
# origin for cross-origin redirects. Each runs in its own JDK 17 process on the classpath pom.xml
# resolves, so the SDK's tests stay on Java 8 with only the SDK's dependencies. The tests reach the
# servers only through WireMock's admin API. test.sh does all of this; to run the tests yourself:
#
#   test-server/servers.sh start
#   . test-server/target/servers.env   # sets FILES_TEST_SERVER_URL and FILES_TEST_STORAGE_SERVER_URL
#   mvn test
#   test-server/servers.sh stop
#
# The servers run on the JDK in FILES_TEST_SERVER_JAVA_HOME, or else jenv's or Debian's JDK 17.
# start reserves target/<server>/process before it launches each server, then records there the
# server's PID and its start time, as ps reports it relative to process 1's (see process_status). It
# refuses to start while any record remains.
# If either server fails to start, start stops every server it launched, following each through its
# own job table rather than ps, and removes a record only once that server has exited. stop signals
# and waits only for the process a record still names, up to FILES_TEST_SERVER_STOP_SECONDS (default
# 30) for each. A server still running after either wait keeps its record, and the command fails, so
# that a later stop can try again. So does a record stop cannot verify, which is never signalled: a
# reservation start never filled in, a recorded PID without a start time that a process still has,
# or a PID whose start time has changed, since then the server has exited and another process has
# its PID.

set -euo pipefail
cd "$(dirname "$0")"

servers=(api storage)
state=target
stop_seconds=${FILES_TEST_SERVER_STOP_SECONDS:-30}
if [[ ! $stop_seconds =~ ^[0-9]+$ ]]; then
  echo "$0: FILES_TEST_SERVER_STOP_SECONDS ($stop_seconds) is not a whole number of seconds" >&2
  exit 2
fi
stop_seconds=$((10#$stop_seconds)) # so that a leading zero is not read as octal

server_java() {
  local home
  if [[ -n ${FILES_TEST_SERVER_JAVA_HOME:-} ]]; then
    [[ -x $FILES_TEST_SERVER_JAVA_HOME/bin/java ]] || {
      echo "$0: FILES_TEST_SERVER_JAVA_HOME ($FILES_TEST_SERVER_JAVA_HOME) has no bin/java" >&2
      return 1
    }
    echo "$FILES_TEST_SERVER_JAVA_HOME/bin/java"
    return
  fi
  for home in "$(jenv prefix 17 2>/dev/null || true)" /usr/lib/jvm/java-17-openjdk-amd64 /usr/lib/jvm/java-17-openjdk-arm64; do
    if [[ -n $home && -x $home/bin/java ]]; then
      echo "$home/bin/java"
      return
    fi
  done
  echo "$0: found no JDK 17 for the test servers; set FILES_TEST_SERVER_JAVA_HOME to one" >&2
  return 1
}

# Whether ps can report the start time of this script's own process.
ps_reports_start_times() {
  LC_ALL=C TZ=UTC ps -o stat= -o lstart= -p $$ > /dev/null 2>&1 || {
    echo "$0: ps cannot report process start times, so the test servers cannot be tracked" >&2
    return 1
  }
}

# Seconds since 1970 for a start time as ps prints it in the C locale and UTC, such as
# "Mon Oct  5 19:36:01 2026": with GNU date, or else BSD date.
start_seconds() {
  LC_ALL=C TZ=UTC date -d "$1" +%s 2> /dev/null || LC_ALL=C TZ=UTC date -j -f '%a %b %e %T %Y' "$1" +%s
}

# Prints the state of process $1 and its start time, nothing if there is no such process. ps
# computes start times from the boot time the kernel reports, which moves whenever the system clock
# is adjusted, so the same process can report another start time later. The start time printed is
# therefore in seconds after process 1's, read in the same ps call: both move together, so that
# difference stays the same for as long as the process runs. If ps does not report process 1, it
# is the start time itself. A fixed locale and time zone keep ps's output readable.
process_status() {
  local lines pid stat started reference= process= seconds base
  if ! lines=$(LC_ALL=C TZ=UTC ps -o pid= -o stat= -o lstart= -p "$1" -p 1 2> /dev/null); then
    ps_reports_start_times
    return
  fi
  while read -r pid stat started; do
    if [[ $pid == 1 ]]; then
      reference=$started
    elif [[ -n $pid ]]; then
      process="$stat $started"
    fi
  done <<< "$lines"
  [[ -n $process ]] || return 0
  read -r stat started <<< "$process"
  seconds=$(start_seconds "$started") || return 1
  if [[ -n $reference ]]; then
    base=$(start_seconds "$reference") || return 1
    seconds=$((seconds - base))
  fi
  echo "$stat $seconds"
}

# Prints whether process $1, which started at $2, is still "running", has "exited" (including an
# exited process not yet reaped), or now reports another start time ("replaced").
process_state() {
  local status ps_state ps_started
  status=$(process_status "$1") || return 1
  read -r ps_state ps_started <<< "$status"
  if [[ -z $ps_state || $ps_state == Z* ]]; then
    echo exited
  elif [[ -n $2 && $ps_started == "$2" ]]; then
    echo running
  else
    echo replaced
  fi
}

# Whether process $1, which this shell launched, is still running, from the shell's own job table:
# it holds a child until the shell has reaped it, so no other process can have taken its PID.
child_running() {
  local running
  running=$(jobs -rp)
  [[ $'\n'$running$'\n' == *$'\n'$1$'\n'* ]]
}

# Records process $2, which this shell just launched, as server $1: first its PID, then the start
# time ps reports for it. It fails, leaving whatever it managed to record, if it cannot do both.
record() {
  local status ps_state ps_started
  printf '%s\n' "$2" > "$state/$1/process" || return 1
  status=$(process_status "$2") || return 1
  read -r ps_state ps_started <<< "$status"
  if ! child_running "$2"; then
    # It has exited already, so ps may describe another process that has since taken its PID.
    ps_started=
  elif [[ -z $ps_started ]]; then
    echo "$0: ps reports no start time for the $1 test server (PID $2), which is still running" >&2
    return 1
  fi
  printf '%s\n%s\n' "$2" "$ps_started" > "$state/$1/process.new" && mv -f "$state/$1/process.new" "$state/$1/process"
}

# Stops server $1, process $2, which this shell launched, waiting through the job table rather than
# ps. The record is removed once it has exited; until then it stays, so that start refuses and stop
# fails.
settle_launched() {
  local name=$1 pid=$2 waited=0
  ! child_running "$pid" || kill -TERM "$pid" 2> /dev/null || true
  while child_running "$pid"; do
    if ((waited >= stop_seconds * 2)); then
      echo "$0: the $name test server (PID $pid) is still running ${stop_seconds}s after being asked to stop; $state/$name/process keeps it" >&2
      return 1
    fi
    sleep 0.5
    waited=$((waited + 1))
  done
  rm -f "$state/$name/process"
}

recorded_pid() {
  sed -n 1p "$state/$1/process"
}

recorded_start() {
  sed -n 2p "$state/$1/process"
}

# Asks the process recorded for server $1 to stop, waits for it to exit and removes the record. It
# fails, keeping the record, if the process is still running after stop_seconds, its PID now
# reports another start time, or ps cannot tell.
settle() {
  local name=$1 pid started now waited=0
  [[ -f $state/$name/process ]] || return 0
  pid=$(recorded_pid "$name")
  started=$(recorded_start "$name")
  if [[ -z $pid ]]; then
    echo "$0: $state/$name/process reserves a test server that start never recorded, so it cannot be checked or signalled; delete that record once no such server is running" >&2
    return 1
  fi
  now=$(process_state "$pid" "$started") || return 1
  if [[ $now == running ]]; then
    kill -TERM "$pid" 2> /dev/null || true
    while now=$(process_state "$pid" "$started") && [[ $now == running ]]; do
      if ((waited >= stop_seconds * 2)); then
        echo "$0: the $name test server (PID $pid) is still running ${stop_seconds}s after being asked to stop; $state/$name/process keeps it" >&2
        return 1
      fi
      sleep 0.5
      waited=$((waited + 1))
    done
  fi
  case $now in
    exited) rm -f "$state/$name/process" ;;
    replaced)
      if [[ -z $started ]]; then
        echo "$0: a process has PID $pid, but $state/$name/process has no start time to show that it is the $name test server, so it was not signalled; stop retries once no process has that PID" >&2
      else
        echo "$0: PID $pid no longer reports the start time $state/$name/process records for the $name test server, so it was not signalled; delete that record once the server is known to be gone" >&2
      fi
      return 1
      ;;
    *) return 1 ;;
  esac
}

# Stops every server a failed start launched; it reads start's launched count and pids.
stop_launched() {
  local i
  for ((i = 0; i < launched; i++)); do
    settle_launched "${servers[i]}" "${pids[i]}" || true
  done
}

# WireMock prints its options, including the port it bound, once it is listening.
bound_port() {
  local name=$1 attempt port pid started
  pid=$(recorded_pid "$name")
  started=$(recorded_start "$name")
  for attempt in $(seq 120); do
    port=$(sed -n 's/^port: *\([0-9][0-9]*\)$/\1/p' "$state/$name/server.log")
    if [[ -n $port ]]; then
      echo "$port"
      return
    fi
    [[ $(process_state "$pid" "$started") == running ]] || break
    sleep 0.5
  done
  echo "$0: the $name test server did not start; its log follows" >&2
  cat "$state/$name/server.log" >&2
  return 1
}

start() {
  local java name port launched=0 pids=() ports=()
  for name in "${servers[@]}"; do
    if [[ -f $state/$name/process ]]; then
      echo "$0: $state/$name/process still records a test server; run $0 stop first" >&2
      return 1
    fi
  done
  ps_reports_start_times
  java=$(server_java)
  rm -f "$state/servers.env"
  # Emptied before any server launches, so that no previous run's port is read for this one.
  for name in "${servers[@]}"; do
    mkdir -p "$state/$name/root"
    : > "$state/$name/server.log"
  done
  mvn -q -B dependency:build-classpath -Dmdep.outputFile="$state/classpath.txt"
  for name in "${servers[@]}"; do
    # Reserved before the server exists, so that no failure after it launches leaves it unowned.
    if ! : > "$state/$name/process"; then
      stop_launched
      return 1
    fi
    "$java" -cp "$(< "$state/classpath.txt")" wiremock.Run --port 0 --bind-address 127.0.0.1 \
      --root-dir "$state/$name/root" --disable-banner --disable-response-templating \
      > "$state/$name/server.log" 2>&1 &
    pids[launched]=$!
    launched=$((launched + 1))
    if ! record "$name" "${pids[launched - 1]}"; then
      echo "$0: could not record the $name test server (PID ${pids[launched - 1]}), so it is being stopped" >&2
      stop_launched
      return 1
    fi
  done
  for name in "${servers[@]}"; do
    if ! port=$(bound_port "$name"); then
      stop_launched
      return 1
    fi
    ports+=("$port")
  done
  if ! printf '%s\n' \
    "export FILES_TEST_SERVER_URL=http://127.0.0.1:${ports[0]}" \
    "export FILES_TEST_STORAGE_SERVER_URL=http://127.0.0.1:${ports[1]}" > "$state/servers.env"; then
    stop_launched
    return 1
  fi
}

stop() {
  local name unresolved=0
  for name in "${servers[@]}"; do
    settle "$name" || unresolved=1
  done
  ((unresolved == 0)) || return 1
  rm -f "$state/servers.env"
}

case "${1:-}" in
  start) start ;;
  stop) stop ;;
  *)
    echo "usage: $0 start|stop" >&2
    exit 2
    ;;
esac
