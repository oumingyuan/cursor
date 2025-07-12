import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuperMario extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int GROUND_Y = 500;
    
    private Mario mario;
    private List<Enemy> enemies;
    private List<Coin> coins;
    private List<Platform> platforms;
    private Timer timer;
    private boolean gameRunning;
    private int score;
    private int lives;
    private boolean gameOver;
    
    public SuperMario() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(135, 206, 235)); // 天蓝色
        setFocusable(true);
        addKeyListener(this);
        
        initGame();
        
        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }
    
    private void initGame() {
        mario = new Mario(50, GROUND_Y - 50);
        enemies = new ArrayList<>();
        coins = new ArrayList<>();
        platforms = new ArrayList<>();
        
        // 添加平台
        platforms.add(new Platform(200, 400, 100, 20));
        platforms.add(new Platform(400, 350, 100, 20));
        platforms.add(new Platform(600, 300, 100, 20));
        platforms.add(new Platform(300, 250, 100, 20));
        
        // 添加敌人
        enemies.add(new Enemy(300, GROUND_Y - 30));
        enemies.add(new Enemy(500, GROUND_Y - 30));
        enemies.add(new Enemy(700, GROUND_Y - 30));
        
        // 添加金币
        for (int i = 0; i < 10; i++) {
            coins.add(new Coin(100 + i * 70, GROUND_Y - 80));
        }
        
        score = 0;
        lives = 3;
        gameRunning = true;
        gameOver = false;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制地面
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, GROUND_Y, WIDTH, HEIGHT - GROUND_Y);
        
        // 绘制平台
        for (Platform platform : platforms) {
            platform.draw(g2d);
        }
        
        // 绘制金币
        for (Coin coin : coins) {
            coin.draw(g2d);
        }
        
        // 绘制敌人
        for (Enemy enemy : enemies) {
            enemy.draw(g2d);
        }
        
        // 绘制马里奥
        mario.draw(g2d);
        
        // 绘制UI
        drawUI(g2d);
        
        if (gameOver) {
            drawGameOver(g2d);
        }
    }
    
    private void drawUI(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + score, 10, 30);
        g2d.drawString("Lives: " + lives, 10, 60);
    }
    
    private void drawGameOver(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        String gameOverText = "GAME OVER";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (WIDTH - fm.stringWidth(gameOverText)) / 2;
        int y = HEIGHT / 2;
        g2d.drawString(gameOverText, x, y);
        
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        String scoreText = "Final Score: " + score;
        fm = g2d.getFontMetrics();
        x = (WIDTH - fm.stringWidth(scoreText)) / 2;
        y += 50;
        g2d.drawString(scoreText, x, y);
        
        String restartText = "Press R to restart";
        fm = g2d.getFontMetrics();
        x = (WIDTH - fm.stringWidth(restartText)) / 2;
        y += 30;
        g2d.drawString(restartText, x, y);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameRunning) return;
        
        update();
        repaint();
    }
    
    private void update() {
        mario.update();
        
        // 更新敌人
        for (Enemy enemy : enemies) {
            enemy.update();
        }
        
        // 更新金币
        for (Coin coin : coins) {
            coin.update();
        }
        
        // 检查碰撞
        checkCollisions();
        
        // 检查游戏结束条件
        if (lives <= 0) {
            gameOver = true;
            gameRunning = false;
        }
    }
    
    private void checkCollisions() {
        // 马里奥与平台的碰撞
        for (Platform platform : platforms) {
            if (mario.getBounds().intersects(platform.getBounds())) {
                mario.handlePlatformCollision(platform);
            }
        }
        
        // 马里奥与地面的碰撞
        if (mario.getY() + mario.getHeight() >= GROUND_Y) {
            mario.setY(GROUND_Y - mario.getHeight());
            mario.setOnGround(true);
        } else {
            mario.setOnGround(false);
        }
        
        // 马里奥与金币的碰撞
        coins.removeIf(coin -> {
            if (mario.getBounds().intersects(coin.getBounds())) {
                score += 10;
                return true;
            }
            return false;
        });
        
        // 马里奥与敌人的碰撞
        for (Enemy enemy : enemies) {
            if (mario.getBounds().intersects(enemy.getBounds())) {
                if (mario.isJumping() && mario.getVelocityY() > 0) {
                    // 马里奥跳到敌人头上
                    enemies.remove(enemy);
                    score += 20;
                    mario.bounce();
                    break;
                } else {
                    // 马里奥被敌人攻击
                    lives--;
                    mario.takeDamage();
                    break;
                }
            }
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                initGame();
            }
            return;
        }
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                mario.setMovingLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                mario.setMovingRight(true);
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:
                mario.jump();
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                mario.setMovingLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                mario.setMovingRight(false);
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Super Mario");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new SuperMario());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}