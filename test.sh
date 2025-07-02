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
mvn test || exit 1

