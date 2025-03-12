package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;
    int hasKey=0;

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
        speed=5;
        direction="up";
    }

    public void getPlayerImage(){
        try{

            up1= ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2= ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1= ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2= ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1= ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            right1= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            left2= ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right2= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
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
    }
    public void pickUpObject(int index){
        if(index!=999){
            String objectName=gp.obj[index].name;

            switch(objectName){
                case "Key":
                    hasKey++;
                    gp.obj[index]=null;
                    System.out.println("Key"+hasKey);
                    break;
                case "Door":
                    if(hasKey>0){
                        gp.obj[index]=null;
                        hasKey--;
                    }
                    System.out.println("Key"+hasKey);
                    break;
            }

        }
    }
    public void draw(Graphics2D g2){

        BufferedImage image=null;

        switch(direction){
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
        g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
    }
}
