#!/usr/bin/env bash
# The five legacy File probes, through the shared simulator, after this job has compiled the normal SDK/tests. Then the
# shared failure-case matrix (FilesFailureCasesTest, the mock server's scenarios/failure-cases.json) through a launch of
# its own, whose launcher adds the scenario's fault rules and requires every case passed with exactly its expected
# attempts and fault decisions; its Surefire report must show the one test run and passed, none skipped.
set -euo pipefail
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
mock="${1:?generated mock server required}"
out="${2:?fresh evidence directory required}"
[[ ! -e "${out}" ]] || { echo "${out} already exists" >&2; exit 1; }
mkdir -p "${out}"
out="$(cd "${out}" && pwd)"
cd "${DIR}"
# Use the same checked build JDK and Maven settings as the existing Java build; compile nothing a second time.
. ./build-jdk.sh
report="${DIR}/target/surefire-reports/TEST-com.files.FilesFileTest.xml"
if [[ -f "${report}" ]]; then
  cp "${report}" "${out}/prior-ordinary.xml"
  rm "${report}"
fi
ruby "${mock}/with-scenario.rb" --scenario file-transfers --out "${out}/launcher" \
  --startup-timeout 30 --consumer-timeout 180 --settle-timeout 5 -- \
  ./mvnw --batch-mode -Dtest=FilesFileTest -Dsurefire.failIfNoSpecifiedTests=true surefire:test
[[ -f "${report}" ]] || { echo "FilesFileTest did not write its test report" >&2; exit 1; }
cp "${report}" "${out}/surefire.xml"
ruby -rrexml/document -e '
  path = ARGV.fetch(0)
  document = REXML::Document.new(File.read(path))
  suite = document.root
  expected = %w[getFileStream saveLocalFile putInputStream putBufferedInputStream putFile].sort
  cases = REXML::XPath.match(document, "//testcase")
  abort("#{path}: did not execute exactly the five legacy File probes") unless
    suite.name == "testsuite" && suite.attributes["name"] == "com.files.FilesFileTest" &&
    suite.attributes["tests"] == "5" && cases.map { |test| test.attributes["name"] }.sort == expected &&
    cases.all? { |test| test.attributes["classname"] == "com.files.FilesFileTest" }
  abort("#{path}: a legacy File probe failed, errored or skipped") unless
    %w[failures errors skipped].all? { |key| suite.attributes[key] == "0" } &&
    cases.all? { |test| %w[failure error skipped].all? { |name| test.elements[name].nil? } }
  puts "#{path}: all five legacy File probes passed, none skipped"
' "${out}/surefire.xml"

failure_report="${DIR}/target/surefire-reports/TEST-com.files.FilesFailureCasesTest.xml"
if [[ -f "${failure_report}" ]]; then
  cp "${failure_report}" "${out}/failure-cases-prior-ordinary.xml"
  rm "${failure_report}"
fi
ruby "${mock}/with-scenario.rb" --scenario failure-cases --out "${out}/failure-cases" \
  --startup-timeout 30 --consumer-timeout 180 --settle-timeout 5 --failure-variant sdk-network-retries -- \
  ./mvnw --batch-mode -Dtest=FilesFailureCasesTest -Dsurefire.failIfNoSpecifiedTests=true surefire:test
[[ -f "${failure_report}" ]] || { echo "FilesFailureCasesTest did not write its test report" >&2; exit 1; }
cp "${failure_report}" "${out}/failure-cases/surefire.xml"
ruby -rrexml/document -e '
  path = ARGV.fetch(0)
  document = REXML::Document.new(File.read(path))
  suite = document.root
  cases = REXML::XPath.match(document, "//testcase")
  abort("#{path}: did not execute exactly the one failure-case test") unless
    suite.name == "testsuite" && suite.attributes["name"] == "com.files.FilesFailureCasesTest" &&
    suite.attributes["tests"] == "1" && cases.map { |test| test.attributes["name"] } == %w[everyFailureCase]
  abort("#{path}: the failure-case test failed, errored or skipped") unless
    %w[failures errors skipped].all? { |key| suite.attributes[key] == "0" } &&
    cases.all? { |test| %w[failure error skipped].all? { |name| test.elements[name].nil? } }
  puts "#{path}: the shared failure cases passed, none skipped"
' "${out}/failure-cases/surefire.xml"
