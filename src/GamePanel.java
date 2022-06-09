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

    int ogTileSize = 16;
    int scale = 6;
    int tileSize = ogTileSize * scale;
    int maxScrnX = 60;
    int maxScrnY = 60;
    int screenX = ogTileSize * maxScrnX;
    int screenY = ogTileSize * maxScrnX;

    int FPS = 60;

    // Controls class
    KeyHandler keyH = new KeyHandler();
    Thread thread;
    // player
    Max max = new Max(this, keyH);
    Image background1;

    public GamePanel() {
        setPreferredSize(new Dimension(screenX, screenY));
        setVisible(true);
        background1 = new ImageIcon("background1.png").getImage();
        this.addKeyListener(keyH);
        this.setFocusable(true);

        thread = new Thread(this);
        thread.start();
    }

    @Override
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

    public void update() {

        max.move();
        max.keepInBound();

    }

    public void initialize() {
        // setups before the game starts running

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(background1, 0, 0, screenX + 200, screenY, null);
        max.draw(g2);
        // for (int i = 0; i < walls.length; i++)
        // g2.fill(walls[i]);
        // g2.fill(rect);
    }

    void checkCollision(Rectangle wall) {
        // check if rect touches wall

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Not Example");
        GamePanel myPanel = new GamePanel();
        Toolkit toolKit = frame.getToolkit();
        Dimension size = toolKit.getScreenSize();
        frame.setSize(new Dimension(960, 960));
        frame.setLocation(size.width / 2 - frame.getWidth() / 2, size.height / 2 -
                (frame.getHeight() / 2) + 50);
        System.out.println(size.getWidth() + " " + size.getHeight());
        frame.add(myPanel);
        frame.setVisible(true);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
