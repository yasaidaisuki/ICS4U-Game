import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];

        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/background/tile1.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/background/tile2.png"));

        } catch (IOException e) {
            System.out.println("IOExceptio " + e);
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
    }

}
