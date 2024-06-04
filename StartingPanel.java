import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the starting panel of the Carrom game.
 */
public class StartingPanel extends JPanel {
    private ImageIcon i = new ImageIcon("blurred_carrom.jpg"); // Background image
    public static final int FRAME = 750; // Size of the frame
    private Clip clip; // Audio clip
    private boolean isPlaying; // Flag indicating if audio is playing
    JFrame cur; // Reference to the main frame

    /**
     * Constructs the starting panel.
     * @param f The main JFrame.
     */
    public StartingPanel(JFrame f) {
        cur = f;
        setLayout(new GridLayout(6, 1)); // Adjusted to 6 rows for better layout

        // Title label
        JLabel title = new JLabel("Welcome to Carrom!");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        title.setForeground(Color.WHITE);
        title.setBackground(Color.BLACK);
        title.setOpaque(true);
        add(title);

        // Panel for selecting a song
        JPanel songPanel = new JPanel();
        songPanel.setLayout(new GridBagLayout());
        songPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Label for selecting a song
        JLabel sub = new JLabel("Select a Song:");
        sub.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        sub.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        songPanel.add(sub, gbc);

        // Combo box for selecting songs
        String[] songs = {"Heroism", "Warm Inspirational Piano", "Calm Piano"};
        JComboBox<String> songsComboBox = new JComboBox<>(songs);
        songsComboBox.setPreferredSize(new Dimension(150, 25)); // Set preferred size
        gbc.gridx = 1;
        gbc.gridy = 0;
        songPanel.add(songsComboBox, gbc);

        // Button for selecting a song
        JButton select = new JButton("Select");
        select.setPreferredSize(new Dimension(100, 40)); // Set preferred size
        select.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopSound();
                playSound(songsComboBox.getSelectedItem().toString());
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 0;
        songPanel.add(select, gbc);

        add(songPanel);

        // Instruction label
        JLabel inst = new JLabel("Select a Mode (Clicking will Start the Game)");
        inst.setHorizontalAlignment(SwingConstants.CENTER);
        inst.setFont(new Font(Font.SERIF, Font.BOLD, 27));
        inst.setForeground(Color.RED);
        inst.setBackground(Color.BLUE);
        inst.setOpaque(true);
        add(inst);

        // Button for starting the game
        JButton singlePlayer = new JButton("Start Game");
        singlePlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cur.dispose();
                JFrame gameFrame = new JFrame("Carrom");
                gameFrame.setSize(750, 750);
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setContentPane(new CarromPanel(gameFrame, 1));
                gameFrame.setVisible(true);
            }
        });
        singlePlayer.setFont(new Font(Font.SERIF, Font.BOLD, 27));
        add(singlePlayer);

        // Button for viewing leaderboard
        JButton leaderboardButton = new JButton("Score");
        leaderboardButton.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        leaderboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame leaderboardFrame = new JFrame("Leaderboard");
                leaderboardFrame.setSize(400, 400);
                leaderboardFrame.setLocationRelativeTo(null);
                leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                leaderboardFrame.setContentPane(new Leaderboard());
                leaderboardFrame.setVisible(true);
            }
        });
        add(leaderboardButton);

        // Button for viewing scores
        JButton viewScoresButton = new JButton("View Scores");
        viewScoresButton.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        viewScoresButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filename = JOptionPane.showInputDialog("Enter the filename:");
                if (filename != null) {
                    Leaderboard leaderboard = new Leaderboard();
                    java.util.List<String> scoresList = leaderboard.getScores(filename);
                    JTextArea textArea = new JTextArea();
                    for (String score : scoresList) {
                        textArea.append(score + "\n");
                    }
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JFrame scoresFrame = new JFrame("Scores");
                    scoresFrame.setSize(400, 400);
                    scoresFrame.setLocationRelativeTo(null);
                    scoresFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    scoresFrame.add(scrollPane);
                    scoresFrame.setVisible(true);
                }
            }
        });
        add(viewScoresButton);
    }

    /**
     * Plays the selected audio file.
     * @param filename The name of the selected audio file.
     */
    public void playSound(String filename) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename.replace(" ", "_") + ".wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            isPlaying = true; // Set flag to indicate audio is playing
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    isPlaying = false; // Reset flag when audio stops
                    clip.stop();
                    stopSound();
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the currently playing audio.
     */
    public void stopSound() {
        if (clip != null && isPlaying) {
            clip.stop(); // Stop the audio
            clip.close(); // Close the clip
            isPlaying = false; // Reset flag
        }
    }

    /**
     * Overrides the paintComponent method to draw the background image.
     * @param g The Graphics object to draw on.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensure the parent class's paintComponent is called
        g.drawImage(i.getImage(), 0, 0, FRAME, FRAME, null); // Draw the background image
    }
}
