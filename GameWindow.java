import javax.swing.*; // need this for GUI objects
import java.awt.*;// need this for Layout Managers
import java.awt.event.*;// need this to respond to GUI events

public class GameWindow extends JFrame implements ActionListener,KeyListener,MouseListener{
    
    // declare instance variables for user interface objects

    // declare labels 

    private JLabel statusBarL;
    private JLabel keyL;
    private JLabel mouseL;

    // declare text fields

    private JTextField statusBarTF;
    private JTextField keyTF;
    private JTextField mouseTF;

    // declare buttons

    private JButton startB;
    private JButton dropB;
    private JButton focusB;
    private JButton exitB;

    private Container c;

    private JPanel mainPanel;
    private GamePanel gamePanel;
    private int keyText= 0;
    private int lives = 3;

    @SuppressWarnings({"unchecked"})    
    public GameWindow() {
        
        setTitle ("Don't Drop The Ball!");
        setSize (500, 550);

        // create user interface objects

        // create labels
        statusBarL = new JLabel ("Application Status: ");
        keyL = new JLabel("Points Obtained / Lives: ");
        mouseL = new JLabel("Time Left: ");
    
        // create text fields and set their colour, etc.

        statusBarTF = new JTextField (25);
        keyTF = new JTextField (25);
        mouseTF = new JTextField (25);

        statusBarTF.setEditable(false);
        keyTF.setEditable(false);
        mouseTF.setEditable(false);

        statusBarTF.setBackground(Color.CYAN);
        keyTF.setBackground(Color.YELLOW);
        mouseTF.setBackground(Color.GREEN);
        
        // create buttons

        startB = new JButton ("Load Game");
        dropB = new JButton ("1 Ball");
        focusB = new JButton ("2 Ball");
        exitB = new JButton ("Exit");

        // add listener to each button (same as the current object)

        startB.addActionListener(this);
        dropB.addActionListener(this);
        focusB.addActionListener(this);
        exitB.addActionListener(this);
        
        // create mainPanel

        mainPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        mainPanel.setLayout(flowLayout);

        GridLayout gridLayout;

        // create the gamePanel for game entities

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(400, 400));
        gamePanel.setBackground(Color.GRAY);
        gamePanel.createGameEntities();
    
        // create infoPanel

        JPanel infoPanel = new JPanel();
        gridLayout = new GridLayout(3, 2);
        infoPanel.setLayout(gridLayout);
        infoPanel.setBackground(Color.ORANGE);
        
        // add user interface objects to infoPanel
    
        infoPanel.add (statusBarL);
        infoPanel.add (statusBarTF);

        infoPanel.add (keyL);
        infoPanel.add (keyTF);        

        infoPanel.add (mouseL);
        infoPanel.add (mouseTF);
        
        // create buttonPanel

        JPanel buttonPanel = new JPanel();
        gridLayout = new GridLayout(1, 4);
        buttonPanel.setLayout(gridLayout);

        // add buttons to buttonPanel
        dropB.setVisible(false);
        focusB.setVisible(false);
        buttonPanel.add (startB);
        buttonPanel.add (dropB);
        buttonPanel.add (focusB);
        buttonPanel.add (exitB);

        // add sub-panels with GUI objects to mainPanel and set its colour

        mainPanel.add(infoPanel);
        mainPanel.add(gamePanel);
        mainPanel.add(buttonPanel);
        mainPanel.setBackground(Color.PINK);

        // set up mainPanel to respond to keyboard and mouse

        gamePanel.addMouseListener(this);
        mainPanel.addKeyListener(this);
        
        // add mainPanel to window surface

        c = getContentPane();
        c.add(mainPanel);

        // set properties of window

        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        // set status bar message

        statusBarTF.setText("Application Started");
    } 
    
    // implement single method in ActionListener interface
    public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
        
      //statusBarTF.setText(command + " button clicked.");
      if (command.equals(focusB.getText())){
         gamePanel.twoBall();
        dropB.setVisible(false);
        focusB.setVisible(false);
       } 

      if (command.equals(startB.getText())){
        statusBarTF.setText("Game Loaded");
        gamePanel.drawGameEntities();
        dropB.setVisible(true);
        focusB.setVisible(true);
      }

      if (command.equals(dropB.getText())){
        statusBarTF.setText("Game Running");
        gamePanel.dropBall();
        dropB.setVisible(false);
        focusB.setVisible(false);
      } 

      if (command.equals(exitB.getText())){
        System.exit(0);
      }
      mainPanel.requestFocus();
    }
    
    // implement methods in KeyListener interface

    public void keyPressed(KeyEvent e) {
     int keyCode = e.getKeyCode();

     lives = gamePanel.getLives();
     mouseTF.setText(""+ gamePanel.getTracker());
     keyTF.setText(keyText + " POINTS / "+ Lives());
     if (keyCode == KeyEvent.VK_RIGHT) {
        gamePanel.updateGameEntities(2);
        gamePanel.drawGameEntities();
        keyText = gamePanel.getPoints();
        if(keyText==28){
            mouseTF.setText(":} WINNER WINNER CHICKEN DINNER :)");
            keyTF.setText(keyText + " POINTS!!! GAME OVER");
            statusBarTF.setText("Game Stopped.");
            gamePanel.endGame();
        }
        else if(gamePanel.getTracker()<=0){
            mouseTF.setText(":{ TIME UP YOU LOSE :(");
            keyTF.setText(keyText + " POINTS!!! GAME OVER");
            statusBarTF.setText("Game Stopped");
            gamePanel.endGame();
        }
        else if(lives<=0){
            mouseTF.setText(":{ NO MORE LIVES YOU LOSE :(");
            keyTF.setText(keyText + " POINTS!!! GAME OVER");
            statusBarTF.setText("Game Stopped.");
            gamePanel.endGame();
        }
     }
     if (keyCode == KeyEvent.VK_LEFT) {
        gamePanel.updateGameEntities(1);
        gamePanel.drawGameEntities();
        keyText = gamePanel.getPoints();
        if(keyText==28){
            mouseTF.setText(":} WINNER WINNER CHICKEN DINNER :)");
            keyTF.setText(keyText + " POINTS!!! GAME OVER");
            statusBarTF.setText("Game Stopped.");
            gamePanel.endGame();
        }
        else if(lives<=0){
            mouseTF.setText(":{ NO MORE LIVES YOU LOSE :(");
            keyTF.setText(keyText + " POINTS GAME OVER" );
            statusBarTF.setText("Game Stopped");
            gamePanel.endGame();
            
        }
        else if(gamePanel.getTracker()<=0){
            mouseTF.setText(":{ TIME UP YOU LOSE :(");
            keyTF.setText(keyText + " POINTS GAME OVER" );
            statusBarTF.setText("Game Stopped");
            gamePanel.endGame();
        }
     }
    }
    
    public void keyReleased(KeyEvent e) {}
    
    public void keyTyped(KeyEvent e) {}

    // implement methods in MouseListener interface

    public void mouseClicked(MouseEvent e) {
    } 
    
    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}
    
    public String Lives(){
       String s="";
       if(lives==3){
            s = "X X X LIVES";
       }
       if(lives==2){
            s = "X X LIVES";
       }
       else if(lives==1){
            s = "X LIVES";
       }
       return s;
    }
}
