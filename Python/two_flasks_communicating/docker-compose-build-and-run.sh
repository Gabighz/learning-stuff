#!/bin/sh

if [ "$(id -u)" -ne 0 ]; then
    echo "Please run this script as root or using sudo."
    exit 1
fi

if [ -z "$1" ]; then
    echo "Please provide an environment argument: development or production."
    exit 1
fi

ENVIRONMENT="$1"

echo "Building services for $ENVIRONMENT environment..."
docker-compose -f "environments/$ENVIRONMENT/docker-compose.yaml" build

echo "Starting services in detached mode for $ENVIRONMENT environment..."
docker-compose -f "environments/$ENVIRONMENT/docker-compose.yaml" up -d

echo "Running containers:"
docker-compose -f "environments/$ENVIRONMENT/docker-compose.yaml" ps