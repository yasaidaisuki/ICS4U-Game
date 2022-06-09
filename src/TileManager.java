import java.io.IOException;

import javax.imageio.ImageIO;

public class TileManager {

    GamePanel gp;
    Tile[] tile;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];

        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/max/max_left.png"));

        } catch (IOException e) {
            System.out.println("IOExceptio " + e);
        }
    }

}
