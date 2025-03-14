package object;

import main.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject {

    public OBJ_Key() {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/object/key.png"));
            uTool.scaleImage(image,tileSize, tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}