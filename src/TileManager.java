import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class TileManager {

	private GamePanel gp;
	private Tile[] tile;
	private int mapTileNum[][];
	private ArrayList<Tile> tiles = new ArrayList<>();

	public TileManager(GamePanel gp) {
		this.gp = gp;

		tile = new Tile[13];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

		getTileImage();
		loadMap();

	}

	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/background/nothing.png"));
			// grass dirt
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/background/grass1.png"));
			// grass rock
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/background/grass2.png"));
			// rock floor
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/background/rock_f.png"));
			// rock wall
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/background/rock_w.png"));
			// dirt wall
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/background/wall.png"));
			// diurt
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/background/dirt_f.png"));
			// brick
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/background/brick.png"));
			// water 1
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/background/water.png"));
			// right dirt
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/background/dark_wall.png"));
			// flowers || 1 - grass || 2 - white flower || 3 - flower bush
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/background/flower1.png"));
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/background/flower2.png"));
			tile[12] = new Tile();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/background/flower3.png"));

		} catch (IOException e) {
			System.out.println("IOExceptio " + e);
		}
	}

	public void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/map/map01.txt");
			BufferedReader br = new BufferedReader((new InputStreamReader(is)));

			int col = 0;
			int row = 0;

			while (row < gp.maxWorldRow) {
				String line = br.readLine();

				// read each row
				while (col < gp.maxWorldCol) {
					// take the number of the maps && assign each textures
					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;

					if (tiles.size() < 3696) {
						if (num == 1 || num == 3 || num == 6 || num == 2 || num == 7 || num == 4 || num == 9 || num == 8)
							tiles.add(new Tile(tile[num].image,
									new Rectangle(col * gp.tileSize, row * gp.tileSize, gp.tileSize, gp.tileSize), num,
									true));
						else
							tiles.add(new Tile(tile[num].image,
									new Rectangle(col * gp.tileSize, row * gp.tileSize, gp.tileSize, gp.tileSize), num,
									false));
					}

					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Where file");
		} catch (IOException e) {
			System.out.println("IOException");
		}
	}

	public void draw(Graphics2D g2) {

		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;

		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
			int tileNum = mapTileNum[col][row];

			int worldX = col * gp.tileSize;
			int worldY = row * gp.tileSize;

			int screenX = worldX - gp.max.player.x + gp.max.getScreenX();
			int screenY = worldY - gp.max.player.y + gp.max.getScreenY();

			if (gp.max.getScreenX() > gp.max.player.x)
				screenX = worldX;

			if (gp.max.getScreenY() > gp.max.player.y)
				screenY = worldY;

			int bottomOffSet = gp.screenY - gp.max.getScreenY();
			if (bottomOffSet > gp.worldHeight - gp.max.player.y) {
				screenY = gp.screenY - (gp.worldHeight - worldY);
			}

			g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize,
					null);

			col++;
			x += gp.tileSize;

			if (col == gp.maxWorldCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}

	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

}
