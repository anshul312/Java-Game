package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font font,finalMessage;
    BufferedImage keyImage;
    public boolean messageOn=false;
    public String message="";
    int messageCounter=0;
    public Boolean gameOver=false;

    double playTime;

    public UI(GamePanel gp) {
        this.gp = gp;
        font=new Font("Arial",Font.PLAIN,40);
        finalMessage=new Font("Arial",Font.BOLD,80);

        OBJ_Key key=new OBJ_Key();
        keyImage=key.image;
    }

    public void showMessage(String text) {
        message=text;
        messageOn=true;
    }

    public void draw(Graphics2D g2){
        this.g2=g2;

        if(gameOver==true){
            g2.setFont(font);
            g2.setColor(Color.white);

            String text="You found the treasure!";
            int textLength=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

            int x=gp.screenWidth/2-textLength/2;
            int y=gp.screenWidth/2-gp.tileSize*3;
            g2.drawString(text,x,y);

            text="Your Time is :"+String.format("%.2f",playTime) +"!";
            textLength=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x=gp.screenWidth/2-textLength/2;
            y=gp.screenWidth/2-gp.tileSize*4;
            g2.drawString(text,x,y);

            g2.setFont(finalMessage);
            g2.setColor(Color.yellow);
            text="Congratulations!";
            textLength=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x=gp.screenWidth/2-textLength/2;
            y=gp.screenWidth/2+gp.tileSize*2;
            g2.drawString(text,x,y);
            gp.playSE(3);

            gp.gameThread=null;

        }
        else{
            if(gp.gameState==gp.playState) {
                g2.setFont(font);
                g2.setColor(Color.white);
                g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
                g2.drawString("Key x " + gp.player.hasKey, 74, 65);

                //Time
                playTime += (double) 1 / 60;
                g2.drawString(String.format("Time %.2f ", playTime), gp.tileSize * 11, 65);

                //Message
                if (messageOn) {
                    g2.setFont(g2.getFont().deriveFont(30f));
                    g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                    messageCounter++;

                    if (messageCounter > 120) {
                        messageCounter = 0;
                        messageOn = false;
                    }
                }
            }
            else{
                drawPauseScreen();
            }
        }
    }
    public void drawPauseScreen() {
        g2.setFont(finalMessage);
        String text="PAUSED";

        int length=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x=gp.screenWidth/2-length/2;
        int y=gp.screenHeight/2;

        g2.drawString(text,x,y);
    }
}
