package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean up, down, left, right;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    public KeyHandler(GamePanel gp) {
        this.gp=gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();

        if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP)up=true;
        if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN)down=true;
        if(code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT)left=true;
        if(code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT)right=true;

        if(code==KeyEvent.VK_P){
            gp.gameState= (gp.gameState==gp.playState)? gp.pauseState : gp.playState;
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
