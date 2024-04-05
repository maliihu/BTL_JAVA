package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    KeyHandler keyH;
    public final int SCREEN_X, SCREEN_Y;
    boolean checkSword = false;
    int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        SCREEN_X = gp.screenWidth/2 - gp.tileSize/2;
        SCREEN_Y = gp.screenHeight /2 - gp.tileSize/2;
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 6; life = maxLife;
        maxArmor = 10; armor = maxArmor;
        maxMana = 2; mana = maxMana;
        exp = 0; nextLevelExp = 5;
        coin = 0;

        currentWeapon = new Weapon(gp);
        currentShield = new Shield(gp);
        attack = currentWeapon.attackValue;
        defense = currentShield.defenseValue;

        projectile = new Fireball(gp);
        
    }

    public void getPlayerImage(){
        up1 = setup("/player/up1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/up2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/down2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/left2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/right2", gp.tileSize, gp.tileSize);
    }
    public void getPlayerAttackImage(){

        attUp1 = setup("/player/att_up_1", gp.tileSize, gp.tileSize*2);
        attUp2 = setup("/player/att_up_2", gp.tileSize, gp.tileSize*2);
        attDown1 = setup("/player/att_down_1", gp.tileSize, gp.tileSize*2);
        attDown2 = setup("/player/att_down_2", gp.tileSize, gp.tileSize*2);
        attLeft1 = setup("/player/att_left_1", gp.tileSize*2, gp.tileSize);
        attLeft2 = setup("/player/att_left_2", gp.tileSize*2, gp.tileSize);
        attRight1 = setup("/player/att_right_1", gp.tileSize*2, gp.tileSize);
        attRight2 = setup("/player/att_right_2", gp.tileSize*2, gp.tileSize);
    }

    public void update(){


        if(keyH.keyAttack && checkSword){
            attacking = true;
            attacking();
        }
        else if(keyH.keyUp || keyH.keyDown || keyH.keyLeft || keyH.keyRight){
            attacking = false;
            if(keyH.keyUp){direction = "up";}
            else if(keyH.keyDown){direction = "down";}
            else if(keyH.keyLeft){direction = "left";}
            else if(keyH.keyRight){direction = "right";}

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION, CHAR AND NPC
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION, CHAR AND MONSTER
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            interactMonster(monsterIndex);

            // CHECK MONSTER AND CHAR
            int monsterIndex1 = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex1);

            // IF COLLISION IS FALSE PLAYER CAN MOVE
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


        }spriteCounter++;
        // sau mỗi 10s sẽ vẽ img1 hoặc img2 lên hình
        if(spriteCounter > 12){
            if(spriteNum == 1) {spriteNum = 2;}
            else if(spriteNum == 2) {spriteNum = 1;}
            spriteCounter = 0;
        }

        if(gp.keyH.keySkill && !projectile.alive && mana > 0){
            projectile.set(worldX, worldY, direction, true);
            gp.projectileList.add(projectile);
            projectile.checkMana();
        }

        // để bật tắt trạng thái invincible
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }
    public void attacking(){

        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction){
                case "up":
                    worldY -= attackArea.height ;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void pickUpObject(int index){
        if(index != 999){
            String objName = gp.obj[index].name;
            switch (objName){
                case "Sword":
                    checkSword = true;
                    gp.obj[index] = null;
                    break;
                case "Heart":
                    if(life < maxLife){
                        life += 1;
                        gp.obj[index] = null;
                    }
                    break;
                case "Chest":
                    int x = gp.obj[index].worldX / gp.tileSize;
                    int y = gp.obj[index].worldY / gp.tileSize;

                    gp.obj[index] = new Heart(gp);
                    gp.obj[index].worldX = x * gp.tileSize;
                    gp.obj[index].worldY = y * gp.tileSize;
                    break;
                case "Key":
                    gp.obj[index] = null;
                    break;
                case "Shop":
                    gp.gameState = 5;
                    break;
            }
        }
    }

    // va chạm của char với npc
    public void interactNPC(int index){
        if(index != 999){
            System.out.println("Hello NPC");
        }
    }

    // va chạm của char với quái
    public void interactMonster(int index){
        if(index != 999){
        //    System.out.println("Hello Monster");

        }
    }

    // va chạm giữa quái và char
    public void contactMonster(int index){
        if(index != 999){
            if(!invincible){
                int damage = gp.monster[index].attack - defense;
                if(damage < 0) damage = 0;
                armor -= damage;
                if(armor == 0){
                    life -= damage;
                }
                invincible = true;
            }
        }
    }

    public void damageMonster(int index, int attack){
        if(index != 999){
            if(!gp.monster[index].invincible){
                knockBack(gp.monster[index]);
                int damage = attack - gp.monster[index].defense;
                if(damage < 0) damage = 0;
                gp.monster[index].life -= damage;
                gp.monster[index].invincible = true;
                gp.monster[index].damageReaction();

                if(gp.monster[index].life <= 0){
                    gp.monster[index].dying = true;
                    exp += gp.monster[index].exp;
                    checkLevelUp();
                //    gp.monster[index] = null;
                }

            }
        }
    }
    public void knockBack(Entity e){
        e.direction = direction;
        e.speed += 10;
        e.knockBack = true;
    }
    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp *= 2;
            maxLife += 5;
            life += 5;
            attack++;
            defense++;
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX = SCREEN_X;
        int tempScreenY = SCREEN_Y;
        switch (direction){
            case "up":
                if(!attacking){
                    if(spriteNum == 1){image = up1;}
                    if(spriteNum == 2){image = up2;}
                }
                else{
                    tempScreenY = SCREEN_Y - gp.tileSize;
                    if(spriteNum == 1){image = attUp1;}
                    if(spriteNum == 2){image = attUp2;}
                }
                break;
            case "down":
                if(!attacking){
                    if(spriteNum == 1){image = down1;}
                    if(spriteNum == 2){image = down2;}
                }
                else{
                    if(spriteNum == 1){image = attDown1;}
                    if(spriteNum == 2){image = attDown2;}
                }
                break;
            case "left":
                if(!attacking){
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                }
                else{
                    tempScreenX = SCREEN_X - gp.tileSize;
                    if(spriteNum == 1){image = attLeft1;}
                    if(spriteNum == 2){image = attLeft2;}
                }
                break;
            case "right":
                if(!attacking){
                    if(spriteNum == 1){image = right1;}
                    if(spriteNum == 2){image = right2;}
                }
                else{
                    if(spriteNum == 1){image = attRight1;}
                    if(spriteNum == 2){image = attRight2;}
                }
                break;
        }

//        double oneScale = (double)gp.tileSize/maxLife;
//        double hpBarValue = oneScale*life;
//
//        // HP BACKGROUND
//        g2.setColor(new Color(35, 35, 35));
//        g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);
//
//        // HP CURRENT
//        g2.setColor(new Color(255, 0, 30));
//        g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);

        if(life <= 0) {System.out.println("Game over");}

        if(invincible) {g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));}
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
