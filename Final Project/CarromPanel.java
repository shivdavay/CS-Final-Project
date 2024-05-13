import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarromPanel extends JPanel
{
   //fields
   private ImageIcon i = new ImageIcon("blurred_carrom.jpg");
   private CarromPanel carromPanel;
   public static final int FRAME = 500;
   private Clip clip; // Define the clip variable
    private boolean isPlaying;
   private BufferedImage img = new BufferedImage(1600, 1200, BufferedImage.TYPE_INT_RGB);

   private Graphics buf;
   private int w = img.getWidth();
   private int h = img.getHeight();
   //private Graphics myBuffer;  
    

   
   //constructors
   public CarromPanel()
   {
      //myBuffer.setBackground(Color.BLACK); 

      JPanel east = new JPanel();
      east.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 230)); // Set layout to center alignment both horizontally and vertically
      add(east, BorderLayout.CENTER);

      JPanel west = new JPanel();
      east.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 230)); // Set layout to center alignment both horizontally and vertically
      add(west, BorderLayout.CENTER);

      /**JButton singlePlayer = new JButton("Single Player");
      singlePlayer.setHorizontalAlignment(SwingConstants.CENTER);
      singlePlayer.setBackground(Color.BLUE);
      singlePlayer.setOpaque(true);
      singlePlayer.setForeground(Color.BLACK);
      east.add(singlePlayer);
   
      Listener_singlePlayer singlePlayerListener = new Listener_singlePlayer();
      singlePlayer.addActionListener(singlePlayerListener);

      JButton multiPlayer = new JButton("Multi Player");
      multiPlayer.setHorizontalAlignment(SwingConstants.CENTER);
      multiPlayer.setBackground(Color.BLUE);
      multiPlayer.setOpaque(true);
      multiPlayer.setForeground(Color.BLACK);
      west.add(multiPlayer);
   
      Listener_multiPlayer multiPlayerListener = new Listener_multiPlayer();
      singlePlayer.addActionListener(multiPlayerListener);*/

      String[] songs = {"Heroism.wav", "Warm_Inspirational_Piano.wav", "Calm_Piano.wav"};
        JComboBox<String> songsComboBox = new JComboBox<>(songs);
        songsComboBox.addActionListener(e -> {
            playSound((String) songsComboBox.getSelectedItem());
        });
       
      add(songsComboBox);

      Listener_songs songListener = new Listener_songs(this);
      songsComboBox.addActionListener(songListener);

     
      
      repaint();
   }

   /**private class Listener_singlePlayer implements ActionListener
   {
       public void actionPerformed(ActionEvent e)
       {
             JFrame frame = new JFrame("Carrom Single Player");
             frame.setLocation(20, 20);
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             frame.setContentPane(new CarromSinglePanel());
             frame.getContentPane().setPreferredSize(new Dimension(500,480));  //NEW: This is a BETTER way to set a 500x500 panel.
             frame.pack();                                                     //Use these two lines together.
             frame.setVisible(true);
       }

   }

  private class Listener_multiPlayer implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
         JFrame frame = new JFrame("Carrom Single Player");
         frame.setLocation(20, 20);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(new CarromSinglePanel());
         frame.getContentPane().setPreferredSize(new Dimension(500,480));  //NEW: This is a BETTER way to set a 500x500 panel.
         frame.pack();                                                     //Use these two lines together.
         frame.setVisible(true);
    }
  }*/
  
  private void playSound(String filename) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename));
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
  
  private void stopSound() {
    if (clip != null && isPlaying) {
        clip.stop();
        clip.close();
        isPlaying = false;
    }
}
  
  private class Listener_songs implements ActionListener
  {
    private CarromPanel carromPanel;

    public Listener_songs(CarromPanel carromPanel) {
        this.carromPanel = carromPanel;
    }
    public void actionPerformed(ActionEvent e)
    {
       JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        String selectedSong = (String) comboBox.getSelectedItem();
        carromPanel.playSound(selectedSong);
    }
  }
  public void paintComponent(Graphics g)
  {
   g.drawImage(i.getImage(), 0, 0, w, h, null);
  }
}

  






