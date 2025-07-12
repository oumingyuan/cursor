package com.supermario;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {
    
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public abstract void update(double deltaTime);
    public abstract void render(Graphics2D g2d);
    
    public Rectangle getBoundsInParent() {
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }
    
    // Getters and Setters
    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public double getWidth() {
        return width;
    }
    
    public void setWidth(double width) {
        this.width = width;
    }
    
    public double getHeight() {
        return height;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
}