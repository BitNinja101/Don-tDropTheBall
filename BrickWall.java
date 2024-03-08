import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.JPanel;

public class BrickWall {
    public ArrayList<Brick> bricks;
    private JPanel panel;
    private Dimension dimension;
    private int count,points;

    public BrickWall(JPanel p) {
        panel = p;
        bricks = new ArrayList<>();
        // Add bricks to the wall (Brick Layout)
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                bricks.add(new Brick(60 * j , 25 * i ));
            }
        }
        count = 28;
        points = 0;
    }

    public void draw() {
        for (Brick brick : bricks) {
            brick.draw(panel); // Draw each brick in the wall
        }
    }
    
    public int getCount(){
       return count;
    }
    
    public ArrayList<Brick> getBricks(){
       return bricks;
    }
    
    public int getPoints(){
       return points;
    }
    
    public void setPoints(){
        points = points + 1;
    }
}

