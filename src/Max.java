
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.io.*;
import java.awt.image.BufferedImage;

public class Max extends Character {

	GamePanel gp;
	KeyHandler keyH;
	double jumpSpeed;
	double gravity;
	boolean aFloat;

	public Max(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
		getMaxImg();
	}

	public void setDefaultValues() {
		x = 0;
		y = gp.screenY + gp.ogTileSize;
		speed = 5;
		jumpSpeed = 20;
		gravity = 0.8;
		hp = 4;
		dmg = 1;
		direction = "right";
	}

	public void getMaxImg() {
		try {

			left = ImageIO.read(getClass().getResourceAsStream("/max/max_left.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/max/max_right.png"));
			left_w1 = ImageIO.read(getClass().getResourceAsStream("/max/max_leftwalk1.png"));
			left_w2 = ImageIO.read(getClass().getResourceAsStream("/max/max_leftwalk2.png"));
			right_w1 = ImageIO.read(getClass().getResourceAsStream("/max/max_rightwalk1.png"));
			right_w2 = ImageIO.read(getClass().getResourceAsStream("/max/max_rightwalk2.png"));
			left_up = ImageIO.read(getClass().getResourceAsStream("/max/max_leftjump.png"));
			right_up = ImageIO.read(getClass().getResourceAsStream("/max/max_rightjump.png"));
			left_atk = ImageIO.read(getClass().getResourceAsStream("/max/max_leftatk.png"));
			right_atk = ImageIO.read(getClass().getResourceAsStream("/max/max_rightatk.png"));

		} catch (FileNotFoundException e) {
			System.out.println("Where file");
		} catch (IOException e) {
			System.out.println("Why");
		}
	}

	public void update() {
		if (keyH.left == true) {
			direction = "left";
			if (x == 0) {
				speed = 0;
			} else {
				speed = 5;
				x -= speed;
			}
		} else if (keyH.right == true) {
			direction = "right";
			if (x > gp.screenX - gp.tileSize) {
				speed = 0;
			} else {
				speed = 5;
				x += speed;
			}
		}
		if (aFloat) {
			y += gravity;
		} else {
			if (keyH.jump == true) {
				if (direction.equals("right"))
					direction = "right_up";
				else if (direction.equals("left"))
					direction = "left_up";
				if (y < 0) {
					y = 0;
				} else {
					y -= jumpSpeed;
				}
				aFloat = true;
			}

		}
		if (y > gp.screenY - gp.tileSize) {
			y = gp.screenY - gp.tileSize;
		}
		spriteCounter++;
		if (spriteCounter > 20) {
			if (spriteNum == 1) {
				spriteNum =2;
			}
			else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter =0;
		}
		
	}

	public void draw(Graphics2D g2) {
		// this is the test case
//		g2.setColor(Color.RED);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);

		BufferedImage image = null;

		switch (direction) {
		case "left":
			if (spriteNum == 1)
				image = left_w1;
			if (spriteNum == 2)
				image = left_w2;
			break;
		case "right":
			if (spriteNum == 1)
				image = right_w1;
			if (spriteNum == 2)
				image = right_w2;
			break;
		case "right_up":
			image = right_up;
			break;
		case "left_up":
			image = left_up;
			break;
		}
		if (image == null) {
			System.out.println("null");
		}
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

	}

}

// spsss this is a hidden easter egg. i love you ding kai peng
// pspsppspss second easter egg || max pls do somthething :) 