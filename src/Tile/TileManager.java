package Tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    boolean drawPath = false;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp){

        this.gp = gp;

        InputStream is = getClass().getResourceAsStream("/maps/tileData.txt");
        assert is != null;
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(is));

        String line;
        try{
        while((line = br.readLine()) != null ){
            fileNames.add(line);
            collisionStatus.add(br.readLine());
        }
        br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        tile = new Tile[fileNames.size()];
        getTileImage();

        is = getClass().getResourceAsStream("/maps/bedroomMap(withFurniture).txt");
        assert is != null;
        br = new BufferedReader(new java.io.InputStreamReader(is));

        try{
            String line2 = br.readLine();
            String[] maxTile = line2.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;
            mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

            br.close();

        }catch(IOException e) {
            System.out.println("EXCEPTION: Could not load map data.");
            e.printStackTrace();
        }
        loadMap("/maps/bedroomMap(withFurniture).txt", 0);
        loadMap("/maps/LivingRoomMap.txt", 1);
        loadMap("/maps/ffOutsideWorldMap.txt", 2);

    }

    public void getTileImage(){
//format of the tile is "TileID", "TileName", "collision"

        for(int i = 0; i < fileNames.size(); i++){
            String fileName;
            boolean collision;

            fileName = fileNames.get(i);

            if(collisionStatus.get(i).equals("true")){
                collision = true;
            } else {
                collision = false;
            }

            setup(i, fileName, collision);
        }
    }

    public void setup(int index, String imagePath, boolean collision){
        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imagePath)));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e){
            e.printStackTrace();
        }

    }


    public void loadMap(String filePath, int map){
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
                    mapTileNum[map][col][row] = num;
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
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
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