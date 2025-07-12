package com.supermario;

import java.awt.Graphics2D;
import java.awt.Color;

public class Mario extends GameObject {
    
    private static final double MOVE_SPEED = 200.0;
    private static final double JUMP_VELOCITY = -400.0;
    private static final double GRAVITY = 800.0;
    
    private double velocityX;
    private double velocityY;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean onGround;
    private boolean facingRight;
    
    private double animationTime;
    private int animationFrame;
    
    public Mario(double x, double y, double size) {
        super(x, y, size, size);
        this.velocityX = 0;
        this.velocityY = 0;
        this.movingLeft = false;
        this.movingRight = false;
        this.onGround = false;
        this.facingRight = true;
        this.animationTime = 0;
        this.animationFrame = 0;
    }
    
    @Override
    public void update(double deltaTime) {
        // 处理水平移动
        if (movingLeft) {
            velocityX = -MOVE_SPEED;
            facingRight = false;
        } else if (movingRight) {
            velocityX = MOVE_SPEED;
            facingRight = true;
        } else {
            velocityX = 0;
        }
        
        // 应用重力
        if (!onGround) {
            velocityY += GRAVITY * deltaTime;
        }
        
        // 更新位置
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
        
        // 更新动画
        updateAnimation(deltaTime);
    }
    
    private void updateAnimation(double deltaTime) {
        animationTime += deltaTime;
        
        // 每0.1秒切换动画帧
        if (animationTime >= 0.1) {
            animationTime = 0;
            if (movingLeft || movingRight) {
                animationFrame = (animationFrame + 1) % 4;
            } else {
                animationFrame = 0;
            }
        }
    }
    
    @Override
    public void render(Graphics2D g2d) {
        // 保存当前状态
        g2d.setTransform(g2d.getTransform());
        
        // 如果面向左边，翻转图形
        if (!facingRight) {
            g2d.scale(-1, 1);
            g2d.translate(-x - width, 0);
        }
        
        // 绘制马里奥
        drawMario(g2d);
        
        // 恢复状态
        g2d.setTransform(g2d.getTransform());
    }
    
    private void drawMario(Graphics2D g2d) {
        // 帽子
        g2d.setColor(Color.RED);
        g2d.fillOval((int)(x + width * 0.2), (int)(y + height * 0.1), (int)(width * 0.6), (int)(height * 0.3));
        
        // 脸部
        g2d.setColor(new Color(255, 218, 185)); // PEACHPUFF
        g2d.fillOval((int)(x + width * 0.15), (int)(y + height * 0.25), (int)(width * 0.7), (int)(height * 0.5));
        
        // 眼睛
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)(x + width * 0.3), (int)(y + height * 0.35), (int)(width * 0.1), (int)(height * 0.1));
        g2d.fillOval((int)(x + width * 0.6), (int)(y + height * 0.35), (int)(width * 0.1), (int)(height * 0.1));
        
        // 鼻子
        g2d.setColor(Color.PINK);
        g2d.fillOval((int)(x + width * 0.45), (int)(y + height * 0.45), (int)(width * 0.1), (int)(height * 0.08));
        
        // 胡子
        g2d.setColor(new Color(139, 69, 19)); // BROWN
        g2d.fillOval((int)(x + width * 0.2), (int)(y + height * 0.5), (int)(width * 0.15), (int)(height * 0.05));
        g2d.fillOval((int)(x + width * 0.65), (int)(y + height * 0.5), (int)(width * 0.15), (int)(height * 0.05));
        
        // 身体
        g2d.setColor(Color.RED);
        g2d.fillRect((int)(x + width * 0.2), (int)(y + height * 0.6), (int)(width * 0.6), (int)(height * 0.3));
        
        // 手臂
        g2d.setColor(new Color(255, 218, 185)); // PEACHPUFF
        g2d.fillOval((int)(x + width * 0.1), (int)(y + height * 0.65), (int)(width * 0.15), (int)(height * 0.2));
        g2d.fillOval((int)(x + width * 0.75), (int)(y + height * 0.65), (int)(width * 0.15), (int)(height * 0.2));
        
        // 腿
        g2d.setColor(Color.BLUE);
        g2d.fillRect((int)(x + width * 0.25), (int)(y + height * 0.85), (int)(width * 0.2), (int)(height * 0.15));
        g2d.fillRect((int)(x + width * 0.55), (int)(y + height * 0.85), (int)(width * 0.2), (int)(height * 0.15));
        
        // 鞋子
        g2d.setColor(new Color(139, 69, 19)); // BROWN
        g2d.fillOval((int)(x + width * 0.2), (int)(y + height * 0.95), (int)(width * 0.3), (int)(height * 0.05));
        g2d.fillOval((int)(x + width * 0.5), (int)(y + height * 0.95), (int)(width * 0.3), (int)(height * 0.05));
        
        // 动画效果 - 移动时的手臂摆动
        if (movingLeft || movingRight) {
            g2d.setColor(new Color(255, 218, 185)); // PEACHPUFF
            double armOffset = Math.sin(animationFrame * 0.5) * 5;
            g2d.fillOval((int)(x + width * 0.1), (int)(y + height * 0.65 + armOffset), (int)(width * 0.15), (int)(height * 0.2));
            g2d.fillOval((int)(x + width * 0.75), (int)(y + height * 0.65 - armOffset), (int)(width * 0.15), (int)(height * 0.2));
        }
    }
    
    public void jump() {
        if (onGround) {
            velocityY = JUMP_VELOCITY;
            onGround = false;
        }
    }
    
    // Getters and Setters
    public double getVelocityX() {
        return velocityX;
    }
    
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }
    
    public double getVelocityY() {
        return velocityY;
    }
    
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
    
    public boolean isMovingLeft() {
        return movingLeft;
    }
    
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }
    
    public boolean isMovingRight() {
        return movingRight;
    }
    
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
    
    public boolean isOnGround() {
        return onGround;
    }
    
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
    
    public boolean isFacingRight() {
        return facingRight;
    }
    
    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }
}