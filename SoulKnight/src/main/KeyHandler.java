package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean keyUp, keyDown, keyLeft, keyRight, keyAttack, keySkill;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    // da nhan phim
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W) {keyUp = true;}
        if(key == KeyEvent.VK_S) {keyDown = true;}
        if(key == KeyEvent.VK_A) {keyLeft = true;}
        if(key == KeyEvent.VK_D) {keyRight = true;}
        if(key == KeyEvent.VK_H) {keySkill = true;}
        if(key == KeyEvent.VK_K) {keyAttack = true;}
        if(key == KeyEvent.VK_P){
            if(gp.gameState == gp.playState){gp.gameState = gp.menuState;}
            else if(gp.gameState == gp.menuState){gp.gameState = gp.playState;}
        }
        if(key == KeyEvent.VK_C){gp.gameState = gp.characterState;}

    }

    // da phat hanh
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) {keyUp = false;}
        if(code == KeyEvent.VK_S) {keyDown = false;}
        if(code == KeyEvent.VK_A) {keyLeft = false;}
        if(code == KeyEvent.VK_D) {keyRight = false;}
        if(code == KeyEvent.VK_H) {keySkill = false;}
        if(code == KeyEvent.VK_K) {keyAttack = false;}
        if(code == KeyEvent.VK_C) {gp.gameState = gp.playState;}
    }
}
