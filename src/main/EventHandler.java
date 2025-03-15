package main;

import java.awt.*;

public class EventHandler {
    GamePanel gamePanel;
    EventRect eventRect [] [];
    int previousEventX , previousEventY ;
    boolean canTouchEvent = true ;
    public EventHandler(GamePanel gamePanel) {
        this.gamePanel=gamePanel;
        eventRect=new EventRect[gamePanel.worldCol][gamePanel.worldRow];
        int col=0,row=0;
        while(col<gamePanel.worldCol && row<gamePanel.worldRow){
            eventRect[col][row]=new EventRect();

            eventRect[col][row].x=23;
            eventRect[col][row].y=23;//
            eventRect[col][row].width=2;
            eventRect[col][row].height=2;
            eventRect[col][row].eventRectDefaultX=eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY=eventRect[col][row].y;

            col++;
            if(col >= gamePanel.worldCol){
                col=0;
                row++;
            }
        }
        //

    }
    public void checkEvent() {
        int xDistance=Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance=Math.abs(gamePanel.player.worldY - previousEventY);
        int distance= Math.max(xDistance,yDistance);
        if(distance > gamePanel.tileSize){
            canTouchEvent=true;
        }
        if(canTouchEvent){
            if(hit(38,26,"down")== true){
                damagePit(gamePanel.dialogueState);
            }

            if(hit(14,20,"up") == true){
                teleport(gamePanel.dialogueState);
            }
            if(hit(23,7,"up" ) == true|| hit(23,7,"idle_up")== true){
                healingPool(23 , 7 , gamePanel.dialogueState);
            }
        }

    }
    public boolean hit(int eventCol,int eventRow, String reqDirection) {
        boolean hit=false;
        gamePanel.player.collisionArea.x=gamePanel.player.worldX+gamePanel.player.collisionArea.x;
        gamePanel.player.collisionArea.y=gamePanel.player.worldY+gamePanel.player.collisionArea.y;
        eventRect[eventCol][eventRow].x=eventCol*gamePanel.tileSize+eventRect[eventCol][eventRow].x;
        eventRect[eventCol][eventRow].y=eventRow*gamePanel.tileSize+eventRect[eventCol][eventRow].y;
        if(gamePanel.player.collisionArea.intersects(eventRect[eventCol][eventRow]) && eventRect[eventCol][eventRow].eventDone == false) {
            if(gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit=true;
                previousEventX= gamePanel.player.worldX;
                previousEventY= gamePanel.player.worldY;
            }
        }
        gamePanel.player.collisionArea.x= gamePanel.player.collisionAreaDefaultX;
        gamePanel.player.collisionArea.y= gamePanel.player.collisionAreaDefaultY;
        eventRect[eventCol][eventRow].x=eventRect[eventCol][eventRow].eventRectDefaultX;
        eventRect[eventCol][eventRow].y=eventRect[eventCol][eventRow].eventRectDefaultY;
        return hit;
    }
    public void damagePit(int gameState) {
        gamePanel.gameState=gameState;
        gamePanel.ui.currentDialogue="You fell in a pit";
        gamePanel.player.life--;
        canTouchEvent=false;
    }
    public void healingPool(int col , int row , int gameState) {
        if(gamePanel.keyHandler.enter == true){
            gamePanel.gameState=gameState;
            gamePanel.ui.currentDialogue="You drank the healing water\nYour lives will be restored.";
            gamePanel.player.life=gamePanel.player.maxLife;
            eventRect[col][row].eventDone=true;
        }

    }
    public void teleport(int gameState) {
        gamePanel.gameState=gameState;
        gamePanel.ui.currentDialogue="You teleported!";
        gamePanel.player.worldX=gamePanel.tileSize*38;
        gamePanel.player.worldY=gamePanel.tileSize*13;

    }
}
