package main;

import java.awt.*;

public class EventHandler {
    GamePanel gamePanel;
    Rectangle eventRect ;
    int eventRectDefaultX , eventRectDefaultY ;
    public EventHandler(GamePanel gamePanel) {
        this.gamePanel=gamePanel;
        eventRect=new Rectangle();
        eventRect.x=23;
        eventRect.y=23;
        eventRect.width=2;
        eventRect.height=2;
        eventRectDefaultX=eventRect.x;
        eventRectDefaultY=eventRect.y;

    }
    public void checkEvent() {
        if(hit(18,20,"any")== true){
            damagePit(gamePanel.dialogueState);
        }
    }
    public boolean hit(int eventCol,int eventRow, String reqDirection) {
        boolean hit=false;
        gamePanel.player.collisionArea.x=gamePanel.player.worldX+gamePanel.player.collisionArea.x;
        gamePanel.player.collisionArea.y=gamePanel.player.worldY+gamePanel.player.collisionArea.y;
        eventRect.x=eventCol*gamePanel.tileSize+eventRect.x;
        eventRect.y=eventRow*gamePanel.tileSize+eventRect.y;
        if(gamePanel.player.collisionArea.intersects(eventRect)) {
            if(gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit=true;
            }
        }
        gamePanel.player.collisionArea.x= gamePanel.player.collisionAreaDefaultX;
        gamePanel.player.collisionArea.y= gamePanel.player.collisionAreaDefaultY;
        return hit;
    }
    public void damagePit(int gameState) {
        gamePanel.gameState=gameState;
        gamePanel.ui.currentDialogue="You fall in pit";
        gamePanel.player.life--;
    }
}
