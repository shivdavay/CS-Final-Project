import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingPanel extends JPanel {
   //fields
   private ImageIcon i = new ImageIcon("blurred_carrom.jpg");
   public static final int FRAME = 750;
   private Clip clip; // Define the clip variable
   private boolean isPlaying;
   JFrame cur;

   //constructors
   public StartingPanel(JFrame f) {
      cur = f;
      setLayout(new GridLayout(6, 1));
      String[] songs = {"Heroism", "Warm Inspirational Piano", "Calm Piano"};
      JComboBox<String> songsComboBox = new JComboBox<>(songs);
      JPanel song = new JPanel(new GridLayout(1, 3));
      song.setOpaque(false);
      JLabel sub = new JLabel("Select a Song:");
      sub.setFont(new Font(Font.SERIF, Font.BOLD, 20));
      sub.setHorizontalAlignment(SwingConstants.CENTER);
      sub.setForeground(Color.BLACK);
      song.add(sub);

      JLabel title = new JLabel("Welcome to Carrom!");
      title.setHorizontalAlignment(SwingConstants.CENTER);
      title.setFont(new Font(Font.SERIF, Font.BOLD, 50));
      title.setForeground(Color.WHITE);
      title.setBackground(Color.BLACK);
      title.setOpaque(true);
      JButton select = new JButton("Select");
      select.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
      select.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            stopSound();
            playSound(songsComboBox.getSelectedItem().toString());
         }
      });

      JLabel inst = new JLabel("Select a Mode (Clicking will Start the Game)");
      inst.setHorizontalAlignment(SwingConstants.CENTER);
      inst.setFont(new Font(Font.SERIF, Font.BOLD, 27));
      inst.setForeground(Color.RED);
      inst.setBackground(Color.BLUE);
      inst.setOpaque(true);

      JButton leaderboardButton = new JButton("Score");
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

      JButton viewScoresButton = new JButton("View Scores");
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

      JButton singlePlayer = new JButton("Single Player");
      JButton doublePlayer = new JButton("Double Player");
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

      doublePlayer.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            cur.dispose();
            JFrame gameFrame = new JFrame("Carrom");
            gameFrame.setSize(750, 750);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setContentPane(new CarromPanel(gameFrame, 2));
            gameFrame.setVisible(true);
         }
      });

      singlePlayer.setFont(new Font(Font.SERIF, Font.BOLD, 27));
      doublePlayer.setFont(new Font(Font.SERIF, Font.BOLD, 27));

      add(title);
      song.add(songsComboBox);
      song.add(select);
      add(song);
      add(inst);
      add(singlePlayer);
      add(doublePlayer);
      add(leaderboardButton);
      add(viewScoresButton);
   }

   public void playSound(String filename) {
      try {
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename.replace(" ", "_") + ".wav"));
         clip = AudioSystem.getClip();
         clip.open(audioInputStream);
         clip.start();
         isPlaying = true;
         clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
               clip.close();
               isPlaying = false;
               clip.stop();
               stopSound();
            }
         });
      } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   public void stopSound() {
      if (clip != null && isPlaying) {
         clip.stop();
         clip.close();
         isPlaying = false;
      }
   }

   public void paintComponent(Graphics g) {
      super.paintComponent(g); // Ensure the parent class's paintComponent is called
      g.drawImage(i.getImage(), 0, 0, FRAME, FRAME, null);
   }
}
