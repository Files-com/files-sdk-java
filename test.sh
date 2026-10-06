#!/usr/bin/env bash

#set -e

# Execute running tests from same directory as current script
cd "$(dirname "$0")"

# We may have switched to other versions before this build step
if command -v jenv &> /dev/null; then
  if [ -d /usr/lib/jvm/java-8-openjdk-amd64 ]; then jenv add /usr/lib/jvm/java-8-openjdk-amd64; fi
  if [ -d /usr/lib/jvm/java-8-openjdk-arm64 ]; then jenv add /usr/lib/jvm/java-8-openjdk-arm64; fi
  if [ -d /opt/jdk-14.0.1 ]; then jenv add /opt/jdk-14.0.1; fi

  jenv local 1.8 # Force 1.8 with jenv
fi
mvn rewrite:run || exit 1
mvn checkstyle:checkstyle || exit 1
# FilesApiTest runs against WireMock servers on JDK 17 (see test-server/servers.sh) unless the
# caller already runs them and set their URLs. Servers this script started are stopped when it
# exits; failing to stop them fails the run without replacing an earlier failure's status.
stop_test_servers() {
  local status=$?
  ./test-server/servers.sh stop || [ "$status" -ne 0 ] || status=1
  exit "$status"
}
if [ -z "$FILES_TEST_SERVER_URL" ]; then
  ./test-server/servers.sh start || exit 1
  trap stop_test_servers EXIT
  . ./test-server/target/servers.env
fi
mvn test || exit 1

