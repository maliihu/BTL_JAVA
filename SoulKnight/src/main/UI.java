package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;


public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage heart_full, heart_half, heart_blank;
    public UI(GamePanel gp){
        this.gp = gp;
    //    arial_40 = new Font("Arial", Font.PLAIN, 40);

    }

    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
    }

    public void drawCharacterScreen(){
        // FRAME
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 50;

        g2.drawString("Level: " + gp.player.level, textX, textY);
        textY += lineHeight;
        g2.drawString("HP: " + gp.player.life + "/" + gp.player.maxLife , textX, textY);
        textY += lineHeight;
        g2.drawString("Attack: " + gp.player.attack, textX, textY);
        textY += lineHeight;
        g2.drawString("Defense: " + gp.player.defense, textX, textY);
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        if(gp.gameState == gp.playState){
            // coming soon
        }
        if(gp.gameState == gp.pauseState){
            drawPauseState();
        }
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
        }


        g2.setFont(new Font("LucidaSans", Font.PLAIN, 40));
        g2.setColor(Color.white);
        g2.drawString("Hello World", 20, 40);

    }

    public void drawPauseState(){
        String text = "PAUSE";
        int x, y;

        g2.drawString(text, 50, 50);
    }


}
