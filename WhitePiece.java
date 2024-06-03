import java.awt.*;
import javax.swing.*;

/**
 * Represents a white carrom piece.
 */
public class WhitePiece implements CarromPieces {
    private int x, y; // Coordinates of the piece
    private double dx, dy; // Velocities of the piece
    private boolean going = false; // Flag indicating whether the piece is in motion

    // Constants defining the size and dimensions of the game board and play area
    private static final int SIZE = 15;
    private static final int BORDER_SIZE = 40;
    private static final int BOARD_SIZE = 750;
    private static final int PLAY_AREA_SIZE = BOARD_SIZE - 2 * BORDER_SIZE;

    /**
     * Constructs a white carrom piece with the specified coordinates.
     * 
     * @param x1 The x-coordinate of the piece.
     * @param y1 The y-coordinate of the piece.
     */
    public WhitePiece(int x1, int y1) {
        x = x1;
        y = y1;
        dx = 0;
        dy = 0;
    }

    /**
     * Draws the white carrom piece on the graphics context.
     * 
     * @param g The graphics context to draw on.
     */
    public void drawMe(Graphics g) {
        ImageIcon blackpiece = new ImageIcon("whitePiece.jpg");
        g.drawImage(blackpiece.getImage(), x, y, SIZE, SIZE, null);
    }

    /**
     * Gets the x-coordinate of the piece.
     * 
     * @return The x-coordinate of the piece.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the piece.
     * 
     * @return The y-coordinate of the piece.
     */
    public int getY() {
        return y;
    }

    /**
     * Activates the piece, indicating that it is in motion.
     */
    public void activate() {
        going = true;
    }

    /**
     * Checks if the piece is in motion.
     * 
     * @return True if the piece is in motion, false otherwise.
     */
    public boolean isActive() {
        return going;
    }

    /**
     * Handles collision between this piece and another carrom piece.
     * 
     * @param obj The carrom piece with which collision occurs.
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
 * Checks and handles collision with the borders of the play area.
 */
private void checkBorderCollision() {
    // Check for collision with left or right border
    if (x <= BORDER_SIZE || x >= PLAY_AREA_SIZE) {
        dx = -dx; // Reverse direction on x-axis
        // Adjust position to prevent going out of bounds
        if (x <= BORDER_SIZE) x = BORDER_SIZE;
        if (x >= PLAY_AREA_SIZE) x = PLAY_AREA_SIZE;
    }
    // Check for collision with top or bottom border
    if (y <= BORDER_SIZE || y >= PLAY_AREA_SIZE) {
        dy = -dy; // Reverse direction on y-axis
        // Adjust position to prevent going out of bounds
        if (y <= BORDER_SIZE) y = BORDER_SIZE;
        if (y >= PLAY_AREA_SIZE) y = PLAY_AREA_SIZE;
    }
}

/**
 * Moves the piece according to its velocity, checks for collisions, and applies friction.
 */
public void step() {
    if (going) {
        x += dx; // Move horizontally
        y += dy; // Move vertically
        checkBorderCollision(); // Check for border collision and handle it
        dx *= 0.98; // Apply friction to horizontal velocity
        dy *= 0.98; // Apply friction to vertical velocity

        // Check if the piece's velocity is very low, indicating it should stop
        if (Math.abs(dx) < 0.1 && Math.abs(dy) < 0.1) {
            going = false; // Stop movement
        }
    }
}

/**
 * Gets the horizontal velocity of the piece.
 * 
 * @return The horizontal velocity.
 */
public double getDx() {
    return dx;
}

/**
 * Gets the vertical velocity of the piece.
 * 
 * @return The vertical velocity.
 */
public double getDy() {
    return dy;
}

/**
 * Sets the horizontal velocity of the piece.
 * 
 * @param dx The new horizontal velocity.
 */
public void setDx(double dx) {
    this.dx = dx;
}

/**
 * Sets the vertical velocity of the piece.
 * 
 * @param dy The new vertical velocity.
 */
public void setDy(double dy) {
    this.dy = dy;
}

/**
 * Gets the size of the piece.
 * 
 * @return The size of the piece.
 */
public int getSize() {
    return SIZE;
}

/**
 * Sets the x-coordinate of the piece.
 * 
 * @param x The new x-coordinate.
 */
public void setX(int x) {
    this.x = x;
}

/**
 * Sets the y-coordinate of the piece.
 * 
 * @param y The new y-coordinate.
 */
public void setY(int y) {
    this.y = y;
}
}
