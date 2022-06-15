import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/background/earth.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/background/grass00.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/background/grass01.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/background/cloud.png"));

        } catch (IOException e) {
            System.out.println("IOExceptio " + e);
        }
    }

    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/background/map01.txt");
            BufferedReader br = new BufferedReader((new InputStreamReader(is)));

            int col = 0;
            int row = 0;

            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                StringTokenizer nums = new StringTokenizer(line);
                while (col < gp.maxWorldCol) {
                    int num = Integer.parseInt(nums.nextToken());
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
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

        int x = 0;
        int y = 0;

        while (x < gp.maxWorldCol && y < gp.maxWorldRow) {
            int tileNum = mapTileNum[x][y];

            int worldX = x * gp.tileSize;
            int worldY = y * gp.tileSize;

            int screenX = worldX - gp.max.player.x + gp.max.screenX;
            int screenY = worldY - gp.max.player.y + gp.max.screenY;

            if (gp.max.screenX > gp.max.player.x) {
                screenX = worldX;
            }
            if (gp.max.screenY > gp.max.player.y) {
                screenY = worldY;
            }
            int rightOffSet = gp.screenX - screenX;
            if (rightOffSet > gp.screenX - gp.max.player.x) {
                x = gp.screenX - gp.worldWidth + gp.max.player.x;
            }

            int bottomOffSet = gp.screenY - screenY;
            if (bottomOffSet > gp.worldHeight - gp.max.player.y) {
                y = gp.screenY - gp.worldHeight + gp.max.player.y;
            }

            if (worldX + gp.tileSize > gp.max.player.x - gp.max.screenX
                    && worldX - gp.tileSize < gp.max.player.x + gp.max.screenX
                    && worldY + gp.tileSize > gp.max.player.y - gp.max.screenY
                    && worldY - gp.tileSize < gp.max.player.y + gp.max.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            } else if (gp.max.screenX > gp.max.player.x || gp.max.screenX > gp.max.player.y
                    || rightOffSet > gp.worldWidth - gp.max.player.x
                    || bottomOffSet > gp.worldHeight - gp.max.player.y) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
            x++;

            if (x == gp.maxWorldCol) {
                x = 0;
                y++;
            }

        }

    }

}
