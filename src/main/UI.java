package main;

import object.OBJ_Heart;
import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica ,purisaB, font,finalMessage;
    BufferedImage keyImage,heart_half,heart_full,heart_blank;
    public boolean messageOn=false;
    public String message="";
    int messageCounter=0;
    public Boolean gameOver=false;
    public String currentDialogue="";
    double playTime;
    public int commandNumber=0;
    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            font=new Font("Arial",Font.PLAIN,40);
            finalMessage=new Font("Arial",Font.BOLD,80);
            purisaB =  Font.createFont(Font.TRUETYPE_FONT,is);
            is=getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica =  Font.createFont(Font.TRUETYPE_FONT,is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        OBJ_Key key=new OBJ_Key();
        keyImage=key.image;
        OBJ_Heart heart=new OBJ_Heart();
        heart_half=heart.image2;
        heart_full=heart.image;
        heart_blank=heart.image3;
    }

    public void showMessage(String text) {
        message=text;
        messageOn=true;
    }

    public void draw(Graphics2D g2){
        this.g2=g2;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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
            if(gp.gameState == gp.titleState){
                drawTitleScreen();
            }
            if(gp.gameState==gp.playState) {
                //key
                g2.setFont(maruMonica.deriveFont(Font.BOLD,35f));
                g2.setColor(Color.white);
                g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
                g2.drawString("Key x " + gp.player.hasKey, 74, 65);
                //life
                drawPlayerLife();
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
            else if(gp.gameState == gp.pauseState){
                drawPauseScreen();
            }
            else if(gp.gameState == gp.dialogueState){
                drawDialogueScreen();
            }

        }
    }
    public void drawPlayerLife(){
        int x=gp.tileSize/2;
        int y=72;
        int i=0;
        while(i<gp.player.maxLife/2){
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x+=gp.tileSize;

        }
        x=gp.tileSize/2;
         y=72;
        i=0;
        while (i<gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i<gp.player.life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+=gp.tileSize;
        }

    }
    public void drawTitleScreen() {
        // Background: Dark Forest Green to Deep Emerald Gradient
        GradientPaint bgGradient = new GradientPaint(
                0, 0, new Color(4, 6, 61),  // Dark Forest Green (Top)
                0, gp.screenHeight, new Color(3, 36, 2) // Deep Emerald (Bottom)
        );
        g2.setPaint(bgGradient);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Set Font
        g2.setFont(maruMonica.deriveFont(Font.BOLD, 96f));
        String text = "Lost Keys of Eldoria";
        int x = gp.ui.getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // Text: Glowing Gold Gradient
        GradientPaint goldGradient = new GradientPaint(
                x, y - 50, new Color(255, 215, 0),  // Bright Gold
                x, y + 50, new Color(198, 142, 23), // Warm Bronze
                true
        );

        // Soft Shadow for Depth
        g2.setColor(new Color(20, 20, 20, 150)); // Darkened shadow
        g2.drawString(text, x + 4, y + 4);

        // Apply Gold Gradient
        g2.setPaint(goldGradient);
        g2.drawString(text, x, y);

        x=gp.screenWidth/2-gp.tileSize;
        y+=gp.tileSize*2;
        g2.drawImage(gp.player.idle_down,x,y,gp.tileSize*2,gp.tileSize*2,null);

        // menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48f));
        text="New Game";
        x=getXforCenteredText(text);

        y+=gp.tileSize*3.5;
        if(commandNumber ==0 ){
            g2.drawImage(gp.player.setup("/player/menu_sword"),x - gp.tileSize ,y - gp.tileSize,null);
        }
        g2.drawString(text, x, y);

        text="Load Game";
        x=getXforCenteredText(text);
        y+=gp.tileSize;
        if(commandNumber ==1 ){
            g2.drawImage(gp.player.setup("/player/menu_sword"),x - gp.tileSize,y- gp.tileSize,null);
        }
        g2.drawString(text, x, y);
        text="Quit";
        x=getXforCenteredText(text);
        y+=gp.tileSize;
        if(commandNumber ==2 ){
            g2.drawImage(gp.player.setup("/player/menu_sword"),x - gp.tileSize,y- gp.tileSize,null);
        }
        g2.drawString(text, x, y);



    }


    public void drawDialogueScreen(){
        //Window
        int x =gp.tileSize *2 , y = gp.tileSize/2, width = gp.screenWidth-(gp.tileSize *4), height= gp.tileSize * 4;
        drawSubWindow(x,y,width,height);
        x+=gp.tileSize;
        y+=gp.tileSize;
        g2.setFont(purisaB);
        g2.setFont(g2.getFont().deriveFont(20f));
        for(String line:currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y+=40;
        }


    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c=new Color(0,0,0 , 185);
        
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);
        c=new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);

        
    }
    public void drawPauseScreen() {
        g2.setFont(finalMessage);
        String text="PAUSED";

        int length=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x=gp.screenWidth/2-length/2;
        int y=gp.screenHeight/2;

        g2.drawString(text,x,y);
    }
    public int getXforCenteredText(String text){
        int length=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x=gp.screenWidth/2-length/2;
        return x;
    }
}
