import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;



public class Red_Queen implements CarromPieces{

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int ddx;
    private int ddy;

    public Red_Queen(int x1, int y1){
        x = x1;
        y = y1;
    }

    public void drawMe(Graphics g)
    {  
      ImageIcon red_queen = new ImageIcon("red_queen.jpg"); // draw the piece
      g.drawImage(red_queen.getImage(),x, y, 20,20,null);
    }
    
}
