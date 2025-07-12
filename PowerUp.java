import java.awt.*;

public class PowerUp {
    private static final int SIZE = 20;
    private static final double FLOAT_SPEED = 0.1;
    
    private double x, y;
    private PowerUpType type;
    private double floatOffset;
    private int animationFrame;
    private long lastAnimationTime;
    
    public PowerUp(double x, double y, PowerUpType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.floatOffset = 0;
        this.animationFrame = 0;
        this.lastAnimationTime = System.currentTimeMillis();
    }
    
    public void update() {
        // 浮动动画
        floatOffset = Math.sin(System.currentTimeMillis() * FLOAT_SPEED) * 5;
        
        // 更新动画帧
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAnimationTime > 150) {
            animationFrame = (animationFrame + 1) % 4;
            lastAnimationTime = currentTime;
        }
    }
    
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        double drawY = y + floatOffset;
        
        switch (type) {
            case MUSHROOM:
                drawMushroom(g2d, drawY);
                break;
            case FLOWER:
                drawFlower(g2d, drawY);
                break;
        }
        
        g2d.dispose();
    }
    
    private void drawMushroom(Graphics2D g2d, double drawY) {
        // 绘制蘑菇柄
        g2d.setColor(Color.WHITE);
        g2d.fillRect((int)x + SIZE / 3, (int)drawY + SIZE / 2, SIZE / 3, SIZE / 2);
        
        // 绘制蘑菇帽
        g2d.setColor(Color.RED);
        g2d.fillOval((int)x, (int)drawY, SIZE, SIZE / 2);
        
        // 绘制蘑菇帽上的白点
        g2d.setColor(Color.WHITE);
        g2d.fillOval((int)x + SIZE / 4, (int)drawY + SIZE / 8, SIZE / 6, SIZE / 6);
        g2d.fillOval((int)x + SIZE * 2 / 3, (int)drawY + SIZE / 8, SIZE / 6, SIZE / 6);
        g2d.fillOval((int)x + SIZE / 3, (int)drawY + SIZE / 4, SIZE / 6, SIZE / 6);
        
        // 绘制蘑菇帽边框
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawOval((int)x, (int)drawY, SIZE, SIZE / 2);
    }
    
    private void drawFlower(Graphics2D g2d, double drawY) {
        // 绘制花朵中心
        g2d.setColor(Color.YELLOW);
        g2d.fillOval((int)x + SIZE / 4, (int)drawY + SIZE / 4, SIZE / 2, SIZE / 2);
        
        // 绘制花瓣
        g2d.setColor(Color.RED);
        int centerX = (int)x + SIZE / 2;
        int centerY = (int)drawY + SIZE / 2;
        int petalSize = SIZE / 3;
        
        // 四个方向的花瓣
        g2d.fillOval(centerX - petalSize, centerY - petalSize - 2, petalSize, petalSize);
        g2d.fillOval(centerX, centerY - petalSize - 2, petalSize, petalSize);
        g2d.fillOval(centerX - petalSize, centerY, petalSize, petalSize);
        g2d.fillOval(centerX, centerY, petalSize, petalSize);
        
        // 绘制花茎
        g2d.setColor(Color.GREEN);
        g2d.fillRect(centerX - 1, centerY + petalSize, 2, SIZE / 3);
        
        // 绘制叶子
        g2d.fillOval(centerX - 3, centerY + petalSize + 2, 6, 4);
        g2d.fillOval(centerX - 3, centerY + petalSize + 8, 6, 4);
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, SIZE, SIZE);
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public PowerUpType getType() { return type; }
}