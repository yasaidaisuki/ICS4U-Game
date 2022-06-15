import java.util.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;

public class BufferedImageLoader {
	// image 
	private BufferedImage image;
	
    //Name: BufferedImage
    //Purpose: loads the image and textures of the game! 
    // Param: String 
    //Return: n/a
	public BufferedImage loadImage(String path) {
		// tries to read the image from a selected path
		try {
			image = ImageIO.read(getClass().getResource(path));
			return image;
		} catch (IOException e) {
			System.out.println("Image cannot be loaded");
		}
		return null;
	}

}
