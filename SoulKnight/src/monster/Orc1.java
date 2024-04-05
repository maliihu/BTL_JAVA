package monster;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Orc1 extends Entity {

    GamePanel gp;

    public Orc1(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 20;
        life = maxLife;
        attack = 10;
        defense = 5;
        type = 2;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;

        getImage();
        getPlayerAttackImage();
    }

    public void getImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_down_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_down_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_down_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_down_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_down_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_down_2.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getPlayerAttackImage(){
        try{
            attUp1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_attack_up_1.png")));
            attUp2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_attack_up_2.png")));
            attDown1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_attack_down_1.png")));
            attDown2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_attack_down_2.png")));
            attLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_attack_left_1.png")));
            attLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_attack_left_2.png")));
            attRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_attack_right_1.png")));
            attRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/orc_attack_right_2.png")));
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

}
