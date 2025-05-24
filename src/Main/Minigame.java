package Main;

import java.awt.*;

public abstract class Minigame {
    protected GamePanel gp;

    public Minigame(GamePanel gp) {
        this.gp = gp;
    }

    public abstract void update(); // Updates the minigame state
    public abstract void draw(Graphics2D g2); // Draws the minigame on the screen
    public abstract boolean isWon();
}
