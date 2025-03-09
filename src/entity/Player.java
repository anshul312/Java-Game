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

    public Player(GamePanel gp,KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        x=100;
        y=100;
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
        if(keyHandler.up==true||keyHandler.down==true||keyHandler.left==true||keyHandler.right==true){

            if(keyHandler.up){
                direction="up";
                y-=speed;
            }
            if(keyHandler.down){
                direction="down";
                y+=speed;
            }
            if(keyHandler.left){
                direction="left";
                x-=speed;
            }
            if(keyHandler.right){
                direction="right";
                x+=speed;
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
        g2.drawImage(image,x,y,gp.tileSize,gp.tileSize,null);
    }
}
