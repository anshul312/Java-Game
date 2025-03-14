package entity;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    public int worldX,worldY;
    public int speed;

    public BufferedImage up1,up2,down1,down2,left1,right1,left2,right2;

    public String direction;

    public int spriteCounter=0;
    public int spriteNum=1;
    public Rectangle collisionArea=new Rectangle(0,0,48,48);

    public int collisionAreaDefaultX,collisionAreaDefaultY;
    public boolean collisionOn=false;
    public int actionCounter=0;

    public Entity(GamePanel gp) {
        this.gp=gp;
    }

    public void setAction(){}

    public void update(){

        setAction();

        collisionOn=false;
        gp.checker.checkTile(this);
        gp.checker.checkObject(this,false);
        gp.checker.checkPlayer(this);

        if(!collisionOn){
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

    public void draw(Graphics2D g2){

        BufferedImage image=null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;


        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                worldY -gp.tileSize< gp.player.worldY + gp.player.screenY ) {
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

    public BufferedImage setup(String imagePath){
        Utility uTool=new Utility();
        BufferedImage image=null;
        try{
            image= ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image=uTool.scaleImage(image,gp.tileSize,gp.tileSize);

        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

}
