#!/usr/bin/env bash
set -euo pipefail

mkdir -p ../bin
rm -f ACTUAL.TXT sources.txt

# collect sources & compile
find ../src/main/java -name "*.java" > sources.txt
javac -Xlint:none -d ../bin @sources.txt

# run with redirected I/O
java -classpath ../bin duke.Bob < input.txt > ACTUAL.TXT

# normalize line endings (Windows safety)
sed -i 's/\r$//' ACTUAL.TXT
[ -f EXPECTED.TXT ] && sed -i 's/\r$//' EXPECTED.TXT

# create baseline EXPECTED once if missing
if [ ! -f EXPECTED.TXT ]; then
  cp ACTUAL.TXT EXPECTED.TXT
  echo "Created EXPECTED.TXT from ACTUAL.TXT (first run)."
fi

# compare
diff -u EXPECTED.TXT ACTUAL.TXT && echo "✅ Test passed"
