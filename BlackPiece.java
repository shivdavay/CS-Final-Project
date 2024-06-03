import java.awt.*;
import javax.swing.*;

/**
 * The BlackPiece class represents a black piece in a carrom game.
 * It implements the CarromPieces interface and handles the behavior of the piece,
 * including drawing, movement, collision detection, and response.
 */
public class BlackPiece implements CarromPieces {
    // Position coordinates
    private int x, y;
    // Velocity components
    private double dx, dy;
    // State of movement
    private boolean going = false;
    // Constants for size and play area
    private static final int SIZE = 15;
    private static final int BORDER_SIZE = 40;
    private static final int BOARD_SIZE = 750;
    private static final int PLAY_AREA_SIZE = BOARD_SIZE - 2 * BORDER_SIZE;

    /**
     * Constructor to initialize the black piece with a specific position.
     * 
     * @param x1 the initial x-coordinate of the piece
     * @param y1 the initial y-coordinate of the piece
     */
    public BlackPiece(int x1, int y1) {
        x = x1;
        y = y1;
        dx = 0;
        dy = 0;
    }

    /**
     * Draws the black piece on the provided Graphics context.
     * 
     * @param g the Graphics context to draw the piece
     */
    public void drawMe(Graphics g) {
        // Load the image for the black piece
        ImageIcon blackpiece = new ImageIcon("blackPiece.jpg");
        // Draw the piece at its current position with its size
        g.drawImage(blackpiece.getImage(), x, y, SIZE, SIZE, null);
    }

    /**
     * Gets the current x-coordinate of the piece.
     * 
     * @return the x-coordinate of the piece
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the current y-coordinate of the piece.
     * 
     * @return the y-coordinate of the piece
     */
    public int getY() {
        return y;
    }

    /**
     * Activates the piece, setting it to a moving state.
     */
    public void activate() {
        going = true;
    }

    /**
     * Checks if the piece is currently active (moving).
     * 
     * @return true if the piece is active, false otherwise
     */
    public boolean isActive() {
        return going;
    }

    /**
     * Handles collision with another CarromPieces object.
     * 
     * @param obj the CarromPieces object to check for collision
     */
    public void collide(CarromPieces obj) {
        // Get the other piece's position
        double otx = obj.getX();
        double oty = obj.getY();
        // Calculate the distance between the pieces
        double distance = Math.hypot(otx - x, oty - y);
        // Check if the distance is less than the sum of their radii (collision)
        if (distance < (SIZE / 2 + obj.getSize() / 2)) {
            // Calculate the angle of impact
            double angle = Math.atan2(oty - y, otx - x);

            // Calculate new velocities using the principles of elastic collision
            double totalMass = SIZE + obj.getSize();
            double newDx1 = (dx * (SIZE - obj.getSize()) + (2 * obj.getSize() * obj.getDx())) / totalMass;
            double newDy1 = (dy * (SIZE - obj.getSize()) + (2 * obj.getSize() * obj.getDy())) / totalMass;
            double newDx2 = (obj.getDx() * (obj.getSize() - SIZE) + (2 * SIZE * dx)) / totalMass;
            double newDy2 = (obj.getDy() * (obj.getSize() - SIZE) + (2 * SIZE * dy)) / totalMass;

            // Update the velocities
            dx = newDx1;
            dy = newDy1;
            obj.setDx(newDx2);
            obj.setDy(newDy2);

            // Activate the other piece
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
     * Checks for and handles collisions with the borders of the play area.
     */
    private void checkBorderCollision() {
        // Check for collision with the left or right border
        if (x <= BORDER_SIZE || x >= PLAY_AREA_SIZE) {
            dx = -dx; // Reverse direction on x-axis
            // Ensure the piece stays within the play area
            if (x <= BORDER_SIZE) x = BORDER_SIZE;
            if (x >= PLAY_AREA_SIZE) x = PLAY_AREA_SIZE;
        }
        // Check for collision with the top or bottom border
        if (y <= BORDER_SIZE || y >= PLAY_AREA_SIZE) {
            dy = -dy; // Reverse direction on y-axis
            // Ensure the piece stays within the play area
            if (y <= BORDER_SIZE) y = BORDER_SIZE;
            if (y >= PLAY_AREA_SIZE) y = PLAY_AREA_SIZE;
        }
    }

    /**
     * Updates the position of the piece based on its velocity and checks for collisions.
     */
    public void step() {
        if (going) {
            // Update position based on velocity
            x += dx;
            y += dy;
            // Check for border collisions
            checkBorderCollision();
            // Apply friction to the velocity
            dx *= 0.98;
            dy *= 0.98;

            // Stop the piece if its velocity is very small
            if (Math.abs(dx) < 0.1 && Math.abs(dy) < 0.1) {
                going = false;
            }
        }
    }

    /**
     * Gets the current x-axis velocity of the piece.
     * 
     * @return the x-axis velocity
     */
    public double getDx() {
        return dx;
    }

    /**
     * Gets the current y-axis velocity of the piece.
     * 
     * @return the y-axis velocity
     */
    public double getDy() {
        return dy;
    }

    /**
     * Sets the x-axis velocity of the piece.
     * 
     * @param dx the new x-axis velocity
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Sets the y-axis velocity of the piece.
     * 
     * @param dy the new y-axis velocity
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Gets the size (diameter) of the piece.
     * 
     * @return the size of the piece
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Sets the x-coordinate of the piece.
     * 
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the piece.
     * 
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }
}
