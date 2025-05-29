package Entities;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    GamePanel gp;

    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2,
            standNorth, standSouth, standEast, standWest;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int actionLockCounter = 0;

    public Rectangle solidArea = new Rectangle(8,8,32,32);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public BufferedImage image;
    public String name;
    public boolean collision = false;


    public String[][] dialogues = new String[20][20];
    public int dialogueIndex = 0;
    public int dialogueSet = 0;

    public int type = 0; // 0: player, 1: npc, 2: monster, 3: object, 4: door, 5: event
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_pillar = 2;
    public final int type_sword = 3;
    public final int type_book = 4;
    public final int type_crown = 5;
    public final int type_door = 6;
    public final int type_CrownPillar = 7;
    public final int type_SwordPillar = 8;
    public final int type_BookPillar = 9;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {}
    public void speak() {


    }

    public void facePlayer(){
        switch(gp.player.direction){
            case "up": direction = "StandDown";break;
            case "down": direction = "StandUp";break;
            case "left":direction = "StandRight";break;
            case "right":direction = "StandLeft";break;
        }
    }
    public void startDialogue (Entity entity,int setNum){

        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;

    }

    public void update(){
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);


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
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY - gp.tileSize &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY + gp.tileSize) {

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
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            //Debug
//            g2.setColor(Color.RED);
//            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

        }
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void interact(){}

    public int removeItem(Entity[][] target, String itemName){
        int index = 999;

        for(int i = 0; i < target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                if(target[gp.currentMap][i].name.equals(itemName)){
                    index = i;
                    break; // Exit loop after finding the item
                }
            }
        }
        return index;
    }
}
