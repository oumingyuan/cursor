package com.supermario;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld {
    private Pane worldPane;
    private List<Platform> platforms;
    private List<Enemy> enemies;
    private List<Coin> coins;
    private int worldWidth, worldHeight, tileSize;
    
    public GameWorld(int width, int height, int tileSize) {
        this.worldWidth = width;
        this.worldHeight = height;
        this.tileSize = tileSize;
        
        this.worldPane = new Pane();
        this.platforms = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.coins = new ArrayList<>();
        
        createWorld();
    }
    
    private void createWorld() {
        // 创建地面
        createGround();
        
        // 创建平台
        createPlatforms();
        
        // 创建敌人
        createEnemies();
        
        // 创建金币
        createCoins();
    }
    
    private void createGround() {
        // 创建地面平台
        for (int x = 0; x < worldWidth; x += tileSize) {
            Platform ground = new Platform(x, worldHeight - tileSize, tileSize, tileSize);
            platforms.add(ground);
            worldPane.getChildren().add(ground.getSprite());
        }
    }
    
    private void createPlatforms() {
        // 创建一些浮动平台
        int[][] platformPositions = {
            {200, 450}, {300, 400}, {400, 350}, {500, 400}, {600, 450},
            {800, 350}, {900, 300}, {1000, 350},
            {300, 250}, {400, 200}, {500, 250},
            {700, 200}, {800, 150}, {900, 200}
        };
        
        for (int[] pos : platformPositions) {
            Platform platform = new Platform(pos[0], pos[1], tileSize * 2, tileSize);
            platforms.add(platform);
            worldPane.getChildren().add(platform.getSprite());
        }
    }
    
    private void createEnemies() {
        // 创建敌人
        int[][] enemyPositions = {
            {400, worldHeight - tileSize - 32},
            {600, worldHeight - tileSize - 32},
            {800, worldHeight - tileSize - 32},
            {300, 400 - 32},
            {500, 400 - 32},
            {900, 300 - 32}
        };
        
        for (int[] pos : enemyPositions) {
            Enemy enemy = new Enemy(pos[0], pos[1], tileSize);
            enemies.add(enemy);
            worldPane.getChildren().add(enemy.getSprite());
        }
    }
    
    private void createCoins() {
        // 创建金币
        int[][] coinPositions = {
            {250, 400}, {350, 350}, {450, 300}, {550, 350}, {650, 400},
            {750, 300}, {850, 250}, {950, 300},
            {350, 200}, {450, 150}, {550, 200},
            {750, 150}, {850, 100}, {950, 150}
        };
        
        for (int[] pos : coinPositions) {
            Coin coin = new Coin(pos[0], pos[1], tileSize / 2);
            coins.add(coin);
            worldPane.getChildren().add(coin.getSprite());
        }
    }
    
    public void update() {
        // 更新敌人
        for (Enemy enemy : enemies) {
            enemy.update();
        }
        
        // 更新金币动画
        for (Coin coin : coins) {
            coin.update();
        }
    }
    
    public void checkPlatformCollisions(Mario mario) {
        boolean onGround = false;
        
        for (Platform platform : platforms) {
            if (mario.intersects(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight())) {
                // 检查是否从上方碰撞（着陆）
                if (mario.intersectsTop(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight())) {
                    mario.setY(platform.getY() - mario.getHeight());
                    mario.setOnGround(true);
                    onGround = true;
                }
                // 检查是否从下方碰撞（撞头）
                else if (mario.intersectsBottom(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight())) {
                    mario.setY(platform.getY() + platform.getHeight());
                    mario.setVelocity(mario.getVelocityX(), 0);
                }
                // 检查左右碰撞
                else {
                    if (mario.getX() < platform.getX()) {
                        mario.setX(platform.getX() - mario.getWidth());
                    } else {
                        mario.setX(platform.getX() + platform.getWidth());
                    }
                    mario.setVelocity(0, mario.getVelocityY());
                }
            }
        }
        
        if (!onGround) {
            mario.setOnGround(false);
        }
    }
    
    public boolean checkEnemyCollisions(Mario mario) {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (mario.intersects(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight())) {
                // 检查是否从上方踩到敌人
                if (mario.intersectsTop(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight()) && 
                    mario.getVelocityY() > 0) {
                    // 消灭敌人
                    worldPane.getChildren().remove(enemy.getSprite());
                    iterator.remove();
                    // 给马里奥一个向上的速度
                    mario.setVelocity(mario.getVelocityX(), -10);
                    return false; // 没有受伤
                } else {
                    // 被敌人伤害
                    return true;
                }
            }
        }
        return false;
    }
    
    public int checkCoinCollisions(Mario mario) {
        int coinsCollected = 0;
        Iterator<Coin> iterator = coins.iterator();
        
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            if (mario.intersects(coin.getX(), coin.getY(), coin.getWidth(), coin.getHeight())) {
                // 收集金币
                worldPane.getChildren().remove(coin.getSprite());
                iterator.remove();
                coinsCollected++;
            }
        }
        
        return coinsCollected;
    }
    
    public Pane getWorldPane() {
        return worldPane;
    }
    
    // 内部类：平台
    private static class Platform {
        private double x, y, width, height;
        private Rectangle sprite;
        
        public Platform(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            
            sprite = new Rectangle(width, height);
            sprite.setFill(Color.GREEN);
            sprite.setStroke(Color.DARKGREEN);
            sprite.setStrokeWidth(2);
            sprite.setX(x);
            sprite.setY(y);
        }
        
        public double getX() { return x; }
        public double getY() { return y; }
        public double getWidth() { return width; }
        public double getHeight() { return height; }
        public Rectangle getSprite() { return sprite; }
    }
    
    // 内部类：敌人
    private static class Enemy {
        private double x, y, width, height;
        private double velocityX;
        private Rectangle sprite;
        private double moveDistance;
        private double startX;
        
        public Enemy(double x, double y, double size) {
            this.x = x;
            this.y = y;
            this.width = size;
            this.height = size;
            this.velocityX = -1.0;
            this.moveDistance = 100;
            this.startX = x;
            
            sprite = new Rectangle(width, height);
            sprite.setFill(Color.BROWN);
            sprite.setStroke(Color.DARKGOLDENROD);
            sprite.setStrokeWidth(2);
            sprite.setX(x);
            sprite.setY(y);
        }
        
        public void update() {
            x += velocityX;
            
            // 简单的巡逻行为
            if (x <= startX - moveDistance || x >= startX + moveDistance) {
                velocityX = -velocityX;
            }
            
            sprite.setX(x);
        }
        
        public double getX() { return x; }
        public double getY() { return y; }
        public double getWidth() { return width; }
        public double getHeight() { return height; }
        public Rectangle getSprite() { return sprite; }
    }
    
    // 内部类：金币
    private static class Coin {
        private double x, y, width, height;
        private Circle sprite;
        private double animationTime;
        
        public Coin(double x, double y, double size) {
            this.x = x;
            this.y = y;
            this.width = size * 2;
            this.height = size * 2;
            this.animationTime = 0;
            
            sprite = new Circle(size);
            sprite.setFill(Color.YELLOW);
            sprite.setStroke(Color.GOLD);
            sprite.setStrokeWidth(2);
            sprite.setCenterX(x + size);
            sprite.setCenterY(y + size);
        }
        
        public void update() {
            animationTime += 0.1;
            // 简单的浮动动画
            sprite.setCenterY(y + width/2 + Math.sin(animationTime) * 3);
        }
        
        public double getX() { return x; }
        public double getY() { return y; }
        public double getWidth() { return width; }
        public double getHeight() { return height; }
        public Circle getSprite() { return sprite; }
    }
}