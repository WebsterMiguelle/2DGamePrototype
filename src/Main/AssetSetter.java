package Main;

import Entities.NPC_Mage;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }
        public void setObject(){
        /*
        int mapNum = 0;
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 31 * gp.tileSize;
        gp.obj[0].worldY = 30 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 35 * gp.tileSize;
        gp.obj[1].worldY = 31 * gp.tileSize;

        gp.obj[2] = new OBJ_Door(gp);
        gp.obj[2].worldX = 27 * gp.tileSize;
        gp.obj[2].worldY = 15 * gp.tileSize;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 30 * gp.tileSize;
        gp.obj[3].worldY = 15 * gp.tileSize;

        gp.obj[4] = new OBJ_BlueDiamond(gp);
        gp.obj[4].worldX = 35 * gp.tileSize;
        gp.obj[4].worldY = 15 * gp.tileSize;*/
        }

        public void setNPC(){
            int mapNum = 0;
            gp.npc[mapNum][0] = new NPC_Mage(gp);
            gp.npc[mapNum][0].worldX = 20 * gp.tileSize;
            gp.npc[mapNum][0].worldY = 29 * gp.tileSize;

        }
}
