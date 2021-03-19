@echo off

chcp 65001 >NUL:

if exist test.log (
    del test.log
)

cmd /c .\gradlew test 2>NUL:

if exist test.log (
    cmd /c type test.log
)
