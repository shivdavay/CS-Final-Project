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
    private boolean going=false;

    public Red_Queen(int x1, int y1){
        x = x1;
        y = y1;
        dx=10;
        dy=30;
        ddx=1;
        ddy=1;
    }

    public void drawMe(Graphics g)
    {  
      ImageIcon red_queen = new ImageIcon("red_queen.jpg"); // draw the piece
      g.drawImage(red_queen.getImage(),x, y, 20,20,null);
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
