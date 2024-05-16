import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public interface CarromPieces {
    public void drawMe(Graphics g);
    public void step();
    public void collide(CarromPieces obj);
    public int getX();
    public int getY();
    public void activate();
}
