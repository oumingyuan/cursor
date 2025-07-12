import java.awt.*;

public class Enemy {
    private static final int WIDTH = 25;
    private static final int HEIGHT = 25;
    private static final double MOVE_SPEED = 1.5;
    private static final int PATROL_DISTANCE = 100;
    
    private double x, y;
    private double velocityX;
    private double startX;
    private boolean movingRight;
    private int animationFrame;
    private long lastAnimationTime;
    
    public Enemy(double x, double y) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.velocityX = MOVE_SPEED;
        this.movingRight = true;
        this.animationFrame = 0;
        this.lastAnimationTime = System.currentTimeMillis();
    }
    
    public void update() {
        // 移动
        x += velocityX;
        
        // 巡逻行为
        if (movingRight && x > startX + PATROL_DISTANCE) {
            velocityX = -MOVE_SPEED;
            movingRight = false;
        } else if (!movingRight && x < startX - PATROL_DISTANCE) {
            velocityX = MOVE_SPEED;
            movingRight = true;
        }
        
        // 更新动画
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAnimationTime > 200) {
            animationFrame = (animationFrame + 1) % 4;
            lastAnimationTime = currentTime;
        }
    }
    
    public void draw(Graphics g) {
        // 绘制敌人身体
        g.setColor(new Color(139, 69, 19)); // 棕色
        g.fillOval((int)x, (int)y, WIDTH, HEIGHT);
        
        // 绘制眼睛
        g.setColor(Color.WHITE);
        g.fillOval((int)x + 5, (int)y + 5, 5, 5);
        g.fillOval((int)x + 15, (int)y + 5, 5, 5);
        
        // 绘制瞳孔
        g.setColor(Color.BLACK);
        g.fillOval((int)x + 6, (int)y + 6, 3, 3);
        g.fillOval((int)x + 16, (int)y + 6, 3, 3);
        
        // 绘制嘴巴
        g.setColor(Color.BLACK);
        g.fillOval((int)x + 8, (int)y + 15, 9, 5);
        
        // 绘制触角
        g.setColor(new Color(139, 69, 19)); // 棕色
        g.fillOval((int)x + 3, (int)y - 3, 4, 4);
        g.fillOval((int)x + 18, (int)y - 3, 4, 4);
        
        // 绘制腿（根据移动方向调整）
        g.setColor(new Color(139, 69, 19)); // 棕色
        if (movingRight) {
            g.fillRect((int)x - 2, (int)y + HEIGHT - 3, 3, 6);
            g.fillRect((int)x + WIDTH - 1, (int)y + HEIGHT - 3, 3, 6);
        } else {
            g.fillRect((int)x - 2, (int)y + HEIGHT - 3, 3, 6);
            g.fillRect((int)x + WIDTH - 1, (int)y + HEIGHT - 3, 3, 6);
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isMovingRight() { return movingRight; }
}