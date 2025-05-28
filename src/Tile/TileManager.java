package Tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[500];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/BedroomMap.txt",0);
        loadMap("/maps/livingroom.txt",1);
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
            //Living Room
        setup(24, "24", true);
        setup(25, "25", true);
        setup(26, "26", true);
        setup(27, "27", true);
        setup(28, "28", true);
        setup(29, "29", false);
        setup(30, "30", true);
        setup(31, "31", true);
        setup(32, "32", true);
        setup(33, "33", true);
        setup(34, "34", true);
        setup(35, "35", true);
        setup(36, "36", true);
        setup(37, "37", true);
        setup(38, "38", true);
        setup(39, "39", true);
        setup(40, "40", true);
        setup(41, "41", true);
        setup(42, "42", true);
        setup(43, "43", true);
        setup(44, "44", true);
        setup(45, "45", true);
        setup(46, "46", false);
        setup(47, "47", false);
        setup(48, "48", false);
        setup(49, "49", false);
        setup(50, "50", false);
        setup(51, "51", false);
        setup(52, "52", false);
        setup(53, "53", false);
        setup(54, "54", false);
        setup(55, "55", false);
        setup(56, "56", false);
        setup(57, "57", false);
        setup(58, "58", false);
        setup(59, "59", false);
        setup(60, "60", false);
        setup(61, "61", false);
        setup(62, "62", false);
        setup(63, "63", false);
        setup(64, "64", false);
        setup(65, "65", false);
        setup(66, "66", false);
        setup(67, "67", false);
        setup(68, "68", false);
        setup(69, "69", false);
        setup(70, "70", false);
        setup(71, "71", false);
        setup(72, "72", false);
        setup(73, "73", false);
        setup(74, "74", false);
        setup(75, "75", false);
        setup(76, "76", false);
        setup(77, "77", false);
        setup(78, "78", false);
        setup(79, "79", false);
        setup(80, "80", false);
        setup(81, "81", false);
        setup(82, "82", false);
        setup(83, "83", false);
        setup(84, "84", false);
        setup(85, "85", false);
        setup(86, "86", false);
        setup(87, "87", false);
        setup(88, "88", false);
        setup(89, "89", false);
        setup(90, "90", false);
        setup(91, "91", false);
        setup(92, "92", false);
        setup(93, "93", true);
        setup(94, "94", true);
        setup(95, "95", true);
        setup(96, "96", true);
        setup(97, "97", true);
        setup(98, "98", true);
        setup(99, "99", false);
        setup(100, "100", false);
        setup(101, "101", false);
        setup(102, "102", false);
        setup(103, "103", false);
        setup(104, "104", false);
        setup(105, "105", false);
        setup(106, "106", false);
        setup(107, "107", false);
        setup(108, "108", true);
        setup(109, "109", false);
        setup(110, "110", false);

        //Outside


        //Outside Tiles
    }

    public void setup(int index, String imagePath, boolean collision){
        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imagePath+".png")));
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