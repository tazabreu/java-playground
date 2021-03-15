#!/bin/bash

version="NOT_SET"
spring_profile="default"

while getopts ":v:p:" opt; do
  case $opt in
  v)
    if [ -n $OPTARG ]; then
      version=$OPTARG
    fi
    ;;
  p)
    if [ -n $OPTARG ]; then
      spring_profile=$OPTARG
    fi
    ;;
  \?)
    echo "Invalid option -$OPTARG" >&2
    ;;
  esac
done

echo version is: $version
echo spring_profile is: $spring_profile

sudo docker container rm -f spring-rest-api-$version
sudo docker run --name=spring-rest-api-$version -e SPRING_PROFILE_ACTIVE=$spring_profile -p 8080:8080 spring-rest-api-$version
