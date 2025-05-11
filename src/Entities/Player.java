package Entities;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    //public int hasKey = 0;
    private int standCounter = 1;

    boolean moving = false;
    int pixelCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 1;
        solidArea.y = 1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 46;
        solidArea.height = 46;

    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 34;
        worldY = gp.tileSize * 27;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {

        up1 = setup("Player_Up_1");
        up2 = setup("Player_Up_2");
        down1 = setup("Player_Down_1");
        down2 = setup("Player_Down_2");
        left1 = setup("Player_Left_1");
        left2 = setup("Player_Left_2");
        right1 = setup("Player_Right_1");
        right2 = setup("Player_Right_2");
        standNorth = setup("stand_back");
        standSouth = setup("stand_front");
        standEast = setup("stand_right");
        standWest = setup("stand_left");

    }
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + imageName + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {

        if (!moving) {
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                if (keyH.upPressed) {
                    direction = "up";
                }
                if (keyH.downPressed) {
                    direction = "down";
                }
                if (keyH.leftPressed) {
                    direction = "left";
                }
                if (keyH.rightPressed) {
                    direction = "right";
                }
                moving = true;
                //CHECKS TILE COLLISION
                collisionOn = false;
                gp.cChecker.checkTile(this);

                //CHECKS OBJECT COLLISION
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

            } else {
                standCounter++;
                if (standCounter == 10) {
                    standCounter = 0;
                    switch (direction) {
                        case "up":
                            direction = "StandUp";
                            break;
                        case "down":
                            direction = "StandDown";
                            break;
                        case "left":
                            direction = "StandLeft";
                            break;
                        case "right":
                            direction = "StandRight";
                            break;
                    }
                }
            }
        }

        if(moving){
            //IF COLLISION IS FALSE, PLAYER CAN MOVE
                if(!collisionOn){
                    switch(direction){
                        case "up": worldY -= speed;break;
                        case "down": worldY += speed;break;
                        case "left": worldX -= speed;break;
                        case "right": worldX += speed;break;
                    }

                }
                spriteCounter++;
                if (spriteCounter > 12) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }

                pixelCounter += speed;

                if(pixelCounter == 48){
                  moving = false;
                  pixelCounter = 0;
                }
            }
        }


    public void pickUpObject(int i) {
        if (i != 999){
           //this is where you have player-object interactions
            //if (gp.obj[i].type == type) {
            //    gp.playSE(1);
            //    hasKey++;
            //    gp.obj[i] = null;
            //}
        }

    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {image = up1;}
                if (spriteNum == 2) {image = up2;}
                break;
            case "down":
                if (spriteNum == 1) {image = down1;}
                if (spriteNum == 2) {image = down2;}
                break;
            case "left":
                if (spriteNum == 1) {image = left1;}
                if (spriteNum == 2) {image = left2;}
                break;
            case "right":
                if (spriteNum == 1) {image = right1;}
                if (spriteNum == 2) {image = right2;}
                break;
            case "StandUp":
                image = standNorth;
                break;
            case "StandDown":
                image = standSouth;
                break;
            case "StandLeft":
                image = standWest;
                break;
            case "StandRight":
                image = standEast;
                break;
        };

        g2.drawImage(image, screenX, screenY, null);
        g2.setColor(Color.RED);
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
