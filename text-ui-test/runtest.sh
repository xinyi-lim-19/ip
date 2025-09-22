#!/usr/bin/env bash
set -euo pipefail

# ====== CONFIG ======
MAIN_CLASS="duke.Bob"

# cd to script directory so relative paths are stable
cd "$(dirname "$0")"

# prepare bin and clean previous output
mkdir -p ../bin
rm -f ACTUAL.TXT

# --- Compile only console classes (exclude JavaFX GUI files) ---
# Excluded:
#   duke/MainApp.java, duke/Launcher.java, and anything under duke/gui/
SOURCES=$(find ../src/main/java -name "*.java" \
  ! -path "*/duke/MainApp.java" \
  ! -path "*/duke/Launcher.java" \
  ! -path "*/duke/gui/*")

# In case the find returns nothing (guard)
if [ -z "${SOURCES}" ]; then
  echo "No console sources found to compile. Check your paths/exclusions."
  exit 1
fi

# Compile (no warnings; output into ../bin)
javac -Xlint:none -d ../bin ${SOURCES}

# --- Run with redirected I/O ---
java -cp ../bin "$MAIN_CLASS" < input.txt > ACTUAL.TXT

# --- Compare outputs ---
if diff -u EXPECTED.TXT ACTUAL.TXT; then
  echo "=== PASS: Output matches EXPECTED.TXT ==="
else
  echo "=== FAIL: Output differs from EXPECTED.TXT ==="
  exit 1
fi

