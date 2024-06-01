import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Leaderboard extends JPanel {
   JLabel nameLabel = new JLabel("Name:");
   JLabel scoreLabel = new JLabel("Score:");
   JLabel fileLabel = new JLabel("File:");
   JTextField nameField = new JTextField(20);
   JTextField scoreField = new JTextField(20);
   JTextField fileField = new JTextField(20);
   JButton findButton = new JButton("Find");
   JButton saveButton = new JButton("Save");

   public Leaderboard() {
      setLayout(new BorderLayout());
      
      JPanel inputPanel = new JPanel();
      inputPanel.setLayout(new GridLayout(3, 2));
      inputPanel.add(nameLabel);
      inputPanel.add(nameField);
      inputPanel.add(scoreLabel);
      inputPanel.add(scoreField);
      inputPanel.add(fileLabel);
      inputPanel.add(fileField);

      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new FlowLayout());
      buttonPanel.add(findButton);
      buttonPanel.add(saveButton);

      add(inputPanel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);

      saveButton.addActionListener(new SaveListener());
      findButton.addActionListener(new FindListener());
   }

   class SaveListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         try {
            String filename = fileField.getText();
            FileWriter writer = new FileWriter(filename, true); // Append to the file
            writer.write(nameField.getText() + "\n" + scoreField.getText() + "\n");
            writer.close();
         } catch (IOException ex) {
            System.out.println("Can't save! " + ex);
         }
      }
   }

   class FindListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         try {
            String filename = fileField.getText();
            Scanner infile = new Scanner(new File(filename));
            if (infile.hasNextLine()) {
               nameField.setText(infile.nextLine().strip());
            }
            if (infile.hasNextLine()) {
               scoreField.setText(infile.nextLine().strip());
            }
            infile.close();
         } catch (Exception ex) {
            System.out.println("Can't find file! " + ex);
         }
      }
   }

   public List<String> getScores(String filename) {
      List<String> scoresList = new ArrayList<>();
      try {
         Scanner infile = new Scanner(new File(filename));
         while (infile.hasNextLine()) {
            String name = infile.nextLine().strip();
            if (infile.hasNextLine()) {
               String score = infile.nextLine().strip();
               scoresList.add(name + ": " + score);
            }
         }
         infile.close();
      } catch (Exception ex) {
         System.out.println("Can't find file! " + ex);
      }
      Collections.sort(scoresList);
      return scoresList;
   }
}
