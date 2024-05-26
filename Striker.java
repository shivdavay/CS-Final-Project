import java.awt.*;
import javax.swing.*;

public class Striker implements CarromPieces {

    private int x, y, dx, dy, ddx, ddy;
    private boolean going = true;
    private static final int SIZE = 30;

    public Striker(int x1, int y1) {
        x = x1;
        y = y1;
        dx = 0;
        dy = -22; // move the stiker up intially
        ddx = 1;
        ddy = 1;
    }

    public void drawMe(Graphics g) {
        ImageIcon striker = new ImageIcon("striker.jpeg");
        g.drawImage(striker.getImage(), x, y, SIZE, SIZE, null);
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
            dx = obj.getX() - x;
            dy = obj.getY() - y;
            obj.activate();
        }
    }

    public void step() {
        if (going) {
            if (Math.abs(dy) > 0.1) {
                y += dy;
                dy *= .99; 
            } else {
                going = false; 
            }
        }
    }
}
