import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil{
    private static final String DB_URL = "jdbc:sqlite:scores.db";
    
    static {
        try {
            // manually forcing the database to load incase the auto load doesn't work
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        }
    }

    //connecting to database
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL);
    }

    // testing the connection
    public static void initializeDatabase(){
        try(Connection conn = getConnection()){
            if(conn != null){
                System.out.println("Connected to SQLite Database");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

 // creating score table
 public static void createTable(){
    String createHitsTable = """
            CREATE TABLE IF NOT EXISTS hits(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            hit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
            """;

    // creating high score table
    String createHighScoresTable = """
        CREATE TABLE IF NOT EXISTS high_scores(
        id INTEGER PRIMARY KEY CHECK (id=1),
        score INTEGER NOT NULL);
        """;

        try(Connection conn = getConnection();
            Statement stmt = conn.createStatement()) {

            stmt.execute(createHitsTable);
            stmt.execute(createHighScoresTable);

            // default high score = 0
            stmt.execute("INSERT INTO high_scores (id, score) VALUES (1, 0) " +
                         "ON CONFLICT (id) DO NOTHING;");
            System.out.println("Score Tables created successfully");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
