package com.supermario;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class Coin extends GameObject {
    
    private double animationTime;
    private double rotationAngle;
    
    public Coin(double x, double y, double size) {
        super(x, y, size, size);
        this.animationTime = 0;
        this.rotationAngle = 0;
    }
    
    @Override
    public void update(double deltaTime) {
        // 更新动画时间
        animationTime += deltaTime;
        
        // 旋转动画
        rotationAngle += 360 * deltaTime; // 每秒旋转360度
        if (rotationAngle >= 360) {
            rotationAngle -= 360;
        }
    }
    
    @Override
    public void render(Graphics2D g2d) {
        // 保存当前状态
        g2d.setTransform(g2d.getTransform());
        
        // 移动到金币中心
        g2d.translate(x + width / 2, y + height / 2);
        
        // 应用旋转
        g2d.rotate(Math.toRadians(rotationAngle));
        
        // 绘制金币
        drawCoin(g2d);
        
        // 恢复状态
        g2d.setTransform(g2d.getTransform());
    }
    
    private void drawCoin(Graphics2D g2d) {
        // 金币外圈（金色）
        g2d.setColor(new Color(255, 215, 0)); // GOLD
        g2d.fillOval((int)(-width / 2), (int)(-height / 2), (int)width, (int)height);
        
        // 金币内圈（深金色）
        g2d.setColor(new Color(184, 134, 11)); // DARKGOLDENROD
        g2d.fillOval((int)(-width * 0.4), (int)(-height * 0.4), (int)(width * 0.8), (int)(height * 0.8));
        
        // 金币中心（亮金色）
        g2d.setColor(Color.YELLOW);
        g2d.fillOval((int)(-width * 0.25), (int)(-height * 0.25), (int)(width * 0.5), (int)(height * 0.5));
        
        // 金币上的数字"1"
        g2d.setColor(new Color(139, 69, 19)); // BROWN
        g2d.setFont(new Font("Arial", Font.BOLD, (int)(width * 0.3)));
        g2d.drawString("1", (int)(-width * 0.1), (int)(height * 0.1));
        
        // 添加高光效果
        g2d.setColor(Color.WHITE);
        g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.3f));
        g2d.fillOval((int)(-width * 0.3), (int)(-height * 0.3), (int)(width * 0.2), (int)(height * 0.2));
        g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f));
    }
}