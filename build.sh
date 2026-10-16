#!/usr/bin/env bash
# Formats the generated sources and packages them under the release line in _VERSION.
# CI runs this after test.sh.
set -euo pipefail
cd "$(dirname "$0")"

. ./build-jdk.sh
./mvnw -B -ntp -V rewrite:run
./mvnw -B -ntp -DnewVersion="$(cat _VERSION)" -DgenerateBackupPoms=false versions:set
./mvnw -B -ntp -DskipTests clean package
