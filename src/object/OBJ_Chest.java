package object;

import entity.Entity;
import main.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Chest extends Entity {


    public OBJ_Chest(GamePanel gp) {
        super(gp);
        name = "Chest";
        down1=setup("/object/chest", gp.tileSize,gp.tileSize);

    }
}