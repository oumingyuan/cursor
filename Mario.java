import java.awt.*;

public class Mario {
    private int x, y;
    private int width = 30;
    private int height = 40;
    private double velocityX = 0;
    private double velocityY = 0;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean onGround = false;
    private boolean jumping = false;
    private boolean invulnerable = false;
    private int invulnerableTimer = 0;
    
    private static final double MOVE_SPEED = 5.0;
    private static final double JUMP_SPEED = -15.0;
    private static final double GRAVITY = 0.8;
    private static final double FRICTION = 0.8;
    private static final int INVULNERABLE_DURATION = 60; // 60 frames = 1 second
    
    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update() {
        // 处理水平移动
        if (movingLeft) {
            velocityX = -MOVE_SPEED;
        } else if (movingRight) {
            velocityX = MOVE_SPEED;
        } else {
            velocityX *= FRICTION;
        }
        
        // 应用重力
        if (!onGround) {
            velocityY += GRAVITY;
        }
        
        // 更新位置
        x += velocityX;
        y += velocityY;
        
        // 边界检查
        if (x < 0) x = 0;
        if (x > 770) x = 770; // 800 - width
        
        // 无敌时间更新
        if (invulnerable) {
            invulnerableTimer--;
            if (invulnerableTimer <= 0) {
                invulnerable = false;
            }
        }
    }
    
    public void jump() {
        if (onGround && !jumping) {
            velocityY = JUMP_SPEED;
            jumping = true;
            onGround = false;
        }
    }
    
    public void bounce() {
        velocityY = JUMP_SPEED * 0.7;
    }
    
    public void takeDamage() {
        if (!invulnerable) {
            invulnerable = true;
            invulnerableTimer = INVULNERABLE_DURATION;
        }
    }
    
    public void handlePlatformCollision(Platform platform) {
        Rectangle marioBounds = getBounds();
        Rectangle platformBounds = platform.getBounds();
        
        // 检查从上方碰撞
        if (velocityY > 0 && marioBounds.y < platformBounds.y) {
            y = platformBounds.y - height;
            velocityY = 0;
            onGround = true;
            jumping = false;
        }
        // 检查从下方碰撞
        else if (velocityY < 0 && marioBounds.y > platformBounds.y) {
            y = platformBounds.y + platformBounds.height;
            velocityY = 0;
        }
        // 检查从左侧碰撞
        else if (velocityX > 0 && marioBounds.x < platformBounds.x) {
            x = platformBounds.x - width;
            velocityX = 0;
        }
        // 检查从右侧碰撞
        else if (velocityX < 0 && marioBounds.x > platformBounds.x) {
            x = platformBounds.x + platformBounds.width;
            velocityX = 0;
        }
    }
    
    public void draw(Graphics2D g2d) {
        // 无敌时闪烁效果
        if (invulnerable && invulnerableTimer % 10 < 5) {
            return;
        }
        
        // 绘制马里奥身体
        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, width, height);
        
        // 绘制帽子
        g2d.setColor(Color.RED);
        g2d.fillRect(x - 2, y - 5, width + 4, 10);
        
        // 绘制帽子装饰
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x + 5, y - 3, 8, 8);
        
        // 绘制眼睛
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x + 5, y + 5, 6, 6);
        g2d.fillOval(x + 15, y + 5, 6, 6);
        
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x + 7, y + 7, 2, 2);
        g2d.fillOval(x + 17, y + 7, 2, 2);
        
        // 绘制胡子
        g2d.setColor(new Color(139, 69, 19)); // 棕色
        g2d.fillRect(x + 8, y + 15, 4, 2);
        g2d.fillRect(x + 14, y + 15, 4, 2);
        
        // 绘制裤子
        g2d.setColor(Color.BLUE);
        g2d.fillRect(x, y + 25, width, 15);
        
        // 绘制鞋子
        g2d.setColor(new Color(139, 69, 19)); // 棕色
        g2d.fillRect(x, y + 35, 8, 5);
        g2d.fillRect(x + 18, y + 35, 8, 5);
    }
    
    // Getters and Setters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public double getVelocityY() { return velocityY; }
    public boolean isJumping() { return jumping; }
    public boolean isOnGround() { return onGround; }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setMovingLeft(boolean movingLeft) { this.movingLeft = movingLeft; }
    public void setMovingRight(boolean movingRight) { this.movingRight = movingRight; }
    public void setOnGround(boolean onGround) { this.onGround = onGround; }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}