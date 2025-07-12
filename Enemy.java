import java.awt.*;

public class Enemy {
    private int x, y;
    private int width = 25;
    private int height = 25;
    private double velocityX = -1.0;
    private boolean movingLeft = true;
    private int animationFrame = 0;
    
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update() {
        // 移动
        x += velocityX;
        
        // 边界检查，改变方向
        if (x <= 0 || x >= 775) { // 800 - width
            velocityX = -velocityX;
            movingLeft = !movingLeft;
        }
        
        // 动画帧更新
        animationFrame++;
        if (animationFrame > 30) {
            animationFrame = 0;
        }
    }
    
    public void draw(Graphics2D g2d) {
        // 绘制蘑菇怪身体
        g2d.setColor(Color.RED);
        g2d.fillOval(x, y, width, height);
        
        // 绘制蘑菇斑点
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x + 5, y + 5, 4, 4);
        g2d.fillOval(x + 15, y + 8, 3, 3);
        g2d.fillOval(x + 8, y + 15, 3, 3);
        
        // 绘制眼睛
        g2d.setColor(Color.BLACK);
        if (movingLeft) {
            g2d.fillOval(x + 5, y + 8, 3, 3);
            g2d.fillOval(x + 15, y + 8, 3, 3);
        } else {
            g2d.fillOval(x + 5, y + 8, 3, 3);
            g2d.fillOval(x + 15, y + 8, 3, 3);
        }
        
        // 绘制嘴巴
        g2d.setColor(Color.BLACK);
        g2d.drawLine(x + 8, y + 18, x + 12, y + 18);
        
        // 绘制脚部
        g2d.setColor(new Color(139, 69, 19)); // 棕色
        g2d.fillRect(x + 3, y + 20, 6, 5);
        g2d.fillRect(x + 16, y + 20, 6, 5);
        
        // 简单的行走动画
        if (animationFrame < 15) {
            g2d.fillRect(x + 3, y + 22, 6, 3);
        } else {
            g2d.fillRect(x + 16, y + 22, 6, 3);
        }
    }
    
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}