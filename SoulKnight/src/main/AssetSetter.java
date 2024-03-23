package main;

import monster.Monster_Orc1;
import monster.Monster_Slime1;
import object.*;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject() {
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 38 * gp.tileSize;
        gp.obj[2].worldY = 8 * gp.tileSize;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 11 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = 8 * gp.tileSize;
        gp.obj[4].worldY = 28 * gp.tileSize;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = 12 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;

        gp.obj[6] = new OBJ_Chest(gp);
        gp.obj[6].worldX = 20 * gp.tileSize;
        gp.obj[6].worldY = 20 * gp.tileSize;

        gp.obj[7] = new OBJ_Weapon(gp);
        gp.obj[7].worldX = 23 * gp.tileSize;
        gp.obj[7].worldY = 23 * gp.tileSize;

        gp.obj[8] = new OBJ_Heart(gp);
        gp.obj[8].worldX = 24 * gp.tileSize;
        gp.obj[8].worldY = 24 * gp.tileSize;
    }

    public void setNPC(){
//        gp.npc[0] = new NPC_OldMan(gp);
//        gp.npc[0].worldX = gp.tileSize * 21;
//        gp.npc[0].worldY = gp.tileSize * 21;
    }

    public void setMonster(){

        gp.monster[0] = new Monster_Slime1(gp);
        gp.monster[0].worldX = gp.tileSize * 20;
        gp.monster[0].worldY = gp.tileSize * 40;

        gp.monster[1] = new Monster_Slime1(gp);
        gp.monster[1].worldX = gp.tileSize * 20;
        gp.monster[1].worldY = gp.tileSize * 20;

        gp.monster[2] = new Monster_Orc1(gp);
        gp.monster[2].worldX = gp.tileSize * 22;
        gp.monster[2].worldY = gp.tileSize * 22;

    }
}