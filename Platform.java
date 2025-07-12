import java.awt.*;

public class Platform {
    private int x, y;
    private int width, height;
    private Color color;
    
    public Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = new Color(139, 69, 19); // 棕色
    }
    
    public void draw(Graphics2D g2d) {
        // 绘制平台主体
        g2d.setColor(color);
        g2d.fillRect(x, y, width, height);
        
        // 绘制平台边缘
        g2d.setColor(color.darker());
        g2d.drawRect(x, y, width, height);
        
        // 绘制平台纹理
        g2d.setColor(color.brighter());
        for (int i = 0; i < width; i += 10) {
            g2d.drawLine(x + i, y, x + i, y + height);
        }
    }
    
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}