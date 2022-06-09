import java.util.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;

public class BufferedImageLoader {

	private BufferedImage image;

	public BufferedImage loadImage(String path) {
		try {
			image = ImageIO.read(getClass().getResource(path));
			return image;
		} catch (IOException e) {
			System.out.println("Image cannot be loaded");
		}
		return null;
	}

}
