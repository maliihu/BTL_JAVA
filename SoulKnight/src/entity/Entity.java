package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    // bản thiết kế
    GamePanel gp;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attUp1, attUp2, attDown1, attDown2, attLeft1, attLeft2, attRight1, attRight2;
    public int solidAreaDefaultX, solidAreaDefaultY;

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean knockBack = false;
    public boolean onPath = false;

    // COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter;
    int knockBackCounter = 0;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    // OBJ
    public String name;
    public boolean collision = false;

    // CHARACTER STATUS
    public int maxLife, life;
    public int maxArmor, armor;
    public int maxMana, mana;
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int typePlayer = 0, typeNPC = 1, typeMonster = 2;
    public int speed, defaultSpeed, level, attack, defense, coin;
    public int exp, nextLevelExp;
    public Entity currentWeapon;
    public Entity currentShield;
    public int useMana;
    public Skill projectile;
    public int attackValue, defenseValue;

    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public void damageReaction(){};
    public void setAction(){}
    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer){
            if(!gp.player.invincible){
                int damage = attack - defense;
                if(damage < 0) damage = 0;
                gp.player.life -= damage;
                gp.player.invincible = true;
            }
        }
    }
    public void update(){
        if(knockBack){
            if(collisionOn){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else{
                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            knockBackCounter++;
            if(knockBackCounter == 3){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }

        }

        setAction();
        checkCollision();

        if(!collisionOn){
            switch (direction){
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1) {spriteNum = 2;}
            else if(spriteNum == 2) {spriteNum = 1;}
            spriteCounter = 0;
        }

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.SCREEN_X &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.SCREEN_X &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.SCREEN_Y &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.SCREEN_Y){

            switch (direction){
                case "up":
                    if(spriteNum == 1) {image = up1;}
                    if(spriteNum == 2) {image = up2;}
                    break;
                case "down":
                    if(spriteNum == 1) {image = down1;}
                    if(spriteNum == 2) {image = down2;}
                    break;
                case "left":
                    if(spriteNum == 1) {image = left1;}
                    if(spriteNum == 2) {image = left2;}
                    break;
                case "right":
                    if(spriteNum == 1) {image = right1;}
                    if(spriteNum == 2) {image = right2;}
                    break;
            }

            // MONSTER HP
            if(type == typeMonster && hpBarOn){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale * life;

                // thanh máu nền
                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);

                // thanh máu hiện tại
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);

                hpBarCounter++;
                if(hpBarCounter > 600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if(invincible){
                hpBarOn = true;
                hpBarCounter = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
            if(dying) dyingAnimation(g2);
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            // RESET ALPHA
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        }
    }
    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        if (dyingCounter <= 5) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        if (dyingCounter > 5 && dyingCounter <= 10) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter > 10 && dyingCounter <= 15) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        if (dyingCounter > 15 && dyingCounter <= 20) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter > 20 && dyingCounter <= 25) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        if (dyingCounter > 25 && dyingCounter <= 30) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter > 30 && dyingCounter <= 35) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        if (dyingCounter > 35 && dyingCounter <= 40) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter > 40){
             dying = false;
             alive = false;
        }
    }

    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool uTool =  new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaledImage(image, width, height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pF.setNodes(startCol, startRow, goalCol, goalRow, this);

        if(gp.pF.search()){
            int nextX = gp.pF.pathList.getFirst().col + gp.tileSize;
            int nextY = gp.pF.pathList.getFirst().row + gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBotY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            }
            else if (enTopY >= nextY && enBotY < nextY + gp.tileSize){
                if (enLeftX > nextX) direction = "left";
                if (enLeftX < nextX) direction = "right";
            }
            else if (enTopY > nextY && enLeftX > nextX){
                direction = "up";
                checkCollision();
                if(collisionOn) direction = "left";
            }
            else if (enTopY > nextY && enLeftX < nextX){
                direction = "up";
                checkCollision();
                if(collisionOn) direction = "right";
            }
            else if (enTopY < nextY && enLeftX > nextX){
                direction = "down";
                checkCollision();
                if(collisionOn) direction = "left";
            }
            else if (enTopY < nextY && enLeftX < nextX){
                direction = "down";
                checkCollision();
                if(collisionOn) direction = "right";
            }
        }
        int nextCol = gp.pF.pathList.getFirst().col;
        int nextRow = gp.pF.pathList.getFirst().row;
        if(nextCol == goalCol && nextRow == goalRow) onPath = false;
    }
}
