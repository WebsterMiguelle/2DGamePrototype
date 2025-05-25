package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

public class SnakeMinigame extends Minigame {

    private final int tileSize = 48;
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
    private final int maxLength = (screenWidth * screenHeight) / (tileSize * tileSize);

    private final int[] snakeX = new int[maxLength];
    private final int[] snakeY = new int[maxLength];

    private boolean right = true, left = false, up = false, down = false;
    private int snakeLength = 4;
    private long lastUpdate = System.currentTimeMillis();

    private int foodX, foodY;
    private final Random rand = new Random();

    private boolean running = true;
    private boolean won = false;

    private BufferedImage background, headRight, headLeft, headUp, headDown, body;

    public SnakeMinigame(GamePanel gp) {
        super(gp);
        loadImages();
        spawnFood();
    }

    @Override
    public void update() {
        int delay = 175;
        if (!running || System.currentTimeMillis() - lastUpdate < delay) return;
        lastUpdate = System.currentTimeMillis();
        move();
        checkCollision();
        checkEat();
        if (snakeLength >= 20) {
            running = false;
            won = true;
            gp.playMusic(0);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);
        if (!running) {
            g2.setColor(won ? Color.cyan : Color.red);
            g2.setFont(new Font("Courier New", Font.BOLD, 50));
            String msg = won ? "You won the quest!" : "Game Over";
            g2.drawString(msg, (screenWidth - g2.getFontMetrics().stringWidth(msg)) / 2, screenHeight / 2);
            return;
        }

        for (int i = 0; i < snakeLength; i++) {
            Image img = (i == 0)
                    ? (right ? headRight : left ? headLeft : up ? headUp : headDown)
                    : body;
            g2.drawImage(img, snakeX[i], snakeY[i], tileSize, tileSize, null);
        }
        g2.drawImage(body, foodX, foodY, tileSize, tileSize, null);
    }

    private void loadImages() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/photoBGs/battle-background-sunny-hillsx4.png")));
            headRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_right.png")));
            headLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_left.png")));
            headUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_back.png")));
            headDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_front.png")));
            body = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_FaceFront.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isWon() {
        return won;
    }

    private void move() {
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        if (right) snakeX[0] += tileSize;
        if (left) snakeX[0] -= tileSize;
        if (up) snakeY[0] -= tileSize;
        if (down) snakeY[0] += tileSize;
    }

    private void checkEat() {
        if (snakeX[0] == foodX && snakeY[0] == foodY) {
            snakeLength++;
            spawnFood();
        }
    }

    private void spawnFood() {
        boolean overlap;
        do {
            overlap = false;
            foodX = rand.nextInt(maxScreenCol) * tileSize;
            foodY = rand.nextInt(maxScreenRow) * tileSize;
            for (int i = 0; i < snakeLength; i++) {
                if (snakeX[i] == foodX && snakeY[i] == foodY) {
                    overlap = true;
                    break;
                }
            }
        } while (overlap);
    }

    private void checkCollision() {
        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                running = false;
                break;
            }
        }
        if (snakeX[0] < 0 || snakeX[0] >= screenWidth || snakeY[0] < 0 || snakeY[0] >= screenHeight) {
            running = false;
        }
    }

    public void handleKeyPress(int code) {
        if (!running) {
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_R) {
                resetGame();
            }
            return;
        }

        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.titleState; // Exit minigame
            gp.playMusic(0); // Resume background music
            return;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState; // Pause the game
            return;
        }

        if ((code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) && !left) {
            right = true; up = false; down = false; left = false;
        }
        if ((code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) && !right) {
            left = true; up = false; down = false; right = false;
        }
        if ((code == KeyEvent.VK_W || code == KeyEvent.VK_UP) && !down) {
            up = true; right = false; left = false; down = false;
        }
        if ((code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) && !up) {
            down = true; right = false; left = false; up = false;
        }
    }

    public void resetGame() {
        snakeLength = 4;
        for (int i = 0; i < snakeLength; i++) {
            snakeX[i] = tileSize * (snakeLength - i - 1);
            snakeY[i] = 0;
        }

        right = true;
        left = up = down = false;

        running = true;
        won = false;

        spawnFood();
        lastUpdate = System.currentTimeMillis();
    }

}
