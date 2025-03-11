package entity;

import main.GamePanel;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyHandler;
    
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp,KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        
        screenX = gp.screenWidth/2-(gp.tileSize/2);
        screenY = gp.screenHeight/2-(gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
    	//Players position on the world map
        worldX=gp.tileSize * 23;
        worldY=gp.tileSize * 21;
        speed=3;
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
        if(keyHandler.up==true||keyHandler.down==true||keyHandler.left==true||keyHandler.right==true){

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
            
            //COLLISION CHECK
            collisionOn = false;
            gp.cChecker.checkTile(this);
            
            if(collisionOn == false) {
            	
            	switch(direction) {
            	case "up":worldY-=speed;
            		break;
            	case "down":worldY+=speed;
            		break;
            	case "left":worldX-=speed;
            		break;
            	case "right":worldX+=speed;
            		break;
       
            	}
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
        g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
        
    }
}










