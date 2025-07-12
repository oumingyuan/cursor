#!/bin/bash

# 检查Java版本
echo "检查Java版本..."
java -version

# 编译所有Java文件
echo "编译Java文件..."
javac -cp . src/main/java/com/supermario/*.java

# 运行游戏
echo "启动超级玛丽游戏..."
java -cp src/main/java com.supermario.SuperMarioGameSwing

echo "游戏结束"