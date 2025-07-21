#!/usr/bin/env bash

# Make sure we have the correct version of Java set
if command -v jenv &> /dev/null; then
  if [ -d /usr/lib/jvm/java-8-openjdk-amd64 ]; then jenv add /usr/lib/jvm/java-8-openjdk-amd64; fi
  if [ -d /usr/lib/jvm/java-8-openjdk-arm64 ]; then jenv add /usr/lib/jvm/java-8-openjdk-arm64; fi
  if [ -d /opt/jdk-14.0.1 ]; then jenv add /opt/jdk-14.0.1; fi

  jenv local 1.8 # Force 1.8 with jenv
fi
mvn rewrite:run
mvn -B -DskipTests -DnewVersion=$(cat ./_VERSION) -DgenerateBackupPoms=false versions:set && mvn -DskipTests clean package