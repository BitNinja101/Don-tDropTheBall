import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Dimension;
import java.util.Random;
import java.util.ArrayList;

public class Ball extends Thread {
    private int x, y, dx, dy, topY; // Position of the ball
    private int intialDy;
    private int diameter; // Diameter of the ball
    private JPanel panel;
    private Ellipse2D.Double ball;
    private Color backgroundColour;
    
    private int spbX, spbW,tracker,lives;
    
    private Slider slider;
    private BrickWall bwall;
    private long lastCollisionTime = 0;
    private static final long COLLISION_COOLDOWN = 500;
    private long bricklastCollisionTime = 0;
    private static final long COLLISION_BRICKCOOLDOWN = 200;
    
    
    private Dimension dimension;
    
    boolean isRunning;
    Random random;
    Random random1;

    // Constructor with arguments
    public Ball(JPanel p,int x, int y, int diameter,Slider sal,BrickWall bw) {
        panel = p;
        dimension = panel.getSize();
        backgroundColour = panel.getBackground ();
        this.x = x;
        this.y = y;
        this.slider = sal;
        this.bwall = bw;
        
        random1 = random = new Random();
        
        spbX = spbW = 0;
        topY = y;
    
        dx = 0;            
        intialDy = dy = 15;          
        
        this.diameter = diameter;
        tracker = 3000;
        lives = 3;
    }
    
    public void move() {
       if (!panel.isVisible()) return;

        x = x + dx;
        y = y + dy;

        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();

        // Check for collision with left and right walls
        if (x <= 0 || x >= panelWidth - diameter) {
           dx = -dx;
        }

        // Check for collision with top wall 
        if (y <= 0) {
           dy = -dy;
        }

        // Check for collision with bottom wall 
        if (y >= panelHeight - diameter) {
           setLocation();
        } 
        
        // Check for collision with slider
        boolean collision = collidesWithBall();
        if (collision && System.currentTimeMillis() - lastCollisionTime >= COLLISION_COOLDOWN) {
           ballPhysics();
           lastCollisionTime = System.currentTimeMillis();
        } 
        
        // Check for collision with brick
        boolean collision1 = collidesWithBrick();
        if (collision1 && System.currentTimeMillis() - bricklastCollisionTime >= COLLISION_BRICKCOOLDOWN) {
           brickPhysics();
           bricklastCollisionTime = System.currentTimeMillis();
        } 

     } 

    
    public void setLocation() {
      int panelWidth = panel.getWidth();
      x = random.nextInt (panelWidth - diameter); 
      lives--;
      dx = 0;
      y=130;
    }

    public void draw() {
        Graphics g = panel.getGraphics ();
        Graphics2D g2 = (Graphics2D) g;
        
        ball = new Ellipse2D.Double(x,y,diameter,diameter);
        int rIndex = random1.nextInt(2);
        if(rIndex==0){
            g2.setColor(Color.YELLOW);
        }
        else if(rIndex==1){
            g2.setColor(Color.GREEN);
        }
        g2.fill(ball); 
        
        g.dispose();
    }

    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(x, y, diameter, diameter);
    }
    
     public void erase () {
      Graphics g = panel.getGraphics ();
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor (backgroundColour);
      g2.fill (new Ellipse2D.Double (x, y, diameter, diameter));

      g.dispose();
    }
   
    public void run () {

      isRunning = true;

      try {
        while (isRunning) {
            erase();
            move ();
            draw();
            sleep (100);    // increase value of sleep time to slow down ball
            tracker--;
         }
      }
      catch(InterruptedException e) {}
    }
    
    public boolean collidesWithBall() {
      Rectangle2D.Double ballRect = getBounds();
      Rectangle2D.Double sliRect = slider.getBounds();
      
      return sliRect.intersects(ballRect); 
    }
    
  
    
    public boolean collidesWithBrick() {
        for (Brick brick : bwall.bricks) {
            if(brick.getVisible()==true){
                    Rectangle2D.Double ballRect = getBounds();
                    Rectangle2D.Double brickRect = brick.getBounds();
                    if(brickRect.intersects(ballRect) == true){
                         brick.setVisible(false);
                         brick.erase(panel);
                         bwall.setPoints();
                         spbX = brick.getX();
                         spbW = brick.getWidth();
                         return true;
                     } 
            }
         
        }
        return false;
    }
    
    public void ballPhysics() {
       int sliderCenterX = slider.getWidth()/ 2;
       sliderCenterX = sliderCenterX + slider.getX();
       int ballCenterX = diameter / 2;
       ballCenterX= ballCenterX + x;
       
       dy = intialDy;
       
       double angle = Math.toRadians(60); // Adjust the angle 
       int newDx = (int) (dy * Math.sin(angle)); // Calculate new horizontal velocity
       int newDy = (int) (dy * Math.cos(angle)); // Calculate new vertical velocity

       dx = newDx;
       dy = -newDy; 

       // prevents multiple collisions
       
       if (ballCenterX < sliderCenterX) {
          dx = Math.abs(dx);   
       } 
       else {
          dx = -Math.abs(dx);
       }   
    } 
    
    

    
    public void brickPhysics() {
       int brickCenterX =  spbW/ 2;
       brickCenterX = brickCenterX + spbX;
       int ballCenterX = diameter / 2;
       ballCenterX= ballCenterX + x;
       
       dy = intialDy;
       dy = dy * -1;
       
       double angle = Math.toRadians(60); // Adjust the angle 
       int newDx = (int) (dy * Math.sin(angle)); // Calculate new horizontal velocity
       int newDy = (int) (dy * Math.cos(angle)); // Calculate new vertical velocity

       
       dx = newDx;
       dy = -newDy; 

       if (ballCenterX < brickCenterX) {
          dx = Math.abs(dx);   
       } 
       else {
          dx = -Math.abs(dx);
       }
    } 
    
    public int getTracker(){
     return tracker;
    }
    
    public int getLives(){
     return lives;
    }
}
