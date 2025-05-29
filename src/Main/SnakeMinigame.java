package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

public class SnakeMinigame extends Minigame {

    private final int tileSize = 48;
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
    private final int maxLength = (screenWidth * screenHeight) / (tileSize * tileSize);

    Font daydreamFont;

    private float headX, headY;
    private int directionX = 1, directionY = 0;

    private final int[] snakeX = new int[maxLength];
    private final int[] snakeY = new int[maxLength];

    private boolean right = true, left = false, up = false, down = false;
    private int snakeLength = 1;
    private long lastUpdate = System.currentTimeMillis();

    private int foodX, foodY;
    private final Random rand = new Random();

    private boolean running = true;
    private boolean won = false;


    private int spriteNum = 1;
    private BufferedImage background, headRight, headLeft, headUp, headDown, body,
    headRight1, headRight2, headLeft1, headLeft2, headUp1, headUp2, headDown1, headDown2,

    bodyRight, bodyLeft, bodyUp, bodyDown,
    bodyRight1,bodyLeft1,bodyUp1,bodyDown1,
    bodyRight2,bodyLeft2,bodyUp2,bodyDown2;

    public SnakeMinigame(GamePanel gp) {
        super(gp);
        gp.stopMusic();
        gp.playMusic(6);
        loadImages();
        spawnFood();

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Daydream.ttf");
            assert is != null;
            daydreamFont = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch(FontFormatException | IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if(!running)return;

        long now = System.currentTimeMillis();
        if (now - lastUpdate >= 120) { // move every 100ms
            move();
            checkCollision();
            checkEat();
            lastUpdate = now;
        }

        if (snakeLength >= 10) {
            running = false;
            gp.stopMusic();
            won = true;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);
        if (!running) {
            gp.inMinigame = false;
            if(!won){
                int textY = screenHeight / 2;
                g2.setColor(Color.darkGray);
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50f));
                String msg = "Game Over";
                g2.drawString(msg, (screenWidth - g2.getFontMetrics().stringWidth(msg)) / 2, textY);

                textY -= 3;
                g2.setColor(Color.white);
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50f));
                msg = "Game Over";
                g2.drawString(msg, ((screenWidth - g2.getFontMetrics().stringWidth(msg)) / 2) - 3, textY);

                textY += 100;
                g2.setColor(Color.red);
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f));
                msg = "Do you accept this fate?";
                g2.drawString(msg, ((screenWidth - g2.getFontMetrics().stringWidth(msg)) / 2), textY);

                textY += 50;
                g2.setColor(Color.white);
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20f));
                msg = "Yes  or  No";
                g2.drawString(msg, ((screenWidth - g2.getFontMetrics().stringWidth(msg)) / 2), textY);
                //!! NEED TO ADD KEY HANDLER FOR YES OR NO
            }
            return;
        }

        for (int i = 0; i < snakeLength; i++) {
            Image img = getImage(i);
            if (i == 0) {
                g2.drawImage(img, (int) headX, (int) headY, tileSize, tileSize, null);
            } else {
                g2.drawImage(img, snakeX[i], snakeY[i], tileSize, tileSize, null);
            }

        }
        g2.drawImage(body, foodX, foodY, tileSize, tileSize, null);
    }

    private Image getImage(int i) {
        Image img;
        if(i == 0) {
            if (right) img = (spriteNum == 1) ? headRight1 : (spriteNum == 2) ? headRight2 : headRight;
            else if (left) img = (spriteNum == 1) ? headLeft1 : (spriteNum == 2) ? headLeft2 : headLeft;
            else if (up) img = (spriteNum == 1) ? headUp1 : (spriteNum == 2) ? headUp2 : headUp;
            else img = (spriteNum == 1) ? headDown1 : (spriteNum == 2) ? headDown2 : headDown;
        } else {
            int xCurr = snakeX[i];
            int yCurr = snakeY[i];
            int xPrev = snakeX[i - 1];
            int yPrev = snakeY[i - 1];

            if (xPrev < xCurr) { // Coming from left, face left
                img = (spriteNum == 1) ? bodyLeft1 : (spriteNum == 2) ? bodyLeft2 : bodyLeft;
            } else if (xPrev > xCurr) { // Coming from right, face right
                img = (spriteNum == 1) ? bodyRight1 : (spriteNum == 2) ? bodyRight2 : bodyRight;
            } else if (yPrev < yCurr) { // Coming from above, face up
                img = (spriteNum == 1) ? bodyUp1 : (spriteNum == 2) ? bodyUp2 : bodyUp;
            } else { // Coming from below, face down
                img = (spriteNum == 1) ? bodyDown1 : (spriteNum == 2) ? bodyDown2 : bodyDown;
            }
        }
        return img;
    }

    private void loadImages() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/photoBGs/battle-background-sunny-hillsx4.png")));
            //KING
            headRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_right.png")));
            headRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Right_1.png")));
            headRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Right_2.png")));

            headLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_left.png")));
            headLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Left_1.png")));
            headLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Left_2.png")));

            headUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_back.png")));
            headUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Up_1.png")));
            headUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Up_2.png")));

            headDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_front.png")));
            headDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Down_1.png")));
            headDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Down_2.png")));
            //PEOPLE
            body = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_FaceFront.png")));

            bodyRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_FaceRight.png")));
            bodyRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_WalkRight1.png")));
            bodyRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_WalkRight2.png")));

            bodyLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_FaceLeft.png")));
            bodyLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_WalkLeft1.png")));
            bodyLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_WalkLeft2.png")));

            bodyUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_FaceBack.png")));
            bodyUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_WalkUp1.png")));
            bodyUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_WalkUp2.png")));

            bodyDown = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_FaceFront.png")));
            bodyDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_WalkDown1.png")));
            bodyDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/NPC/Mage_WalkDown2.png")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isWon() {
        return won;
    }

    private void move() {
        // Move body
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        // Move head in tile units
        snakeX[0] += directionX * tileSize;
        snakeY[0] += directionY * tileSize;

        // Sync floating head position for smooth rendering
        headX = snakeX[0];
        headY = snakeY[0];

        spriteNum = (spriteNum == 1) ? 2 : 1;
    }


    private void checkEat() {
        if (snakeX[0] == foodX && snakeY[0] == foodY) {
            snakeX[snakeLength] = snakeX[snakeLength - 1];
            snakeY[snakeLength] = snakeY[snakeLength - 1];
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
        // Self-collision
        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                running = false;
                gp.stopMusic();
                return;
            }
        }

        // Wall collision
        if (snakeX[0] < 0 || snakeX[0] >= screenWidth || snakeY[0] < 0 || snakeY[0] >= screenHeight) {
            running = false;
            gp.stopMusic();
        }
    }


    public void handleKeyPress(int code) {
        if (!running) {
            gp.stopMusic();
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_R) {
                resetGame();
            }
            return;
        }

        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.titleState; // Exit minigame
            return;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState; // Pause the game
            return;
        }

        if ((code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) && directionX != -1) {
            directionX = 1; directionY = 0;
            right = true; left = up = down = false;
        }
        if ((code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) && directionX != 1) {
            directionX = -1; directionY = 0;
            left = true; right = up = down = false;
        }
        if ((code == KeyEvent.VK_W || code == KeyEvent.VK_UP) && directionY != 1) {
            directionX = 0; directionY = -1;
            up = true; right = left = down = false;
        }
        if ((code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) && directionY != -1) {
            directionX = 0; directionY = 1;
            down = true; right = left = up = false;
        }
    }

    public void resetGame() {
        gp.playMusic(6);
        snakeLength = 1;
        for (int i = 0; i < snakeLength; i++) {
            snakeX[i] = 0;
            snakeY[i] = 0;
        }

        directionX = 1;
        directionY = 0;
        right = true;
        left = up = down = false;

        running = true;
        won = false;

        spawnFood();
        lastUpdate = System.currentTimeMillis();

        // Set initial head position
        headX = snakeX[0];
        headY = snakeY[0];
    }


}
