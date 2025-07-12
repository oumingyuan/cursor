# Super Mario Game

一个使用Java和JavaFX开发的超级玛丽游戏。

## 游戏特性

- 🎮 经典的马里奥角色控制
- �‍♂️ 流畅的移动和跳跃机制
- � 敌人（蘑菇怪）AI
- 🪙 金币收集系统
- 🏗️ 平台跳跃关卡
- 📊 分数和生命系统
- � 简洁的图形界面

## 控制方式

- **A/左箭头**: 向左移动
- **D/右箭头**: 向右移动
- **W/上箭头/空格**: 跳跃

## 游戏目标

- 收集金币获得分数
- 踩踏敌人消灭它们
- 避免被敌人伤害
- 探索关卡中的所有平台

## 系统要求

- Java 11 或更高版本
- Maven 3.6 或更高版本

## 安装和运行

### 1. 克隆项目
```bash
git clone <repository-url>
cd super-mario-game
```

### 2. 编译项目
```bash
mvn clean compile
```

### 3. 运行游戏
```bash
mvn javafx:run
```

或者使用Java命令：
```bash
java -jar target/super-mario-game-1.0.0.jar
```

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── supermario/
│   │           ├── SuperMarioGame.java    # 主游戏类
│   │           ├── Mario.java             # 马里奥角色类
│   │           ├── GameWorld.java         # 游戏世界类
│   │           └── GameState.java         # 游戏状态管理
│   └── resources/
│       ├── assets/                        # 游戏资源文件
│       └── sounds/                        # 音效文件
```

## 技术栈

- **Java 11**: 主要编程语言
- **JavaFX**: 图形用户界面框架
- **Maven**: 项目构建工具

## 游戏机制

### 物理系统
- 重力系统
- 碰撞检测
- 摩擦力

### 敌人AI
- 简单的巡逻行为
- 自动转向

### 收集系统
- 金币收集
- 分数计算

## 开发计划

- [ ] 添加更多关卡
- [ ] 实现音效系统
- [ ] 添加更多敌人类型
- [ ] 实现存档功能
- [ ] 添加背景音乐
- [ ] 优化图形效果

## 贡献

欢迎提交Issue和Pull Request来改进这个项目！

## 许可证

MIT License
