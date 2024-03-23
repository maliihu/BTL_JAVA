package monster;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOError;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Monster_Slime1 extends Entity{
    GamePanel gp;
    public Monster_Slime1(GamePanel gp) {
        super(gp);
        name = "Monster";
        type = 2;
        direction = "down";
        speed = 1;
        maxLife = 10;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 5;
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage(){

        try{

            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i <= 25 ){direction = "up";}
            if(i > 25 && i <= 50){direction = "down";}
            if(i > 50 && i <= 75){direction = "left";}
            if(i > 75){direction = "right";}

            actionLockCounter = 0;
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;
        this.direction = gp.player.direction;


    }
}
