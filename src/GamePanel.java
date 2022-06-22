import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.Graphics2D;
import java.io.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    int ogTileSize = 16; // 16 pxl tile size
    int scale = 3; // scale multiplier
    int tileSize = ogTileSize * scale; // tile size
    int maxScreenCol = 24; // max screen x
    int maxScreenRow = 17; // max screen y
    int screenX = tileSize * maxScreenCol; // actual screen dimensions
    int screenY = tileSize * maxScreenRow; // actual screen dimensions

    // Title menu commands
    int commandNum = 0;

    int maxWorldCol = 168;
    int maxWorldRow = 22;
    int worldHeight = maxWorldCol * tileSize;
    int worldWidth = maxWorldRow * tileSize;

    int FPS = 60; // fps

    // Tyler arrayList
    ArrayList<Tyler> tylerList = new ArrayList<>();
    File[] sounds = new File[30];

    // Controls class
    TileManager tileM = new TileManager(this); // tile manager object
    KeyHandler keyH = new KeyHandler(this); // control handler object
    Thread thread; // thread
    // player
    Max max = new Max(this, keyH);
    Wong wong = new Wong(this);
    // image background
    Image background;

    // Name: GamePanel
    // Purpose: game constructor
    // Param: n/a
    // Return: n/a
    public GamePanel() {
        // set dimensions
        setPreferredSize(new Dimension(screenX, screenY - 2 * tileSize));
        setVisible(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        // this.setDoubleBuffered(true);

        // inputting sounds
        try {
            sounds[0] = new File("map1.wav");
        } catch (Exception e) {
            System.out.println(e);
        }

        // start threading game
        thread = new Thread(this);
        thread.start();
    }

    // Name: run
    // Purpose: constantly loop the game || makes it run
    // Param: n/a
    // Return: n/a
    public void run() {
        initialize();
        while (true) {

            update();
            this.repaint();

            try {
                Thread.sleep(1000 / FPS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Name: update
    // Purpose: updates values inside the run
    // Param: n/a
    // Return: void
    public void update() {

        maxAction();
        tylerAction();
        wongAction();
        checkCollision();

        if (max.dead) {
            max.player.x = 0;
            max.player.y = 0;
            max.hp = max.maxHp;
            max.dead = false;
        }

    }

    public void maxAction() {
        max.move();
        max.keepInBound();
    }

    public void tylerAction() {
        for (int i = 0; i < tylerList.size(); i++) {
            for (int j = tylerList.size() - 1; j >= 0; j--) {
                tylerList.get(j).checkTylerCollision(tylerList.get(i));
            }
        }
        for (int i = 0; i < tylerList.size(); i++) {
            tylerList.get(i).move();
            tylerList.get(i).setAction();
            tylerList.get(i).checkPlayerCollision(max, keyH);
            tylerList.get(i).keepInBound();
        }
    }

    public void wongAction() {
        wong.move();
        wong.setAction();
        wong.keepInBound();
        wong.keepInBoundProj();
    }

    public void checkCollision() {
        // player collision
        boolean flag = false;
        for (int i = 0; i < tileM.getTiles().size(); i++) {
            if (max.checkCollision(tileM.getTiles().get(i))) {
                flag = true;
            }
            for (int j = 0; j < tylerList.size(); j++) {
                max.checkTylerCollision(tylerList.get(j));
                if (tylerList.get(j).checkCollision(tileM.getTiles().get(i), tylerList.get(j).getyLoc())) {
                    flag = true;
                }
            }
            if (wong.checkCollision(tileM.getTiles().get(i))) {
                flag = true;
            }
            for (int k = 0; i < wong.projList.size(); i++) {
                max.checkProjCollision(wong.proj);
                wong.checkProjCollision(tileM.getTiles().get(i), k, max);
            }
        }

        // max.checkWongCollision(wong);

        // for (int i = 0; i < tileM.getTiles().size(); i++) {
        // if (tyler.checkCollision(tileM.getTiles().get(i))) {
        // flag = true;
        // }
        // }
        // if (!flag) {
        // max.setAirborne(true);
        // }
    }

    // Name:
    // Purpose:
    // Param:
    // Return:
    public void initialize() {
        // setups before the game starts running

        // if map 1
        tylerList.add(new Tyler(this, (int) (tileSize * 14), (int) (tileSize * 15), 16));
        tylerList.add(new Tyler(this, (int) (tileSize * 96), (int) (tileSize * 7), 7));

        try {
            background = ImageIO.read(getClass().getResourceAsStream("/background/map1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        playSound(0);
    }

    // Name: paintComponent
    // Purpose: draw the game || characters, background, ect.
    // Param: Graphics
    // Return: void
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(background, 0, 0, screenX + 200, screenY, null);
        tileM.draw(g2);
        max.draw(g2);
        for (int i = 0; i < tylerList.size(); i++) {
            if (!tylerList.get(i).dead)
                tylerList.get(i).draw(g2);
            else
                tylerList.remove(i);
        }
        wong.draw(g2);
    }

    public void playSound(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sounds[i]);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loopSound(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sounds[i]);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.loop(clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Max Souls");
        GamePanel myPanel = new GamePanel();
        frame.add(myPanel);
        frame.setVisible(true);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}