package Object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject{
    public OBJ_Key(){

        name = "Key";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/Blue_Key.png")));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
