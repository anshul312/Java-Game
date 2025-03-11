package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

	
	//----SCREEN SETTINGS----
    final int originalTileSize = 16;
    final int scale=3;

    public final int tileSize=originalTileSize*scale;//48*48

    public final int screenCol=18;
    public final int screenRow=16;

    public final int screenWidth=screenCol*tileSize;
    public final int screenHeight=screenRow*tileSize;

    //World map settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow =50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    int FPS = 80;
    TileManager tileM = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player=new Player(this,keyHandler);//passes the gamePanel class(here represented by this) and the keyhandler
    public SuperObject obj[] = new SuperObject[10];
    
    
    

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);//smoothens gameplay 
        this.addKeyListener(keyHandler);
        this.setFocusable(true);//to revive input
    }
    
    public void setupGame() {
    	
    	aSetter.setObject();
    }

    public void startGameThread(){
        gameThread=new Thread(this);// this is passed here because GamePanel implements runnable 
        gameThread.start();//thread calls run()
    }

    @Override
    public void run() {

        double drawInterval=1e9/FPS;
        double nextDrawTime=System.nanoTime()+drawInterval;//returns the current value of the running java virtual machine

        while(gameThread !=null){
        	//updates character position
            update();
           // to draw the screen with updated information
            repaint();


            try {
                double extraTime=nextDrawTime-System.nanoTime();
                if(extraTime<0) extraTime=0;

                Thread.sleep((long) (extraTime/1e6));

                nextDrawTime+=drawInterval;
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){
        player.update();
    }
//Built-in method in java to draw things
    //Graphics is a class which has many functions to draw objects on the screen
    public void paintComponent(Graphics g){
    	//calls the parent class that is Jpanel
        super.paintComponent(g);
        //Graphics 2-D class extends Graphics class to provide better control
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        
        for(int i=0;i< obj.length;i++)
        {
        	if(obj[i]!=null) {
        		obj[i].draw(g2, this);
        	}
        }
        
        player.draw(g2);
        g2.dispose();//prevents memory leaks
    }
}
