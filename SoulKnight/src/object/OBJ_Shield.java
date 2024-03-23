package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity {

    public OBJ_Shield(GamePanel gp) {
        super(gp);

        name = "Shield";
        down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 1;
    }
}
