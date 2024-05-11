import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
//import java.util.ArrayList;

public class CarromPanel extends JPanel
{
   //fields
   private ImageIcon i = new ImageIcon("carrom.jpg");
   //private Image img;
   //img = Toolkit.getDefaultToolkit().createImage("carrom.jpg");
   //Most of this is the same as AnimationPanel
   public static final int FRAME = 500;
   
   private BufferedImage img= new BufferedImage(1600,1200,BufferedImage.TYPE_INT_RGB);

   private Graphics buf = img.getGraphics();
   private int w = img.getWidth();
   private int h = img.getHeight();
  
   //private Graphics myBuffer;  

   
   //constructors
   /**public CarromPanel()
   {
      //myBuffer.setBackground(Color.BLACK); 

      JPanel east = new JPanel();
      east.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 230)); // Set layout to center alignment both horizontally and vertically
      add(east, BorderLayout.CENTER);

      JPanel west = new JPanel();
      east.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 230)); // Set layout to center alignment both horizontally and vertically
      add(west, BorderLayout.CENTER);

      JButton singlePlayer = new JButton("Single Player");
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
      singlePlayer.addActionListener(multiPlayerListener);

      String[] songOptions = {"Song 1", "Song 2", "Song 3"}; // Example song options
      JComboBox<String> songs = new JComboBox<>(songOptions);
      songs.setBackground(Color.BLUE);
      songs.setOpaque(true);
      songs.setForeground(Color.BLACK);
      east.add(songs);

      Listener_songs songListener = new Listener_songs();
      songs.addActionListener(songListener);

     
      
      repaint();
   }

   private class Listener_singlePlayer implements ActionListener
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
  }


  private class Listener_songs implements ActionListener, LineListener {
    private Clip clip;
    private boolean playing;

    public Listener_songs() {
        playing = false;
    }

    public void actionPerformed(ActionEvent e) {
        if (playing) {
            stop();
            return;
        }

        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        String selectedSong = (String) comboBox.getSelectedItem();
        String fileName = "";
        
        songListener.playSong("Song 1");

        setTitle("Music Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);


        switch (selectedSong) {
            case "Song 1":
                fileName = "Heroism.wav";
                break;
            case "Song 2":
                fileName = "Calm_Piano.wav";
                break;
            case "Song 3":
                fileName = "Warm_Inspirational_Piano.wav";
                break;
        }

        if (!fileName.isEmpty()) {
            try {
                File audioFile = new File(fileName);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                clip = AudioSystem.getClip();
                clip.addLineListener(this);
                clip.open(audioStream);
                clip.start();
                playing = true;
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void stop() {
        if (clip != null && playing) {
            clip.stop();
            clip.close();
            playing = false;
        }
    }

    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            clip.setFramePosition(0); // Restart the clip
            clip.start();
        }
    }
  }*/
  public void paintComponent(Graphics g)
  {
    buf.drawImage( i.getImage() , 0 , 0 , w , h , null );
  }  
}






