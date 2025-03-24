import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Score {

    // Record a hit (insert a new hit into the 'hits' table)
    public void recordHit() {
        String sql = "INSERT INTO hits DEFAULT VALUES"; 
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.executeUpdate(); 
            System.out.println("Hit recorded!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveCurrentScore(int currentScore) {
        String insertScoreQuery = "INSERT INTO scores (score) VALUES (?)"; // Insert the score into a 'scores' table
    
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertScoreQuery)) {
    
            pstmt.setInt(1, currentScore); // Set the current score
            pstmt.executeUpdate(); // Execute the insert
            System.out.println("Current score saved to database.");
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Calculate and update the high score
    public void calculateHighScore(int currentScore) {
        String selectHighScoreQuery = "SELECT score FROM high_scores WHERE id = 1"; 
        String updateHighScoreQuery = "UPDATE high_scores SET score = ? WHERE id = 1 AND score < ?"; 

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectHighScoreQuery);
             ResultSet rs = selectStmt.executeQuery()) {

            if (rs.next()) {
                int highScore = rs.getInt("score"); // Get the current high score

                // If current score exceeds the high score, update it
                if (currentScore > highScore) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateHighScoreQuery)) {
                        updateStmt.setInt(1, currentScore);  // Set the new high score
                        updateStmt.setInt(2, currentScore);  // Ensure it's greater than the existing high score
                        updateStmt.executeUpdate();
                        System.out.println("High score updated to: " + currentScore);
                    }
                } else {
                    System.out.println("High score not updated. Current score did not exceed high score.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getHighScore() {
        String sql = "SELECT score FROM high_scores WHERE id = 1"; // Query to fetch the high score
        int highScore = 0;  // Default high score

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                highScore = rs.getInt("score"); // Get the high score from the database
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return highScore;
    }

    // Method to display the current high score
    public void displayHighScore() {
        String sql = "SELECT score FROM high_scores WHERE id = 1"; // Query to fetch the high score

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int highScore = rs.getInt("score"); 
                System.out.println("High Score: " + highScore); // show the high score
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

