package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class GamePanel extends JPanel implements Runnable {
    final int originalTitleSize = 16; // kich thuoc cua nhan vat va 1 o ban do ( pixel)
    final int scale = 3; // ti le theo man hinh
    public final int tileSize = originalTitleSize * scale;
    public final int maxScreenCol = 16, maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50, maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldRow, worldHeight = tileSize * maxWorldCol;

    // FPS
    int FPS = 60;

    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public GameSetup aSetter = new GameSetup(this);
    public UserInterface ui = new UserInterface(this);

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[10];
    public ArrayList<Entity> projectileList  = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int menuState = 2;
    public final int characterState = 4;
    public final int shopState = 5;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true); // tranh bi de phim
    }
    public void setUpGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = playState;
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
        }
    }
    public void update(){
        if (gameState == playState){
            // PLAYER
            player.update();

            // NPC
            for (Entity e : npc) {if (e != null) {e.update();} }

            // MONSTER
            for(int i = 0; i<monster.length; i++){
                if(monster[i] != null){
                    if(monster[i].alive && !monster[i].dying) monster[i].update();
                    if(!monster[i].alive) monster[i] = null;
                }
            }

            // PROJECTILE
            Iterator<Entity> iterator = projectileList.iterator();
            while (iterator.hasNext()) {
                Entity e = iterator.next();
                if (e != null && !e.alive) {
                    iterator.remove();
                } else if (e != null) {
                    e.update();
                }
            }

        }
        if (gameState == menuState){
            // coming soon
        }
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // TILE
        tileM.draw(g2);

        // OBJECT AND PLAYER
        entityList.add(player);

        for (Entity e : npc) {if (e != null) entityList.add(e);}

        for (Entity e : obj) {if (e != null) entityList.add(e);}

        for (Entity e : monster){if (e != null) entityList.add(e);}

        for(Entity e : projectileList){if (e != null) entityList.add(e);}

        entityList.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return Integer.compare(o1.worldY, o2.worldY);
            }
        });

        // DRAW
        for (Entity entity : entityList) {entity.draw(g2);}
        entityList.clear();

        // UI
        try {
            ui.draw(g2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

