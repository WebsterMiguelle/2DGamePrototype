package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class HangmanMinigame extends Minigame {
    private final int tileSize = 48;
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    public int maxLife = 7;
    public int life = maxLife;

    BufferedImage background, heartFull, heartHalf, heartBlank;
    private boolean running = false;
    private boolean won = false;

    //HANGMAN VARIABLES
    private final WordDB wordDB;
    private int incorrectGuesses;
    private String[] wordChallenge;
    private char[] displayWord;



    public HangmanMinigame(GamePanel gp) {
        super(gp);

        running = true;
        life = maxLife;
        loadImages();
        wordDB = new WordDB();
        wordChallenge = wordDB.loadChallenge();

        String actualWord = wordChallenge[1];
        displayWord = new char[actualWord.length()];
        for (int i = 0; i < actualWord.length(); i++) {
            displayWord[i] = (actualWord.charAt(i) == ' ') ? ' ' : '_';
        }
    }

    @Override
    public void update() {
        // Only run logic if the minigame is active
        if (!running) return;
    }

    private void loadImages() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/photoBGs/battle-background-sunny-hillsx4.png")));
            heartFull = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/heart_full.png")));
            heartHalf = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/heart_half.png")));
            heartBlank = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/heart_blank.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);
        if (running) {

            if (wordChallenge == null) {
                g2.setColor(Color.RED);
                g2.drawString("No challenge loaded!", 100, 100);
                return;
            }
            // CATEGORY LABEL
            String category = wordChallenge[0];
            int labelHeight = 50;

            Font font = new Font("Courier New", Font.BOLD, 30);
            g2.setFont(font);
            FontMetrics metrics = g2.getFontMetrics(font);
            int textWidth = metrics.stringWidth(category);
            int textHeight = metrics.getAscent();

            int boxX = 0;
            int boxY = screenHeight / 2 - labelHeight / 2; // Lifted for visibility

            int textX = (screenWidth - textWidth) / 2;
            int textY = boxY+ (labelHeight + textHeight) / 2-4;

            Color yellowTransparent = new Color(255, 255, 0, 180);
            g2.setColor(yellowTransparent);
            g2.fillRect(boxX, boxY, screenWidth, labelHeight);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(boxX, boxY, screenWidth - 3, labelHeight);

            g2.setColor(Color.black);
            g2.drawString(category,  textX, textY);

            //HIDDEN WORD PANEL
            Font hiddenFont = new Font("Arial", Font.BOLD, 70);
            g2.setFont(hiddenFont);
            FontMetrics metrics1 = g2.getFontMetrics(hiddenFont);
            String word = WordDB.hideWords(new String(displayWord));

            int hiddenTextWidth = metrics1.stringWidth(word);
            int hiddenTextHeight = metrics1.getAscent();

            int hiddenBoxX = 0;
            int hiddenBoxY = boxY + labelHeight;

            int hiddenBoxHeight = screenHeight - boxY;

            int hiddenTextX = (screenWidth - hiddenTextWidth) / 2;
            int hiddenTextY = hiddenBoxY + (hiddenBoxHeight - hiddenTextHeight) / 2 + hiddenTextHeight/2;

            Color blackTransparent = new Color(0, 0, 0, 120);
            g2.setColor(blackTransparent);
            g2.fillRect(hiddenBoxX, hiddenBoxY, screenWidth, hiddenBoxHeight);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(hiddenBoxX, hiddenBoxY, screenWidth - 3, hiddenBoxHeight);

            g2.setColor(Color.white);
            g2.drawString(word,  hiddenTextX, hiddenTextY);

            //PLAYER HEART LIVES STATUS
            int x = gp.tileSize / 2;
            int y = gp.tileSize / 2;

            for (int i = 0; i < maxLife / 2; i++) {
                g2.drawImage(heartBlank, x + i * gp.tileSize, y, gp.tileSize, gp.tileSize, null);
            }

            // Draw full hearts
            x = gp.tileSize / 2;
            int fullHearts = life / 2;
            for (int i = 0; i < fullHearts; i++) {
                g2.drawImage(heartFull, x + i * gp.tileSize, y, gp.tileSize, gp.tileSize, null);
            }

            // If odd number, draw half heart
            if (life % 2 != 0) {
                g2.drawImage(heartHalf, x + fullHearts * gp.tileSize, y, gp.tileSize, gp.tileSize, null);
            }

        }
        if (!running) {
            g2.setColor(won ? Color.cyan : Color.red);
            g2.setFont(new Font("Courier New", Font.BOLD, 50));
            String msg = won ? "You won the quest!" : "Game Over";
            g2.drawString(msg, (screenWidth - g2.getFontMetrics().stringWidth(msg)) / 2, screenHeight / 2);
            return;
        }

        }


        @Override
        public boolean isWon() {
            return false;
        }

        public void handleKeyPress(int code) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.titleState;
                gp.playMusic(0);
                return;
            }
            if (code >= KeyEvent.VK_A && code <= KeyEvent.VK_Z) {
                char guessedChar = (char) code;
                guessedChar = Character.toLowerCase(guessedChar); // normalize input

                String actualWord = wordChallenge[1].toLowerCase();
                char[] wordChars = actualWord.toCharArray();

                // Loop through each character in the word
                boolean found = false;
                for (int i = 0; i < actualWord.length(); i++) {
                    if (wordChars[i] == guessedChar) {
                        displayWord[i] = wordChallenge[1].charAt(i); // Keep original case
                        found = true;

                    }
                }
                if (!found) {
                    life--;
                }
                if (new String(displayWord).equalsIgnoreCase(wordChallenge[1])) {
                    won = true;
                    running = false;
                }
                if(life <= 0) {
                    won = false;
                    running = false;
                }
            }

        }
}

