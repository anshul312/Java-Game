package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp=gp;
        tile=new Tile[10];
        mapTileNum=new int[gp.worldCol][gp.worldRow];
        getTileImage();
        loadMap();
    }

    public void getTileImage(){
        try{
            tile[0]= new Tile();
            tile[0].image= ImageIO.read(getClass().getResource("/Tiles/grass.png"));

            tile[1]= new Tile();
            tile[1].image= ImageIO.read(getClass().getResource("/Tiles/wall.png"));
            tile[1].collision=true;

            tile[2]= new Tile();
            tile[2].image= ImageIO.read(getClass().getResource("/Tiles/water.png"));
            tile[2].collision=true;

            tile[3]= new Tile();
            tile[3].image= ImageIO.read(getClass().getResource("/Tiles/earth.png"));

            tile[4]= new Tile();
            tile[4].image= ImageIO.read(getClass().getResource("/Tiles/tree.png"));
            tile[4].collision=true;

            tile[5]= new Tile();
            tile[5].image= ImageIO.read(getClass().getResource("/Tiles/sand.png"));


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(){
        try{
            InputStream is=getClass().getResourceAsStream("/map/world01.txt");
            BufferedReader br=new BufferedReader(new InputStreamReader(is));

            int col=0;
            int row=0;
            while(col<gp.worldCol && row<gp.worldRow){
                String line=br.readLine();

                while(col<gp.worldCol){
                    String numbers[]=line.split(" ");

                    int number=Integer.parseInt(numbers[col]);
                    mapTileNum[col][row]=number;
                    col++;
                }
                if(col==gp.worldCol){
                    col=0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e){

        }
    }
    public void draw(Graphics2D g2){
        int worldCol=0;
        int worldRow=0;
        int x=0;
        int y=0;

        while(worldCol<gp.worldCol && worldRow<gp.worldRow){
            int tileNum=mapTileNum[worldCol][worldRow];

            int worldX=worldCol*gp.tileSize;
            int worldY=worldRow*gp.tileSize;
            int screenX=(int)(worldX-gp.player.worldX+gp.player.screenX);
            int screenY=(int)(worldY-gp.player.worldY+gp.player.screenY);

            g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            worldCol++;


            if(worldCol==gp.worldCol){
                worldCol=0;
                worldRow++;
            }
        }
    }
}
