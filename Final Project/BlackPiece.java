import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;



public class BlackPiece implements CarromPieces{

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int ddx;
    private int ddy;
    
    

    public BlackPiece(int x1, int y1){
        x = x1;
        y = y1;
    }

    public void drawMe(Graphics g)
    {  
      ImageIcon whitepiece = new ImageIcon("blackPiece.jpg"); // draw the piece
      g.drawImage(whitepiece.getImage(),x, y, 15,15,null);
    }
    
}
