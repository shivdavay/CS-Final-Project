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
        animationObjects.add(new Striker(365,600));
        animationObjects.add(new Red_Queen(365, 365));
        animationObjects.add(new WhitePiece(365, 345));
        animationObjects.add(new WhitePiece(350, 365));
        animationObjects.add(new WhitePiece(333, 382));
        animationObjects.add(new WhitePiece(380, 365));
        animationObjects.add(new WhitePiece(335, 350));
        animationObjects.add(new WhitePiece(355, 332));
        //animationObjects.add(new WhitePiece(340, 380));
        animationObjects.add(new WhitePiece(365, 380));
        //animationObjects.add(new WhitePiece(340, 300));
        //animationObjects.add(new WhitePiece(340, 320));
        animationObjects.add(new BlackPiece(353, 382));
        animationObjects.add(new BlackPiece(383, 382));
        animationObjects.add(new BlackPiece(353, 353));
        animationObjects.add(new BlackPiece(383, 353));
        animationObjects.add(new BlackPiece(338, 368));
        animationObjects.add(new BlackPiece(345, 338));
        animationObjects.add(new BlackPiece(375, 335));
        
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