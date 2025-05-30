package Main;

import Entities.Entity;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect;
    Entity eventEntity;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;


        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if (canTouchEvent) {
            if(hit(0,21, 25, "up")) {teleport(1,15,33);}
            else if(hit(1,15, 33, "left")) {teleport(0,21,25);}

            else if(hit(1,19, 28, "up")) {teleport(2,10,13);}
            else if(hit(2,10, 13, "up")) {teleport(1,19,27);}
        }



    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;
        if(map == gp.currentMap){
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row])) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }


        return hit;
    }

    public void teleport(int map, int col, int row){
    gp.currentMap = map;
    gp.player.worldX = col * gp.tileSize;
    gp.player.worldY = row * gp.tileSize;

    previousEventX = gp.player.worldX;
    previousEventY = gp.player.worldY;
    canTouchEvent = false;
    //instert teleport sound here
        gp.playSE(9);

    }

    public void reset() {
        previousEventX = 0;
        previousEventY = 0;
        canTouchEvent = true;

        // Optionally reset all EventRect objects if needed
        for (int map = 0; map < gp.maxMap; map++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                for (int row = 0; row < gp.maxWorldRow; row++) {
                    if (eventRect[map][col][row] != null) {
                        eventRect[map][col][row].x = 23;
                        eventRect[map][col][row].y = 23;
                        eventRect[map][col][row].width = 2;
                        eventRect[map][col][row].height = 2;
                        eventRect[map][col][row].eventRectDefaultX = 23;
                        eventRect[map][col][row].eventRectDefaultY = 23;
                    }
                }
            }
        }
    }

}
