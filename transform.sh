#!/usr/bin/env bash

LOG_FILE="$HOME/Downloads/logs.txt"

log() {
  echo "$*" >> "$LOG_FILE"
}

MANIFEST_FILE=$(mktemp)
while IFS= read line; do
  echo "$line" >> "$MANIFEST_FILE"
done

java -jar "$HOME"/Workspace/personal/chameleon/cli/target/cli-*.jar transform "$MANIFEST_FILE" | tee "$LOG_FILE"