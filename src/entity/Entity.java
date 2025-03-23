package entity;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
////
public GamePanel gp;
    public int worldX,worldY;
    public int speed;
    public int maxLife;
    public int life;
    public BufferedImage up1,up2,down1,down2,left1,right1,left2,right2;
    public BufferedImage attackUp1 , attackUp2 , attackLeft1 , attackLeft2 , attackDown1 , attackDown2 , attackRight1 , attackRight2;
    public String direction="down";
    public  String dialogues[] = new  String[20];
    public int spriteCounter=0;
    public int spriteNum=1;
    public Rectangle collisionArea=new Rectangle(0,0,48,48);
    public Rectangle attackArea=new Rectangle(0,0,0,0);
    public boolean invincible = false;
    public int invincibleCounter=0;
    public int collisionAreaDefaultX,collisionAreaDefaultY;
    public boolean collisionOn=false;
    public int actionCounter=0;
    public int dialogueIndex=0;
    public BufferedImage image,image2,image3;
    public String name;
    public boolean collision = false;
    public boolean attacking = false;
    public int type;//0 = player ,, 1 = npc , 2 = monster
    public Entity(GamePanel gp) {

        this.gp=gp;
    }

    public void setAction(){}

    public void speak(){
        switch(gp.player.direction){
            case  "down":
                direction="up";
                break;
            case "right":

                direction="left";
                break;
            case "left":

                direction="right";
                break;
            case "up":

                direction="down";
                break;
        }
        if(dialogues[dialogueIndex] == null){
            dialogueIndex=0;
        }
        gp.ui.currentDialogue=dialogues[dialogueIndex++];
    }
    public void update(){

        setAction();

        collisionOn=false;
        gp.checker.checkTile(this);
        gp.checker.checkObject(this,false);
        gp.checker.checkEntity(this , gp.npc);
        gp.checker.checkEntity(this , gp.monster);
        boolean contactPlayer=gp.checker.checkPlayer(this);

        if(this.type == 2 && contactPlayer){
            if(!gp.player.invincible){
                gp.player.life--;
                gp.player.invincible=true;
            }
        }
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
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 40){
                invincible=false;
                invincibleCounter=0;
            }
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
            if(invincible){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public BufferedImage setup(String imagePath , int width , int height){
        Utility uTool=new Utility();
        BufferedImage image=null;
        try{
            image= ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image=uTool.scaleImage(image,width,height);

        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
//
}
//