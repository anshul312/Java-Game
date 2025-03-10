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

        int dx=0,dy=0;
        if(keyHandler.up ||keyHandler.down ||keyHandler.left ||keyHandler.right ){

            if(keyHandler.up){
                direction="up";
                dy-=1;
            }
            if(keyHandler.down){
                direction="down";
                dy+=1;
            }
            if(keyHandler.left){
                direction="left";
                dx-=1;
            }
            if(keyHandler.right){
                direction="right";
                dx+=1;
            }
            if(dx!=0 && dy!=0){
                double normFactor = Math.sqrt(2) / 2; // so that velocity along the diagonal direction is same as vertical(or horizontal)
                x += dx * speed * normFactor;
                y += dy * speed * normFactor;
            } else {
                x += dx * speed;
                y += dy * speed;
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
        g2.drawImage(image,(int)x,(int)y,gp.tileSize,gp.tileSize,null);
    }
}
