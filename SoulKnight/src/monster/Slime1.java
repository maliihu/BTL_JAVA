package monster;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Slime1 extends Entity{
    GamePanel gp;
    public Slime1(GamePanel gp) {
        super(gp);
        name = "Monster";
        type = 2;
        direction = "down";
        defaultSpeed = 1;
        speed = defaultSpeed;
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
        onPath = true;
        getImage();
    //    setFinding();
    }

    public void getImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/monster/mon_2.png")));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void setAction(){
//        if(onPath){
//            int col = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
//            int row = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
//            searchPath(col, row);
//            int i = new Random().nextInt(200) +1;
//            if(i > 197 && !projectile.alive){
//                projectile.set(worldX, worldY, direction, true);
//                gp.projectileList.add(projectile);
//
//            }
//        }
//        else{
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
      //  }

    }

    public void setFinding(){
        onPath = true;
    }

}
