import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.io.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    int ogTileSize = 16; // 16 pxl tile size
    int scale = 3; // scale multiplier
    int tileSize = ogTileSize * scale; // tile size
    int maxScreenCol = 24; // max screen x
    int maxScreenRow = 18; // max screen y
    int screenX = tileSize * maxScreenCol; // actual screen dimensions
    int screenY = tileSize * maxScreenRow; // actual screen dimensions

    int maxWorldCol = 168;
    int maxWorldRow = 22;
    int worldHeight = maxWorldCol * tileSize;
    int worldWidth = maxWorldRow * tileSize;

    int FPS = 60; // fps

    // Controls class
    TileManager tileM = new TileManager(this); // tile manager object
    KeyHandler keyH = new KeyHandler(); // control handler object
    Thread thread; // thread
    // player
    Max max = new Max(this, keyH);
    Wong tyler = new Wong(this);
    // image background
    Image background;

    // Name: GamePanel
    // Purpose: game constructor
    // Param: n/a
    // Return: n/a
    public GamePanel() {
        // set dimensions
        setPreferredSize(new Dimension(screenX, screenY));
        setVisible(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        // this.setDoubleBuffered(true);

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

        max.move();
        max.keepInBound();
        
        tyler.setAction();

        
    }

    // Name:
    // Purpose:
    // Param:
    // Return:
    public void initialize() {
        // setups before the game starts running

    }

    // Name: paintComponent
    // Purpose: draw the game || characters, background, ect.
    // Param: Graphics
    // Return: void
    public void paintComponent(Graphics g) {
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/background/background1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(background, 0, 0, screenX + 200, screenY, null);
        tileM.draw(g2);
        max.draw(g2);
        tyler.draw(g2);
        
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Not Example");
        GamePanel myPanel = new GamePanel();
        frame.add(myPanel);
        frame.setVisible(true);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
