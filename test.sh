#!/usr/bin/env bash
# Formats the generated sources, then runs every check without changing them: the Java 8 compile,
# tests, packaging, the formatting check and Checkstyle. CI runs this for the Java target.
set -euo pipefail
cd "$(dirname "$0")"

. ./build-jdk.sh
./mvnw -B -ntp -V rewrite:run
# FilesApiTest runs against WireMock servers on JDK 17 (see test-server/servers.sh) unless the
# caller already runs them and set their URLs. Servers this script started are stopped when it
# exits; failing to stop them fails the run without replacing an earlier failure's status.
stop_test_servers() {
  local status=$?
  ./test-server/servers.sh stop || [ "$status" -ne 0 ] || status=1
  exit "$status"
}
if [ -z "${FILES_TEST_SERVER_URL:-}" ]; then
  ./test-server/servers.sh start || exit 1
  trap stop_test_servers EXIT
  . ./test-server/target/servers.env
fi
./mvnw -B -ntp verify
