package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject{
    public OBJ_Heart() {
        name = "Heart";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/object/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/object/heart_blank.png"));
            image=uTool.scaleImage(image,tileSize, tileSize);
            image2=uTool.scaleImage(image2,tileSize, tileSize);
            image3 = uTool.scaleImage(image3,tileSize, tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}
