#!/usr/bin/env bash

mvn -B -DnewVersion=$(cat ./_VERSION) -DgenerateBackupPoms=false versions:set && mvn package
