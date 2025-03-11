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

    public Player(GamePanel gp,KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        screenX=gp.screenWidth/2;
        screenY=gp.screenHeight/2;

        collisionArea=new Rectangle(8,16,32,32);
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

        collisionOn=false;
        gp.checker.checkTile(this);

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
            if(collisionOn==false){
                switch(direction){
                    case "up": dy-=1; break;
                    case "down": dy+=1; break;
                    case "left": dx-=1; break;
                    case "right": dx+=1; break;
                }
            }
            if(dx!=0 && dy!=0){
                double normFactor = Math.sqrt(2) / 2; // so that velocity along the diagonal direction is same as vertical(or horizontal)
                worldX += dx * speed * normFactor;
                worldY += dy * speed * normFactor;
            } else {
                worldX += dx * speed;
                worldY += dy * speed;
            }

            spriteCounter++;
            if(spriteCounter>10) {
                spriteNum *= -1;
                spriteCounter = 0;
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
        g2.drawImage(image,(int)screenX,(int)screenY,gp.tileSize,gp.tileSize,null);
    }
}
