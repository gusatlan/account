#!/bin/bash
./compile

docker build -t account-img:1.0 .
docker build -t account-img:latest .

./gradlew clean
rm *.log

