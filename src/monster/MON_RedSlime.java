package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_RedSlime extends Entity {
    public  MON_RedSlime(GamePanel gp) {
        super(gp);
        name="Red Slime";
        speed=2;
        type=2;
        maxLife=4;
        life=maxLife;
        collisionArea.x=3;
        collisionArea.y=18;
        collisionArea.width=42;
        collisionArea.height=30;
        collisionAreaDefaultX=collisionArea.x;
        collisionAreaDefaultY=collisionArea.y;
        getImage();
    }
    public void getImage() {
        up1=setup("/monster/redslime_down_1", gp.tileSize,gp.tileSize);
        up2=setup("/monster/redslime_down_2", gp.tileSize,gp.tileSize);
        left1=setup("/monster/redslime_down_1", gp.tileSize,gp.tileSize);
        left2=setup("/monster/redslime_down_2", gp.tileSize,gp.tileSize);
        right1=setup("/monster/redslime_down_1", gp.tileSize,gp.tileSize);
        right2=setup("/monster/redslime_down_2", gp.tileSize,gp.tileSize);
        down1=setup("/monster/redslime_down_1", gp.tileSize,gp.tileSize);
        down2=setup("/monster/redslime_down_2", gp.tileSize,gp.tileSize);

    }
    public void setAction(){
        actionCounter++;

        if(actionCounter>120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            }
            else if (i <= 75) {
                direction = "left";
            } else  {
                direction = "right";
            }
            actionCounter=0;
        }
    }
}
