import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.util.ArrayList; 
import java.util.Random; 
import javax.imageio.ImageIO; 
import java.awt.image.BufferedImage; 
import java.io.IOException; 
import java.io.File; 
import javax.sound.sampled.*; 
public class GamePanel extends JPanel implements ActionListener, KeyListener { 
    private Timer stopWatch; 
    private Timer enemySpawner; 
    private Player player; 
    private ArrayList<Enemy> enemies; 
    private Random random; 
    private boolean gameOver; 
    private boolean startScreen; 
    private static final int MAX_ENEMIES = 10; 
    private int score; 
    private int highScore; 
    private Clip backgroundMusicClip; 
 
    public static final int PANEL_WIDTH = 800; 
    public static final int PANEL_HEIGHT = 800; 
 
    private BufferedImage backgroundImage; 
    private BufferedImage startBackgroundImage; 
    private BufferedImage gameOverImage; 
 
    // Puzzle related variables 
    private boolean puzzleTriggered = false; 
 
    private JPanel mcqPanel; 
 
    public GamePanel() { 
        player = new Player(PANEL_WIDTH / 2, PANEL_HEIGHT - 50); 
        stopWatch = new Timer(16, this); // 60 FPS 
        enemySpawner = new Timer(2500, e -> spawnEnemies()); 
        enemies = new ArrayList<>(); 
        random = new Random(); 
        gameOver = false; 
        startScreen = true; 
        score = 0; 
        highScore = getHighScoreFromDatabase(); 
 
        // Initialize MCQPanel but don't display it yet 
        
 
        loadImages(); 
        setFocusable(true); 
        requestFocusInWindow(); 
        addKeyListener(this); 
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT)); 
        loadAudio(); 
    } 
 
    private void loadImages() { 
        try { 
            backgroundImage = ImageIO.read(getClass().getResource("/assets/game bg 1.png")); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
 
        try { 
            startBackgroundImage = ImageIO.read(getClass().getResource("/assets/Start game png.png")); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
 
        try { 
            gameOverImage = ImageIO.read(getClass().getResource("/assets/game over png.png")); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
 
    // Load background music 
    private void loadAudio() { 
        try { 
            File musicFile = new File(getClass().getResource("/assets/fun-piano-bgm242448.wav").toURI()); 
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile); 
            backgroundMusicClip = AudioSystem.getClip(); 
            backgroundMusicClip.open(audioStream); 
            System.out.println("Background music loaded successfully."); 
        } catch (Exception e) { 
            e.printStackTrace(); 
            System.out.println("Error: Unable to load background music."); 
        } 
    } 
     
    public void startGame() { 
        stopWatch.start(); 
        enemySpawner.start(); 
        gameOver = false; 
        enemies.clear(); 
        score = 0; // Reset score when game starts 
        if (backgroundMusicClip != null) { 
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Play music in a loop 
        } 
     
    } 
 
    private void spawnEnemies() { 
        if (enemies.size() < MAX_ENEMIES) { 
            int spawnX = random.nextInt(PANEL_WIDTH - 40); 
            enemies.add(new Enemy(spawnX, 0)); 
        } 
    } 
 
    @Override 
    protected void paintComponent(Graphics g) { 
        super.paintComponent(g); 
 
        if (startScreen) { 
            drawStartScreen(g); 
        } else if (gameOver) { 
            drawGameOverScreen(g); 
        } else { 
            drawGameplay(g); 
            g.setColor(Color.BLACK); 
            g.setFont(new Font("Arial", Font.BOLD, 20)); 
            g.drawString("Score: " + score, 10, 30); 
            g.drawString("High Score: " + highScore, PANEL_WIDTH - 150, 30); 
        } 
    } 
 
    private void drawStartScreen(Graphics g) { 
        if (startBackgroundImage != null) { 
            g.drawImage(startBackgroundImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, this); 
        } else { 
            g.setColor(Color.BLACK); 
            g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT); 
            g.setColor(Color.WHITE); 
            g.drawString("Press any key to start", PANEL_WIDTH / 2 - 50, PANEL_HEIGHT / 2); 
        } 
    } 
 
    private void drawGameOverScreen(Graphics g) { 
        if (gameOverImage != null) { 
            g.drawImage(gameOverImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, this); 
        } else { 
            g.setColor(Color.RED); 
            g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT); 
            g.setColor(Color.WHITE); 
            g.drawString("Game Over", PANEL_WIDTH / 2 - 50, PANEL_HEIGHT / 2); 
        } 
        g.setColor(Color.YELLOW); 
        g.setFont(new Font("Arial", Font.PLAIN, 20)); 
        g.drawString("High Score: " + highScore, PANEL_WIDTH - 150, 30); 
    } 
 
    private void drawGameplay(Graphics g) { 
        if (backgroundImage != null) { 
            g.drawImage(backgroundImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, this); 
        } 
 
        player.draw(g); 
        for (Enemy enemy : enemies) { 
            enemy.draw(g); 
        } 
    } 
 
    @Override 
    public void actionPerformed(ActionEvent e) { 
        if (!gameOver && !startScreen) { 
            updateGameState(); 
        } 
        repaint(); 
    } 
 
    private void updateGameState() { 
        player.update(); 
 
        for (int i = 0; i < enemies.size(); i++) { 
            Enemy enemy = enemies.get(i); 
            enemy.update(); 
 
            if (enemy.getY() > PANEL_HEIGHT) { 
                gameOver = true; 
                stopGame(); 
                return; 
            } 
        } 
 
        checkCollisions(); 
    } 
 
    private void checkCollisions() { 
        ArrayList<Bullet> bullets = new ArrayList<>(player.getBullets()); 
        for (int i = 0; i < enemies.size(); i++) { 
            Enemy enemy = enemies.get(i); 
            for (Bullet bullet : bullets) { 
                if (bullet.getBounds().intersects(enemy.getBounds())) { 
                    enemies.remove(i); 
                    player.removeBullet(bullet); 
                    i--; 
                    score++; 
                     // Trigger method when an enemy is destroyed 
                    break; 
                } 
            } 
        } 
 
        for (Enemy enemy : enemies) { 
            if (enemy.getBounds().intersects(player.getBounds())) { 
                gameOver = true; 
                stopGame(); 
                break; 
            } 
        } 
 
        if (gameOver) { 
            Score scoreHandler = new Score(); 
            scoreHandler.calculateHighScore(score); 
            scoreHandler.saveCurrentScore(score); 
            highScore = getHighScoreFromDatabase(); 
        } 
    } 
 
    private void stopGame() { 
        stopWatch.stop(); 
        enemySpawner.stop(); 
        System.out.println("Game Over"); 
    } 
 
     
     
 
    @Override 
    public void keyPressed(KeyEvent e) { 
        if (startScreen) { 
            startScreen = false; 
            startGame(); 
        } else { 
            player.keyPressed(e); 
        } 
    } 
 
    @Override 
    public void keyReleased(KeyEvent e) { 
        player.keyReleased(e); 
    } 
 
    @Override 
    public void keyTyped(KeyEvent e) { 
        // Not used 
    } 
 
    private int getHighScoreFromDatabase() { 
        Score scoreHandler = new Score(); 
        return scoreHandler.getHighScore(); 
    } 
} 
