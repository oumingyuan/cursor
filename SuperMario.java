import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SuperMario extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int DELAY = 16; // ~60 FPS
    
    private Timer timer;
    private boolean running = false;
    
    // 游戏对象
    private Mario mario;
    private List<Platform> platforms;
    private List<Enemy> enemies;
    private List<Coin> coins;
    private List<PowerUp> powerUps;
    private Background background;
    
    // 游戏状态
    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;
    private boolean levelComplete = false;
    
    public SuperMario() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(135, 206, 235)); // 天蓝色
        setFocusable(true);
        addKeyListener(this);
        
        initGame();
    }
    
    private void initGame() {
        // 初始化马里奥
        mario = new Mario(50, HEIGHT - 100);
        
        // 初始化平台
        platforms = new ArrayList<>();
        createPlatforms();
        
        // 初始化敌人
        enemies = new ArrayList<>();
        createEnemies();
        
        // 初始化金币
        coins = new ArrayList<>();
        createCoins();
        
        // 初始化道具
        powerUps = new ArrayList<>();
        createPowerUps();
        
        // 初始化背景
        background = new Background();
        
        startGame();
    }
    
    private void createPlatforms() {
        // 地面
        platforms.add(new Platform(0, HEIGHT - 20, WIDTH, 20));
        
        // 平台
        platforms.add(new Platform(200, HEIGHT - 120, 100, 20));
        platforms.add(new Platform(400, HEIGHT - 150, 100, 20));
        platforms.add(new Platform(600, HEIGHT - 180, 100, 20));
        platforms.add(new Platform(300, HEIGHT - 200, 100, 20));
        platforms.add(new Platform(500, HEIGHT - 250, 100, 20));
    }
    
    private void createEnemies() {
        enemies.add(new Enemy(300, HEIGHT - 40));
        enemies.add(new Enemy(500, HEIGHT - 40));
        enemies.add(new Enemy(700, HEIGHT - 40));
    }
    
    private void createCoins() {
        coins.add(new Coin(250, HEIGHT - 140));
        coins.add(new Coin(450, HEIGHT - 170));
        coins.add(new Coin(650, HEIGHT - 200));
        coins.add(new Coin(350, HEIGHT - 220));
        coins.add(new Coin(550, HEIGHT - 270));
    }
    
    private void createPowerUps() {
        powerUps.add(new PowerUp(400, HEIGHT - 170, PowerUpType.MUSHROOM));
        powerUps.add(new PowerUp(600, HEIGHT - 200, PowerUpType.FLOWER));
    }
    
    private void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (running) {
            drawGame(g);
        } else if (gameOver) {
            drawGameOver(g);
        } else if (levelComplete) {
            drawLevelComplete(g);
        }
    }
    
    private void drawGame(Graphics g) {
        // 绘制背景
        background.draw(g);
        
        // 绘制平台
        for (Platform platform : platforms) {
            platform.draw(g);
        }
        
        // 绘制金币
        for (Coin coin : coins) {
            coin.draw(g);
        }
        
        // 绘制道具
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(g);
        }
        
        // 绘制敌人
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        
        // 绘制马里奥
        mario.draw(g);
        
        // 绘制UI
        drawUI(g);
    }
    
    private void drawUI(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 30);
        g.drawString("Lives: " + lives, 10, 60);
    }
    
    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String gameOverText = "GAME OVER";
        FontMetrics fm = g.getFontMetrics();
        int x = (WIDTH - fm.stringWidth(gameOverText)) / 2;
        int y = HEIGHT / 2;
        g.drawString(gameOverText, x, y);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String restartText = "Press R to restart";
        fm = g.getFontMetrics();
        x = (WIDTH - fm.stringWidth(restartText)) / 2;
        y += 50;
        g.drawString(restartText, x, y);
    }
    
    private void drawLevelComplete(Graphics g) {
        g.setColor(new Color(0, 255, 0, 150));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String completeText = "LEVEL COMPLETE!";
        FontMetrics fm = g.getFontMetrics();
        int x = (WIDTH - fm.stringWidth(completeText)) / 2;
        int y = HEIGHT / 2;
        g.drawString(completeText, x, y);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String scoreText = "Final Score: " + score;
        fm = g.getFontMetrics();
        x = (WIDTH - fm.stringWidth(scoreText)) / 2;
        y += 50;
        g.drawString(scoreText, x, y);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            update();
            repaint();
        }
    }
    
    private void update() {
        // 更新马里奥
        mario.update();
        
        // 更新敌人
        for (Enemy enemy : enemies) {
            enemy.update();
        }
        
        // 更新金币
        for (Coin coin : coins) {
            coin.update();
        }
        
        // 更新道具
        for (PowerUp powerUp : powerUps) {
            powerUp.update();
        }
        
        // 碰撞检测
        checkCollisions();
        
        // 检查游戏状态
        checkGameState();
    }
    
    private void checkCollisions() {
        // 马里奥与平台碰撞
        for (Platform platform : platforms) {
            if (mario.getBounds().intersects(platform.getBounds())) {
                mario.handlePlatformCollision(platform);
            }
        }
        
        // 马里奥与敌人碰撞
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            if (mario.getBounds().intersects(enemy.getBounds())) {
                if (mario.isJumping() && mario.getVelocityY() > 0) {
                    // 马里奥踩到敌人
                    enemies.remove(i);
                    score += 100;
                    mario.bounce();
                } else {
                    // 马里奥被敌人攻击
                    if (!mario.isInvulnerable()) {
                        lives--;
                        mario.takeDamage();
                        if (lives <= 0) {
                            gameOver = true;
                            running = false;
                        }
                    }
                }
            }
        }
        
        // 马里奥与金币碰撞
        for (int i = coins.size() - 1; i >= 0; i--) {
            Coin coin = coins.get(i);
            if (mario.getBounds().intersects(coin.getBounds())) {
                coins.remove(i);
                score += 50;
            }
        }
        
        // 马里奥与道具碰撞
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            if (mario.getBounds().intersects(powerUp.getBounds())) {
                powerUps.remove(i);
                mario.powerUp(powerUp.getType());
                score += 200;
            }
        }
    }
    
    private void checkGameState() {
        // 检查是否完成关卡（收集所有金币）
        if (coins.isEmpty() && !levelComplete) {
            levelComplete = true;
            running = false;
        }
        
        // 检查马里奥是否掉出屏幕
        if (mario.getY() > HEIGHT) {
            lives--;
            if (lives <= 0) {
                gameOver = true;
                running = false;
            } else {
                mario.resetPosition(50, HEIGHT - 100);
            }
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (running) {
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
        } else if (gameOver && e.getKeyCode() == KeyEvent.VK_R) {
            restartGame();
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
    
    private void restartGame() {
        score = 0;
        lives = 3;
        gameOver = false;
        levelComplete = false;
        initGame();
    }
    
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