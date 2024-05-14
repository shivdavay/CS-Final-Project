import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;



public class Striker implements CarromPieces{

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int ddx;
    private int ddy;

    public Striker(int x1, int y1){
        x = x1;
        y = y1;
    }

    public void drawMe(Graphics g)
    {  
      ImageIcon striker = new ImageIcon("striker.jpeg"); // draw the piece
      g.drawImage(striker.getImage(),x, y, 30,30,null);
    }
    
}
