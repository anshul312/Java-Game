package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale=3;

    public final int tileSize=originalTileSize*scale;//48*48

    public final int screenCol =16;
    public final int screenRow =12;

    public final int screenWidth= screenCol *tileSize;
    public final int screenHeight= screenRow *tileSize;

    //World Settings
    public final int worldCol=50;
    public final int worldRow=50;
    public final int worldWidth=tileSize*worldCol;
    public final int worldHeight=tileSize*worldRow;

    int FPS = 80;
    TileManager tileManager=new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    public Player player=new Player(this,keyHandler);


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread=new Thread(this);// this is passed here because GamePanel implements runnable , so thread calls run()
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval=1e9/FPS;
        double nextDrawTime=System.nanoTime()+drawInterval;

        while(gameThread !=null){
            update();

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

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
