import java.awt.*;
import javax.swing.*;

public class Carromdriver
{
    public static void main(String[] args)
   {
      JFrame mainFrame = new JFrame("Carrom");
      mainFrame.setLocation(20, 20);
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.setContentPane(new StartingPanel(mainFrame));
      mainFrame.getContentPane().setPreferredSize(new Dimension(950,750)); 
      mainFrame.pack();
      mainFrame.setVisible(true);
   }
}