# Wild-Blue-Yonder
A simple shooting game implemented in Java using **Swing** and **AWT**, where the player moves around and shoots bullets to destroy enemies. The game features collision detection, enemy spawning, and a start/game-over screen for better user experience.  

---
## Preview
<img src="https://github.com/user-attachments/assets/544b9b89-7ca7-4310-b214-3379a429c6ea" width="400" height="auto" style="border-radius: 15px;" />


## Features  
**Game Window** – A square (800x800) game window.  
**Player Movement** – Controlled using arrow keys.  
**Shooting Mechanism** – Press `SPACE` to fire bullets.  
**Enemies** – Appear at random positions and move downward.  
**Collision Detection** – Bullets destroy enemies on impact.  
**Game Start & Game Over Screens** – Displays appropriate messages.  

---

## Project Structure  

### **1. Main.java**  
- Initializes the game window (`800x800`).  
- Calls an instance of `GamePanel`.  

### **2. GamePanel.java**  
- Handles game logic, rendering, and event listeners.  
- Manages the player, bullets, and enemies.  
- Includes timers for enemy spawning and movement.  
- Implements **collision detection** between bullets and enemies.  
- Displays the **Start Game** and **Game Over** screens.  

### **3. Player.java**  
- Manages player attributes (position, movement, speed).  
- Controls player movement using arrow keys.  
- Allows shooting bullets by pressing `SPACE`.  
- Stores and updates a list of fired bullets.  

### **4. Bullet.java**  
- Represents a bullet fired by the player.  
- Moves upwards at a constant speed.  
- Removes itself when it moves off-screen.  

### **5. Enemy.java**  
- Represents enemy objects that move downward.  
- Spawns randomly at the top of the screen.  
- Gets destroyed when hit by a bullet.  

---

## Game Controls  

| Key | Action |  
|------|--------|  
| ⬆️ | Move Up |  
| ⬇️ | Move Down |  
| ⬅️ | Move Left |  
| ➡️ | Move Right |  
| Spacebar | Shoot Bullets |  
| Any Key (Start Screen) | Start the Game |  

---

## Game Logic Flow  

1. **Start Screen** – The game starts with a welcome screen. Press any key to begin.  
2. **Gameplay** – Move the player using arrow keys and shoot bullets using `SPACE`.  
3. **Enemy Spawning** – Enemies appear at random positions and move downward.  
4. **Collision Detection** – If a bullet hits an enemy, the enemy is destroyed.  
5. **Game Over** – The game ends when an enemy reaches the bottom of the screen.  

---

## How to Run the Game  

1. Clone the repository:  
   ```sh
   git clone https://github.com/Jyothikannan/Wild-Blue-Yonder.git
   cd shooting-game
   ```
2. Compile the java files :
   ```sh
   javac -d bin src/*.java
   ```
3. Run the game :
   ```sh
   java -cp bin Main
   ```

---
## Note :
This project served as an educational resource for understanding game loops, event-driven programming, collision detection, and rendering in Java Swing. It was designed to be beginner-friendly while demonstrating key game development concepts. Suggestions for improvements are welcome.
