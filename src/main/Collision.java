package main;

import entity.Entity;

public class Collision {

    GamePanel gp;

    public Collision(GamePanel gp) {
        this.gp=gp;
    }

    public void checkTile(Entity entity){

        int entityLeftWorldX= (int)entity.worldX+entity.collisionArea.x;
        int entityTopWorldY= (int)entity.worldY+entity.collisionArea.y;
        int entityRightWorldX= (int)entity.worldX+entity.collisionArea.x+entity.collisionArea.width;
        int entityBottomWorldY= (int)entity.worldY+entity.collisionArea.y+entity.collisionArea.height;

        int entityLeftCol=entityLeftWorldX/gp.tileSize;
        int entityTopRow=entityTopWorldY/gp.tileSize;
        int entityRightCol=entityRightWorldX/gp.tileSize;
        int entityBottomRow=entityBottomWorldY/gp.tileSize;

        int tileNum1,tileNum2;

        switch(entity.direction){
            case "up":
                entityTopRow=(entityTopWorldY-entity.speed)/gp.tileSize;
                tileNum1=gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2=gp.tileManager.mapTileNum[entityRightCol][entityTopRow];

                if(gp.tileManager.tile[tileNum1].collision==true || gp.tileManager.tile[tileNum2].collision==true){
                    entity.collisionOn=true;
                }
                break;
            case "down":
                entityBottomRow=(entityBottomWorldY+entity.speed)/gp.tileSize;
                tileNum1=gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2=gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                if(gp.tileManager.tile[tileNum1].collision==true || gp.tileManager.tile[tileNum2].collision==true){
                    entity.collisionOn=true;
                }
                break;
            case "left":
                entityLeftCol=(entityLeftWorldX-entity.speed)/gp.tileSize;
                tileNum1=gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2=gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

                if(gp.tileManager.tile[tileNum1].collision==true || gp.tileManager.tile[tileNum2].collision==true){
                    entity.collisionOn=true;
                }
                break;
            case "right":
                entityRightCol=(entityRightWorldX+entity.speed)/gp.tileSize;
                tileNum1=gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2=gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                if(gp.tileManager.tile[tileNum1].collision==true || gp.tileManager.tile[tileNum2].collision==true){
                    entity.collisionOn=true;
                }
                break;
        }


    }
}
