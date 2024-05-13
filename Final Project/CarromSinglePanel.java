//ahan banerjee and shiv davay
//I will uphold academic and personal integrity within the TJ community 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;


public class CarromSinglePanel extends JPanel
{
    
   //fields
   
   //Most of this is the same as AnimationPanel
   public static final int FRAME = 500;
   private static final Color BACKGROUND = Color.BLACK;
   
   private BufferedImage myImage;  
   private Graphics myBuffer;
   
   private Timer t;

   //private ArrayList<Animatable> animationObjects;
   
   //But we do need a new one: this will be true when the user is holding down the left arrow key.
   //Explaining why we need this is complicated; see the assignment for details.
   private boolean w;
   private boolean s;
   private boolean up;
   private boolean down;
   
   //And we need to declare our square we can control with arrow keys as a field, separately from 
   //the arraylist, so we can give it specific commands outside the constructor.
   private Paddle sq;
   private Paddle sq2;
   private PongBall ball;
   
   private int onescore;
   private int twoscore;
   
      
   //constructors
   public CarromSinglePanel()
   {
      myImage =  new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB); 
      myBuffer = myImage.getGraphics(); 
      myBuffer.setColor(BACKGROUND);    
      myBuffer.fillRect(0,0,FRAME,FRAME);
      
      animationObjects = new ArrayList<Animatable>();  
      
      ball = new PongBall();
      animationObjects.add(ball);
       
      //Make our ArrowkeySquare
      sq = new Paddle(25); //DO NOT RE-DECLARE sq - it's a field, it's already declared.  Just assign.
      sq2 = new Paddle(475);
      animationObjects.add(sq2);
      animationObjects.add(sq); 
      
      t = new Timer(5, new AnimationListener());
      t.start();  //Animation starts, but square -won't move yet...
      
      //Here's how to enable keyboard input:
      addKeyListener(new Key());  //Key is a private class defined below
      setFocusable(true);  //Don't forget this!
      up = false;
      down = false;
      w = false;
      s= false;
      onescore = 0;
      twoscore = 0;

   }
   
   
   //overridden methods
   
   public void paintComponent(Graphics g)  
   {
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);  
      g.setColor(Color.WHITE);
      g.setFont(new Font("Serif",Font.BOLD,30));
      g.drawString("" + onescore, 460, 40);
      g.drawString("" + twoscore, 20, 40);
   }
   
   
   
   //instance methods
   
   public void animate()
   {  
      boolean f = ball.collide(sq);
      boolean j = ball.collide(sq2);
      if(f == true){
         ball.LeftPaddle(sq);
      }
      if(j == true){
         ball.RightPaddle(sq2);
      }


      if(ball.hitRightwall() == true){
            twoscore++;
            ball.setX(240);
            ball.setY(240);
            ball.setDY((int)(Math.random()*2));
            ball.setDX(-1);

      }    
      if(ball.hitLeftwall() == true) {
         onescore++;
         ball.setX(250);
         ball.setY(250);
         ball.setDY((int)(Math.random()*2));
         ball.setDX(1);


      }

      if(onescore == 5 || twoscore == 5) {
         sq.setY(200);
         sq2.setY(200);
         if(onescore == 5){
            System.out.println("Winner! Right Side!");
         }else{
            System.out.println("Winner! Left Side!");
         }
            
         t.stop();
      }
      //Clear the current state of myImage by writing over it with a new blank background
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0,0,FRAME,FRAME);

      //Loop through the ArrayList of Animatable objects; do an animation step on each one & draw it
      for(Animatable animationObject : animationObjects)
      {
         animationObject.step();  
         animationObject.drawMe(myBuffer);  
      }
      
      //Call built-in JFrame method repaint(), which calls paintComponent, which puts the next frame on screen
      repaint();

   }
   
   
   
   //private classes
   
   private class AnimationListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)  //Gets called over and over by the Timer
      {
         animate();
      }
   }
   
   private class Key extends KeyAdapter //Make ONE class that EXTENDS KeyAdapter, and tell it what to do when keys are pressed or released
   {
      public void keyPressed(KeyEvent e) //Make ONE method for key presses; this is overridden, and will be called whenever a key is pressed
      {
   
         if(e.getKeyCode() == KeyEvent.VK_UP && !up) //e.getKeyCode() lets us retrieve which key was just pushed.  !left lets us know the user isn't already holding the left arrow down.
         {
            if(sq.getY() > 0) {
            sq2.setDY(sq2.getDY() - 4);  
            up = true;
            }
         }
         // write code for the other keys here
         if(e.getKeyCode() == KeyEvent.VK_DOWN && !down) //e.getKeyCode() lets us retrieve which key was just pushed.  !left lets us know the user isn't already holding the left arrow down.
         {
            if(sq.getY() < 500) {
            sq2.setDY(sq2.getDY() + 4);  
            down = true;  
            }
         }

         if(e.getKeyCode() == KeyEvent.VK_W && !w) //e.getKeyCode() lets us retrieve which key was just pushed.  !left lets us know the user isn't already holding the left arrow down.
         {
            sq.setDY(sq.getDY() - 4);  //Subtract 2 from Square's dX value, effectively setting the value to 0.
            w = true;  //Now, the user is holding down the left key, so set this to true.  Why do we need to keep track of this?  So that holding down one (or even two) key works as expected.
         }
         // write code for the other keys here
         if(e.getKeyCode() == KeyEvent.VK_S && !s) //e.getKeyCode() lets us retrieve which key was just pushed.  !left lets us know the user isn't already holding the left arrow down.
         {
            sq.setDY(sq.getDY() + 4);  //Subtract 2 from Square's dX value, effectively setting the value to 0.
            s = true;  //Now, the user is holding down the left key, so set this to true.  Why do we need to keep track of this?  So that holding down one (or even two) key works as expected.
         }
      }
      
      public void keyReleased(KeyEvent e) //Also overridden; ONE method that will be called any time a key is released
      {
         
         if(e.getKeyCode() == KeyEvent.VK_UP) // If the user lets go of the left arrow
         {            
            sq2.setDY(sq2.getDY() + 4); 
            up = false;  
         
      }
         //write code for the other keys here
         if(e.getKeyCode() == KeyEvent.VK_DOWN) // If the user lets go of the left arrow
         {
            sq2.setDY(sq2.getDY() - 4);  //Again: add 2, don't set to 0 precisely.  
            down = false;  //User is no longer holding the left key, so set this back to false.
            
         }
         //write code for the other keys here
         if(e.getKeyCode() == KeyEvent.VK_W) // If the user lets go of the left arrow
         {
            sq.setDY(sq.getDY() + 4);  //Again: add 2, don't set to 0 precisely.  
            w = false;  //User is no longer holding the left key, so set this back to false.
         }
         //write code for the other keys here
         if(e.getKeyCode() == KeyEvent.VK_S) // If the user lets go of the left arrow
         {
            sq.setDY(sq.getDY() - 4);  //Again: add 2, don't set to 0 precisely.  
            s = false;  //User is no longer holding the left key, so set this back to false.
         }

         
      }
   }
}


