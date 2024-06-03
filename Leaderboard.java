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

/**
 * This class represents a leaderboard panel.
 */
public class Leaderboard extends JPanel {
   JLabel nameLabel = new JLabel("Name:"); // Label for the name input field
   JLabel scoreLabel = new JLabel("Score:"); // Label for the score input field
   JLabel fileLabel = new JLabel("File:"); // Label for the file input field
   JTextField nameField = new JTextField(20); // Text field for entering name
   JTextField scoreField = new JTextField(20); // Text field for entering score
   JTextField fileField = new JTextField(20); // Text field for entering file name
   JButton findButton = new JButton("Find"); // Button to find data from file
   JButton saveButton = new JButton("Save"); // Button to save data to file

   /**
    * Constructs the leaderboard panel.
    */
   public Leaderboard() {
      setLayout(new BorderLayout()); // Setting layout to BorderLayout
      
      JPanel inputPanel = new JPanel(); // Panel for input fields
      inputPanel.setLayout(new GridLayout(3, 2)); // Using GridLayout for arranging input fields
      inputPanel.add(nameLabel); // Adding name label
      inputPanel.add(nameField); // Adding name input field
      inputPanel.add(scoreLabel); // Adding score label
      inputPanel.add(scoreField); // Adding score input field
      inputPanel.add(fileLabel); // Adding file label
      inputPanel.add(fileField); // Adding file input field

      JPanel buttonPanel = new JPanel(); // Panel for buttons
      buttonPanel.setLayout(new FlowLayout()); // Using FlowLayout for arranging buttons
      buttonPanel.add(findButton); // Adding find button
      buttonPanel.add(saveButton); // Adding save button

      add(inputPanel, BorderLayout.CENTER); // Adding input panel to the center of the panel
      add(buttonPanel, BorderLayout.SOUTH); // Adding button panel to the bottom of the panel

      saveButton.addActionListener(new SaveListener()); // Adding action listener to save button
      findButton.addActionListener(new FindListener()); // Adding action listener to find button
   }

   /**
    * ActionListener for the Save button.
    */
   class SaveListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         try {
            String filename = fileField.getText(); // Get the filename from the text field
            FileWriter writer = new FileWriter(filename, true); // Create a FileWriter to append to the file
            writer.write(nameField.getText() + "\n" + scoreField.getText() + "\n"); // Write name and score to the file
            writer.close(); // Close the FileWriter
         } catch (IOException ex) {
            System.out.println("Can't save! " + ex); // Display error message if unable to save
         }
      }
   }

   /**
    * ActionListener for the Find button.
    */
   class FindListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         try {
            String filename = fileField.getText(); // Get the filename from the text field
            Scanner infile = new Scanner(new File(filename)); // Create a Scanner to read from the file
            if (infile.hasNextLine()) {
               nameField.setText(infile.nextLine().strip()); // Read and set the name from the file
            }
            if (infile.hasNextLine()) {
               scoreField.setText(infile.nextLine().strip()); // Read and set the score from the file
            }
            infile.close(); // Close the Scanner
         } catch (Exception ex) {
            System.out.println("Can't find file! " + ex); // Display error message if unable to find file
         }
      }
   }

   /**
    * Retrieves scores from a file and returns them as a sorted list.
    * @param filename The name of the file to read scores from.
    * @return A sorted list of scores in the format "name: score".
    */
   public List<String> getScores(String filename) {
      List<String> scoresList = new ArrayList<>(); // List to hold scores
      try {
         Scanner infile = new Scanner(new File(filename)); // Create a Scanner to read from the file
         while (infile.hasNextLine()) {
            String name = infile.nextLine().strip(); // Read name from file
            if (infile.hasNextLine()) {
               String score = infile.nextLine().strip(); // Read score from file
               scoresList.add(name + ": " + score); // Add name and score to the list
            }
         }
         infile.close(); // Close the Scanner
      } catch (Exception ex) {
         System.out.println("Can't find file! " + ex); // Display error message if unable to find file
      }
      Collections.sort(scoresList); // Sort the list of scores
      return scoresList; // Return the sorted list
   }
}
