#!/usr/bin/env bash

#set -e

mvn -B -DnewVersion=$(cat ./_VERSION) -DgenerateBackupPoms=false versions:set && mvn clean package
