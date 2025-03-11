package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public double worldX,worldY;
    public int speed;

    public BufferedImage up1,up2,down1,down2,left1,right1,left2,right2;

    public String direction;

    public int spriteCounter=0;
    public int spriteNum=1;
    public Rectangle collisionArea;
    public boolean collisionOn=false;

}
