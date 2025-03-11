package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Entity {
    public int worldX,worldY;
    public int speed;

    public BufferedImage up1,up2,down1,down2,left1,right1,left2,right2;

    public String direction;

    public int spriteCounter=0;
    public int spriteNum=1;
    
    public Rectangle solidArea;
    public boolean collisionOn = false;
}
