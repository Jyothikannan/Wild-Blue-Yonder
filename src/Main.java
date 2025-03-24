
import javax.swing.*;
public class Main {
   // Define the game window's width and height
   public static final int width = 800;  // Window width
   public static final int height = 800; // Window height
   public static void main(String[] args) {
       
// Create the JFrame for the game window
       JFrame gframe = new JFrame("Mini Project - Java Shooter Game");
       
//Set the close operation to exit on close, window size, and prevent resizing
       gframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       gframe.setSize(width, height);
       gframe.setResizable(false);
       
       // Create an instance of GamePanel (the panel for the game)
              GamePanel panel = new GamePanel();
              
     // Add the GamePanel to the JFrame
     gframe.add(panel);

     //Make the JFrame visible
     gframe.setVisible(true);
     
     // Ensure that the panel can listen for key events
     panel.requestFocusInWindow();    //new
     DatabaseUtil.initializeDatabase(); // 
     DatabaseUtil.createTable();  //new 
     
// Start the game by calling the startGame method in GamePanel
     panel.startGame();
 }
}
    