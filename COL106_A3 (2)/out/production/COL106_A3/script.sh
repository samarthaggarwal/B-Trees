#!/usr/bin/env bash

declare -a tests=(StressTest);

for file in $1/*
do
    cp -r col106_backup col106
    cp -n "$file"/COL106_A3/src/col106/a3/*.java col106/a3/ 2> /dev/null
    cp -n "$file"/"$file"/COL106_A3/src/col106/a3/*.java col106/a3/ 2> dev/null
    javac col106/a3/*.java col106/a3/ta/*.java
    echo "$file" >> $2
    for test in "${tests[@]}"
    do
        echo "$test" >> $2
        timeout 1s java col106/a3/ta/$test >> $2
    done
    rm -r col106
done
