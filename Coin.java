import java.awt.*;

public class Coin {
    private int x, y;
    private int width = 15;
    private int height = 15;
    private int animationFrame = 0;
    private double bounceOffset = 0;
    private double bounceSpeed = 0.1;
    
    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update() {
        // 动画帧更新
        animationFrame++;
        if (animationFrame > 60) {
            animationFrame = 0;
        }
        
        // 弹跳效果
        bounceOffset = Math.sin(animationFrame * bounceSpeed) * 3;
    }
    
    public void draw(Graphics2D g2d) {
        // 保存当前变换
        Graphics2D g2 = (Graphics2D) g2d.create();
        
        // 应用弹跳效果
        g2.translate(x + width/2, y + height/2 + bounceOffset);
        
        // 旋转动画
        double rotation = (animationFrame * 6) * Math.PI / 180; // 6度每帧
        g2.rotate(rotation);
        
        // 绘制金币
        g2.setColor(Color.YELLOW);
        g2.fillOval(-width/2, -height/2, width, height);
        
        // 绘制金币内部
        g2.setColor(Color.ORANGE);
        g2.fillOval(-width/2 + 2, -height/2 + 2, width - 4, height - 4);
        
        // 绘制金币中心
        g2.setColor(Color.YELLOW);
        g2.fillOval(-3, -3, 6, 6);
        
        // 绘制"$"符号
        g2.setColor(new Color(139, 69, 19)); // 棕色
        g2.setFont(new Font("Arial", Font.BOLD, 8));
        FontMetrics fm = g2.getFontMetrics();
        String dollarSign = "$";
        int textX = -fm.stringWidth(dollarSign) / 2;
        int textY = fm.getAscent() / 2;
        g2.drawString(dollarSign, textX, textY);
        
        // 恢复变换
        g2.dispose();
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