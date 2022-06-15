import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Image;

public class TileManager {

    GamePanel gp;
    Image[] tile;
    int mapTileNum[][];
    public ArrayList<Tile> tiles = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Image[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = ImageIO.read(getClass().getResourceAsStream("/background/earth.png"));
            tile[1] = ImageIO.read(getClass().getResourceAsStream("/background/grass00.png"));
            tile[2] = ImageIO.read(getClass().getResourceAsStream("/background/grass01.png"));
            tile[3] = ImageIO.read(getClass().getResourceAsStream("/background/cloud.png"));
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
                    tiles.add(new Tile(tile[num],
                            new Rectangle(col * gp.tileSize, row * gp.tileSize, gp.tileSize, gp.tileSize), false, num));
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
                screenX = gp.screenX - (gp.worldWidth - gp.max.player.x);
            }

            int bottomOffSet = gp.screenY - screenY;
            if (bottomOffSet > gp.worldHeight - gp.max.player.y) {
                screenY = gp.screenY - (gp.worldHeight - gp.max.player.y);
            }

            if (worldX + gp.tileSize > gp.max.player.x - gp.max.screenX
                    && worldX - gp.tileSize < gp.max.player.x + gp.max.screenX
                    && worldY + gp.tileSize > gp.max.player.y - gp.max.screenY
                    && worldY - gp.tileSize < gp.max.player.y + gp.max.screenY) {
                g2.drawImage(tile[tileNum], screenX, screenY, gp.tileSize, gp.tileSize, null);
            } else if (gp.max.screenX > gp.max.player.x || gp.max.screenX > gp.max.player.y
                    || rightOffSet > gp.worldWidth - gp.max.player.x
                    || bottomOffSet > gp.worldHeight - gp.max.player.y) {
                g2.drawImage(tile[tileNum], screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
            x++;

            if (x == gp.maxWorldCol) {
                x = 0;
                y++;
            }

        }

    }

}
