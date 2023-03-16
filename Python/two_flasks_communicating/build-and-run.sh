#!/bin/sh

docker build -t needs_the_validator -f needs_the_validator/Dockerfile .
docker build -t validator -f validator/Dockerfile .

docker network create app_network

docker run -d --rm --name needs_the_validator -p 5000:5000 --env APP_NAME=needs_the_validator --network app_network needs_the_validator
docker run -d --rm --name validator -p 5001:5001 --env APP_NAME=validator --network app_network validator
