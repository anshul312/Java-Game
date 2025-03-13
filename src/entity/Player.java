package entity;

import main.GamePanel;
import main.KeyHandler;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    public int hasKey=0;
    int doorIndex=0;
    BufferedImage idle_left,idle_right,idle_up,idle_down;
    public Player(GamePanel gp,KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        screenX=gp.screenWidth/2;
        screenY=gp.screenHeight/2;

        collisionArea=new Rectangle(8,16,32,32);
        collisionAreaDefaultX=collisionArea.x;
        collisionAreaDefaultY=collisionArea.y;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX=gp.tileSize*23-gp.tileSize/2;
        worldY=gp.tileSize*21-gp.tileSize/2;
        speed=4;
        direction="idle_down";
    }

    public void getPlayerImage(){

        idle_up=setup("boy_idle_up");
        idle_down=setup("boy_idle_down");
        idle_right=setup("boy_idle_right");
        idle_left=setup("boy_idle_left");
        left1=setup("boy_left_1");
        right1=setup("boy_right_1");
        left2=setup("boy_left_2");
        right2=setup("boy_right_2");
        up1=setup("boy_up_1");
        up2=setup("boy_up_2");
        down1=setup("boy_down_1");
        down2=setup("boy_down_2");
    }

    public BufferedImage setup(String imageName){
        Utility uTool=new Utility();
        BufferedImage image=null;
        try{
            image=ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
            image=uTool.scaleImage(image,gp.tileSize,gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void update(){

        int dx=0,dy=0;

        if(keyHandler.up ||keyHandler.down ||keyHandler.left ||keyHandler.right  ){

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

            //if no collision then player can move
            if(collisionOn==false){
                switch(direction){
                    case "up": worldY-=speed; break;
                    case "down": worldY+=speed; break;
                    case "left": worldX-=speed; break;
                    case "right": worldX+=speed; break;
                }
            }
            spriteCounter++;
            if(spriteCounter>10) {
                spriteNum *= -1;
                spriteCounter = 0;
            }
        }
        else{
            if(direction.contains("left")){
              direction="idle_down";  direction="idle_left";
            } else if (direction.contains("right")) {
                direction="idle_right";

            } else if (direction.contains("up")) {
                direction="idle_up";

            } else if (direction.contains("down")) {
                direction="idle_down";
            }
            else {
                direction="idle_down";
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
    public void draw(Graphics2D g2){

        BufferedImage image=null;

        switch(direction){
            case "idle_right":
                image=idle_right;
                break;
            case "idle_up":
                image=idle_up;
                break;
            case "idle_left":
                image=idle_left;
                break;

            case "idle_down":
                image=idle_down;
                break;
            case "up":
                image=(spriteNum==1)?up1:up2;
                break;
            case "down":
                image=(spriteNum==1)?down1:down2;
                break;
            case "left":
                image=(spriteNum==1)?left1:left2;
                break;
            case "right":
                image=(spriteNum==1)?right1:right2;
                break;
        }
        g2.drawImage(image,screenX,screenY,null);

    }
}
