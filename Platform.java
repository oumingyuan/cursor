import java.awt.*;

public class Platform {
    private int x, y, width, height;
    private Color color;
    
    public Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = new Color(139, 69, 19); // 棕色
    }
    
    public Platform(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        
        // 添加一些纹理效果
        g.setColor(color.darker());
        for (int i = 0; i < width; i += 20) {
            g.drawLine(x + i, y, x + i, y + height);
        }
        for (int i = 0; i < height; i += 10) {
            g.drawLine(x, y + i, x + width, y + i);
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}