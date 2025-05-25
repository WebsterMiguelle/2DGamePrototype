package Object;

import Entities.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Door extends Entity {
    GamePanel gp;
    public OBJ_Door(GamePanel gp){
       super(gp);
        name = "Door";
        down1 = setup("/objects/Door.png");
        collision = true;


    }
}
