import java.awt.*;
import javax.swing.*;

public class Red_Queen implements CarromPieces {

    private int x, y, dx, dy, ddx, ddy;
    private boolean going = false;
    private static final int SIZE = 20;

    public Red_Queen(int x1, int y1) {
        x = x1;
        y = y1;
        dx = 0;
        dy = 0;
        ddx = 1;
        ddy = 1;
    }

    public void drawMe(Graphics g) {
        ImageIcon red_queen = new ImageIcon("red_queen.jpg");
        g.drawImage(red_queen.getImage(), x, y, SIZE, SIZE, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void activate() {
        going = true;
    }

    public boolean isActive() {
        return going;
    }

    public void collide(CarromPieces obj) {
        int otx = obj.getX();
        int oty = obj.getY();
        if (Math.abs(otx - x) < SIZE && Math.abs(oty - y) < SIZE && obj.isActive()) {
            activate();
            if (going == false){
               dx = obj.getX() - x;
               dy = obj.getY() - y;
               going = true;
            }else{
               dx = obj.getX() + x;
               dy = obj.getY() + y;
            }
        }
    }

    public void step() {
        if (going) {
            if (Math.abs(dx) > 0.1 || Math.abs(dy) > 0.1) {
                x += dx;
                y += dy;
                dx *= 0.98; 
                dy *= 0.98; 
            } else {
                going = false; 
            }
        }
    }
}