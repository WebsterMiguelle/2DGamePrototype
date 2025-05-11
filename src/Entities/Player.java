package Entities;

import Main.GamePanel;
import Main.KeyHandler;

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

    public int hasKey = 0;
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

        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/Player_Right_2.png")));
            standNorth = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_back.png")));
            standSouth = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_front.png")));
            standEast = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_right.png")));
            standWest = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/stand_left.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                if (standCounter == 15) {
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
            String objectName = gp.obj[i].name;
            switch (objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    gp.playSE(2);
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                        break;
                    }   else{

                        gp.ui.showMessage("You need a key!");
                        break;
                    }
                case "BlueDiamond":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(3);

                    break;
            }
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

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        g2.setColor(Color.RED);
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
