package com.supermario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class SuperMarioGameSwing extends JFrame {
    
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 600;
    private static final int TILE_SIZE = 32;
    
    private GamePanel gamePanel;
    private Set<Integer> pressedKeys;
    private GameWorld gameWorld;
    private long lastTime;
    
    public SuperMarioGameSwing() {
        setTitle("Super Mario Game");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        // 初始化游戏组件
        pressedKeys = new HashSet<>();
        gameWorld = new GameWorld(WINDOW_WIDTH, WINDOW_HEIGHT, TILE_SIZE);
        
        // 创建游戏面板
        gamePanel = new GamePanel();
        add(gamePanel);
        
        // 设置键盘监听器
        setupKeyListeners();
        
        // 启动游戏循环
        startGameLoop();
    }
    
    private void setupKeyListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });
        
        setFocusable(true);
        requestFocus();
    }
    
    private void startGameLoop() {
        lastTime = System.nanoTime();
        
        Timer timer = new Timer(16, e -> { // 约60FPS
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;
            
            update(deltaTime);
            gamePanel.repaint();
        });
        
        timer.start();
    }
    
    private void update(double deltaTime) {
        // 处理输入
        handleInput();
        
        // 更新游戏世界
        gameWorld.update(deltaTime);
    }
    
    private void handleInput() {
        // 移动控制
        if (pressedKeys.contains(KeyEvent.VK_LEFT) || pressedKeys.contains(KeyEvent.VK_A)) {
            gameWorld.getMario().setMovingLeft(true);
        } else {
            gameWorld.getMario().setMovingLeft(false);
        }
        
        if (pressedKeys.contains(KeyEvent.VK_RIGHT) || pressedKeys.contains(KeyEvent.VK_D)) {
            gameWorld.getMario().setMovingRight(true);
        } else {
            gameWorld.getMario().setMovingRight(false);
        }
        
        // 跳跃控制
        if ((pressedKeys.contains(KeyEvent.VK_SPACE) || pressedKeys.contains(KeyEvent.VK_UP) || 
             pressedKeys.contains(KeyEvent.VK_W)) && gameWorld.getMario().isOnGround()) {
            gameWorld.getMario().jump();
        }
    }
    
    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // 启用抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 渲染游戏世界
            gameWorld.render(g2d);
            
            // 渲染UI
            renderUI(g2d);
        }
        
        private void renderUI(Graphics2D g2d) {
            // 渲染分数
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Score: " + gameWorld.getScore(), 10, 30);
            g2d.drawString("Lives: " + gameWorld.getLives(), 10, 60);
            
            // 渲染游戏状态
            if (gameWorld.isGameOver()) {
                g2d.setColor(Color.RED);
                g2d.setFont(new Font("Arial", Font.BOLD, 40));
                g2d.drawString("GAME OVER", WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT / 2);
            } else if (gameWorld.isLevelComplete()) {
                g2d.setColor(Color.GREEN);
                g2d.setFont(new Font("Arial", Font.BOLD, 40));
                g2d.drawString("LEVEL COMPLETE!", WINDOW_WIDTH / 2 - 150, WINDOW_HEIGHT / 2);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SuperMarioGameSwing game = new SuperMarioGameSwing();
            game.setVisible(true);
        });
    }
}