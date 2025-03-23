package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean up, down, left, right,enter;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    public KeyHandler(GamePanel gp) {
        this.gp=gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
        //title state
        if(gp.gameState == gp.titleState){
            if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP)gp.ui.commandNumber--;
            if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN)gp.ui.commandNumber++;
            if(code==KeyEvent.VK_ENTER){
                if(gp.ui.commandNumber == 0){
                    gp.gameState=gp.playState;
                    gp.stopMusic();
                    gp.playMusic(0);
                }
                else if(gp.ui.commandNumber == 1){
                    System.exit(0);
                }
            }
            if(gp.ui.commandNumber<0)gp.ui.commandNumber=0;
            if(gp.ui.commandNumber>1)gp.ui.commandNumber=1;

        }
        // play state
        else if(gp.gameState== gp.playState){
            if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP)up=true;
            if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN)down=true;
            if(code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT)left=true;
            if(code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT)right=true;
            if(code==KeyEvent.VK_ENTER)enter=true;
            if(code==KeyEvent.VK_P){
                gp.gameState= gp.pauseState ;
                //gp.stopMusic();
            }
        }
        //pause state
        else if(gp.gameState==gp.pauseState){
            if(code==KeyEvent.VK_P){
                gp.gameState= gp.playState ;
                //gp.playMusic(0);
            }
        }
        // dialogue state
        else if (gp.gameState==gp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState= gp.playState ;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP)up=false;
        if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN)down=false;
        if(code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT)left=false;
        if(code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT)right=false;
    }
}
