package com.supermario;

public class GameState {
    private int score;
    private int lives;
    private boolean gameRunning;
    private boolean gameOver;
    private int level;
    
    public GameState() {
        this.score = 0;
        this.lives = 3;
        this.gameRunning = true;
        this.gameOver = false;
        this.level = 1;
    }
    
    public void addScore(int points) {
        this.score += points;
    }
    
    public void loseLife() {
        this.lives--;
        if (this.lives <= 0) {
            this.gameOver = true;
            this.gameRunning = false;
        }
    }
    
    public void gameOver() {
        this.gameOver = true;
        this.gameRunning = false;
    }
    
    public void reset() {
        this.score = 0;
        this.lives = 3;
        this.gameRunning = true;
        this.gameOver = false;
        this.level = 1;
    }
    
    public void nextLevel() {
        this.level++;
        // 可以在这里添加关卡切换逻辑
    }
    
    // Getters
    public int getScore() {
        return score;
    }
    
    public int getLives() {
        return lives;
    }
    
    public boolean isGameRunning() {
        return gameRunning;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public int getLevel() {
        return level;
    }
    
    // Setters
    public void setScore(int score) {
        this.score = score;
    }
    
    public void setLives(int lives) {
        this.lives = lives;
    }
    
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }
    
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
}