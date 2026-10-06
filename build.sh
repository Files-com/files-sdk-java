#!/usr/bin/env bash

# Make sure we have the correct version of Java set
if command -v jenv &> /dev/null; then
  if [ -d /usr/lib/jvm/java-8-openjdk-amd64 ]; then jenv add /usr/lib/jvm/java-8-openjdk-amd64; fi
  if [ -d /usr/lib/jvm/java-8-openjdk-arm64 ]; then jenv add /usr/lib/jvm/java-8-openjdk-arm64; fi
  if [ -d /opt/jdk-14.0.1 ]; then jenv add /opt/jdk-14.0.1; fi

  jenv local 1.8 # Force 1.8 with jenv
fi
mvn rewrite:run || exit 1
# versions:set by its full coordinates: the bare prefix would resolve whichever versions-maven-plugin is
# newest when the build runs.
mvn -B -DskipTests -DnewVersion=$(cat ./_VERSION) -DgenerateBackupPoms=false org.codehaus.mojo:versions-maven-plugin:2.22.0:set && mvn -DskipTests clean package