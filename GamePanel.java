import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.awt.Image;


public class GamePanel extends JPanel{

    Ball ball,ball2;
    Slider slider;
    BrickWall bwall;
    private Timer timer;
    private Graphics g;
    private static int count;

    private BufferedImage image;
 	 private Image backgroundImage;
    
    public GamePanel() {
        ball = null;
        ball2 = null;
        slider = null;
        bwall = null;
        count = 0;
        backgroundImage = ImageManager.loadImage ("images/Background.jpg");
        image = new BufferedImage (400, 400, BufferedImage.TYPE_INT_RGB);
    }
    
    public void createGameEntities() {
       slider = new Slider (this, 50, 350,50,10); 
       bwall = new BrickWall(this);
       ball = new Ball (this, 130, 130,10,slider,bwall); 
       
    } 
    
    public void twoBall(){
       ball2 = new Ball (this, 120, 130,10,slider,bwall); 
       if (ball2 != null) {
           ball2.draw();
        }
        if (ball2 != null) {
           ball2.start();
       }
       if (ball != null) {
           ball.start();
       }
    }

    protected void drawGameEntities() {
        if (slider != null) {
           slider.draw();
        } 
        if (ball != null) {
           ball.draw();
        }
        if ((bwall != null)&&(count==0)) {
           bwall.draw();
           count++;
        }
    }
    
    public void updateGameEntities(int direction) {
        if (slider == null){
            return;
        }
        slider.erase();
        slider.move(direction);
    }
    
    public void dropBall() {
       if (ball != null) {
           ball.start();
       }
    }
    
    public void endGame() {  
         ball.endGame();
         if (ball2 != null) {
            ball2.endGame();
         }
    }
 
    public boolean isOnSlider (int x, int y) {
       if (slider != null){
             return slider.isOnSlider(x, y);
       }
       else {
         return false;
       }
    }
    
    public int getTracker(){
          return ball.getTracker();
    }
    
    public int getLives(){
        if(ball2==null) {
          return ball.getLives();
        }
        else{
            int nums=3;
            int total=ball2.getLives() + ball.getLives();
            if(total==6){
               nums=3;
            } 
            else if(total==5){
               nums=2;
            }    
            else if(total==4){
               nums=1;
            }   
            else{
               nums=0;
            }    
            return nums;
        }
    }
    
    
    
    public int getPoints() {
         return bwall.getPoints();
    }
}
