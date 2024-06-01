import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.ArrayList;

public class Powder extends KeyAdapter {
    private ArrayList<CarromPieces> pieces;
    private JLabel feedbackLabel;

    public Powder(ArrayList<CarromPieces> pieces, JLabel feedbackLabel) {
        this.pieces = pieces;
        this.feedbackLabel = feedbackLabel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            for (CarromPieces piece : pieces) {
                if (piece instanceof Striker) {
                    Striker striker = (Striker) piece;
                    striker.setDx(striker.getDx() + 0.5);
                    striker.setDy(striker.getDy() + 0.5);
                    feedbackLabel.setText("Powder Added, Striker Speed Increased");
                }
            }
            
        }
    }
}
