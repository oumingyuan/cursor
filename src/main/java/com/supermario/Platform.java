package com.supermario;

import java.awt.Graphics2D;
import java.awt.Color;

public class Platform extends GameObject {
    
    public Platform(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    
    @Override
    public void update(double deltaTime) {
        // 平台不需要更新
    }
    
    @Override
    public void render(Graphics2D g2d) {
        // 绘制平台
        drawPlatform(g2d);
    }
    
    private void drawPlatform(Graphics2D g2d) {
        // 平台主体（棕色）
        g2d.setColor(new Color(139, 69, 19)); // BROWN
        g2d.fillRect((int)x, (int)y, (int)width, (int)height);
        
        // 平台边缘（深棕色）
        g2d.setColor(new Color(101, 67, 33)); // DARKBROWN
        g2d.fillRect((int)x, (int)y, (int)width, (int)(height * 0.1));
        g2d.fillRect((int)x, (int)(y + height * 0.9), (int)width, (int)(height * 0.1));
        g2d.fillRect((int)x, (int)y, (int)(width * 0.1), (int)height);
        g2d.fillRect((int)(x + width * 0.9), (int)y, (int)(width * 0.1), (int)height);
        
        // 平台纹理（木纹效果）
        g2d.setColor(new Color(160, 82, 45)); // SADDLEBROWN
        for (int i = 0; i < width; i += 10) {
            g2d.fillRect((int)x + i, (int)y, 2, (int)height);
        }
        
        // 添加一些装饰性的钉子
        g2d.setColor(Color.BLACK);
        g2d.fillOval((int)(x + width * 0.1), (int)(y + height * 0.5), 3, 3);
        g2d.fillOval((int)(x + width * 0.3), (int)(y + height * 0.5), 3, 3);
        g2d.fillOval((int)(x + width * 0.5), (int)(y + height * 0.5), 3, 3);
        g2d.fillOval((int)(x + width * 0.7), (int)(y + height * 0.5), 3, 3);
        g2d.fillOval((int)(x + width * 0.9), (int)(y + height * 0.5), 3, 3);
    }
}