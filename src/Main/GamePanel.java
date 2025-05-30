package Main;

import Entities.Entity;
import Entities.Player;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile

    public final int maxScreenCol = 20; //16 tiles across
    public final int maxScreenRow = 12; //12 tiles down
    public final int screenWidth = tileSize * maxScreenCol; //960 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels
    //WORLD SETTINGS
    public int maxWorldCol = 50; //50 tiles across
    public int maxWorldRow = 50; //50 tiles down
    public final int maxMap = 5;
    public int currentMap = 0;

    //Current Map: 0 = Bedroom, 1 = Living Room, 2 = Outside *not implemented yet*

    //FOr fullscreen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;


    int FPS = 60;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound sound = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public CutsceneManager cutsceneManager = new CutsceneManager(this);
    Config config = new Config(this);
    Thread gameThread;

    //Entity and Object
    public Player player = new Player(this, keyH);
    public Entity[][] obj = new Entity[maxMap][10];
    public Entity[][] npc = new Entity[maxMap][10];

    ArrayList<Entity> entityList = new ArrayList<>();

    //Game States
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int optionsState = 4;
    public final int cutsceneState = 6;
    //Minigames state

    public final int minigameState = 5;
    public Minigame currentMinigame = null;
    public boolean trueWarrior = false ,trueSage = false ,trueKing = false;
    public boolean inMinigame = false;


    public GamePanel(){
        this.setPreferredSize(new java.awt.Dimension(screenWidth, screenHeight));
        this.setBackground(java.awt.Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        //playMusic(0);
        //stopMusic();
        cutsceneManager.sceneNum = 0; // Set initial cutscene scene
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn) {
            setFullScreen();
        }
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void setFullScreen(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        //
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    //GameLoop is Sleep Method
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update(){
        // Update game logic here
        if(gameState == playState) {
            // Player
            player.update();
            // NPC
            for(int i = 0 ; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }
            //OBJ
            for(int i = 0 ; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    obj[currentMap][i].update();
                }
            }
        }
        if(gameState == pauseState) {
            //nothing
        }

        if (gameState == minigameState && currentMinigame != null) {
            currentMinigame.update();
            if (currentMinigame.isWon()) {
                if(currentMinigame instanceof SnakeMinigame) {
                    trueKing = true;
                } else if (currentMinigame instanceof HangmanMinigame) {
                    trueSage = true;
                } else if (currentMinigame instanceof RPSMinigame) {
                    trueWarrior = true;
                }

                // You can transition state or reward player
                gameState = playState;
                stopMusic();
                playSE(7);
                playMusic(0);
            }
        }


    }
    public void drawToTempScreen(){

        //Debug
        long drawStart = 0;
        if(keyH.showDebugText){
            drawStart = System.nanoTime();
        }
        if (gameState == minigameState && currentMinigame != null) {
            currentMinigame.draw(g2);
            return; // skip the rest of the main game rendering
        }
        //Title SCREEN
        if(gameState == titleState) {
            ui.draw(g2);
        } else {
            //Tile
            tileM.draw(g2);
            //Player
            entityList.add(player);
            //NPC
            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);
                }
            }
            //Objects
            for(int i = 0; i < obj[1].length; i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            }); // Sort entities by their worldY position)
            //draw entities
           for(int i = 0 ; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
           }
           entityList.clear();

           //Cutscene
            if(gameState == cutsceneState) {
                cutsceneManager.draw(g2);
            }
            //UI
            ui.draw(g2);
        }
        if(keyH.showDebugText) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font ("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
            g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;

            g2.drawString("Draw Time: " + passed, x, y);
        }
    }

    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
    public void resetGame() {
        // Reset map and state
        currentMap = 0;
        gameState = titleState;

// Clear any UI or event data if needed
        ui.reset();
        eHandler.reset();

        // Reset player
        player.reset();
        player = new Player(this, keyH);

        // Reset NPCs and Objects
         for (int i = 0; i < maxMap; i++) {
             for (int j = 0; j < 10; j++) {
                 npc[i][j] = null;
                 obj[i][j] = null;
             }
         }
        npc = new Entity[maxMap][10];
        obj = new Entity[maxMap][10];


        aSetter.setNPC();
        aSetter.setObject();

        // Reset cutscene
        cutsceneManager.sceneNum = 0;

        // Reset minigame and sound
        stopMusic();
        currentMinigame = null;
        inMinigame = false;



        // Reset camera/screen if needed
        if (fullScreenOn) {
            setFullScreen();
        }

        // Optionally redraw immediately
        repaint();
    }
}
