package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class UserInterface {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    int menu = 0;

    public UserInterface(GamePanel gp){
        this.gp = gp;
       arial_40 = new Font("Arial", Font.PLAIN, 40);
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
        g2.drawString("Armor: " + gp.player.armor + "/" + gp.player.maxArmor, textX, textY);
        textY += lineHeight;
        g2.drawString("Mana: " + gp.player.mana + "/" + gp.player.maxMana, textX, textY);
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
    public void draw(Graphics2D g2) throws IOException {

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        if(gp.gameState == gp.playState){
            // coming soon
        }
        if(gp.gameState == gp.menuState){
            drawMenuScreen();
        }
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
        }
        if(gp.gameState == gp.shopState){
            drawShopState();
        }

//        BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/avt.png")));
//        g2.drawImage(img, 100, 30,gp.tileSize, gp.tileSize, null);

        drawSubWindow(10, 30, gp.tileSize + 40, 60);

        double oneScale = (double)(gp.tileSize+20)/gp.player.maxLife;
        double hpBarValue = oneScale*gp.player.life;

        // HP
        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(20, 40, gp.tileSize+20, 12);
        g2.setColor(new Color(255, 0, 30));
        g2.fillRect(20, 40, (int)hpBarValue, 12);

        // ARMOR
        oneScale = (double)(gp.tileSize+20)/gp.player.maxArmor;
        double armorBarValue = oneScale*gp.player.armor;

        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(20, 55, gp.tileSize+20, 12);
        g2.setColor(new Color(110, 110, 110));
        g2.fillRect(20, 55, (int)armorBarValue, 12);

        // MANA
        oneScale = (double)(gp.tileSize+20)/gp.player.maxMana;
        double manaBarValue = oneScale*gp.player.mana;

        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(20, 70, gp.tileSize+20, 12);
        g2.setColor(new Color(0, 208, 255));
        g2.fillRect(20, 70, (int)manaBarValue, 12);

    }

    public void drawMenuScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int x = gp.tileSize * 6, y = gp.tileSize, width = gp.tileSize * 8, height = gp.tileSize * 10;
        drawSubWindow(x, y, width, height );

        switch (menu){
            case 0:
                int temp_x = x + gp.tileSize, temp_y = gp.tileSize*2;

                g2.drawString("Resume", temp_x, temp_y);
                g2.drawString("Full Screen", temp_x , temp_y + gp.tileSize);
                g2.drawString("Music", temp_x , temp_y + gp.tileSize*2);
                g2.drawString("Quit", temp_x + gp.tileSize*4, temp_y  );
                break;
            case 1: break;
            case 2: break;
        }
    }

    public void drawShopState(){
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(10, 10, 300, 300, 0, 0);
        g2.setFont(arial_40);g2.setColor(Color.white);
        g2.drawString("SHOP", 100, 100);

    }

}
