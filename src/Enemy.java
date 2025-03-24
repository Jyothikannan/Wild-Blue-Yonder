import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Image;  
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Enemy {
    private int x, y;
    private int speed = 2;
    private int width = 40;
    private int height = 40;
    private BufferedImage spirite;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            spirite =  ImageIO.read(getClass().getResource("/assets/golem-preview.png")); 
            System.out.println("Image loaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in loading the image");
        }
    }

    public void update() {
        y += speed;  // Move enemy downwards
    }

    public void draw(Graphics g) {
        if (spirite != null) {
            Image scaledImage = spirite.getScaledInstance(width, height, Image.SCALE_SMOOTH);  // Scaled Image
            g.drawImage(scaledImage, x, y, width, height, null);
        } else {
            g.setColor(Color.GRAY);  // Color for the enemy
            g.fillRect(x, y, width, height);  // Draw the enemy rectangle
        }
    }

    public boolean isOffScreen() {
        return y > Main.height;  // Check if the enemy has moved off the screen
    }

    // Getters for x, y, width, and height
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);  // Create a rectangle for collision detection
    }
}
