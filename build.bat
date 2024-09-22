@echo off

if exists "build" (
	rd /s /q "build"
)

mkdir "build"

javac src\*.java -d build
