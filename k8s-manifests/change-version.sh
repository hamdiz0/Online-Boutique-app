#!/bin/bash

version="latest" # default the version to latest

# accept version with -v 
while getopts "v:" opt; do
  case $opt in
    v) version=$OPTARG;;
  esac
done

DIR=. # set the directory to the default "."

echo "changing k8s yaml files version ..."

for file in "$DIR"/*.y*ml; do
    if [ -f "$file" ]; then
        sed -i "s|image: 192.168.1.16:8082/\([^:]*\):.*|image: 192.168.1.16:8082/\1:$version|" $file # update the version
        echo "$file updated"
    else
        echo "no yaml files in : $DIR"
        break
    fi
done
