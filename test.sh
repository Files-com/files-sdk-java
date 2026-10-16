#!/usr/bin/env bash
# Formats the generated sources, then runs every check without changing them: the Java 8 compile,
# tests, packaging, the formatting check and Checkstyle. CI runs this for the Java target.
set -euo pipefail
cd "$(dirname "$0")"

# OpenRewrite formats and checks only the sources that the nearest Git repository at or above the
# build does not both ignore and leave untracked. Generated in the generator's checkout, whose
# .gitignore ignores generated/, the SDK would be skipped, so it gets a repository of its own when the
# enclosing repository ignores it. An SDK with its own .git (a clone, worktree or submodule) and a copy
# outside any repository are left as they are, without needing Git.
if [ ! -e .git ]; then
  enclosing=$(pwd -P)
  until [ -e "$enclosing/.git" ] || [ "$enclosing" = / ]; do
    enclosing=$(dirname "$enclosing")
  done
  if [ -e "$enclosing/.git" ]; then
    if ! command -v git > /dev/null; then
      echo "$0: Git is needed to keep $enclosing/.git from hiding the sources from formatting" >&2
      exit 1
    fi
    ignored=0
    (unset GIT_DIR GIT_WORK_TREE; git check-ignore -- pom.xml src > /dev/null) || ignored=$?
    if [ "$ignored" -eq 0 ]; then
      (unset GIT_DIR GIT_WORK_TREE; git init -q)
    elif [ "$ignored" -ne 1 ]; then
      echo "$0: Git could not tell whether $enclosing/.git ignores the sources" >&2
      exit 1
    fi
  fi
fi

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
