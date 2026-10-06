#!/usr/bin/env bash
#
# Runs servers.sh and test.sh from throwaway copies, with stand-ins for the JDK and Maven, to show
# how they own the two test servers: a clean start and stop, a repeated start, a start in which
# one server fails, a start that cannot record a server it launched, a server still running when
# stop gives up, a recorded PID that ps reports for another process, and what test.sh reports when
# the tests fail, the servers will not stop, or both.
#
#   test-server/servers-test.sh

set -euo pipefail

here=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
work=$(mktemp -d)
passed=0
failed=0
unrelated=

# Each stand-in server records its PID and name in its checkout's fake/pids.
cleanup() {
  local pids pid name
  [[ -z $unrelated ]] || kill -KILL "$unrelated" 2> /dev/null || true
  for pids in "$work"/*/fake/pids; do
    [[ -f $pids ]] || continue
    while read -r pid name; do
      if ps -o args= -p "$pid" 2> /dev/null | grep -q -F "$work"; then
        kill -KILL "$pid" 2> /dev/null || true
      fi
    done < "$pids"
  done
  rm -rf -- "$work"
}
trap cleanup EXIT

mkdir -p "$work/bin" "$work/jdk/bin" "$work/reuse-bin" "$work/hide-bin" "$work/shift-bin"

cat > "$work/jdk/bin/java" <<'SH'
#!/usr/bin/env bash
# Stands in for the JDK running WireMock. --root-dir names the server, and FAKE_API or FAKE_STORAGE
# chooses whether it starts ("ready", the default), exits before it starts ("dies"), or ignores
# requests to stop until $FAKE_DIR/release exists ("holds-on"), counting them in terms-<server>. It
# records its PID and name in pids only once it is ready for that.
while (($#)); do
  [[ $1 != --root-dir ]] || root=$2
  shift
done
name=$(basename "$(dirname "$root")")
behavior=FAKE_$(echo "$name" | tr '[:lower:]' '[:upper:]')
behavior=${!behavior:-ready}
[[ $behavior != holds-on ]] || trap 'echo TERM >> "$FAKE_DIR/terms-$name"; [[ ! -e $FAKE_DIR/release ]] || exit 0' TERM
echo "$$ $name" >> "$FAKE_DIR/pids"
case $behavior in
  dies)
    echo "stand-in $name server: exiting before it starts"
    exit 1
    ;;
esac
if [[ $name == api ]]; then echo "port: 41001"; else echo "port: 41002"; fi
while :; do sleep 0.2; done
SH

cat > "$work/bin/mvn" <<'SH'
#!/usr/bin/env bash
# Stands in for Maven: writes a classpath when asked for one, and for `test` notes the server URLs
# it was given and how many stand-in servers were running, then exits with FAKE_TEST_STATUS.
for arg in "$@"; do
  case $arg in
    -Dmdep.outputFile=*) echo /stand-in/wiremock.jar > "${arg#-Dmdep.outputFile=}" ;;
    test)
      running=0
      while read -r pid name; do
        state=$(ps -o stat= -p "$pid" 2> /dev/null) && [[ $state != Z* ]] && running=$((running + 1))
      done < "$FAKE_DIR/pids"
      echo "$FILES_TEST_SERVER_URL $FILES_TEST_STORAGE_SERVER_URL running=$running" > "$FAKE_DIR/mvn-test"
      if [[ ${FAKE_TEST_STATUS:-0} != 0 ]]; then echo "stand-in mvn: tests failed" >&2; fi
      exit "${FAKE_TEST_STATUS:-0}"
      ;;
  esac
done
SH

cat > "$work/reuse-bin/ps" <<'SH'
#!/usr/bin/env bash
# Stands in for ps as if the PID in FAKE_REUSED_PID now belonged to the process FAKE_REUSER_PID.
args=()
while (($#)); do
  if [[ $1 == -p && $2 == "$FAKE_REUSED_PID" ]]; then
    args+=(-p "$FAKE_REUSER_PID")
    shift 2
  else
    args+=("$1")
    shift
  fi
done
exec "$REAL_PS" "${args[@]}"
SH
cat > "$work/hide-bin/ps" <<'SH'
#!/usr/bin/env bash
# Stands in for ps as if it could not read the servers start launches. With FAKE_PS_FAILURE=hidden
# it reports nothing about any process but its own callers; with "broken", once asked about such a
# process, every later query fails. It answers about a stand-in server only once that server has
# recorded its PID, so the server is ready for whatever start then does.
callers=" "
pid=$$
while [[ -n $pid && $pid != 0 && $pid != 1 ]]; do
  callers+="$pid "
  pid=$("$REAL_PS" -o ppid= -p "$pid" | tr -d ' ')
done
args=("$@")
[[ ! -e $FAKE_DIR/ps-broken ]] || { echo "ps: cannot read the process table" >&2; exit 1; }
while (($#)); do
  if [[ $1 == -p && $callers != *" $2 "* ]]; then
    for attempt in $(seq 100); do
      ! grep -q "^$2 " "$FAKE_DIR/pids" || break
      sleep 0.1
    done
    [[ $FAKE_PS_FAILURE != broken ]] || touch "$FAKE_DIR/ps-broken"
    echo "ps: cannot read process $2" >&2
    exit 1
  fi
  shift
done
exec "$REAL_PS" "${args[@]}"
SH
cat > "$work/shift-bin/ps" <<'SH'
#!/usr/bin/env bash
# Stands in for ps after the system clock was adjusted: the boot time the kernel reports, from which
# ps computes start times, has moved, so every start time it reports is a second later.
status=0
output=$("$REAL_PS" "$@") || status=$?
date_pattern='[A-Z][a-z][a-z] [A-Z][a-z][a-z] [ 0-9][0-9] [0-9][0-9]:[0-9][0-9]:[0-9][0-9] [0-9][0-9][0-9][0-9]'
while IFS= read -r line; do
  if [[ $line =~ ^(.*)($date_pattern)(.*)$ ]]; then
    seconds=$(LC_ALL=C TZ=UTC date -d "${BASH_REMATCH[2]}" +%s)
    line="${BASH_REMATCH[1]}$(LC_ALL=C TZ=UTC date -d "@$((seconds + 1))" '+%a %b %e %T %Y')${BASH_REMATCH[3]}"
  fi
  [[ -z $output ]] || printf '%s\n' "$line"
done <<< "$output"
exit "$status"
SH
chmod +x "$work/jdk/bin/java" "$work/bin/mvn" "$work/reuse-bin/ps" "$work/hide-bin/ps" "$work/shift-bin/ps"
real_ps=$(command -v ps)

# checkout <name>: a throwaway copy of the Java target's test.sh and test-server/servers.sh.
checkout() {
  local dir="$work/$1"
  mkdir -p "$dir/test-server" "$dir/fake"
  : > "$dir/fake/pids"
  cp -p "$here/../test.sh" "$dir/test.sh"
  cp -p "$here/servers.sh" "$dir/test-server/servers.sh"
  echo "$dir"
}

# run <checkout> <command...>: runs the command there with the stand-ins, setting status and output.
# FAKE_API, FAKE_STORAGE, FAKE_TEST_STATUS, FAKE_PS_FAILURE and FAKE_PATH vary the stand-ins.
run() {
  local dir=$1
  shift
  status=0
  output=$(cd "$dir" && env -u FILES_TEST_SERVER_URL -u FILES_TEST_STORAGE_SERVER_URL \
    PATH="${FAKE_PATH:-$work/bin}:/usr/bin:/bin" FILES_TEST_SERVER_JAVA_HOME="$work/jdk" \
    FILES_TEST_SERVER_STOP_SECONDS=2 FAKE_DIR="$dir/fake" FAKE_API="${FAKE_API:-ready}" \
    FAKE_STORAGE="${FAKE_STORAGE:-ready}" FAKE_TEST_STATUS="${FAKE_TEST_STATUS:-0}" \
    FAKE_REUSED_PID="${FAKE_REUSED_PID:-}" FAKE_REUSER_PID="${FAKE_REUSER_PID:-}" REAL_PS="$real_ps" \
    FAKE_PS_FAILURE="${FAKE_PS_FAILURE:-hidden}" \
    "$@" 2>&1) || status=$?
}

# expect <description> <exit status> [text the output contains]
expect() {
  if [[ $status == "$2" && $output == *"${3:-}"* ]]; then
    passed=$((passed + 1))
    echo "ok - $1"
  else
    failed=$((failed + 1))
    echo "not ok - $1: expected exit $2${3:+ and \"$3\"}, got exit $status: $output"
  fi
}

# check <description> <command...>
check() {
  local description=$1
  shift
  if "$@"; then
    passed=$((passed + 1))
    echo "ok - $description"
  else
    failed=$((failed + 1))
    echo "not ok - $description"
  fi
}

running() {
  local state
  state=$(ps -o stat= -p "$1" 2> /dev/null) && [[ $state != Z* ]]
}

# The PID of the last stand-in launched for server $2 in checkout $1.
stand_in() {
  sed -n "s/^\([0-9]*\) $2\$/\1/p" "$1/fake/pids" | tail -n 1
}

alive() {
  running "$(stand_in "$1" "$2")"
}

none_alive() {
  local pid name
  while read -r pid name; do
    ! running "$pid" || return 1
  done < "$1/fake/pids"
}

launched() {
  [[ $(wc -l < "$1/fake/pids" | tr -d ' ') == "$2" ]]
}

# terms <checkout> <server> <count>: how many requests to stop that holds-on stand-in has received.
# Its trap runs once its current sleep ends, so the count is read after longer than that.
terms() {
  sleep 0.5
  [[ $(cat "$1/fake/terms-$2" 2> /dev/null | wc -l | tr -d ' ') == "$3" ]]
}

dir=$(checkout clean)
run "$dir" ./test-server/servers.sh start
expect "start launches both servers" 0
check "both servers run after start" eval 'alive "$dir" api && alive "$dir" storage'
check "servers.env names both servers" grep -q -F 'FILES_TEST_STORAGE_SERVER_URL=http://127.0.0.1:41002' "$dir/test-server/target/servers.env"
run "$dir" ./test-server/servers.sh stop
expect "stop stops both servers" 0
check "no server runs after stop" none_alive "$dir"
check "stop removes servers.env" test ! -e "$dir/test-server/target/servers.env"
run "$dir" ./test-server/servers.sh start
expect "start works again once stop has settled both" 0
run "$dir" ./test-server/servers.sh stop

dir=$(checkout repeated-start)
run "$dir" ./test-server/servers.sh start
cp "$dir/test-server/target/servers.env" "$dir/servers.env-before"
run "$dir" ./test-server/servers.sh start
expect "a second start refuses while the first servers are recorded" 1 "still records a test server"
check "the second start launches nothing" launched "$dir" 2
check "the first servers keep running" eval 'alive "$dir" api && alive "$dir" storage'
check "servers.env still names the first servers" cmp -s "$dir/servers.env-before" "$dir/test-server/target/servers.env"
run "$dir" ./test-server/servers.sh stop
expect "stop still stops the first servers" 0
check "no server runs after that stop" none_alive "$dir"

dir=$(checkout partial-start)
FAKE_STORAGE=dies run "$dir" ./test-server/servers.sh start
expect "start fails when one server does not start" 1 "the storage test server did not start"
check "the failed start stops the API server it launched" none_alive "$dir"
check "the failed start writes no servers.env" test ! -e "$dir/test-server/target/servers.env"
run "$dir" ./test-server/servers.sh start
expect "the failed start leaves nothing recorded, so start works again" 0
run "$dir" ./test-server/servers.sh stop

# ps cannot read the server start has just launched, so start cannot record it.
dir=$(checkout unrecorded-start)
FAKE_PATH="$work/hide-bin:$work/bin" run "$dir" ./test-server/servers.sh start
expect "start fails when it cannot record a server it launched" 1 "could not record the api test server"
check "that server is stopped before start returns" none_alive "$dir"
check "start launches no other server" launched "$dir" 1
run "$dir" ./test-server/servers.sh start
expect "nothing stays recorded once that server has stopped, so start works again" 0
run "$dir" ./test-server/servers.sh stop

dir=$(checkout unrecorded-start-holds-on)
FAKE_API=holds-on FAKE_PATH="$work/hide-bin:$work/bin" run "$dir" ./test-server/servers.sh start
expect "start fails when a server it cannot record is still running after the wait" 1 "the api test server (PID $(stand_in "$dir" api)) is still running"
check "that server is still running" alive "$dir" api
check "start asked it to stop once" terms "$dir" api 1
run "$dir" ./test-server/servers.sh stop
expect "stop fails rather than report a server it cannot verify as gone" 1 "so it was not signalled"
check "stop does not signal that server" terms "$dir" api 1
run "$dir" ./test-server/servers.sh start
expect "start refuses while that server is still recorded" 1 "still records a test server"
run "$dir" ./test.sh
expect "test.sh fails rather than start over that server" 1 "still records a test server"
check "neither start nor test.sh signals it again" terms "$dir" api 1
touch "$dir/fake/release"
kill -TERM "$(stand_in "$dir" api)"
for attempt in $(seq 40); do alive "$dir" api || break; sleep 0.25; done
run "$dir" ./test-server/servers.sh stop
expect "once that server has exited, stop settles its record" 0
run "$dir" ./test-server/servers.sh start
expect "and start works again" 0
run "$dir" ./test-server/servers.sh stop

# ps fails outright while start records a server that will not stop.
dir=$(checkout unrecorded-start-ps-broken)
FAKE_API=holds-on FAKE_PS_FAILURE=broken FAKE_PATH="$work/hide-bin:$work/bin" run "$dir" ./test-server/servers.sh start
expect "start fails when ps breaks while it records a server that will not stop" 1 "the api test server (PID $(stand_in "$dir" api)) is still running"
check "that server is still running" alive "$dir" api
run "$dir" ./test-server/servers.sh stop
expect "a later stop still owns that server rather than report it gone" 1 "so it was not signalled"
check "and does not signal it" terms "$dir" api 1
run "$dir" ./test-server/servers.sh start
expect "a later start refuses rather than launch beside it" 1 "still records a test server"
check "start launched nothing beside it" launched "$dir" 1
touch "$dir/fake/release"
kill -TERM "$(stand_in "$dir" api)"
for attempt in $(seq 40); do alive "$dir" api || break; sleep 0.25; done
run "$dir" ./test-server/servers.sh stop
expect "once that server has exited, stop settles its record" 0

dir=$(checkout unresolved-stop)
FAKE_API=holds-on run "$dir" ./test-server/servers.sh start
FAKE_API=holds-on run "$dir" ./test-server/servers.sh stop
expect "stop fails while a server is still running after the wait" 1 "the api test server (PID $(stand_in "$dir" api)) is still running"
check "that server is still running" alive "$dir" api
check "the other server is stopped" eval '! alive "$dir" storage'
run "$dir" ./test-server/servers.sh start
expect "start refuses while that server is still recorded" 1 "still records a test server"
touch "$dir/fake/release"
run "$dir" ./test-server/servers.sh stop
expect "a later stop settles it from the kept record" 0
check "no server runs after the later stop" none_alive "$dir"

# ps reports start times to the second, so the unrelated process starts a second earlier.
dir=$(checkout replaced-pid)
sleep 600 &
unrelated=$!
sleep 1.1
run "$dir" ./test-server/servers.sh start
api=$(stand_in "$dir" api)
FAKE_PATH="$work/reuse-bin:$work/bin" FAKE_REUSED_PID=$api FAKE_REUSER_PID=$unrelated run "$dir" ./test-server/servers.sh stop
expect "stop refuses a recorded PID that ps reports for another process" 1 "PID $api no longer reports the start time"
check "stop does not signal that PID" running "$api"
check "stop still stops the other server" eval '! alive "$dir" storage'
run "$dir" ./test-server/servers.sh stop
expect "once ps reports the recorded server again, the kept record settles it" 0
check "no server runs after that stop" none_alive "$dir"
{ kill -KILL "$unrelated" && wait "$unrelated"; } 2> /dev/null || true
unrelated=

# Adjusting the system clock moves every start time ps reports; the servers are still recognized.
dir=$(checkout clock-adjusted)
run "$dir" ./test-server/servers.sh start
FAKE_PATH="$work/shift-bin:$work/bin" run "$dir" ./test-server/servers.sh stop
expect "stop still recognizes the servers after the clock moves every start time ps reports" 0
check "no server runs after that stop" none_alive "$dir"

dir=$(checkout recipe)
run "$dir" ./test.sh
expect "test.sh passes when the tests pass and the servers stop" 0
check "mvn test ran against both servers" grep -q -F 'http://127.0.0.1:41001 http://127.0.0.1:41002 running=2' "$dir/fake/mvn-test"
check "no server runs after test.sh" none_alive "$dir"

dir=$(checkout recipe-test-failure)
FAKE_TEST_STATUS=1 run "$dir" ./test.sh
expect "test.sh fails when the tests fail" 1 "stand-in mvn: tests failed"
check "the servers are stopped after failed tests" none_alive "$dir"

dir=$(checkout recipe-cleanup-failure)
FAKE_API=holds-on run "$dir" ./test.sh
expect "test.sh fails when a server will not stop, though the tests passed" 1 "is still running"
check "the tests themselves passed" grep -q -F 'running=2' "$dir/fake/mvn-test"
touch "$dir/fake/release"
run "$dir" ./test-server/servers.sh stop
expect "the record test.sh kept lets stop settle that server" 0
check "no server runs after settling" none_alive "$dir"

dir=$(checkout recipe-both-fail)
FAKE_TEST_STATUS=1 FAKE_API=holds-on run "$dir" ./test.sh
expect "test.sh still reports the test failure when a server also will not stop" 1 "stand-in mvn: tests failed"
check "and reports the server that will not stop" eval '[[ $output == *"is still running"* ]]'
touch "$dir/fake/release"
run "$dir" ./test-server/servers.sh stop

dir=$(checkout recipe-start-failure)
FAKE_STORAGE=dies run "$dir" ./test.sh
expect "test.sh fails when a server does not start" 1 "the storage test server did not start"
check "mvn test does not run" test ! -e "$dir/fake/mvn-test"
check "no server runs after that failure" none_alive "$dir"

dir=$(checkout recipe-already-running)
run "$dir" ./test-server/servers.sh start
run "$dir" ./test.sh
expect "test.sh fails rather than start over servers already recorded" 1 "still records a test server"
check "test.sh leaves those servers running" eval 'alive "$dir" api && alive "$dir" storage'
run "$dir" ./test-server/servers.sh stop
expect "their owner can still stop them" 0

echo "$passed passed, $failed failed"
[[ $failed == 0 ]]
