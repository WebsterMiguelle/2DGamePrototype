package Main;

import Entities.Player;
import Tile.TileManager;
import Object.SuperObject;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile

    public final int maxScreenCol = 16; //16 tiles across
    public final int maxScreenRow = 12; //12 tiles down\
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels
    //WORLD SETTINGS
    public final int maxWorldCol = 50; //50 tiles across
    public final int maxWorldRow = 50; //50 tiles down
    public final int worldWidth = tileSize * maxWorldCol; //2400 pixels
    public final int worldHeight = tileSize * maxWorldRow; //2400 pixels
    int FPS = 60;
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];


    public GamePanel(){
        this.setPreferredSize(new java.awt.Dimension(screenWidth, screenHeight));
        this.setBackground(java.awt.Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setupGame(){
        aSetter.setObject();

    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
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
                repaint();
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
       player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Tile
        tileM.draw(g2);
        //Objects
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        //Player
        player.draw(g2);

        g2.dispose();
    }
}
