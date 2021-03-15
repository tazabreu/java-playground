#!/bin/bash

version="NOT_SET"

while getopts ":v:" opt; do
  case $opt in
  v)
    if [ -n $OPTARG ]; then
      version=$OPTARG
    fi
    ;;
  \?)
    echo "Invalid option -$OPTARG" >&2
    ;;
  esac
done

echo "Version is: $version"

sudo docker build -t spring-rest-api-$version .
