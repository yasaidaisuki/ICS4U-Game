import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wong extends Character{

	GamePanel gp;
	
	public Wong(GamePanel gp) {
		this.gp = gp;
		
		setDefaultValues();
		getImg();
	}
	
	public void setDefaultValues() {
		speed = 0.1;
		player = new Rectangle((int) (gp.tileSize * 0), 0, gp.tileSize, gp.tileSize * 2);
		maxHp = 5;
		hp = maxHp;
		dmg = 2;
		direction = "left";
		
		
	}
	
	public void getImg() {
		try {
		left = ImageIO.read(getClass().getResourceAsStream("/wong/wong_left.png"));
		right = ImageIO.read(getClass().getResourceAsStream("/wong/wong_right.png"));
		left = ImageIO.read(getClass().getResourceAsStream("/wong/wong_left.png"));
		right = ImageIO.read(getClass().getResourceAsStream("/wong/wong_right.png"));
		left_w1 = ImageIO.read(getClass().getResourceAsStream("/wong/wong_leftwalk1.png"));
		left_w2 = ImageIO.read(getClass().getResourceAsStream("/wong/wong_leftwalk2.png"));
		left_w3 = ImageIO.read(getClass().getResourceAsStream("/wong/wong_leftwalk3.png"));
		left_w4 = ImageIO.read(getClass().getResourceAsStream("/wong/wong_leftwalk4.png"));
		right_w1 = ImageIO.read(getClass().getResourceAsStream("/wong/wong_rightwalk1.png"));
		right_w2 = ImageIO.read(getClass().getResourceAsStream("/wong/wong_rightwalk2.png"));
		right_w3 = ImageIO.read(getClass().getResourceAsStream("/wong/wong_rightwalk3.png"));
		right_w4 = ImageIO.read(getClass().getResourceAsStream("/wong/wong_rightwalk4.png"));
		left_atk = ImageIO.read(getClass().getResourceAsStream("/wong/wong_leftatk.png"));
		right_atk = ImageIO.read(getClass().getResourceAsStream("/wong/wong_rightatk.png"));
		} catch (FileNotFoundException e) {
			System.out.println("Where file");
		} catch (IOException e) {
			System.out.println("Why");
		}
	}
	
	public void setAction() {
		
		
	}
	
}
