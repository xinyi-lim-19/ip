#!/usr/bin/env bash
set -euo pipefail

# ====== CONFIG ======
<<<<<<< HEAD
MAIN_CLASS="duke.Bob"
=======
MAIN_CLASS="duke.Bob"   
>>>>>>> refs/heads/A-FullCommitMessage

# cd to script directory so relative paths are stable
cd "$(dirname "$0")"

# prepare bin and clean previous output
mkdir -p ../bin
rm -f ACTUAL.TXT

# --- Compile only console classes (exclude JavaFX GUI files) ---
<<<<<<< HEAD
# Excluded:
#   duke/MainApp.java, duke/Launcher.java, and anything under duke/gui/
<<<<<<< HEAD
SOURCES=$(find ../src/main/java -type f -name "*.java" \
=======
SOURCES=$(find ../src/main/java -name "*.java" \
>>>>>>> branch-A-TextUiTesting
=======
SOURCES=$(find ../src/main/java -name "*.java" \
>>>>>>> refs/heads/A-FullCommitMessage
  ! -path "*/duke/MainApp.java" \
  ! -path "*/duke/Launcher.java" \
  ! -path "*/duke/gui/*")

<<<<<<< HEAD
# In case the find returns nothing (guard)
=======
>>>>>>> refs/heads/A-FullCommitMessage
if [ -z "${SOURCES}" ]; then
  echo "No console sources found to compile. Check your paths/exclusions."
  exit 1
fi

<<<<<<< HEAD
# Compile (no warnings; output into ../bin)
=======
>>>>>>> refs/heads/A-FullCommitMessage
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

