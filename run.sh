#!/bin/bash

echo "编译超级玛丽游戏..."
javac *.java

if [ $? -eq 0 ]; then
    echo "编译成功！启动游戏..."
    java SuperMario
else
    echo "编译失败，请检查错误信息。"
    exit 1
fi