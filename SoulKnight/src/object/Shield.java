package object;

import entity.Entity;
import main.GamePanel;

public class Shield extends Entity {

    public Shield(GamePanel gp) {
        super(gp);

        name = "Shield";
        down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 1;
    }
}
