
 import java.awt.Graphics;
 import java.awt.Color;
 import java.awt.image.BufferedImage;
 import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
 import java.awt.Rectangle;

 public class Bullet {
    private int x, y;
    private final int speed = 10;
    private static BufferedImage bulletImage;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        loadImage();
    }
    public void loadImage(){
        if(bulletImage == null){
            try{
                bulletImage = ImageIO.read(getClass().getResource("/assets/bullet2.png"));
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("Error in loading image");
            }
        } 
    }
    public void update() {
        y -= speed; // Move up
    }
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, 5, 10);
    }
    public boolean isOffScreen() {
        return y < 0;
    }
    public Rectangle getBounds(){
        return new Rectangle(x,y,bulletImage != null ? bulletImage.getWidth() : 5,
        bulletImage != null ? bulletImage.getHeight() : 10);
    }
    //added getters here too, since collision detection requires them
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
 }