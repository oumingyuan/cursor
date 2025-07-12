package com.supermario;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;

public class Mario {
    private double x, y;
    private double velocityX, velocityY;
    private double width, height;
    private Rectangle sprite;
    private boolean isOnGround;
    private boolean isMovingLeft, isMovingRight;
    private boolean isJumping;
    
    // 物理常量
    private static final double GRAVITY = 0.8;
    private static final double JUMP_VELOCITY = -15.0;
    private static final double MOVE_SPEED = 5.0;
    private static final double FRICTION = 0.8;
    
    public Mario(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isOnGround = false;
        this.isMovingLeft = false;
        this.isMovingRight = false;
        this.isJumping = false;
        
        createSprite();
    }
    
    private void createSprite() {
        // 创建马里奥的身体（红色矩形）
        sprite = new Rectangle(width, height);
        sprite.setFill(Color.RED);
        sprite.setStroke(Color.DARKRED);
        sprite.setStrokeWidth(2);
        
        // 添加帽子（红色圆形）
        Circle hat = new Circle(width / 2, height / 4, width / 3);
        hat.setFill(Color.RED);
        hat.setStroke(Color.DARKRED);
        hat.setStrokeWidth(1);
        
        // 添加眼睛（白色圆形）
        Circle leftEye = new Circle(width / 3, height / 3, width / 8);
        leftEye.setFill(Color.WHITE);
        Circle rightEye = new Circle(2 * width / 3, height / 3, width / 8);
        rightEye.setFill(Color.WHITE);
        
        // 添加瞳孔（黑色圆形）
        Circle leftPupil = new Circle(width / 3, height / 3, width / 16);
        leftPupil.setFill(Color.BLACK);
        Circle rightPupil = new Circle(2 * width / 3, height / 3, width / 16);
        rightPupil.setFill(Color.BLACK);
        
        // 添加胡子（棕色矩形）
        Rectangle mustache = new Rectangle(width * 0.6, height / 8);
        mustache.setFill(Color.BROWN);
        mustache.setX(width * 0.2);
        mustache.setY(height * 0.6);
        
        // 将所有元素添加到精灵中
        sprite.getChildren().addAll(hat, leftEye, rightEye, leftPupil, rightPupil, mustache);
        
        updateSpritePosition();
    }
    
    public void update() {
        // 应用重力
        if (!isOnGround) {
            velocityY += GRAVITY;
        }
        
        // 应用摩擦力
        if (isOnGround) {
            velocityX *= FRICTION;
        }
        
        // 更新位置
        x += velocityX;
        y += velocityY;
        
        // 限制水平速度
        if (velocityX > MOVE_SPEED) {
            velocityX = MOVE_SPEED;
        } else if (velocityX < -MOVE_SPEED) {
            velocityX = -MOVE_SPEED;
        }
        
        // 更新精灵位置
        updateSpritePosition();
        
        // 重置地面状态
        isOnGround = false;
    }
    
    private void updateSpritePosition() {
        sprite.setX(x);
        sprite.setY(y);
    }
    
    public void moveLeft() {
        isMovingLeft = true;
        isMovingRight = false;
        velocityX = -MOVE_SPEED;
    }
    
    public void moveRight() {
        isMovingRight = true;
        isMovingLeft = false;
        velocityX = MOVE_SPEED;
    }
    
    public void jump() {
        if (isOnGround && !isJumping) {
            velocityY = JUMP_VELOCITY;
            isJumping = true;
            isOnGround = false;
        }
    }
    
    public void stopMoving() {
        isMovingLeft = false;
        isMovingRight = false;
        velocityX = 0;
    }
    
    public void setOnGround(boolean onGround) {
        this.isOnGround = onGround;
        if (onGround) {
            isJumping = false;
            velocityY = 0;
        }
    }
    
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        updateSpritePosition();
    }
    
    public void setVelocity(double velocityX, double velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getVelocityX() { return velocityX; }
    public double getVelocityY() { return velocityY; }
    public Rectangle getSprite() { return sprite; }
    public boolean isOnGround() { return isOnGround; }
    
    // 碰撞检测方法
    public boolean intersects(double otherX, double otherY, double otherWidth, double otherHeight) {
        return x < otherX + otherWidth &&
               x + width > otherX &&
               y < otherY + otherHeight &&
               y + height > otherY;
    }
    
    public boolean intersectsTop(double otherX, double otherY, double otherWidth, double otherHeight) {
        return intersects(otherX, otherY, otherWidth, otherHeight) && velocityY > 0;
    }
    
    public boolean intersectsBottom(double otherX, double otherY, double otherWidth, double otherHeight) {
        return intersects(otherX, otherY, otherWidth, otherHeight) && velocityY < 0;
    }
}