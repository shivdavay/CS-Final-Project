import java.awt.*;

// this is an interface

/**
 * This interface represents the carrom pieces in the game.
 */
public interface CarromPieces {
    /**
     * Method to draw the carrom piece on the graphics object.
     * @param g The Graphics object to draw on.
     */
    void drawMe(Graphics g);
    
    /**
     * Get the x-coordinate of the carrom piece.
     * @return The x-coordinate.
     */
    int getX();
    
    /**
     * Get the y-coordinate of the carrom piece.
     * @return The y-coordinate.
     */
    int getY();
    
    /**
     * Set the x-coordinate of the carrom piece.
     * @param x The new x-coordinate.
     */
    void setX(int x);
    
    /**
     * Set the y-coordinate of the carrom piece.
     * @param y The new y-coordinate.
     */
    void setY(int y);
    
    /**
     * Set the horizontal velocity of the carrom piece.
     * @param dx The horizontal velocity.
     */
    void setDx(double dx);
    
    /**
     * Set the vertical velocity of the carrom piece.
     * @param dy The vertical velocity.
     */
    void setDy(double dy);
    
    /**
     * Get the horizontal velocity of the carrom piece.
     * @return The horizontal velocity.
     */
    double getDx();
    
    /**
     * Get the vertical velocity of the carrom piece.
     * @return The vertical velocity.
     */
    double getDy();
    
    /**
     * Check if the carrom piece is active.
     * @return True if active, false otherwise.
     */
    boolean isActive();
    
    /**
     * Activate the carrom piece.
     */
    void activate();
    
    /**
     * Method to handle collision with another carrom piece.
     * @param obj The carrom piece to collide with.
     */
    void collide(CarromPieces obj);
    
    /**
     * Method to update the state of the carrom piece.
     */
    void step();
    
    /**
     * Get the size of the carrom piece.
     * @return The size.
     */
    int getSize();
}
