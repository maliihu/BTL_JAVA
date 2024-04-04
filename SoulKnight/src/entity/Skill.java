package entity;

import main.GamePanel;

public class Skill extends Entity{
    public Skill(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive){
        this.worldX= worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.life = maxLife;
    }

    public void update(){
        // damage skill
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        if(monsterIndex != 999) {gp.player.damageMonster(monsterIndex, attack);}

        life--;
        if(life <= 0){
            alive = false;
        }
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

        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1){spriteNum = 2;}
            else if (spriteNum == 2) {spriteNum = 1;}
            spriteCounter = 0;
        }
    }
    public void checkMana() {}
}
