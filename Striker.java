import java.awt.*;
import javax.swing.*;

/**
 * This class represents a carrom piece called Striker.
 */
public class Striker implements CarromPieces {
    private int x, y; // Position of the striker
    private double dx, dy; // Velocity of the striker
    private boolean going = true; // Flag indicating if the striker is moving
    private static final int SIZE = 30; // Size of the striker
    private static final int BORDER_SIZE = 40; // Size of the border around the board
    private static final int BOARD_SIZE = 750; // Size of the board
    private static final int PLAY_AREA_SIZE = BOARD_SIZE - 2 * BORDER_SIZE; // Play area size

    /**
     * Constructs a Striker object with given initial position.
     * @param x1 The initial x-coordinate of the striker.
     * @param y1 The initial y-coordinate of the striker.
     */
    public Striker(int x1, int y1) {
        x = x1;
        y = y1;
        dx = 0;
        dy = -22; // Move the striker up initially
    }

    /**
     * Draws the striker on the graphics object.
     * @param g The Graphics object to draw on.
     */
    public void drawMe(Graphics g) {
        ImageIcon striker = new ImageIcon("striker.jpeg");
        g.drawImage(striker.getImage(), x, y, SIZE, SIZE, null);
    }

    /**
     * Gets the x-coordinate of the striker.
     * @return The x-coordinate of the striker.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the striker.
     * @return The y-coordinate of the striker.
     */
    public int getY() {
        return y;
    }

    /**
     * Activates the striker to start moving.
     */
    public void activate() {
        going = true;
    }

    /**
     * Checks if the striker is active.
     * @return True if the striker is active, otherwise false.
     */
    public boolean isActive() {
        return going;
    }

    /**
     * Handles collision between the striker and another carrom piece.
     * @param obj The carrom piece to collide with.
     */
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

    /**
     * Checks and handles border collision of the striker.
     */
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

    /**
     * Moves the striker according to its velocity and handles friction.
     */
    public void step() {
        if (going) {
            x += dx;
            y += dy;
            checkBorderCollision();
            dx *= 0.99; // Friction
            dy *= 0.99; // Friction

            if (Math.abs(dx) < 0.1 && Math.abs(dy) < 0.1) {
                going = false; // Stop movement
            }
        }
    }

    /**
     * Gets the horizontal velocity of the striker.
     * @return The horizontal velocity of the striker.
     */
    public double getDx() {
        return dx;
    }

    /**
     * Gets the vertical velocity of the striker.
     * @return The vertical velocity of the striker.
     */
    public double getDy() {
        return dy;
    }

    /**
     * Sets the horizontal velocity of the striker.
     * @param dx The new horizontal velocity of the striker.
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Sets the vertical velocity of the striker.
     * @param dy The new vertical velocity of the striker.
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Gets the size of the striker.
     * @return The size of the striker.
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Sets the x-coordinate of the striker.
     * @param x The new x-coordinate of the striker.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the striker.
     * @param y The new y-coordinate of the striker.
     */
    public void setY(int y) {
        this.y = y;
    }
}
