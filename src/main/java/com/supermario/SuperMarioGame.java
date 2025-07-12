package com.supermario;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashSet;
import java.util.Set;

public class SuperMarioGame extends Application {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 600;
    private static final int TILE_SIZE = 32;
    
    private Pane gamePane;
    private Mario mario;
    private GameWorld gameWorld;
    private Set<KeyCode> pressedKeys;
    private GameState gameState;
    private Text scoreText;
    private Text livesText;
    private Text gameOverText;
    
    @Override
    public void start(Stage primaryStage) {
        // 初始化游戏组件
        gamePane = new Pane();
        pressedKeys = new HashSet<>();
        gameState = new GameState();
        
        // 创建游戏世界
        gameWorld = new GameWorld(WINDOW_WIDTH, WINDOW_HEIGHT, TILE_SIZE);
        
        // 创建马里奥
        mario = new Mario(100, WINDOW_HEIGHT - 100, TILE_SIZE);
        
        // 创建UI元素
        createUI();
        
        // 设置场景
        Scene scene = new Scene(gamePane, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setFill(Color.SKYBLUE);
        
        // 设置键盘事件
        setupKeyboardEvents(scene);
        
        // 设置舞台
        primaryStage.setTitle("Super Mario Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // 开始游戏循环
        startGameLoop();
    }
    
    private void createUI() {
        // 添加游戏世界到面板
        gamePane.getChildren().add(gameWorld.getWorldPane());
        
        // 添加马里奥到面板
        gamePane.getChildren().add(mario.getSprite());
        
        // 创建分数显示
        scoreText = new Text(10, 30, "Score: 0");
        scoreText.setFont(Font.font("Arial", 20));
        scoreText.setFill(Color.WHITE);
        scoreText.setStroke(Color.BLACK);
        scoreText.setStrokeWidth(1);
        gamePane.getChildren().add(scoreText);
        
        // 创建生命显示
        livesText = new Text(10, 60, "Lives: 3");
        livesText.setFont(Font.font("Arial", 20));
        livesText.setFill(Color.WHITE);
        livesText.setStroke(Color.BLACK);
        livesText.setStrokeWidth(1);
        gamePane.getChildren().add(livesText);
        
        // 创建游戏结束文本（初始隐藏）
        gameOverText = new Text(WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT / 2, "GAME OVER");
        gameOverText.setFont(Font.font("Arial", 40));
        gameOverText.setFill(Color.RED);
        gameOverText.setStroke(Color.BLACK);
        gameOverText.setStrokeWidth(2);
        gameOverText.setVisible(false);
        gamePane.getChildren().add(gameOverText);
    }
    
    private void setupKeyboardEvents(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            pressedKeys.add(event.getCode());
        });
        
        scene.setOnKeyReleased((KeyEvent event) -> {
            pressedKeys.remove(event.getCode());
        });
    }
    
    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameState.isGameRunning()) {
                    updateGame();
                    updateUI();
                }
            }
        };
        gameLoop.start();
    }
    
    private void updateGame() {
        // 处理输入
        handleInput();
        
        // 更新马里奥
        mario.update();
        
        // 更新游戏世界
        gameWorld.update();
        
        // 检查碰撞
        checkCollisions();
        
        // 检查游戏状态
        checkGameState();
    }
    
    private void handleInput() {
        if (pressedKeys.contains(KeyCode.LEFT) || pressedKeys.contains(KeyCode.A)) {
            mario.moveLeft();
        }
        if (pressedKeys.contains(KeyCode.RIGHT) || pressedKeys.contains(KeyCode.D)) {
            mario.moveRight();
        }
        if (pressedKeys.contains(KeyCode.SPACE) || pressedKeys.contains(KeyCode.UP) || pressedKeys.contains(KeyCode.W)) {
            mario.jump();
        }
    }
    
    private void checkCollisions() {
        // 检查马里奥与平台的碰撞
        gameWorld.checkPlatformCollisions(mario);
        
        // 检查马里奥与敌人的碰撞
        if (gameWorld.checkEnemyCollisions(mario)) {
            gameState.loseLife();
            if (gameState.getLives() <= 0) {
                gameState.gameOver();
                gameOverText.setVisible(true);
            } else {
                resetMarioPosition();
            }
        }
        
        // 检查马里奥与金币的碰撞
        int coinsCollected = gameWorld.checkCoinCollisions(mario);
        if (coinsCollected > 0) {
            gameState.addScore(coinsCollected * 100);
        }
    }
    
    private void checkGameState() {
        // 检查马里奥是否掉出屏幕
        if (mario.getY() > WINDOW_HEIGHT) {
            gameState.loseLife();
            if (gameState.getLives() <= 0) {
                gameState.gameOver();
                gameOverText.setVisible(true);
            } else {
                resetMarioPosition();
            }
        }
    }
    
    private void resetMarioPosition() {
        mario.setPosition(100, WINDOW_HEIGHT - 100);
        mario.setVelocity(0, 0);
    }
    
    private void updateUI() {
        scoreText.setText("Score: " + gameState.getScore());
        livesText.setText("Lives: " + gameState.getLives());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}