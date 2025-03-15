package tile;

import main.GamePanel;
import main.Utility;

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
        tile=new Tile[15];
        mapTileNum=new int[gp.worldCol][gp.worldRow];
        getTileImage();
        loadMap();
    }

    public void getTileImage(){
        setup(0, "grass1", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);
        setup(6, "grass2", false);
        setup(7, "cut_tree", true);
        setup(8, "grass3", false);
        setup(9, "grass4", false);
        setup(10, "grass5", false);
        setup(11, "flower1", false);
        setup(12, "flower2", false);
        setup(13, "pit", false);

    }
    public void setup(int index,String imageName,boolean collision){
        Utility uTool=new Utility();

        try{
            tile[index]=new Tile();
            tile[index].image= ImageIO.read(getClass().getResource("/Tiles/"+imageName+".png"));
            tile[index].image=uTool.scaleImage(tile[index].image,gp.tileSize, gp.tileSize);
            tile[index].collision=collision;


        } catch (IOException e) {
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
            int screenX=worldX-gp.player.worldX+gp.player.screenX;
            int screenY=worldY-gp.player.worldY+gp.player.screenY;

            //only draw tiles that are visible instead of drawing the whole world map.
            if( worldX+gp.tileSize>gp.player.worldX-gp.player.screenX &&
                worldX-gp.tileSize<gp.player.worldX+gp.player.screenX &&
                worldY+gp.tileSize>gp.player.worldY-gp.player.screenY &&
                worldY-gp.tileSize<gp.player.worldY+gp.player.screenY){
                g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if(worldCol==gp.worldCol){
                worldCol=0;
                worldRow++;
            }
        }
    }
}
