package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {
    public Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);

    }
}
