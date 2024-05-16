import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;



public class WhitePiece implements CarromPieces{

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int ddx;
    private int ddy;
    private boolean going=false;
    
    

    public WhitePiece(int x1, int y1){
        x = x1;
        y = y1;
        dx=10;
        dy=50;
        ddx=1;
        ddy=1;
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

    public void drawMe(Graphics g)
    {  
      ImageIcon whitepiece = new ImageIcon("whitePiece.jpg"); // draw the piece
      g.drawImage(whitepiece.getImage(),x, y, 20,20,null);
    }
    public void collide(CarromPieces obj){

    }

    public void step(){
      if(y<45 || y>715){
        y*=-1;
        dy*=-1;
    }
      if(dy>0 && going){
        y-=dy;
        dy-=ddy;
    }
    }
    
}
