#!/bin/bash

# Taken from https://stackoverflow.com/a/38622819

if [ $# -eq 0 ]
then
    echo "Usage: $(basename $0) path/to/file [<diff-options>]"
    exit 1
fi

file="$1"
shift 1

for rev in $(git log --pretty=%H -- "$file")
do
    if git diff --exit-code $@ $rev -- $file &> /dev/null
    then
        echo $rev
    fi
done

