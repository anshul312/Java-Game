package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity {

    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    public int hasKey=0;
    int doorIndex=0;
    //public BufferedImage idle_left,idle_right,idle_up,idle_down;
    public Player(GamePanel gp,KeyHandler keyHandler) {
        super(gp);
        //this.gp = gp;
        this.keyHandler = keyHandler;

        screenX=gp.screenWidth/2;
        screenY=gp.screenHeight/2;

        collisionArea=new Rectangle(8,16,32,32);
        collisionAreaDefaultX=collisionArea.x;
        collisionAreaDefaultY=collisionArea.y;
        attackArea.width=36;
        attackArea.height=36;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    public void setDefaultValues(){
        worldX=gp.tileSize*23-gp.tileSize/2;
        worldY=gp.tileSize*21-gp.tileSize/2;
        speed=4;
        direction="down";
        maxLife=6;
        life=maxLife;
    }

    public void getPlayerImage(){

        left1=setup("/player/boy_left_1", gp.tileSize,gp.tileSize);
        right1=setup("/player/boy_right_1", gp.tileSize,gp.tileSize);
        left2=setup("/player/boy_left_2", gp.tileSize,gp.tileSize);
        right2=setup("/player/boy_right_2", gp.tileSize,gp.tileSize);
        up1=setup("/player/boy_up_1", gp.tileSize,gp.tileSize);
        up2=setup("/player/boy_up_2", gp.tileSize,gp.tileSize);
        down1=setup("/player/boy_down_1", gp.tileSize,gp.tileSize);
        down2=setup("/player/boy_down_2", gp.tileSize,gp.tileSize);
    }
    public void getPlayerAttackImage(){
        attackUp1= setup("/player/boy_attack_up_1", gp.tileSize,gp.tileSize*2);
        attackUp2= setup("/player/boy_attack_up_2", gp.tileSize,gp.tileSize*2);
        attackDown1 =  setup("/player/boy_attack_down_1", gp.tileSize,gp.tileSize*2);//
        attackDown2=setup("/player/boy_attack_down_2", gp.tileSize,gp.tileSize*2);
        attackLeft1 =  setup("/player/boy_attack_left_1", gp.tileSize*2,gp.tileSize);
        attackLeft2 =  setup("/player/boy_attack_left_2", gp.tileSize*2,gp.tileSize);
        attackRight2 =  setup("/player/boy_attack_right_2", gp.tileSize*2,gp.tileSize);
        attackRight1 =  setup("/player/boy_attack_right_1", gp.tileSize*2,gp.tileSize);
    }

    public void update(){
        if(attacking){
            attacking();
        }
        else if(keyHandler.up ||keyHandler.down ||keyHandler.left ||keyHandler.right || keyHandler.enter ){

            if(keyHandler.up){
                direction="up";
            }
            if(keyHandler.down){
                direction="down";
            }
            if(keyHandler.left){
                direction="left";
            }
            if(keyHandler.right){
                direction="right";
            }

            //Check for collision
            collisionOn=false;
            gp.checker.checkTile(this);

            //check object collision
            int objIndex=gp.checker.checkObject(this,true);
            pickUpObject(objIndex);

            //check NPC collision
            int npcIndex=gp.checker.checkEntity(this, gp.npc);
            if (npcIndex != 999) {
                collisionOn = true;  // Prevent movement
            }
            interactNPC(npcIndex);
            //System.out.println(npcIndex);

            //check monster
            int monsterIndex=gp.checker.checkEntity(this, gp.monster);
            if (monsterIndex != 999) {
                collisionOn = true;  // Prevent movement
            }
            contactMonster(monsterIndex);
            // check event
            gp.eHandler.checkEvent();



            //if no collision then player can move
            if(collisionOn==false && keyHandler.enter == false){

                switch(direction){
                    case "up": worldY-=speed; break;
                    case "down": worldY+=speed; break;
                    case "left": worldX-=speed; break;
                    case "right": worldX+=speed; break;
                }
            }
            gp.keyHandler.enter=false;
            spriteCounter++;
            if(spriteCounter>10) {
                spriteNum *= -1;
                spriteCounter = 0;
            }
        }

        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible=false;
                invincibleCounter=0;
            }
        }
    }

    public void attacking(){
        spriteCounter++;
        if(spriteCounter<=5){
            spriteNum=1;
        }
        if(spriteCounter > 5 && spriteNum<=25){
            spriteNum=2;
            int currentWorldX=worldX;
            int currentWorldY=worldY;
            int collisionAreaWidth=collisionArea.width;
            int collisionAreaHeight=collisionArea.height;
            switch(direction){
                case "up":worldY-=attackArea.height; break;
                case "down":worldY+=attackArea.height; break;
                case "left":worldX-=attackArea.width; break;
                case "right":worldX+=attackArea.width; break;
            }
            collisionArea.width=attackArea.width;
            collisionArea.height=attackArea.height;
            int monsterIndex=gp.checker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);
            worldX=currentWorldX;
            worldY=currentWorldY;
            collisionArea.width=collisionAreaWidth;
            collisionArea.height=collisionArea.height;
        }
        if(spriteCounter > 25){
            spriteNum=1;
            spriteCounter=0;
            attacking=false;
        }
    }
    public void contactMonster(int i){
        if(i!=999){
            if(invincible == false){
                life-=1;
                invincible=true;
            }
        }
    }
    public void damageMonster(int i){
        if(i!=999){
            if(gp.monster[i].invincible == false){
                gp.monster[i].life-=1;
                gp.monster[i].invincible=true;
                if(gp.monster[i].life<=0){
                    gp.monster[i]=null;
                }
            }

        }
    }
    public void pickUpObject(int index){

        if(index!=999){
            String objectName=gp.obj[index].name;

            switch(objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[index]=null;
                    System.out.println("Key"+hasKey);
                    gp.ui.showMessage("You got a key.");
                    break;
                case "Door":
                    if(hasKey>0){
                        String message="";
                        doorIndex++;
                        gp.playSE(2);
                        gp.obj[index]=null;
                        hasKey--;

                        if(doorIndex==1)  {
                             message="1st";
                        }
                        else if(doorIndex==2)  {
                             message="2nd";
                        }
                        else if(doorIndex==3){
                             message="3rd";
                        }
                        gp.ui.showMessage("You opened the " +message +" door!");
                    }
                    else{
                        gp.ui.showMessage("It won't budge!");
                    }
                    System.out.println("Key"+hasKey);
                    break;

                case "Chest":
                    gp.ui.gameOver=true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    public void interactNPC(int index){
        if(gp.keyHandler.enter){
            if(index!=999){
                    gp.gameState=gp.dialogueState;
                    gp.npc[index].speak();
            }
            else{
                    attacking=true;

            }
        }

    }

    public void draw(Graphics2D g2){
        BufferedImage image=null;
        int tempScreenX=screenX,tempScreenY=screenY;
        switch(direction){
            case "up":
                if(attacking == false){ image=(spriteNum==1)?up1:up2;}
                else{
                    tempScreenY=screenY-gp.tileSize;
                    image=(spriteNum==1)?attackUp1:attackUp2;
                }
                break;
            case "down":
                if(attacking == false){
                    image=(spriteNum==1)?down1:down2;
                }
                else{
                    image=(spriteNum==1)?attackDown1:attackDown2;
                }
                break;
            case "left":
                if(attacking == false){
                    image=(spriteNum==1)?left1:left2;
                }
                else{
                    tempScreenX=screenX-gp.tileSize;
                    image=(spriteNum==1)?attackLeft1:attackLeft2;
                }
                break;
            case "right":
                if(attacking == false){
                    image=(spriteNum==1)?right1:right2;
                }
                else{
                    image=(spriteNum==1)?attackRight1:attackRight2;
                }
                break;
        }
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image,tempScreenX,tempScreenY,null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
