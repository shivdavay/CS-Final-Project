import java.awt.*;
import javax.swing.*;

public class WhitePiece implements CarromPieces {
    private int x, y;
    private double dx, dy;
    private boolean going = false;
    private static final int SIZE = 15;
    private static final int BORDER_SIZE = 40;
    private static final int BOARD_SIZE = 750;
    private static final int PLAY_AREA_SIZE = BOARD_SIZE - 2 * BORDER_SIZE;

    public WhitePiece(int x1, int y1) {
        x = x1;
        y = y1;
        dx = 0;
        dy = 0;
    }

    public void drawMe(Graphics g) {
        ImageIcon blackpiece = new ImageIcon("whitePiece.jpg");
        g.drawImage(blackpiece.getImage(), x, y, SIZE, SIZE, null);
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
        double otx = obj.getX();
        double oty = obj.getY();
        double distance = Math.hypot(otx - x, oty - y);
        if (distance < (SIZE / 2 + obj.getSize() / 2)) { // Collision detected
            // Calculate the angle of impact
            double angle = Math.atan2(oty - y, otx - x);

            // Calculate new velocities using the principles of elastic collision
            double totalMass = SIZE + obj.getSize();
            double newDx1 = (dx * (SIZE - obj.getSize()) + (2 * obj.getSize() * obj.getDx())) / totalMass;
            double newDy1 = (dy * (SIZE - obj.getSize()) + (2 * obj.getSize() * obj.getDy())) / totalMass;
            double newDx2 = (obj.getDx() * (obj.getSize() - SIZE) + (2 * SIZE * dx)) / totalMass;
            double newDy2 = (obj.getDy() * (obj.getSize() - SIZE) + (2 * SIZE * dy)) / totalMass;

            dx = newDx1;
            dy = newDy1;
            obj.setDx(newDx2);
            obj.setDy(newDy2);

            obj.activate();

            // Adjust positions to prevent overlap
            double overlap = 0.5 * (SIZE / 2 + obj.getSize() / 2 - distance);
            x -= overlap * Math.cos(angle);
            y -= overlap * Math.sin(angle);
            obj.setX((int) (obj.getX() + overlap * Math.cos(angle)));
            obj.setY((int) (obj.getY() + overlap * Math.sin(angle)));
        }
    }

    private void checkBorderCollision() {
        if (x <= BORDER_SIZE || x >= PLAY_AREA_SIZE) {
            dx = -dx; // Reverse direction on x-axis
            if (x <= BORDER_SIZE) x = BORDER_SIZE;
            if (x >= PLAY_AREA_SIZE) x = PLAY_AREA_SIZE;
        }
        if (y <= BORDER_SIZE || y >= PLAY_AREA_SIZE) {
            dy = -dy; // Reverse direction on y-axis
            if (y <= BORDER_SIZE) y = BORDER_SIZE;
            if (y >= PLAY_AREA_SIZE) y = PLAY_AREA_SIZE;
        }
    }

    public void step() {
        if (going) {
            x += dx;
            y += dy;
            checkBorderCollision();
            dx *= 0.98; // Friction
            dy *= 0.98; // Friction

            if (Math.abs(dx) < 0.1 && Math.abs(dy) < 0.1) {
                going = false; // Stop movement
            }
        }
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public int getSize() {
        return SIZE;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
