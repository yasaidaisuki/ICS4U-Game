import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Heart {
	
	public BufferedImage image;
	public String name = "heart";
	public boolean collision = false;
	public int worldX, worldY;

	public Heart() {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/heart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
