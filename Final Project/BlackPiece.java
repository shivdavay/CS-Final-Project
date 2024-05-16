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
    private boolean going=false;
    
    

    public BlackPiece(int x1, int y1){
        x = x1;
        y = y1;
        dx=10;
        dy=30;
        ddx=1;
        ddy=1;
    }

    public void drawMe(Graphics g)
    {  
      ImageIcon whitepiece = new ImageIcon("blackPiece.jpg"); // draw the piece
      g.drawImage(whitepiece.getImage(),x, y, 15,15,null);
    }
    public int getX(){
      return x;
    }

    public int getY(){
      return y;
    }

    public void activate(){
      going=true;
    }

    public void collide(CarromPieces obj){

    }

    public void step(){
      if(y<(45+dy) || y>715){
        y*=-1;
        dy*=-1;
      }
      if(dy>0 && going){
        y-=dy;
        dy-=ddy;
      }

    }
    
}
