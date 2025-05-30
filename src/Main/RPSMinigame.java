package Main;

import Tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

public class RPSMinigame extends Minigame {
    private final int tileSize = 48;
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    private boolean showingAcceptFate = false;
    private boolean minigameCompleted = false;
    private boolean playerWonMinigame = false;

    public int maxLife = 7;
    public int life = maxLife;

    Font daydreamFont;
    BufferedImage title, background, sword, spear, shield, stage,
            player, playerSword, playerShield, playerSpear, npc, npcSword, npcShield, npcSpear, stick;
    Image sword1, spear1, shield1, currentPlayerSprite, currentNpcSprite, stick1;
    Image standardPlayerSprite, standardNpcSprite; // new standard sprites
    int weaponSize = tileSize * 2;
    int spriteSize = tileSize * 5;
    private boolean running = false;
    private boolean won = false;
    private boolean draw = false;
    private boolean gameTitle = true;

    // RPS VARIABLES
    private int playerChoice = -1; // 0: Sword, 1: Shield, 2: Spear
    private int npcChoice = -1;
    private String finalResult = "";
    private int playerWins = 0;
    private int npcWins = 0;
    private int rounds = 0;
    private long gameOverTime = 0;

    private boolean showingResult = false;
    private long resultStartTime = 0;
    private final long roundDisplayDuration = 2000; // 2 seconds for round result
    private final long finalDisplayDuration = 1000 * 30; // 3 seconds for final game resul

    TileManager tileManager;

    public RPSMinigame(GamePanel gp) {
        super(gp);
        running = true;
        loadImages();
        resetGame();

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Daydream.ttf");
            assert is != null;
            daydreamFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImages() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/photoBGs/Battleground1.png")));
            title = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/photoBGs/rps.png")));
            sword = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/SwordT2.png")));
            spear = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/Bow.png")));
            shield = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/ShieldLargeT2.png")));
            stage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/stage4.png")));

            player = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/PlayerStatic.png")));
            playerSword = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/PlayerSword.png")));
            playerShield = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/PlayerShield.png")));
            playerSpear = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/PlayerBow.png")));

            npc = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/EnemyStatic.png")));
            npcSword = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/EnemySword.png")));
            npcShield = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/EnemyShield.png")));
            npcSpear = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/EnemyBow.png")));

            stick = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/coin.png")));

            sword1 = sword.getScaledInstance(weaponSize, weaponSize, Image.SCALE_FAST);
            spear1 = spear.getScaledInstance(weaponSize, weaponSize, Image.SCALE_FAST);
            shield1 = shield.getScaledInstance(weaponSize, weaponSize, Image.SCALE_FAST);
            standardPlayerSprite = player.getScaledInstance(spriteSize, spriteSize, Image.SCALE_SMOOTH);
            standardNpcSprite = npc.getScaledInstance(spriteSize, spriteSize, Image.SCALE_SMOOTH);

            currentPlayerSprite = standardPlayerSprite;
            currentNpcSprite = standardNpcSprite;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (!running) return;

        if (showingResult) {
            long elapsed = System.currentTimeMillis() - resultStartTime;
            long duration = minigameCompleted ? finalDisplayDuration : roundDisplayDuration;
            if (elapsed >= duration) {
                if (minigameCompleted) {
                } else {
                    playerChoice = -1;
                    npcChoice = -1;
                    won = false;
                    draw = false;
                    currentPlayerSprite = standardPlayerSprite;
                    currentNpcSprite = standardNpcSprite;
                    showingResult = false;
                }
            }
        }

    }

    public void draw(Graphics2D g2) {

        if(gameTitle){
            g2.drawImage(title, 0, 0, screenWidth, screenHeight, null);
            return;
        }

        g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);

        if(showingAcceptFate){
            drawAcceptFateScreen(g2);
            return;
        }

        int spriteY = screenHeight / 3 + 10;
        int spriteXOffset = tileSize * 6;

       update();

        if (currentPlayerSprite != null) {
            g2.drawImage(currentPlayerSprite, (screenWidth / 2 - spriteXOffset - tileSize) - 70, spriteY, null);
        }
        if (currentNpcSprite != null) {
            g2.drawImage(currentNpcSprite, (screenWidth / 2 + spriteXOffset - tileSize) - 70, spriteY, null);
        }

        g2.drawImage(stage, 0, 0, screenWidth, screenHeight, null);

        if (minigameCompleted) {
            result(g2);
        } else {
            drawOptions(g2);
            if (playerChoice != -1 && npcChoice != -1) {
                result(g2);
            }

            int playerX = (screenWidth / 2 - (tileSize * 6) - tileSize) - 70;
            int npcX = (screenWidth / 2 + (tileSize * 6) - tileSize) - 70;
            int pointY = 30;

            for (int i = 0; i < playerWins; i++) {
                g2.drawImage(stick, playerX + (i * (tileSize) + 20), pointY, tileSize, tileSize, null);
            }
            for (int i = 0; i < npcWins; i++) {
                g2.drawImage(stick, npcX + (i * (tileSize) + 20), pointY, tileSize, tileSize, null);
            }
        }
    }

    private void drawAcceptFateScreen(Graphics2D g2){
        int textY = screenHeight / 2;
        g2.setColor(Color.darkGray);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50f));
        String msg = "Game Over";
        g2.drawString(msg, (screenWidth - g2.getFontMetrics().stringWidth(msg)) / 2, textY);

        textY -= 3;
        g2.setColor(Color.white);
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
    }

    private void drawOptions(Graphics2D g2) {
        int hiddenBoxY = screenHeight - screenHeight / 4;
        int hiddenBoxHeight = screenHeight - hiddenBoxY;
        int spacing = screenWidth / 4;

        int playerLabelX = (screenWidth / 2 - (tileSize * 6) - tileSize) - 70;
        int npcLabelX = (screenWidth / 2 + (tileSize * 6) - tileSize) - 70;
        int pointY = 30;

        Image[] assets = {sword1, shield1, spear1};
        String[] labels = {"Sword (1)", "Shield (2)", "Bow (3)"};

        for (int i = 0; i < assets.length; i++) {
            int centerX = spacing * (i + 1);
            int x = centerX - weaponSize / 2;
            int y = hiddenBoxY + (hiddenBoxHeight / 2 - weaponSize) + 32;

            // Draw rounded background box
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 10f));
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(labels[i]);
            int boxWidth = Math.max(weaponSize, textWidth) + 20;
            int boxHeight = weaponSize + 25;
            int weaponBoxX = centerX - boxWidth / 2;
            int weaponBoxY = y;

            Color c = new Color(30, 30, 30, 180);
            g2.setColor(c);
            g2.fillRoundRect(weaponBoxX + 6, weaponBoxY + 5, boxWidth, boxHeight, 25, 25);

            c = new Color(0, 0, 0, 220);
            g2.setColor(c);
            g2.fillRoundRect(weaponBoxX, weaponBoxY, boxWidth, boxHeight, 15, 15);

            c = new Color(255, 255, 255);
            g2.setColor(c);
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(weaponBoxX - 3, weaponBoxY - 3, boxWidth + 3, boxHeight + 3, 25, 25);

            // Draw weapon
            g2.drawImage(assets[i], x, y, null);

            // Draw label
            g2.setColor(Color.black);
            g2.drawString(labels[i], (centerX - textWidth / 2) + 1, y + weaponSize + 11);

            g2.setColor(Color.yellow);
            g2.drawString(labels[i], centerX - textWidth / 2, y + weaponSize + 10);

            //Point Standing
            c = new Color(100, 0, 0, 120);
            g2.setColor(c);
            g2.fillRect(playerLabelX, pointY - 5, screenWidth / 4, tileSize + 10);

            g2.setColor(c);
            g2.fillRect(npcLabelX, pointY - 5, screenWidth /4 , tileSize + 10);

            c = new Color(255, 255, 0, 255);
            g2.setColor(c);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(playerLabelX - 3, pointY - 5 - 3, screenWidth / 4 + 5, tileSize + 15);

            g2.setColor(c);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(npcLabelX - 3, pointY - 5 - 3, screenWidth /4 + 5 , tileSize + 15);
        }
    }

    public void result(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50f));
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(Color.YELLOW);
        int x, y;
        if(running) {
            if (draw) {
                finalResult = "Draw!";
            } else if (won) {
                finalResult = "You Win!";
            } else {
                finalResult = "You Lose!";
            }
        }


        x = screenWidth / 2 - fm.stringWidth(finalResult) / 2;
        y = screenHeight / 2;
        g2.drawString(finalResult, x, y);
    }

    private void makeChoice(int choice) {
        Random rand = new Random();
        playerChoice = choice;
        npcChoice = rand.nextInt(3);

        showingResult = true;
        resultStartTime = System.currentTimeMillis();

        won = false;
        draw = false;

        switch (playerChoice) {
            case 0 -> currentPlayerSprite = playerSword.getScaledInstance(spriteSize, spriteSize, Image.SCALE_SMOOTH);
            case 1 -> currentPlayerSprite = playerShield.getScaledInstance(spriteSize, spriteSize, Image.SCALE_SMOOTH);
            case 2 -> currentPlayerSprite = playerSpear.getScaledInstance(spriteSize, spriteSize, Image.SCALE_SMOOTH);
        }

        switch (npcChoice) {
            case 0 -> currentNpcSprite = npcSword.getScaledInstance(spriteSize, spriteSize, Image.SCALE_SMOOTH);
            case 1 -> currentNpcSprite = npcShield.getScaledInstance(spriteSize, spriteSize, Image.SCALE_SMOOTH);
            case 2 -> currentNpcSprite = npcSpear.getScaledInstance(spriteSize, spriteSize, Image.SCALE_SMOOTH);
        }

        if (playerChoice == npcChoice) {
            draw = true;
        } else if (
                (playerChoice == 1 && npcChoice == 0) || // Shield beats Bow
                        (playerChoice == 2 && npcChoice == 1) || // Sword beats Shield
                        (playerChoice == 0 && npcChoice == 2)    // Bow beats Sword
        ) {
            playerWins++;
            won = true;
        } else {
            npcWins++;
        }
        rounds++;

        if (playerWins == 3 || npcWins == 3) {
            running = false;
            showingResult = true;
            resultStartTime = System.currentTimeMillis();
            gameOverTime = resultStartTime;
        }

        if(playerWins == 3){
            minigameCompleted = true;
            playerWonMinigame = true;
            running = false;
        } else if (npcWins == 3){
            minigameCompleted = true;
            playerWonMinigame = false;
            showingAcceptFate = true;
            running = false;
        }

        System.out.println("Player Choice: " + playerChoice + "\n NPC Choice: " + npcChoice);
        System.out.println("Rounds: " + rounds + "\nWins: " + playerWins + "\nLoses: " + npcWins);
    }

    public void resetGame() {
        playerWins = 0;
        npcWins = 0;
        playerChoice = -1;
        npcChoice = -1;
        rounds = 0;
        won = false;
        draw = false;
        resultStartTime = 0;
        showingResult = false;

        minigameCompleted = false;
        playerWonMinigame = false;
        showingAcceptFate = false;

        currentPlayerSprite = standardPlayerSprite;
        currentNpcSprite = standardNpcSprite;
    }

    @Override
    public boolean isWon() {
        return playerWonMinigame;
    }

    public void handleKeyPress(int code) {
        if(showingAcceptFate){
            if(code == KeyEvent.VK_Y){
                gp.gameState = gp.playState;
                gp.playMusic(0);
                gp.inMinigame = false;
            } else if (code == KeyEvent.VK_N){
                resetGame();
                running = true;
            }
            return;
        }
        if (gameTitle) {
            if (code == KeyEvent.VK_NUMPAD1 || code == KeyEvent.VK_NUMPAD2 || code == KeyEvent.VK_NUMPAD3 ||
                    code == KeyEvent.VK_1 || code == KeyEvent.VK_2 || code == KeyEvent.VK_3) {
                gameTitle= false;
            }
            return;
        }

        if (!running) {
            if (minigameCompleted) {
                if (System.currentTimeMillis() - gameOverTime >= resultStartTime) {
                    if (code == KeyEvent.VK_ENTER) {
                        resetGame();
                        running = true;
                    }
                }
                return;
            }
        }

            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.titleState;
                gp.playMusic(0);
                return;
            }


        if (code == KeyEvent.VK_1 || code == KeyEvent.VK_NUMPAD1) {
            if (!showingResult && !minigameCompleted) {
                makeChoice(0);
            }
        } else if (code == KeyEvent.VK_2 || code == KeyEvent.VK_NUMPAD2) {
            if (!showingResult && !minigameCompleted) {
                makeChoice(1);
            }
        } else if (code == KeyEvent.VK_3 || code == KeyEvent.VK_NUMPAD3) {
            if (!showingResult && !minigameCompleted) {
                makeChoice(2);
            }
        }
    }
}

