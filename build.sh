#!/bin/bash

if [ -d "build" ]; then
	rm -rf build
fi

mkdir build

javac src/*.java -d build
