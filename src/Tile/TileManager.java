package Tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/BedroomMap.txt");
    }

    public void getTileImage(){
//format of the tile is "TileID", "TileName", "collision"
        //Bedroom Tiles
            setup(0,"00",true);
            setup(1,"01",true);
            setup(2,"02",true);
            setup(3,"03",true);
            setup(4,"04",true);
            setup(5,"05",true);
            setup(6,"06",true);
            setup(7,"07",true);
            setup(8,"08",true);
            setup(9,"09",false);
            setup(10,"10",true);
            setup(11,"11",true);
            setup(12,"12",true);
            setup(13,"13",true);
            setup(14,"14",true);
            setup(15,"15",true);
            setup(16,"16",true);
            setup(17,"17",true);
            setup(18,"18",true);
            setup(19,"19",true);
            setup(20,"20",true);
            setup(21,"21",true);
            setup(22,"22",true);
            setup(23,"23",true);
            //Outside Tiles
    }

    public void setup(int index, String imagePath, boolean collision){
        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/Bedroom/" + imagePath+".png")));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e){
            e.printStackTrace();
        }

    }


    public void loadMap(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader by = new BufferedReader(new java.io.InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = by.readLine();

                while(col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            by.close();
        } catch (Exception e) {

        }

    }
    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY - gp.tileSize &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY + gp.tileSize) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;
            if(worldCol == gp.maxWorldCol){
                worldCol = 0;

                worldRow++;

            }
        }
    }
}