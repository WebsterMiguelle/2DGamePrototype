package Main;

import Entities.*;
import Entities.NPC_Mage;
import Object.*;

public class AssetSetter {

    GamePanel gp;


    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }
        public void setObject(){
        int mapNum = 0;
int i = 0;

        mapNum = 1;

        mapNum = 2;

            gp.obj[mapNum][i] = new OBJ_Crown(gp);
            gp.obj[mapNum][i].worldX = 35 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 43 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_Sword(gp);
            gp.obj[mapNum][i].worldX = 46 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 33 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_Book(gp);
            gp.obj[mapNum][i].worldX = 9 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 44 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_CrownPillar(gp);
            gp.obj[mapNum][i].worldX = 28 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 42 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_BookPillar(gp);
            gp.obj[mapNum][i].worldX = 21 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 36 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_SwordPillar(gp);
            gp.obj[mapNum][i].worldX = 34 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 36 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_Star(gp);
            gp.obj[mapNum][i].worldX = 28 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 36 * gp.tileSize;
            i++;
        }

        public void setNPC(){
            int mapNum = 0;
int i = 0;
            mapNum = 1;
            gp.npc[mapNum][i] = new NPC_Mother(gp);
            gp.npc[mapNum][i].worldX = 19 * gp.tileSize;
            gp.npc[mapNum][i].worldY = 29 * gp.tileSize;
            i++;

            mapNum = 2;

            gp.npc[mapNum][i] = new NPC1(gp);
            gp.npc[mapNum][i].worldX = 35 * gp.tileSize;
            gp.npc[mapNum][i].worldY = 13 * gp.tileSize;
            i++;

            gp.npc[mapNum][i] = new NPC2(gp);
            gp.npc[mapNum][i].worldX = 27 * gp.tileSize;
            gp.npc[mapNum][i].worldY = 6 * gp.tileSize;
            i++;

            gp.npc[mapNum][i] = new NPC3(gp);
            gp.npc[mapNum][i].worldX = 12 * gp.tileSize;
            gp.npc[mapNum][i].worldY = 5 * gp.tileSize;
            i++;
        }
}
