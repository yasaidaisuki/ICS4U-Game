import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Heart {

	GamePanel gp;
	public BufferedImage image;
	public String name = "heart";
	public boolean collision = false;
	public int worldX, worldY;

	public Heart(GamePanel gp) {
		this.gp = gp;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/heart.png"));
			// uTool.scaleImage(image, gp.ogTileSize, gp.TileSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {

	}

}
