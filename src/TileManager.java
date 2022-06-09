import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[16][12];

        getTileImage();
        loadMpa();
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

    public void loadMpa() {
        try {
            InputStream is = getClass().getResourceAsStream("/background/map01.txt");
            BufferedReader br = new BufferedReader((new InputStreamReader(is)));

            int col = 0;
            int row = 0;

            while (col < 16 && row < 12) {
                String line = br.readLine();
                while (col < 16) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == 16) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < 16 && row < 12) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == 16) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }

    }

}
