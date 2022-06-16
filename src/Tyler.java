import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tyler extends Character{
	GamePanel gp;
	KeyHandler keyH;
	
	public void getImg() {
		try {
		left = ImageIO.read(getClass().getResourceAsStream("/max/max_left.png"));
		right = ImageIO.read(getClass().getResourceAsStream("/max/max_right.png"));
		left = ImageIO.read(getClass().getResourceAsStream("/max/max_left.png"));
		right = ImageIO.read(getClass().getResourceAsStream("/max/max_right.png"));
		left_w1 = ImageIO.read(getClass().getResourceAsStream("/max/max_leftwalk1.png"));
		left_w2 = ImageIO.read(getClass().getResourceAsStream("/max/max_leftwalk2.png"));
		left_w3 = ImageIO.read(getClass().getResourceAsStream("/max/max_leftwalk3.png"));
		left_w4 = ImageIO.read(getClass().getResourceAsStream("/max/max_leftwalk4.png"));
		right_w1 = ImageIO.read(getClass().getResourceAsStream("/max/max_rightwalk1.png"));
		right_w2 = ImageIO.read(getClass().getResourceAsStream("/max/max_rightwalk2.png"));
		right_w3 = ImageIO.read(getClass().getResourceAsStream("/max/max_rightwalk3.png"));
		right_w4 = ImageIO.read(getClass().getResourceAsStream("/max/max_rightwalk4.png"));
		left_atk = ImageIO.read(getClass().getResourceAsStream("/max/max_leftatk.png"));
		right_atk = ImageIO.read(getClass().getResourceAsStream("/max/max_rightatk.png"));
		} catch (FileNotFoundException e) {
			System.out.println("Where file");
		} catch (IOException e) {
			System.out.println("Why");
		}
	}
	
	public void setAction() {
		
		
	}
	
}
