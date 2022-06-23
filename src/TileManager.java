// Assignment: ISU
// Name: Max Luo and Dami Peng
// Date: June 22, 2022
// Description: a class for handling tiles

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

	// some dank variables
	private GamePanel gp;
	private Tile[] tile;
	private int mapTileNum[][];
	private ArrayList<Tile> tiles = new ArrayList<>();

	// method: tileManager
	// Purpose: manages some tiles/blocks
	// Param: GamePanel
	// Return: n/a
	public TileManager(GamePanel gp) {
		this.gp = gp;

		// 17 blocks in total
		tile = new Tile[17];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

		getTileImage();

	}

	// method: getTileImage
	// Purpose: reads in block images/ sprites
	// Param: n/a
	// Return: void
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
			// dirt
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
			tile[13] = new Tile();
			tile[13].image = ImageIO.read(getClass().getResourceAsStream("/background/c1.png"));
			tile[14] = new Tile();
			tile[14].image = ImageIO.read(getClass().getResourceAsStream("/background/c2.png"));
			tile[15] = new Tile();
			tile[15].image = ImageIO.read(getClass().getResourceAsStream("/background/c3.png"));
			tile[16] = new Tile();
			tile[16].image = ImageIO.read(getClass().getResourceAsStream("/background/c4.png"));
		} catch (IOException e) {
			System.out.println("IOExceptio " + e);
		}
	}

	// method: loadMap
	// Purpose: loads map from text file
	// Param: n/a
	// Return: void
	public void loadMap() {
		tiles.clear();
		try {
			// uses the map number
			InputStream is = getClass().getResourceAsStream("/map/map0" + gp.mapNum + ".txt");
			BufferedReader br = new BufferedReader((new InputStreamReader(is)));

			int col = 0;
			int row = 0;

			// loads the map using the tiles
			while (row < gp.maxWorldRow) {
				String line = br.readLine();

				// read each row
				while (col < gp.maxWorldCol) {
					// take the number of the maps && assign each textures
					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;

					if (tiles.size() < 3696) {
						// tiles with collision
						if (num == 1 || num == 3 || num == 6 || num == 2 || num == 7 || num == 4 || num == 9
								|| num == 8)
							tiles.add(new Tile(tile[num].image,
									new Rectangle(col * gp.tileSize, row * gp.tileSize, gp.tileSize, gp.tileSize), num,
									true));
						// tiles without collision
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

	// method: draw
	// Purpose: draws the map
	// Param: Graphics2D
	// Return: void
	public void draw(Graphics2D g2) {

		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;

		// creates the map using the map dimensions
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
			int tileNum = mapTileNum[col][row];

			// uses columns of the map and the tile size to find world dimensions
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

	// method: getTiles
	// Purpose: returns tiles
	// Param: n/a
	// Return: ArrayList<Tile>
	public ArrayList<Tile> getTiles() {
		return tiles;
	}

}
