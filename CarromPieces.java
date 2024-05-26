import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public interface CarromPieces {
    void drawMe(Graphics g);
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
    void setDx(double dx);
    void setDy(double dy);
    double getDx();
    double getDy();
    boolean isActive();
    void activate();
    void collide(CarromPieces obj);
    void step();
    int getSize();
}

