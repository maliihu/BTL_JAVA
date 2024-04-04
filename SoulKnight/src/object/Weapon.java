package object;

import entity.Entity;
import main.GamePanel;

public class Weapon extends Entity {
    GamePanel gp;
    public Weapon(GamePanel gp){
        super(gp);
        name = "Sword";
        down1 = setup("/objects/sword", gp.tileSize, gp.tileSize);
        attackValue = 1;
    }
}
