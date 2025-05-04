package Main;

import Object.*;
public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }
        public void setObject(){

        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 31 * gp.tileSize;
        gp.obj[0].worldY = 30 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 35 * gp.tileSize;
        gp.obj[1].worldY = 31 * gp.tileSize;

        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = 27 * gp.tileSize;
        gp.obj[2].worldY = 15 * gp.tileSize;

        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 30 * gp.tileSize;
        gp.obj[3].worldY = 15 * gp.tileSize;

        }
}
