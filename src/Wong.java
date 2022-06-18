import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Wong extends Character {

	GamePanel gp;
	private int screenX;
	private int screenY;
	int actionLockCounter = 0;

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

		actionLockCounter++;
		// random left or right
		Random random = new Random();
		int i = random.nextInt(100) + 1;

		if (actionLockCounter == 20) {
			// if random number is divisible by 5, then the tyler is idle
			if (i % 5 == 0) {
				if (direction.equals("left")) {
					direction = "idle_l";
				} else if (direction.equals("right")) {
					direction = "idle_r";
				}
				// if random number is even then the tyler moves right
			} else if (i % 2 != 0) {
				player.x += speed;
				direction = "right";
				// else then the tyler moves left
			} else {
				player.x -= speed;
				direction = "left";
			}

			actionLockCounter = 0;
		}

		// animation
		spriteCounter++;
		if (spriteCounter > 10) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 3;
			} else if (spriteNum == 3) {
				spriteNum = 4;
			} else if (spriteNum == 4) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		// checks for left
		if (direction.equals("left")) {
			if (spriteNum == 1)
				image = left_w1;
			if (spriteNum == 2)
				image = left_w4;
			if (spriteNum == 3)
				image = left_w2;
			if (spriteNum == 4)
				image = left_w3;

		}
		// checks for right
		else if (direction.equals("right")) {
			if (spriteNum == 1)
				image = right_w1;
			if (spriteNum == 2)
				image = right_w4;
			if (spriteNum == 3)
				image = right_w2;
			if (spriteNum == 4)
				image = right_w3;
		}

		// checks for idle
		else if (direction.equals("idle_l")) {
			image = left;
		} else if (direction.equals("idle_r")) {
			image = right;
		}

		// debug
		if (image == null) {
			System.out.println("null");
		}

		int x = screenX;
		int y = screenY;
		if (screenX > player.x)
			x = player.x;
		int bottomOffSet = gp.screenY - screenY;
		if (bottomOffSet > gp.worldHeight - player.y) {
			y = gp.screenY - (gp.worldHeight - player.y);
		}

		// System.out.println("x: " + player.x + "y: " + player.y);

		g2.drawImage(image, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

	}

}
