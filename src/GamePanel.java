import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

    // Screen settings

    int ogTileSize = 16;
    int scale = 3;

    int tileSize = ogTileSize * scale;
    int maxScrnX = 50;
    int maxScrnY = 50;
    int screenX = ogTileSize * maxScrnX;
    int screenY = ogTileSize * maxScrnX;

    int FPS = 60;

    // Controls class
    KeyHandler keyH = new KeyHandler();
    Thread thread;
    // player
    Max max = new Max(this, keyH);

    public GamePanel() {
        setPreferredSize(new Dimension(screenX, screenY));
        setVisible(true);
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
        frame.add(myPanel);
        frame.setVisible(true);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
