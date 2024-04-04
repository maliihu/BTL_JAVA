package object;

import entity.Entity;
import main.GamePanel;

public class Shop extends Entity {
    GamePanel gp;
    public Shop(GamePanel gp){
        super(gp);
        name = "Shop";
        down1 = setup("/objects/shop", gp.tileSize, gp.tileSize);
        collision = true;
    }
}
