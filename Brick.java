import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.geom.Rectangle2D;
import java.util.Random;
public class Brick {
    private int x, y; 
    private int width, height; 
    private boolean isVisible; 
    Random random;
    
    private Rectangle2D.Double brick;

    public Brick(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 30;
        this.height = 10;
        isVisible = true;
        random = new Random();
    }

    public void draw(JPanel panel) {
     
        
        Graphics g = panel.getGraphics ();
        Graphics2D g2 = (Graphics2D) g;

        brick = new Rectangle2D.Double(x, y, width, height);
        int rIndex = random.nextInt(3);
        if(rIndex==0){
            g2.setColor(Color.RED);
        }
        else if (rIndex==1){
            g2.setColor(Color.WHITE);
        }
        else{
            g2.setColor(Color.BLACK);
        }
        g2.fill(brick);

        g.dispose();
    }
    
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public boolean getVisible() {
        return isVisible;
    }
    
    public void erase (JPanel panel) {
      Graphics g = panel.getGraphics ();
      Graphics2D g2 = (Graphics2D) g;

      g2.setColor(Color.GRAY);
      g2.fill (new Rectangle2D.Double (x, y, width, height));

      g.dispose();
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    
     public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

