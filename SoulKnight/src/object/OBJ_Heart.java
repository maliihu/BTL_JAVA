package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);

    }
}
