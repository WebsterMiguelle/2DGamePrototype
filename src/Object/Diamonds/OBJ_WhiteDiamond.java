package Object.Diamonds;
import Main.GamePanel;
import Main.UtilityTool;
import Object.OBJ_Diamonds;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_WhiteDiamond extends OBJ_Diamonds {
    GamePanel gp;
    UtilityTool uTool = new UtilityTool();
    
    public OBJ_WhiteDiamond(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "WhiteDiamond";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/White_Diamond.png")));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {

        }
        collision = true;

    }
    }
