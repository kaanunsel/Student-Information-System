#!/bin/bash

# Function to kill background processes on exit
cleanup() {
  echo "Stopping all services..."
  # Kill all child processes of this script
  pkill -P $$
  exit
}

# Trap SIGINT (Ctrl+C) to call cleanup
trap cleanup SIGINT

echo "Starting Backend..."
mvn spring-boot:run &

echo "Starting Frontend..."
cd frontend
# Check if pnpm is installed, otherwise fall back to npm
if command -v pnpm &> /dev/null; then
    pnpm dev &
else
    echo "pnpm not found, using npm..."
    npm run dev &
fi

# Wait for all background processes
wait

