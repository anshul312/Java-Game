package object;

import entity.Entity;
import main.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Door extends Entity {


    public OBJ_Door(GamePanel gp) {
        super(gp);
        name = "Door";
        down1=setup("/object/door");
        collision=true;
    }
}