package com.supermario;

import java.awt.Graphics2D;
import java.awt.Color;

public class Enemy extends GameObject {
    
    private static final double MOVE_SPEED = 50.0;
    private static final double PATROL_DISTANCE = 100.0;
    
    private double startX;
    private double velocityX;
    private boolean movingRight;
    private double animationTime;
    private int animationFrame;
    
    public Enemy(double x, double y, double size) {
        super(x, y, size, size);
        this.startX = x;
        this.velocityX = MOVE_SPEED;
        this.movingRight = true;
        this.animationTime = 0;
        this.animationFrame = 0;
    }
    
    @Override
    public void update(double deltaTime) {
        // 巡逻移动
        if (movingRight) {
            velocityX = MOVE_SPEED;
            if (x > startX + PATROL_DISTANCE) {
                movingRight = false;
            }
        } else {
            velocityX = -MOVE_SPEED;
            if (x < startX - PATROL_DISTANCE) {
                movingRight = true;
            }
        }
        
        // 更新位置
        x += velocityX * deltaTime;
        
        // 更新动画
        updateAnimation(deltaTime);
    }
    
    private void updateAnimation(double deltaTime) {
        animationTime += deltaTime;
        
        // 每0.2秒切换动画帧
        if (animationTime >= 0.2) {
            animationTime = 0;
            animationFrame = (animationFrame + 1) % 2;
        }
    }
    
    @Override
    public void render(Graphics2D g2d) {
        // 绘制蘑菇怪
        drawGoomba(g2d);
    }
    
    private void drawGoomba(Graphics2D g2d) {
        // 身体（棕色圆形）
        g2d.setColor(new Color(139, 69, 19)); // BROWN
        g2d.fillOval((int)(x + width * 0.1), (int)(y + height * 0.2), (int)(width * 0.8), (int)(height * 0.6));
        
        // 眼睛（白色背景）
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int)(x + width * 0.25), (int)(y + height * 0.3), (int)(width * 0.15), (int)(height * 0.15));
        g2d.fillOval((int)(x + width * 0.6), (int)(y + height * 0.3), (int)(width * 0.15), (int)(height * 0.15));
        
        // 瞳孔（黑色）
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)(x + width * 0.28), (int)(y + height * 0.33), (int)(width * 0.09), (int)(height * 0.09));
        g2d.fillOval((int)(x + width * 0.63), (int)(y + height * 0.33), (int)(width * 0.09), (int)(height * 0.09));
        
        // 眉毛（愤怒表情）
        g2d.setColor(new Color(139, 69, 19)); // BROWN
        g2d.fillRect((int)(x + width * 0.2), (int)(y + height * 0.25), (int)(width * 0.25), (int)(height * 0.03));
        g2d.fillRect((int)(x + width * 0.55), (int)(y + height * 0.25), (int)(width * 0.25), (int)(height * 0.03));
        
        // 嘴巴
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)(x + width * 0.4), (int)(y + height * 0.5), (int)(width * 0.2), (int)(height * 0.1));
        
        // 腿
        g2d.setColor(new Color(139, 69, 19)); // BROWN
        g2d.fillRect((int)(x + width * 0.2), (int)(y + height * 0.8), (int)(width * 0.15), (int)(height * 0.2));
        g2d.fillRect((int)(x + width * 0.65), (int)(y + height * 0.8), (int)(width * 0.15), (int)(height * 0.2));
        
        // 脚
        g2d.setColor(new Color(101, 67, 33)); // DARKBROWN
        g2d.fillOval((int)(x + width * 0.15), (int)(y + height * 0.95), (int)(width * 0.25), (int)(height * 0.05));
        g2d.fillOval((int)(x + width * 0.6), (int)(y + height * 0.95), (int)(width * 0.25), (int)(height * 0.05));
        
        // 动画效果 - 移动时的腿部摆动
        if (Math.abs(velocityX) > 0) {
            double legOffset = Math.sin(animationFrame * Math.PI) * 3;
            g2d.setColor(new Color(139, 69, 19)); // BROWN
            g2d.fillRect((int)(x + width * 0.2), (int)(y + height * 0.8 + legOffset), (int)(width * 0.15), (int)(height * 0.2));
            g2d.fillRect((int)(x + width * 0.65), (int)(y + height * 0.8 - legOffset), (int)(width * 0.15), (int)(height * 0.2));
        }
    }
    
    // Getters and Setters
    public double getVelocityX() {
        return velocityX;
    }
    
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }
    
    public boolean isMovingRight() {
        return movingRight;
    }
    
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
}