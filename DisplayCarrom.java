import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

/**
 * DisplayCarrom class represents the panel where the Carrom game is displayed.
 */
public class DisplayCarrom extends JPanel {
    private ImageIcon i = new ImageIcon("carrom.jpg"); // Background image
    private BufferedImage myImage; // Image for drawing on
    private Graphics myBuffer; // Graphics object for drawing
    private Timer t; // Timer for animation
    private int players; // Number of players
    private ArrayList<CarromPieces> animationObjects; // List of game objects
    private Striker striker; // The striker object
    private int player1Score; // Player 1 score
    private int player2Score; // Player 2 score
    private int strikerShots; // Number of shots taken by the striker

    // Define the valid ranges where the striker can be placed
    private static final int[][] VALID_RANGES = {
        {162, 610, 589, 631},
        {114, 170, 141, 582},
        {161, 123, 584, 145},
        {612, 171, 635, 587}
    };
    
    // Define the hole positions and radius
    private static final int[][] HOLES = {
        {40, 40}, {690, 40}, {40, 695}, {690, 690}
    };
    private static final int HOLE_RADIUS = 60; // Radius of the hole in pixels

    /**
     * Constructor for DisplayCarrom class.
     * @param p The number of players.
     */
    public DisplayCarrom(int p) {
        animationObjects = new ArrayList<CarromPieces>();
        players = p;
        myImage = new BufferedImage(750, 750, BufferedImage.TYPE_INT_RGB);
        myBuffer = myImage.getGraphics();
        myBuffer.setColor(Color.GRAY);
        myBuffer.fillRect(0, 0, 750, 750); // Fill background with gray

        player1Score = 0;
        player2Score = 0;
        strikerShots = 0;

        // Initialize game objects
        striker = new Striker(365, 600);
        animationObjects.add(striker);
        animationObjects.add(new Red_Queen(365, 365));
        animationObjects.add(new WhitePiece(365, 345));
        animationObjects.add(new WhitePiece(350, 365));
        animationObjects.add(new WhitePiece(333, 382));
        animationObjects.add(new WhitePiece(380, 365));
        animationObjects.add(new WhitePiece(335, 350));
        animationObjects.add(new WhitePiece(355, 332));
        animationObjects.add(new WhitePiece(388, 337));
        animationObjects.add(new WhitePiece(365, 380));
        animationObjects.add(new WhitePiece(397, 367));
        animationObjects.add(new BlackPiece(400, 384));
        animationObjects.add(new BlackPiece(353, 382));
        animationObjects.add(new BlackPiece(383, 382));
        animationObjects.add(new BlackPiece(353, 353));
        animationObjects.add(new BlackPiece(383, 353));
        animationObjects.add(new BlackPiece(338, 368));
        animationObjects.add(new BlackPiece(345, 338));
        animationObjects.add(new BlackPiece(375, 335));
        animationObjects.add(new BlackPiece(397, 355));

        // Start animation timer
        t = new Timer(20, new AnimationListener());
        t.start();

        // Add mouse listener for placing and launching striker
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                if (allPiecesStopped() && isWithinValidRange(mouseX, mouseY)) {
                    placeAndLaunchStriker(mouseX, mouseY);
                }
            }
        });
    }

    /**
     * Animates the game.
     */
    public void animate() {
        myBuffer.drawImage(i.getImage(), 0, 0, 750, 750, null); // Draw background
        for (int i = 0; i < animationObjects.size(); i++) {
            CarromPieces animationObject = animationObjects.get(i);
            animationObject.step(); // Move object
            for (CarromPieces checkCollision : animationObjects) {
                if (animationObject != checkCollision) {
                    animationObject.collide(checkCollision); // Check for collisions
                }
            }
            if (isInHole(animationObject)) {
                if (animationObject != striker) {
                    updateScore(animationObject); // Update score if piece falls into hole
                    animationObjects.remove(i);
                    i--; // Adjust index after removal
                } else {
                    resetStriker(); // Reset the striker if it falls in a hole
                }
            } else {
                animationObject.drawMe(myBuffer); // Draw object
            }
        }
        repaint();
    }

    /**
     * Paints the component.
     * @param g The Graphics object to paint on.
     */
    public void paintComponent(Graphics g) {
        g.drawImage(myImage, 0, 0, 750, 750, null); // Draw image
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Player 1 Score: " + player1Score, 50, 20);
        g.drawString("Player 2 Score: " + player2Score, 550, 20);
    }

    /**
     * ActionListener for animation.
     */
    private class AnimationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            animate();
        }
    }

    /**
     * Checks if all game pieces have stopped moving.
     * @return true if all pieces have stopped, false otherwise.
     */
    private boolean allPiecesStopped() {
        for (CarromPieces piece : animationObjects) {
            if (piece.isActive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given coordinates are within a valid range for placing the striker.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return true if the coordinates are within a valid range, false otherwise.
     */
    private boolean isWithinValidRange(int x, int y) {
        for (int[] range : VALID_RANGES) {
            if (x >= range[0] && x <= range[2] && y >= range[1] && y <= range[3]) {
                return true;
            }
        }
        return false;
    }    private void placeAndLaunchStriker(int x, int y) {
        striker.setX(x);
        striker.setY(y);

        // Calculate the direction to the center (365, 365)
        int centerX = 365;
        int centerY = 365;
        double dx = centerX - x;
        double dy = centerY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Normalize the direction and scale by the desired speed
        double speed = 15; // Adjust speed as necessary
        dx = (dx / distance) * speed;
        dy = (dy / distance) * speed;

        striker.setDx(dx);
        striker.setDy(dy);
        striker.activate();
        strikerShots++; // Increment striker shots count
    }
   /**
    * Checks if a carrom piece is in any of the holes on the carrom board.
    * @param piece The carrom piece to check.
    * @return True if the carrom piece is in a hole, otherwise false.
    */
   private boolean isInHole(CarromPieces piece) {
       // Iterate through each hole on the carrom board
       for (int[] hole : HOLES) {
           // Calculate the distance between the piece and the current hole
           double distance = Math.sqrt(Math.pow(piece.getX() - hole[0], 2) + Math.pow(piece.getY() - hole[1], 2));
           // If the distance is less than or equal to the hole radius, the piece is in a hole
           if (distance <= HOLE_RADIUS) {
               return true;
           }
       }
       // If the piece is not in any hole, return false
       return false;
   }
   
   /**
    * Updates the score based on the type of carrom piece that was pocketed.
    * @param piece The carrom piece that was pocketed.
    */
   private void updateScore(CarromPieces piece) {
       // Check the type of carrom piece and update the scores accordingly
       if (piece instanceof WhitePiece) {
           player1Score++;
       } else if (piece instanceof BlackPiece) {
           player2Score++;
       } else if (piece instanceof Red_Queen) {
           // If the Red Queen is pocketed, the player who pocketed it gains 3 points
           if (strikerShots % 2 == 0) {
               player1Score += 3;
           } else {
               player2Score += 3;
           }
       } else if (piece instanceof Striker) {
           // If the Striker is pocketed, the player who pocketed it loses 1 point
           if (strikerShots % 2 == 0) {
               player1Score -= 1;
           } else {
               player2Score -= 1;
           }
       }
   }
   
   /**
    * Resets the position and velocity of the striker to its initial state.
    */
   private void resetStriker() {
       // Reset the position and velocity of the striker
       striker.setX(365);
       striker.setY(600);
       striker.setDx(0);
       striker.setDy(0);
   }
}