package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Weapon extends Entity {
    GamePanel gp;
    public OBJ_Weapon(GamePanel gp){
        super(gp);
        name = "Sword";
        down1 = setup("/objects/sword", gp.tileSize, gp.tileSize);
        attackValue = 10;
    }
}
