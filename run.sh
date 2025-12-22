#!/bin/bash

# Store PIDs for cleanup
REDIS_PID=""

# Function to kill background processes on exit
cleanup() {
  echo "Stopping all services..."
  
  # Stop Redis if we started it
  if [ -n "$REDIS_PID" ]; then
    echo "Stopping Redis..."
    kill $REDIS_PID 2>/dev/null
  fi
  
  # Kill all child processes of this script
  pkill -P $$
  exit
}

# Trap SIGINT (Ctrl+C) to call cleanup
trap cleanup SIGINT

# Check if Redis is already running
if ! redis-cli ping &> /dev/null; then
  echo "Starting Redis..."
  redis-server --daemonize yes --port 6379
  # Give Redis a moment to start
  sleep 1
  
  # Verify Redis started successfully
  if redis-cli ping &> /dev/null; then
    echo "Redis started successfully on port 6379"
    # Get Redis PID for cleanup
    REDIS_PID=$(pgrep redis-server)
  else
    echo "Warning: Failed to start Redis. Application will use in-memory cache."
  fi
else
  echo "Redis is already running on port 6379"
fi

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

