package com.supermario;

import java.awt.Graphics2D;
import java.awt.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld {
    
    private int width;
    private int height;
    private int tileSize;
    
    private Mario mario;
    private List<Enemy> enemies;
    private List<Coin> coins;
    private List<Platform> platforms;
    private List<GameObject> gameObjects;
    
    private int score;
    private int lives;
    private boolean gameOver;
    private boolean levelComplete;
    
    private double cameraX;
    
    public GameWorld(int width, int height, int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        
        this.enemies = new ArrayList<>();
        this.coins = new ArrayList<>();
        this.platforms = new ArrayList<>();
        this.gameObjects = new ArrayList<>();
        
        this.score = 0;
        this.lives = 3;
        this.gameOver = false;
        this.levelComplete = false;
        this.cameraX = 0;
        
        initializeLevel();
    }
    
    private void initializeLevel() {
        // 创建马里奥
        mario = new Mario(100, height - 200, tileSize);
        
        // 创建平台
        createPlatforms();
        
        // 创建敌人
        createEnemies();
        
        // 创建金币
        createCoins();
    }
    
    private void createPlatforms() {
        // 地面
        for (int x = 0; x < width + 1000; x += tileSize) {
            platforms.add(new Platform(x, height - tileSize, tileSize, tileSize));
        }
        
        // 一些浮动平台
        platforms.add(new Platform(300, height - 150, 200, 20));
        platforms.add(new Platform(600, height - 200, 150, 20));
        platforms.add(new Platform(900, height - 250, 200, 20));
        platforms.add(new Platform(1200, height - 300, 150, 20));
        platforms.add(new Platform(1500, height - 200, 200, 20));
        platforms.add(new Platform(1800, height - 150, 150, 20));
        
        // 添加平台到游戏对象列表
        gameObjects.addAll(platforms);
    }
    
    private void createEnemies() {
        enemies.add(new Enemy(400, height - tileSize - 32, tileSize));
        enemies.add(new Enemy(700, height - tileSize - 32, tileSize));
        enemies.add(new Enemy(1100, height - tileSize - 32, tileSize));
        enemies.add(new Enemy(1400, height - tileSize - 32, tileSize));
        enemies.add(new Enemy(1700, height - tileSize - 32, tileSize));
        
        gameObjects.addAll(enemies);
    }
    
    private void createCoins() {
        // 在平台上放置金币
        coins.add(new Coin(350, height - 180, tileSize / 2));
        coins.add(new Coin(650, height - 230, tileSize / 2));
        coins.add(new Coin(950, height - 280, tileSize / 2));
        coins.add(new Coin(1250, height - 330, tileSize / 2));
        coins.add(new Coin(1550, height - 230, tileSize / 2));
        coins.add(new Coin(1850, height - 180, tileSize / 2));
        
        // 地面上的金币
        coins.add(new Coin(500, height - tileSize - 16, tileSize / 2));
        coins.add(new Coin(800, height - tileSize - 16, tileSize / 2));
        coins.add(new Coin(1300, height - tileSize - 16, tileSize / 2));
        coins.add(new Coin(1600, height - tileSize - 16, tileSize / 2));
        
        gameObjects.addAll(coins);
    }
    
    public void update(double deltaTime) {
        if (gameOver || levelComplete) {
            return;
        }
        
        // 更新马里奥
        mario.update(deltaTime);
        
        // 更新敌人
        for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }
        
        // 更新金币动画
        for (Coin coin : coins) {
            coin.update(deltaTime);
        }
        
        // 碰撞检测
        checkCollisions();
        
        // 更新相机位置
        updateCamera();
        
        // 检查游戏状态
        checkGameState();
    }
    
    private void checkCollisions() {
        // 马里奥与平台的碰撞
        mario.setOnGround(false);
        for (Platform platform : platforms) {
            if (mario.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                handleMarioPlatformCollision(mario, platform);
            }
        }
        
        // 马里奥与敌人的碰撞
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (mario.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                if (mario.getVelocityY() > 0 && mario.getY() < enemy.getY()) {
                    // 马里奥踩到敌人
                    enemyIterator.remove();
                    gameObjects.remove(enemy);
                    score += 100;
                    mario.setVelocityY(-300); // 反弹
                } else {
                    // 马里奥被敌人伤害
                    lives--;
                    if (lives <= 0) {
                        gameOver = true;
                    } else {
                        // 重置马里奥位置
                        mario.setX(100);
                        mario.setY(height - 200);
                        mario.setVelocityX(0);
                        mario.setVelocityY(0);
                    }
                }
            }
        }
        
        // 马里奥与金币的碰撞
        Iterator<Coin> coinIterator = coins.iterator();
        while (coinIterator.hasNext()) {
            Coin coin = coinIterator.next();
            if (mario.getBoundsInParent().intersects(coin.getBoundsInParent())) {
                coinIterator.remove();
                gameObjects.remove(coin);
                score += 50;
            }
        }
    }
    
    private void handleMarioPlatformCollision(Mario mario, Platform platform) {
        double marioBottom = mario.getY() + mario.getHeight();
        double marioTop = mario.getY();
        double platformTop = platform.getY();
        double platformBottom = platform.getY() + platform.getHeight();
        
        // 从上方碰撞（站在平台上）
        if (marioBottom > platformTop && mario.getVelocityY() >= 0 && marioTop < platformTop) {
            mario.setY(platformTop - mario.getHeight());
            mario.setVelocityY(0);
            mario.setOnGround(true);
        }
        // 从下方碰撞
        else if (marioTop < platformBottom && mario.getVelocityY() < 0) {
            mario.setY(platformBottom);
            mario.setVelocityY(0);
        }
        // 从左侧碰撞
        else if (mario.getX() + mario.getWidth() > platform.getX() && 
                 mario.getX() < platform.getX()) {
            mario.setX(platform.getX() - mario.getWidth());
            mario.setVelocityX(0);
        }
        // 从右侧碰撞
        else if (mario.getX() < platform.getX() + platform.getWidth() && 
                 mario.getX() + mario.getWidth() > platform.getX() + platform.getWidth()) {
            mario.setX(platform.getX() + platform.getWidth());
            mario.setVelocityX(0);
        }
    }
    
    private void updateCamera() {
        // 相机跟随马里奥
        double targetCameraX = mario.getX() - width / 2;
        cameraX = Math.max(0, targetCameraX);
    }
    
    private void checkGameState() {
        // 检查马里奥是否掉出屏幕
        if (mario.getY() > height + 100) {
            lives--;
            if (lives <= 0) {
                gameOver = true;
            } else {
                // 重置马里奥位置
                mario.setX(100);
                mario.setY(height - 200);
                mario.setVelocityX(0);
                mario.setVelocityY(0);
            }
        }
        
        // 检查是否完成关卡（到达某个位置）
        if (mario.getX() > 2000) {
            levelComplete = true;
        }
    }
    
    public void render(Graphics2D g2d) {
        // 应用相机变换
        g2d.translate(-cameraX, 0);
        
        // 渲染背景
        renderBackground(g2d);
        
        // 渲染游戏对象
        for (GameObject obj : gameObjects) {
            obj.render(g2d);
        }
        
        // 渲染马里奥
        mario.render(g2d);
        
        // 恢复变换
        g2d.translate(cameraX, 0);
    }
    
    private void renderBackground(Graphics2D g2d) {
        // 简单的背景
        g2d.setColor(new Color(135, 206, 235)); // SKYBLUE
        g2d.fillRect(0, 0, width + 1000, height);
        
        // 云朵
        g2d.setColor(Color.WHITE);
        g2d.fillOval(100, 50, 60, 40);
        g2d.fillOval(400, 80, 80, 50);
        g2d.fillOval(700, 60, 70, 45);
        g2d.fillOval(1000, 90, 90, 55);
        g2d.fillOval(1300, 70, 75, 48);
        g2d.fillOval(1600, 85, 85, 52);
    }
    
    // Getters
    public Mario getMario() {
        return mario;
    }
    
    public int getScore() {
        return score;
    }
    
    public int getLives() {
        return lives;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public boolean isLevelComplete() {
        return levelComplete;
    }
}