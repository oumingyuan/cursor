import java.awt.*;

public class Mario {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 40;
    private static final double GRAVITY = 0.8;
    private static final double JUMP_VELOCITY = -15.0;
    private static final double MOVE_SPEED = 5.0;
    private static final int INVULNERABLE_TIME = 2000; // 2秒无敌时间
    
    private double x, y;
    private double velocityX, velocityY;
    private boolean movingLeft, movingRight;
    private boolean jumping;
    private boolean onGround;
    private boolean invulnerable;
    private long invulnerableStartTime;
    private MarioState state;
    private int animationFrame;
    private long lastAnimationTime;
    
    public enum MarioState {
        SMALL, BIG, FIRE
    }
    
    public Mario(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
        this.movingLeft = false;
        this.movingRight = false;
        this.jumping = false;
        this.onGround = false;
        this.invulnerable = false;
        this.state = MarioState.SMALL;
        this.animationFrame = 0;
        this.lastAnimationTime = System.currentTimeMillis();
    }
    
    public void update() {
        // 处理水平移动
        if (movingLeft && !movingRight) {
            velocityX = -MOVE_SPEED;
        } else if (movingRight && !movingLeft) {
            velocityX = MOVE_SPEED;
        } else {
            velocityX = 0;
        }
        
        // 应用重力
        if (!onGround) {
            velocityY += GRAVITY;
        }
        
        // 更新位置
        x += velocityX;
        y += velocityY;
        
        // 更新动画
        updateAnimation();
        
        // 检查无敌状态
        if (invulnerable && System.currentTimeMillis() - invulnerableStartTime > INVULNERABLE_TIME) {
            invulnerable = false;
        }
    }
    
    private void updateAnimation() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAnimationTime > 100) { // 每100ms更新一次动画
            animationFrame = (animationFrame + 1) % 4;
            lastAnimationTime = currentTime;
        }
    }
    
    public void jump() {
        if (onGround) {
            velocityY = JUMP_VELOCITY;
            jumping = true;
            onGround = false;
        }
    }
    
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }
    
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }
    
    public void handlePlatformCollision(Platform platform) {
        Rectangle marioBounds = getBounds();
        Rectangle platformBounds = platform.getBounds();
        
        // 检查碰撞方向
        if (velocityY > 0 && marioBounds.y < platformBounds.y) {
            // 从上方碰撞
            y = platformBounds.y - HEIGHT;
            velocityY = 0;
            onGround = true;
            jumping = false;
        } else if (velocityY < 0 && marioBounds.y + marioBounds.height > platformBounds.y + platformBounds.height) {
            // 从下方碰撞
            y = platformBounds.y + platformBounds.height;
            velocityY = 0;
        } else if (velocityX > 0 && marioBounds.x < platformBounds.x) {
            // 从左侧碰撞
            x = platformBounds.x - WIDTH;
        } else if (velocityX < 0 && marioBounds.x + marioBounds.width > platformBounds.x + platformBounds.width) {
            // 从右侧碰撞
            x = platformBounds.x + platformBounds.width;
        }
    }
    
    public void bounce() {
        velocityY = JUMP_VELOCITY * 0.7;
        jumping = true;
        onGround = false;
    }
    
    public void takeDamage() {
        if (!invulnerable) {
            invulnerable = true;
            invulnerableStartTime = System.currentTimeMillis();
            
            // 降级状态
            switch (state) {
                case FIRE:
                    state = MarioState.BIG;
                    break;
                case BIG:
                    state = MarioState.SMALL;
                    break;
                case SMALL:
                    // 小马里奥受伤会失去生命
                    break;
            }
        }
    }
    
    public void powerUp(PowerUpType type) {
        switch (type) {
            case MUSHROOM:
                if (state == MarioState.SMALL) {
                    state = MarioState.BIG;
                }
                break;
            case FLOWER:
                if (state == MarioState.BIG) {
                    state = MarioState.FIRE;
                } else if (state == MarioState.SMALL) {
                    state = MarioState.BIG;
                }
                break;
        }
    }
    
    public void resetPosition(double x, double y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
        this.onGround = false;
        this.jumping = false;
    }
    
    public void draw(Graphics g) {
        // 无敌状态闪烁效果
        if (invulnerable && (System.currentTimeMillis() - invulnerableStartTime) % 200 < 100) {
            return;
        }
        
        // 根据状态绘制不同大小的马里奥
        int drawWidth = WIDTH;
        int drawHeight = HEIGHT;
        
        switch (state) {
            case BIG:
            case FIRE:
                drawWidth = WIDTH + 10;
                drawHeight = HEIGHT + 10;
                break;
        }
        
        // 绘制马里奥身体
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, drawWidth, drawHeight);
        
        // 绘制帽子
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, drawWidth, drawHeight / 3);
        
        // 绘制帽子装饰
        g.setColor(Color.WHITE);
        g.fillOval((int)x + drawWidth / 4, (int)y, drawWidth / 2, drawHeight / 4);
        
        // 绘制眼睛
        g.setColor(Color.WHITE);
        g.fillOval((int)x + drawWidth / 4, (int)y + drawHeight / 4, drawWidth / 6, drawHeight / 6);
        g.fillOval((int)x + drawWidth * 2 / 3, (int)y + drawHeight / 4, drawWidth / 6, drawHeight / 6);
        
        // 绘制瞳孔
        g.setColor(Color.BLACK);
        g.fillOval((int)x + drawWidth / 4 + 2, (int)y + drawHeight / 4 + 2, drawWidth / 12, drawHeight / 12);
        g.fillOval((int)x + drawWidth * 2 / 3 + 2, (int)y + drawHeight / 4 + 2, drawWidth / 12, drawHeight / 12);
        
        // 绘制嘴巴
        g.setColor(Color.BLACK);
        g.fillOval((int)x + drawWidth / 3, (int)y + drawHeight * 2 / 3, drawWidth / 3, drawHeight / 6);
        
        // 绘制工作服
        g.setColor(Color.BLUE);
        g.fillRect((int)x, (int)y + drawHeight * 2 / 3, drawWidth, drawHeight / 3);
        
        // 绘制手臂
        g.setColor(Color.PINK);
        g.fillRect((int)x - 5, (int)y + drawHeight / 3, 8, drawHeight / 3);
        g.fillRect((int)x + drawWidth - 3, (int)y + drawHeight / 3, 8, drawHeight / 3);
        
        // 绘制腿
        g.setColor(Color.BLUE);
        g.fillRect((int)x + 2, (int)y + drawHeight - 5, drawWidth / 3, 5);
        g.fillRect((int)x + drawWidth * 2 / 3 - 2, (int)y + drawHeight - 5, drawWidth / 3, 5);
        
        // 如果是火焰马里奥，添加火焰效果
        if (state == MarioState.FIRE) {
            g.setColor(Color.ORANGE);
            g.fillOval((int)x + drawWidth / 4, (int)y - 5, drawWidth / 2, 10);
        }
    }
    
    public Rectangle getBounds() {
        int drawWidth = WIDTH;
        int drawHeight = HEIGHT;
        
        switch (state) {
            case BIG:
            case FIRE:
                drawWidth = WIDTH + 10;
                drawHeight = HEIGHT + 10;
                break;
        }
        
        return new Rectangle((int)x, (int)y, drawWidth, drawHeight);
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getVelocityY() { return velocityY; }
    public boolean isJumping() { return jumping; }
    public boolean isInvulnerable() { return invulnerable; }
    public MarioState getState() { return state; }
}