package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		int entityLWorldX = entity.worldX + entity.solidArea.x;
		int entityRWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTWorldY = entity.worldY + entity.solidArea.y;
		int entityBWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLCol = entityLWorldX/gp.tileSize;
		int entityRCol = entityRWorldX/gp.tileSize;
		int entityTRow = entityTWorldY/gp.tileSize;
		int entityBRow = entityBWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch(entity.direction) {
		case "up":
			entityTRow = (entityTWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLCol][entityTRow];
			tileNum2 = gp.tileM.mapTileNum[entityRCol][entityTRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBRow = (entityBWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLCol][entityBRow];
			tileNum2 = gp.tileM.mapTileNum[entityRCol][entityBRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLCol = (entityLWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLCol][entityTRow];
			tileNum2 = gp.tileM.mapTileNum[entityLCol][entityBRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRCol = (entityRWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRCol][entityTRow];
			tileNum2 = gp.tileM.mapTileNum[entityRCol][entityBRow];
			if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
			{
				entity.collisionOn = true;
			}
			break;
		}
	}

}
