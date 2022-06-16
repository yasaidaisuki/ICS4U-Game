import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class TileManager {

	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	ArrayList<Tile>tileList = new ArrayList<>();

	public TileManager(GamePanel gp) {
		this.gp = gp;

		tile = new Tile[13];
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

		getTileImage();
		loadMap();

	}

	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/background/nothing.png"));
			// grass dirt
			tile[1] = new Tile();
			tile[1].collision = true;
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/background/grass1.png"));
			// grass rock
			tile[2] = new Tile();
			tile[2].collision = true;
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/background/grass2.png"));
			// rock floor
			tile[3] = new Tile();
			tile[3].collision = true;
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/background/rock_f.png"));
			// rock wall
			tile[4] = new Tile();
			tile[4].collision = true;
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/background/rock_w.png"));
			// dirt wall
			tile[5] = new Tile();
			tile[5].collision = true;
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/background/dirt_w.png"));
			// dirt floor
			tile[6] = new Tile();
			tile[6].collision = true;
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/background/dirt_f.png"));
			// brick
			tile[7] = new Tile();
			tile[7].collision = true;
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/background/brick.png"));
			// water 1 || water 2
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/background/water1.png"));
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/background/water2.png"));
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

			while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
				String line = br.readLine();

				// read each row
				while (col < gp.maxScreenCol) {
					// take the number of the maps && assign each textures
					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					col++;
				}
				if (col == gp.maxScreenCol) {
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

		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
			int tileNum = mapTileNum[col][row];

			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;

			if (col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}

	}

}
