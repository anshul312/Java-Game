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
        up1=setup("/monster/redslime_down_1");
        up2=setup("/monster/redslime_down_2");
        left1=setup("/monster/redslime_down_1");
        left2=setup("/monster/redslime_down_2");
        right1=setup("/monster/redslime_down_1");
        right2=setup("/monster/redslime_down_2");
        down1=setup("/monster/redslime_down_1");
        down2=setup("/monster/redslime_down_2");

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
