import java.awt.*;
import javax.swing.*;

public class Carromdriver
{
    public static void main(String[] args)
   {
      JFrame mainFrame = new JFrame("Carrom");
      mainFrame.setLocation(20, 20);
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.setContentPane(new CarromPanel());
      mainFrame.getContentPane().setPreferredSize(new Dimension(500,480)); 
      mainFrame.pack();                                                     //Use these two lines together.
      mainFrame.setVisible(true);
   }
}