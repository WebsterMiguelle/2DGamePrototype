package Entities;

import Main.*;
import Object.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity{

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    private int standCounter = 0;

    private boolean canInteract = false;
    public boolean hasCrown, hasSword, hasBook;
    public boolean crownDone, bookDone, swordDone;

    Font interactableFont;
    private BufferedImage notificationBox;
    private int boxX = 0;
    private int boxY = 0;
    //boolean moving = false;
    //int pixelCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);


        boxX = screenX + gp.tileSize / 2 - 24;
        boxY = screenY - 24 - 10;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 12;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        interactableFont = new Font("Calibre", Font.BOLD, 15);

        prepareNotifBox();

    }
    public void setDefaultValues() {
        //Sets player in his bed
        worldX = gp.tileSize * 17;
        worldY = gp.tileSize * 27;
        //Sets player center of the pillars
//        worldX = gp.tileSize * 24;
//        worldY = gp.tileSize * 36;
        //Sets Player in living room
//        worldX = gp.tileSize * 16;
//        worldY = gp.tileSize * 33;

        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {

        up1 = setup("/player/Player_Up_1");
        up2 = setup("/player/Player_Up_2");
        down1 = setup("/player/Player_Down_1");
        down2 = setup("/player/Player_Down_2");
        left1 = setup("/player/Player_Left_1");
        left2 = setup("/player/Player_Left_2");
        right1 = setup("/player/Player_Right_1");
        right2 = setup("/player/Player_Right_2");

        standNorth = setup("/player/stand_back");
        standSouth = setup("/player/stand_front");
        standEast = setup("/player/stand_right");
        standWest = setup("/player/stand_left");

    }

    public void update() {

            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
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


                //CHECKS TILE COLLISION
                collisionOn = false;
                gp.cChecker.checkTile(this);

                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

// Only show prompt if either object or NPC is interactable
                canInteract = (objIndex != 999) || (npcIndex != 999);

                //CHECKS EVENT COLLISION
                gp.eHandler.checkEvent();

                //IF COLLISION IS FALSE, PLAYER CAN MOVE
                if (!collisionOn && !keyH.enterPressed) {
                    switch (direction) {
                        case "up":      worldY -= speed;break;
                        case "down":    worldY += speed;break;
                        case "left":    worldX -= speed;break;
                        case "right":   worldX += speed;break;
                    }
                }
                gp.keyH.enterPressed = false;

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
            else {
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

    public boolean hasDoneAllMinigames() {
        return crownDone && bookDone && swordDone;
    }

    public void pickUpObject(int i) {
        if(i == 999) {return;}
            //CROWN PILLAR / KING TRIALS
        switch(gp.obj[gp.currentMap][i].type) {
            case type_crown -> { if (keyH.enterPressed) {
                gp.obj[gp.currentMap][i].interact();
                    hasCrown = true;
                    gp.obj[gp.currentMap][i] = null;
                }}

            case type_book -> { if (keyH.enterPressed) {
                gp.obj[gp.currentMap][i].interact();
                    hasBook = true;
                    gp.obj[gp.currentMap][i] = null;
                }}

            case type_sword -> { if (keyH.enterPressed) {
                gp.obj[gp.currentMap][i].interact();
                    hasSword = true;
                    gp.obj[gp.currentMap][i] = null;
                }}

            case type_CrownPillar -> {
                if (!hasCrown) {
                    if (keyH.enterPressed) {
                        gp.obj[gp.currentMap][i].interact();
                    }
                }
                if (hasCrown) {
                    if (keyH.enterPressed) {
                        gp.ui.startTransition(gp.minigameState);
                        gp.inMinigame = true;
                        gp.currentMinigame = new SnakeMinigame(gp);
                        hasCrown = false;
                        crownDone = true;
                        gp.obj[2][i] = new OBJ_Crown(gp);
                        gp.obj[2][i].worldX = 28 * gp.tileSize;
                        gp.obj[2][i].worldY = 40 * gp.tileSize;
                    }
                }
            }

            case type_BookPillar -> {
                if (!hasBook) {
                    if (keyH.enterPressed) {
                        gp.obj[gp.currentMap][i].interact();
                    }
                }
                if (hasBook) {
                    if (keyH.enterPressed) {
                        gp.ui.startTransition(gp.minigameState);
                        gp.inMinigame = true;
                        gp.currentMinigame = new HangmanMinigame(gp);
                        hasBook = false;
                        bookDone = true;
                        gp.obj[2][i] = new OBJ_Book(gp);
                        gp.obj[2][i].worldX = 21 * gp.tileSize;
                        gp.obj[2][i].worldY = 34 * gp.tileSize;
                    }
                }
            }

            case type_SwordPillar -> {
                if (!hasSword) {
                    if (keyH.enterPressed) {
                        gp.obj[gp.currentMap][i].interact();
                    }
                }
                if (hasSword) {
                    if (keyH.enterPressed) {
                        gp.ui.startTransition(gp.minigameState);
                        gp.inMinigame = true;
                        gp.currentMinigame = new RPSMinigame(gp);
                        hasSword = false;
                        swordDone = true;
                        gp.obj[2][i] = new OBJ_Sword(gp);
                        gp.obj[2][i].worldX = 34 * gp.tileSize;
                        gp.obj[2][i].worldY = 34 * gp.tileSize;
                    }
                }
            }

            case type_star -> {
                if (keyH.enterPressed) {
                    gp.obj[gp.currentMap][i].interact();
                }
            }
        }

    }

    public void interactNPC(int i){
        if(gp.keyH.enterPressed) {
            if (i != 999) {
                gp.npc[gp.currentMap][i].speak();
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
        }

        g2.drawImage(image, screenX, screenY, null);
        //debug
//        g2.setColor(Color.RED);
//        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        if (canInteract && notificationBox != null) {
            g2.drawImage(notificationBox, boxX, boxY , null);
        }
    }
    private void prepareNotifBox(){
        notificationBox = new BufferedImage(70, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = notificationBox.createGraphics();

        g.setColor(new Color(46, 46, 46, 180));
        g.fillRoundRect(0, 0, 70, 30, 10, 10);

        g.setColor(Color.WHITE);
        g.setFont(interactableFont);
        g.drawString("E/Enter", 5, 20); // You can write full "E to Interact" if it fits
        g.dispose();
    }

    public void reset() {
        worldX = 0;
        worldY = 0;
        direction = "down";
        spriteCounter = 0;
        spriteNum = 1;
        actionLockCounter = 0;
        collisionOn = false;
        dialogueIndex = 0;
        dialogueSet = 0;

        // Reset solid area
        solidArea.x = solidAreaDefaultX;
        solidArea.y = solidAreaDefaultY;
    }
}
