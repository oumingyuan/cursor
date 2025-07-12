import java.awt.*;

public class Coin {
    private static final int SIZE = 15;
    private static final double ROTATION_SPEED = 0.2;
    
    private double x, y;
    private double rotation;
    private int animationFrame;
    private long lastAnimationTime;
    
    public Coin(double x, double y) {
        this.x = x;
        this.y = y;
        this.rotation = 0;
        this.animationFrame = 0;
        this.lastAnimationTime = System.currentTimeMillis();
    }
    
    public void update() {
        // 旋转动画
        rotation += ROTATION_SPEED;
        if (rotation > Math.PI * 2) {
            rotation -= Math.PI * 2;
        }
        
        // 上下浮动动画
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAnimationTime > 100) {
            animationFrame = (animationFrame + 1) % 8;
            lastAnimationTime = currentTime;
        }
    }
    
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        // 设置抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 计算浮动偏移
        double floatOffset = Math.sin(animationFrame * 0.5) * 3;
        
        // 移动到金币位置
        g2d.translate(x + SIZE / 2, y + SIZE / 2 + floatOffset);
        
        // 应用旋转
        g2d.rotate(rotation);
        
        // 绘制金币
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(-SIZE / 2, -SIZE / 2, SIZE, SIZE);
        
        // 绘制金币边框
        g2d.setColor(Color.ORANGE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(-SIZE / 2, -SIZE / 2, SIZE, SIZE);
        
        // 绘制金币内部图案
        g2d.setColor(Color.ORANGE);
        g2d.fillOval(-SIZE / 3, -SIZE / 3, SIZE * 2 / 3, SIZE * 2 / 3);
        
        // 绘制中心图案
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(-SIZE / 4, -SIZE / 4, SIZE / 2, SIZE / 2);
        
        // 绘制"$"符号
        g2d.setColor(Color.ORANGE);
        g2d.setFont(new Font("Arial", Font.BOLD, SIZE / 2));
        FontMetrics fm = g2d.getFontMetrics();
        String symbol = "$";
        int textX = -fm.stringWidth(symbol) / 2;
        int textY = fm.getAscent() / 2;
        g2d.drawString(symbol, textX, textY);
        
        g2d.dispose();
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, SIZE, SIZE);
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
}