#!/usr/bin/env bash

declare -a tests=(Insert_Search_Test_Small Insert_Search_Test_Large Insert_Delete_Search_Test_Small Insert_Delete_Search_Test_Large Insert_toString_Test);

for file in $1/*
do
    cp -r col106_backup col106
    cp -n "$file"/*.java col106/a3 2> /dev/null
    cp -n "$file"/col106/a3/*.java col106/a3 2> /dev/null
    cp -n "$file"/COL106_A3/src/col106/a3/*.java col106/a3/ 2> /dev/null
    cp -n "$file"/*/COL106_A3/src/col106/a3/*.java col106/a3/ 2> /dev/null
    echo "$file" >> $2
    echo "$file"
    echo "Compiling" >> $2
    javac col106/a3/*.java col106/a3/ta/*.java 2>> $2
    echo >> $2
    echo "Testing" >> $2
    for test in "${tests[@]}"
    do
        echo "$test" >> $2
        timeout 4s java col106/a3/ta/$test >> $2 2>>$2
        echo >> $2
    done
    echo "JAR Test" >> $2
    cp -n "$file"/*.jar . 2> /dev/null
    cp -n "$file"/*/*.jar . 2> /dev/null
    timeout 1s java -jar *.jar < input.txt >> $2 2>>$2
    rm -r col106
    rm *.jar 2> /dev/null
    echo "###################################################################" >> $2
    echo >>$2
    echo >>$2
done
