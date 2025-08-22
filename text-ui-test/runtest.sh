#!/usr/bin/env bash
set -euo pipefail

mkdir -p ../bin
rm -f ACTUAL.TXT sources.txt

find ../src/main/java -name "*.java" > sources.txt
javac -Xlint:none -d ../bin @sources.txt

java -classpath ../bin duke.Bob < input.txt > ACTUAL.TXT

# normalize CRLF -> LF to avoid false diffs on Windows
sed -i 's/\r$//' ACTUAL.TXT
[ -f EXPECTED.TXT ] && sed -i 's/\r$//' EXPECTED.TXT

# bootstrap expected on first run
if [ ! -f EXPECTED.TXT ]; then
  cp ACTUAL.TXT EXPECTED.TXT
  echo "Created EXPECTED.TXT from ACTUAL.TXT (first run)."
fi

diff -u EXPECTED.TXT ACTUAL.TXT && echo "✅ Test passed"
