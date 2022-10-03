#!/bin/bash

echo ''
echo ' ---------------------------------------------- '
echo '|          Android Test Report Printer         |'
echo ' ---------------------------------------------- '
echo ''

unit_result_path="/build/test-results/"
inst_result_path="./app/build/outputs/androidTest-results/connected/"

for xml_file in `find . -name "TEST-*.xml"`; do
	echo "$xml_file"
done
