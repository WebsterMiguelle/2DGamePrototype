package Object.Diamonds;
import Main.GamePanel;
import Main.UtilityTool;
import Object.OBJ_Diamonds;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_PinkDiamond extends OBJ_Diamonds {
    GamePanel gp;
    UtilityTool uTool = new UtilityTool();

    public OBJ_PinkDiamond(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "PinkDiamond";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Pink_Diamond.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){

        }
        collision = true;


    }
}