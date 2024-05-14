import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

public class DisplayCarrom extends JPanel{
    private ImageIcon i = new ImageIcon("carrom.jpg");

    private BufferedImage myImage;
    private Graphics myBuffer;
    private Timer t;
    private int players;
    private ArrayList<CarromPieces> animationObjects;

    public DisplayCarrom(int p){
        animationObjects = new ArrayList<CarromPieces>();
        players = p;
        myImage = new BufferedImage(750, 750, BufferedImage.TYPE_INT_RGB);
        myBuffer = myImage.getGraphics();
        myBuffer.setColor(Color.GRAY);
        myBuffer.fillRect(0,0,750,750);
        animationObjects.add(new Striker(375,600));
        t = new Timer(5, new AnimationListener());
        t.start();
    }


    public void animate(){

        // Iterate through each chess piece and animate it
        myBuffer.drawImage(i.getImage(), 0, 0, 750, 750, null);
        for(CarromPieces animationObject : animationObjects){
           animationObject.drawMe(myBuffer);
        }        
        repaint();
     }

    public void paintComponent(Graphics g){
        g.drawImage(myImage, 0, 0, 750, 750, null);
     }

    private class AnimationListener implements ActionListener{
        public void actionPerformed(ActionEvent e)
        {
           animate();
        }
     }
     
}