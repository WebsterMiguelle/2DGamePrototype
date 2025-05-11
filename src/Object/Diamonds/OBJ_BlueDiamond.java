package Object.Diamonds;
import Object.OBJ_Diamonds;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_BlueDiamond extends OBJ_Diamonds {

    public OBJ_BlueDiamond(){
        name = "BlueDiamond";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Blue_Diamond.png")));
        }catch (IOException e){

        }
        collision = true;


    }
}