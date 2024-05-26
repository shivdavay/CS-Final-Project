import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public interface CarromPieces {
    void drawMe(Graphics g);
    void step();
    void collide(CarromPieces obj);
    int getX();
    int getY();
    void activate();
    boolean isActive();
}
