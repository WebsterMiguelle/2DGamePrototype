package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class HangmanMinigame extends Minigame {
    private final int tileSize = 48;
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
    private final int originalScreenWidth = screenWidth;
    private final int originalScreenHeight = screenHeight;

    private int correctAnswers = 0;

    Font daydreamFont;

    public int maxLife = 5;
    public int life = 5;

    BufferedImage title, background,
    life0,life1, life2, life3, life4, life5, life6, life7;
    private boolean gameTitle = true;
    private boolean running = false;
    private boolean won = false;
    private boolean finished = false;
    private boolean guessedOnce = false;
    private boolean lostSound = false;

    //HANGMAN VARIABLES
    private final WordDB wordDB;
    private int incorrectGuesses;
    private String[] wordChallenge;
    private char[] displayWord;



    public HangmanMinigame(GamePanel gp) {
        super(gp);
        gp.stopMusic();
        gp.playMusic(4);
        running = true;
        loadImages();
        wordDB = new WordDB();
        wordChallenge = wordDB.loadChallenge();

        String actualWord = wordChallenge[1];
        displayWord = new char[actualWord.length()];
        for (int i = 0; i < actualWord.length(); i++) {
            displayWord[i] = (actualWord.charAt(i) == ' ') ? ' ' : '_';

            try {
                InputStream is = getClass().getResourceAsStream("/fonts/Daydream.ttf");
                assert is != null;
                daydreamFont = Font.createFont(Font.TRUETYPE_FONT, is);

            } catch(FontFormatException | IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update() {
        // Only run logic if the minigame is active
        if (!running)
            return;
    }

    private void loadImages() {
        try {
            title = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/photoBGs/hangman.png")));
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/photoBGs/night-sky-background-zoomed.png")));
            life0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/life0.png")));
            life1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/life1.png")));
            life2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/life2.png")));
            life3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/life3.png")));
            life4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/life4.png")));
            life5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/minigame/life5.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        if(gameTitle) {
            g2.drawImage(title, 0, 0, screenWidth, screenHeight, null);
            return;
        }
        g2.drawImage(background, 0, 0, screenWidth, screenHeight, null);
        if (running) {
            if (wordChallenge == null) {
                g2.setColor(Color.RED);
                g2.drawString("No challenge loaded!", 100, 100);
                return;
            }
            Image lifeImage = null;
            switch(life){
                case 0 -> lifeImage = life0;
                case 1 -> lifeImage = life1;
                case 2 -> lifeImage = life2;
                case 3 -> lifeImage = life3;
                case 4 -> lifeImage = life4;
                case 5 -> lifeImage = life5;
            }

            if (lifeImage != null) {
                int centerX = (screenWidth - 400) / 2;
                int centerY = (screenHeight - 400) / 2;

                float opacity = 0.5f;
                Composite originalComposite = g2.getComposite();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2.drawImage(lifeImage, centerX, centerY, 400, 400, null);
                g2.setComposite(originalComposite);
            }

            // CATEGORY LABEL
            String category = wordChallenge[0];
            int labelHeight = 50;

            Font font = g2.getFont().deriveFont(Font.PLAIN, 20f);
            g2.setFont(font);
            FontMetrics metrics = g2.getFontMetrics(font);
            int textWidth = metrics.stringWidth(category);
            int textHeight = metrics.getAscent();

            int boxX = 0;
            int boxY = screenHeight / 2 - labelHeight / 2; // Lifted for visibility

            int textX = (screenWidth - textWidth) / 2;
            int textY = boxY+ (labelHeight + textHeight) / 2-4;

            g2.setColor(Color.WHITE);
            String[] lines = category.split("=");
            int lineHeight = g2.getFontMetrics().getHeight();
            int totalTextHeight = lines.length * lineHeight;
            int startY = boxY + (labelHeight - totalTextHeight) / 2 + g2.getFontMetrics().getAscent();

            for (int i = 0; i < lines.length; i++) {
                int lineWidth = g2.getFontMetrics().stringWidth(lines[i]);
                int lineX = (screenWidth - lineWidth) / 2;
                int lineY = startY + i * lineHeight;
                g2.drawString(lines[i], lineX, lineY);
            }

            //HIDDEN WORD PANEL
            Font hiddenFont = g2.getFont().deriveFont(Font.PLAIN, 20f);
            g2.setFont(hiddenFont);
            FontMetrics metrics1 = g2.getFontMetrics(hiddenFont);
            String word = new String(displayWord);

            int hiddenTextWidth = metrics1.stringWidth(word);
            int hiddenTextHeight = metrics1.getAscent();

            int hiddenBoxX = 0;
            int hiddenBoxY = boxY + labelHeight;

            int hiddenBoxHeight = screenHeight - boxY;

            int hiddenTextX = (screenWidth - hiddenTextWidth) / 2;
            int hiddenTextY = hiddenBoxY + (hiddenBoxHeight - hiddenTextHeight) / 2 + hiddenTextHeight/2;

            Color blackTransparent = new Color(0, 0, 0, 0);
            g2.setColor(blackTransparent);
            g2.fillRect(hiddenBoxX, hiddenBoxY, screenWidth, hiddenBoxHeight);


            g2.setColor(Color.white);
            g2.drawString(word,  hiddenTextX, hiddenTextY);

        }
        if (!running) {
            gp.inMinigame = false;
            if(!won) {
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
                finished = true;
            }
            //!! NEED TO ADD KEY HANDLER FOR YES OR NO
            return;
        }
    }
        @Override
        public boolean isWon() {
            return won;
        }

        public void handleKeyPress(int code) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.titleState;
                return;
            }

            if (gameTitle) {
                if (code == KeyEvent.VK_ENTER) {
                    gameTitle= false;
                }
                return;
            }

            if (code >= KeyEvent.VK_A && code <= KeyEvent.VK_Z) {
                guessedOnce = true;
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
                if (!found && guessedOnce) {
                    life--;
                }
                if (new String(displayWord).equalsIgnoreCase(wordChallenge[1])) {
                    correctAnswers++;
                    int REQUIRED_CORRECT_ANSWERS = 3;
                    if (correctAnswers >= REQUIRED_CORRECT_ANSWERS) {
                        gp.stopMusic();
                        gp.playMusic(0);
                        won = true;      // Player wins the whole game
                        running = false;
                    } else {
                        // Reset for next challenge
                        loadNewChallenge();   // method to load a new riddle + reset displayWord and life
                    }
                }
                if(life <= 0) {
                    gp.playSE(8);
                    won = false;
                    running = false;
                    correctAnswers = 0;
                }
            }
           if(finished){
               if(code == KeyEvent.VK_Y) {
                   gp.gameState = gp.playState;
                   gp.stopMusic();
                   gp.playMusic(0);
               }
               if(code == KeyEvent.VK_N) {
                   reset();
               }
           }
        }
        public void reset() {
            running = true;
            gp.stopMusic();
            gp.playMusic(4);
            won = false;
            finished = false;
            life = 5;
            wordChallenge = wordDB.loadChallenge();
            wordDB.reset();
            loadNewChallenge();
            String actualWord = wordChallenge[1];
            displayWord = new char[actualWord.length()];
            for (int i = 0; i < actualWord.length(); i++) {
                displayWord[i] = (actualWord.charAt(i) == ' ') ? ' ' : '_';
            }
        }

    private void loadNewChallenge() {
        wordChallenge = wordDB.loadChallenge();
        initializeDisplayWord();
        running = true;
    }

    private void initializeDisplayWord() {
        String answer = wordChallenge[1];
        displayWord = new char[answer.length()];
        for (int i = 0; i < displayWord.length; i++) {
            if (answer.charAt(i) == ' ') {
                displayWord[i] = ' ';
            } else {
                displayWord[i] = '_';
            }
        }
    }


}

