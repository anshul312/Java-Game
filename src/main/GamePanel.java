package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale=3;

    public final int tileSize=originalTileSize*scale;//48*48

    final int screenCol=18;
    final int screenRow=16;

    final int screenWidth=screenCol*tileSize;
    final int screenHeight=screenRow*tileSize;

    int FPS = 80;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player=new Player(this,keyHandler);


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
        player.draw(g2);
        g2.dispose();
    }
}
