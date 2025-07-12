import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Background {
    private List<Cloud> clouds;
    private List<Mountain> mountains;
    private Random random;
    
    public Background() {
        clouds = new ArrayList<>();
        mountains = new ArrayList<>();
        random = new Random();
        
        // 创建云朵
        for (int i = 0; i < 5; i++) {
            clouds.add(new Cloud(random.nextInt(800), random.nextInt(200) + 50));
        }
        
        // 创建山脉
        mountains.add(new Mountain(100, 400));
        mountains.add(new Mountain(300, 450));
        mountains.add(new Mountain(500, 420));
        mountains.add(new Mountain(700, 480));
    }
    
    public void draw(Graphics g) {
        // 绘制天空渐变
        drawSkyGradient(g);
        
        // 绘制山脉
        for (Mountain mountain : mountains) {
            mountain.draw(g);
        }
        
        // 绘制云朵
        for (Cloud cloud : clouds) {
            cloud.draw(g);
        }
    }
    
    private void drawSkyGradient(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(135, 206, 235),  // 浅蓝色
            0, 600, new Color(100, 149, 237)  // 深蓝色
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, 800, 600);
        
        g2d.dispose();
    }
    
    // 云朵内部类
    private static class Cloud {
        private int x, y;
        private int size;
        
        public Cloud(int x, int y) {
            this.x = x;
            this.y = y;
            this.size = 30 + (int)(Math.random() * 20);
        }
        
        public void draw(Graphics g) {
            g.setColor(Color.WHITE);
            
            // 绘制多个圆形组成云朵
            g.fillOval(x, y, size, size / 2);
            g.fillOval(x + size / 3, y - size / 4, size / 2, size / 2);
            g.fillOval(x + size * 2 / 3, y, size / 2, size / 2);
            g.fillOval(x + size / 2, y + size / 4, size / 2, size / 2);
        }
    }
    
    // 山脉内部类
    private static class Mountain {
        private int x, y;
        private int height;
        
        public Mountain(int x, int y) {
            this.x = x;
            this.y = y;
            this.height = 100 + (int)(Math.random() * 50);
        }
        
        public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 绘制山脉渐变
            GradientPaint mountainGradient = new GradientPaint(
                x, y, new Color(139, 69, 19),  // 棕色
                x, y + height, new Color(160, 82, 45)  // 深棕色
            );
            g2d.setPaint(mountainGradient);
            
            // 绘制三角形山脉
            int[] xPoints = {x, x + 80, x + 160};
            int[] yPoints = {y + height, y, y + height};
            g2d.fillPolygon(xPoints, yPoints, 3);
            
            // 绘制雪顶
            g2d.setColor(Color.WHITE);
            int[] snowXPoints = {x + 20, x + 80, x + 140};
            int[] snowYPoints = {y + height - 20, y + 10, y + height - 20};
            g2d.fillPolygon(snowXPoints, snowYPoints, 3);
            
            g2d.dispose();
        }
    }
}