package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction="down";
        speed=1;

        getImage();
        setDialogue();
    }

    public void getImage(){
        left1=setup("/npc/oldman_left_1", gp.tileSize,gp.tileSize);
        right1=setup("/npc/oldman_right_1", gp.tileSize,gp.tileSize);
        left2=setup("/npc/oldman_left_2", gp.tileSize,gp.tileSize);
        right2=setup("/npc/oldman_right_2", gp.tileSize,gp.tileSize);
        up1=setup("/npc/oldman_up_1", gp.tileSize,gp.tileSize);
        up2=setup("/npc/oldman_up_2", gp.tileSize,gp.tileSize);
        down1=setup("/npc/oldman_down_1", gp.tileSize,gp.tileSize);
        down2=setup("/npc/oldman_down_2", gp.tileSize,gp.tileSize);
    }
    public void setDialogue(){
        dialogues[0]="Hello , lad.";
        dialogues[1]="So you've come to this island to\nfind the treasure?";
        dialogues[2]="Years ago ,\nI used to be a great wizard but now...\nI'm bit too old for an adventure.";
        dialogues[3]="Well , good luck to you.";
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

    @Override
    public void speak() {
        super.speak();
    }
}
