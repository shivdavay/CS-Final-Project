import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class DisplayCarrom extends JPanel {
    private ImageIcon i = new ImageIcon("carrom.jpg");
    private BufferedImage myImage;
    private Graphics myBuffer;
    private Timer t;
    private int players;
    private ArrayList<CarromPieces> animationObjects;
    private Striker striker;
    private int player1Score;
    private int player2Score;
    private int strikerShots;

    private static final int[][] VALID_RANGES = {
        {162, 610, 589, 631},
        {114, 170, 141, 582},
        {161, 123, 584, 145},
        {612, 171, 635, 587}
    };
    
    // Define the hole positions and radius
    private static final int[][] HOLES = {
        {40, 40}, {690, 40}, {40, 690}, {690, 690}
    };
    private static final int HOLE_RADIUS = 60; // Radius of the hole in pixels

    public DisplayCarrom(int p) {
        animationObjects = new ArrayList<CarromPieces>();
        players = p;
        myImage = new BufferedImage(750, 750, BufferedImage.TYPE_INT_RGB);
        myBuffer = myImage.getGraphics();
        myBuffer.setColor(Color.GRAY);
        myBuffer.fillRect(0, 0, 750, 750);

        player1Score = 0;
        player2Score = 0;
        strikerShots = 0;

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

        t = new Timer(20, new AnimationListener());
        t.start();

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

    public void animate() {
        myBuffer.drawImage(i.getImage(), 0, 0, 750, 750, null);
        for (int i = 0; i < animationObjects.size(); i++) {
            CarromPieces animationObject = animationObjects.get(i);
            animationObject.step();
            for (CarromPieces checkCollision : animationObjects) {
                if (animationObject != checkCollision) {
                    animationObject.collide(checkCollision);
                }
            }
            if (isInHole(animationObject)) {
                if (animationObject != striker) { // Check if the piece is not the striker
                    updateScore(animationObject);
                    animationObjects.remove(i);
                    i--; // Adjust the index after removal
                } else {
                    resetStriker(); // Reset the striker if it falls in a hole
                }
            } else {
                animationObject.drawMe(myBuffer);
            }
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(myImage, 0, 0, 750, 750, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Player 1 Score: " + player1Score, 50, 20);
        g.drawString("Player 2 Score: " + player2Score, 550, 20);
    }

    private class AnimationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            animate();
        }
    }

    private boolean allPiecesStopped() {
        for (CarromPieces piece : animationObjects) {
            if (piece.isActive()) {
                return false;
            }
        }
        return true;
    }

    private boolean isWithinValidRange(int x, int y) {
        for (int[] range : VALID_RANGES) {
            if (x >= range[0] && x <= range[2] && y >= range[1] && y <= range[3]) {
                return true;
            }
        }
        return false;
    }

    private void placeAndLaunchStriker(int x, int y) {
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

    private boolean isInHole(CarromPieces piece) {
        for (int[] hole : HOLES) {
            double distance = Math.sqrt(Math.pow(piece.getX() - hole[0], 2) + Math.pow(piece.getY() - hole[1], 2));
            if (distance <= HOLE_RADIUS) {
                return true;
            }
        }
        return false;
    }

    private void updateScore(CarromPieces piece) {
        if (piece instanceof WhitePiece) {
            player1Score++;
        } else if (piece instanceof BlackPiece) {
            player2Score++;
        } else if (piece instanceof Red_Queen) {
            if (strikerShots % 2 == 0) {
                player1Score += 3;
            } else {
                player2Score += 3;
            }
        } else if (piece instanceof Striker) {
            if (strikerShots % 2 == 0) {
                player1Score -= 1;
            } else {
                player2Score -= 1;
            }
        }
    }

    private void resetStriker() {
        // Reset the striker to its initial position
        striker.setX(365);
        striker.setY(600);
        striker.setDx(0);
        striker.setDy(0);
    }
}