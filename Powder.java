import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.ArrayList;

/**
 * The Powder class handles key events for adding powder to the striker.
 */
public class Powder extends KeyAdapter {
    private ArrayList<CarromPieces> pieces; // List of carrom pieces
    private JLabel feedbackLabel; // Label for providing feedback

    /**
     * Constructs a Powder object.
     * @param pieces The list of carrom pieces.
     * @param feedbackLabel The label for providing feedback.
     */
    public Powder(ArrayList<CarromPieces> pieces, JLabel feedbackLabel) {
        this.pieces = pieces;
        this.feedbackLabel = feedbackLabel;
    }

    /**
     * Handles key pressed events.
     * @param e The KeyEvent object representing the key event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Check if the W key is pressed
        if (e.getKeyCode() == KeyEvent.VK_W) {
            System.out.println("W key pressed: Striker speed increased");
            // Iterate over each carrom piece
            for (CarromPieces piece : pieces) {
                // Check if the current piece is a Striker
                if (piece instanceof Striker) {
                    Striker striker = (Striker) piece; // Cast to Striker
                    // Increase the striker speed
                    striker.setDx(striker.getDx() * 1.5);
                    striker.setDy(striker.getDy() * 1.5);
                    // Update feedback label
                    feedbackLabel.setText("Powder Added, Striker Speed Increased");
                    // Print message to console
                }
            }
        }
    }
}
