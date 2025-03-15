package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    final static int originalTileSize = 16;
    final static int  scale=3;

    public static final int tileSize=originalTileSize*scale;//48*48

    public final int screenCol =16;
    public final int screenRow =12;

    public final int screenWidth= screenCol *tileSize;
    public final int screenHeight= screenRow *tileSize;

    //World Settings
    public final int worldCol=50;
    public final int worldRow=50;

    int FPS = 60;
    TileManager tileManager=new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(GamePanel.this);

    Sound music = new Sound();
    Sound se=new Sound();
    Thread gameThread;

    public Collision checker=new Collision(this);
    public EventHandler eHandler=new EventHandler(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Player player=new Player(this,keyHandler);
//
    public Entity obj[] = new Entity[10];
    public Entity npc[]=new Entity[10];
    public Entity monster[]=new Entity[10];
    ArrayList<Entity> entityList=new ArrayList<>();
    //Game State
    public int gameState;
    public final int titleState=0;
    public final int playState=1;
    public final int pauseState=2;
    public final int dialogueState=3;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        playMusic(4);
        gameState=titleState;
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
        if(gameState==playState){
            //PLAYER
            player.update();

            //NPC
            for(int i=0;i<npc.length;i++){
                if(npc[i]!=null){
                    npc[i].update();
                }
            }
            for (int i=0;i<monster.length;i++){
                if(monster[i]!=null){
                    monster[i].update();
                }
            }
        }
        if(gameState==pauseState){
            //nothing
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //debug
        long drawStart=0;
        drawStart=System.nanoTime();
        //title state
        if(gameState==titleState){
            ui.draw(g2);
        }
        else {

        tileManager.draw(g2);

        entityList.add(player);

        //adding entities
        for(int i=0; i<npc.length;i++){
            if(npc[i]!=null){
                entityList.add(npc[i]);
            }
        }
        for (int i=0; i< obj.length;i++){
            if(obj[i]!=null){
                entityList.add(obj[i]);
            }
        }

        for (int i=0; i<monster.length;i++){
            if(monster[i]!=null){
                entityList.add(monster[i]);
            }
        }
        Collections.sort(entityList,new Comparator<Entity>() {

            @Override
            public int compare(Entity e1, Entity e2) {
                int result = Integer.compare(e1.worldY,e2.worldY);
                return result;
            }
        });
        //draw entities
            for (int i=0; i< entityList.size();i++){
                entityList.get(i).draw(g2);
            }
        //UI
            //empty entity list
            for (int i=0; i< entityList.size();i++){
                entityList.remove(i);
            }
        ui.draw(g2);

        long drawEnd=System.nanoTime();
        long deltaTime=drawEnd-drawStart;
        g2.setColor(Color.BLACK);
        }
//        g2.drawString("Draw time"+deltaTime,10,400);
//        System.out.println("Draw time"+deltaTime);

        g2.dispose();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
