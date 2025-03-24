
import java.awt.Graphics;
import java.awt.Color; 
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
   private int x,y;
   private int width=70;
   private int height=70;
   private int speed=5;
   private boolean movingLeft = false;
   private boolean movingRight = false;
   private boolean movingUp = false;
   private boolean movingDown = false;
   //new 
   private ArrayList<Bullet> bullets;
   private BufferedImage playerImage;
     public Player(int x, int y){
       this.x =x;
       this.y=y;
       //new
       bullets = new ArrayList<>();
       try{
           playerImage=ImageIO.read(getClass().getResource("/assets/shooter_3-removebg-preview.png"));
       }catch(IOException e){
          e.printStackTrace();
          System.out.println("Error in loading image");
       }
   }
   //added a getter to access the private bullet arraylist
   public List<Bullet> getBullets() {
       return Collections.unmodifiableList(bullets);
   }
   //added a remove bullet method to safely remove bullets without arraylist going out of bounds
   public void removeBullet(Bullet b){
       bullets.remove(b);
   }
   public void addBullet(Bullet bullet) {
       bullets.add(bullet);
   } 
   public void draw(Graphics g){
       if(playerImage !=null){
           g.drawImage(playerImage,x,y,width,height,null);
       }else{
           g.setColor(Color.MAGENTA);
           g.fillRect(x,y,width,height);

       }
       for (Bullet bullet : bullets) {
           bullet.draw(g);
       }
}
public void update(){
   if (movingLeft && x>0) x -= speed;
   if (movingRight && x<(800-width)) x += speed;
   if (movingUp && y>0) y -= speed;
   if (movingDown && y<(600-height)) y += speed;
//new
// Update bullets
    for (int i = 0; i < bullets.size(); i++) {
       Bullet bullet = bullets.get(i);
       bullet.update();
       if (bullet.isOffScreen())
{
           bullets.remove(i);
           i--;
       }
   }
}
public void keyPressed(KeyEvent e){
   switch(e.getKeyCode()){
       case KeyEvent.VK_LEFT:
       movingLeft=true;
       break;
       case KeyEvent.VK_RIGHT:
       movingRight=true;
       break;
       case KeyEvent.VK_UP:
       movingUp=true;
       break;
       case KeyEvent.VK_DOWN:
       movingDown=true;
       break;
       
   }
 //new
   if (e.getKeyCode() == KeyEvent.VK_SPACE) {
       bullets.add(new Bullet(x + width / 2 - 2, y)); // Centers bullet
   }
}
public void keyReleased(KeyEvent e){
   switch(e.getKeyCode()){
       case KeyEvent.VK_LEFT:
       movingLeft=false;
       break;
       case KeyEvent.VK_RIGHT:
       movingRight=false;
       break;
       case KeyEvent.VK_UP:
       movingUp=false;
       break;
       case KeyEvent.VK_DOWN:
       movingDown=false;
       break;

   }
}
public void reset() {
   this.x = 400; // Reset position to center horizontally
   this.y = 600; // Reset position to the bottom
   this.bullets.clear(); // Clear any remaining bullets
   // Reset other variables if needed
}
public Rectangle getBounds() {
   return new Rectangle(x, y, width, height);
}
}

