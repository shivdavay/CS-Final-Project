import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This class represents the panel for the Carrom game.
 */
public class CarromPanel extends JPanel {

    private JFrame cur; // Reference to the parent JFrame
    private JLabel feedbackLabel; // Label to display feedback
    private ArrayList<CarromPieces> pieces; // List to hold carrom pieces

    /**
     * Constructor for the CarromPanel.
     * @param f The parent JFrame.
     * @param p Number of players.
     */
    public CarromPanel(JFrame f, int p) {
        cur = f;
        pieces = new ArrayList<>(); // Initialize the list of carrom pieces
        
        // Creating and configuring the Carrom board display
        DisplayCarrom board = new DisplayCarrom(p);
        board.addMouseListener(new Mouse());
        board.setPreferredSize(new Dimension(750,750));
        add(board); // Add the Carrom board display to this panel
        
        // Adding feedback label for powder feature
        feedbackLabel = new JLabel();
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Serif", Font.BOLD, 16));
        feedbackLabel.setForeground(Color.RED);
        add(feedbackLabel, BorderLayout.SOUTH); // Add the feedback label to the bottom of this panel

        // Adding Powder key listener
        Powder powder = new Powder(pieces, feedbackLabel);
        cur.addKeyListener(powder); // Add key listener for powder feature
    }

    // Ranges where striker can be
    /**************
    X: 162   Y: 610
    X: 589   Y: 631

    X: 114   Y: 170
    X: 141   Y: 582

    X: 161   Y: 123
    X: 584   Y: 145

    X: 612   Y: 171
    X: 635   Y: 587
    **************/

    /**
     * This inner class handles mouse events.
     */
    private class Mouse extends MouseAdapter {
        
        /**
         * Invoked when a mouse button has been clicked (pressed and released) on the Carrom board.
         * @param e The MouseEvent containing information about the mouse click.
         */
        public void mouseClicked(MouseEvent e) {  
            int x = e.getX();
            int y = e.getY();
            // Check if the click is within the ranges where striker can be placed
            if (x > 160 && x < 590 && y > 610 && y < 630) {
                System.out.println("WORKING"); // Print a message indicating that the click is within the valid range
            } else {
                System.out.println(x + " " + y); // Print the coordinates of the click
            }
        }
    }
}
