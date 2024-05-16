import java.awt.*;
import javax.swing.*;



public class Striker implements CarromPieces{

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int ddx;
    private int ddy;
    // private WhitePiece W1;
//     private WhitePiece W2;
//     private WhitePiece W3;
//     private WhitePiece W4;
//     private WhitePiece W5;
//     private WhitePiece W6;
//     private WhitePiece W7;
//     private WhitePiece W8;
//     private BlackPiece B1;
//     private BlackPiece B2;
//     private BlackPiece B3;
//     private BlackPiece B4;
//     private BlackPiece B5;
//     private BlackPiece B6;
//     private BlackPiece B7;
//     private BlackPiece B8;
//     private Red_Queen RQ;
//     private Striker S;
    

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
