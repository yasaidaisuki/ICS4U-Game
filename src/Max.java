
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.io.*;
import java.awt.image.BufferedImage;

public class Max extends Character {

	GamePanel gp;
	KeyHandler keyH;
	double jumpSpeed;
	double gravity;
	boolean airborne;

	public Max(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
		getMaxImg();
	}

	public void setDefaultValues() {
		x = 0;
		y = gp.screenY + gp.ogTileSize;
		xVel = 0;
		yVel = 0;
		speed = 5;
		jumpSpeed = 30;
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
			left_w3 = ImageIO.read(getClass().getResourceAsStream("/max/max_leftwalk3.png"));
			left_w4 = ImageIO.read(getClass().getResourceAsStream("/max/max_leftwalk4.png"));
			right_w1 = ImageIO.read(getClass().getResourceAsStream("/max/max_rightwalk1.png"));
			right_w2 = ImageIO.read(getClass().getResourceAsStream("/max/max_rightwalk2.png"));
			right_w3 = ImageIO.read(getClass().getResourceAsStream("/max/max_rightwalk3.png"));
			right_w4 = ImageIO.read(getClass().getResourceAsStream("/max/max_rightwalk4.png"));
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

	public void move() {
		if (keyH.left) {
			xVel = -speed;
			direction = "left";
		} else if (keyH.right) {
			xVel = speed;
			direction = "right";
		} else
			xVel = 0;

		if (airborne) {
			yVel -= gravity;
		} else {
			if (keyH.jump) {
				airborne = true;
				yVel = jumpSpeed;
			}
		}

		x += xVel;
		y -= yVel;

		spriteCounter++;
		if (spriteCounter > 10) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 3;
			} else if (spriteNum == 3) {
				spriteNum = 4;
			} else if (spriteNum == 4) {
				spriteNum =1;
			}
			spriteCounter = 0;
		}
	}

	public void draw(Graphics2D g2) {
		// this is the test case
		// g2.setColor(Color.RED);
		// g2.fillRect(x, y, gp.tileSize, gp.tileSize);

		BufferedImage image = null;

		switch (direction) {
			case "left":
				if (spriteNum == 1)
					image = left_w1;
				if (spriteNum == 2)
					image = left_w4;
				if (spriteNum == 3)
					image = left_w2;
				if (spriteNum == 4)
					image = left_w3;
			
				break;
			case "right":
				if (spriteNum == 1)
					image = right_w1;
				if (spriteNum == 2)
					image = right_w4;
				if (spriteNum == 3)
					image = right_w2;
				if (spriteNum == 4)
					image = right_w3;
				
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

	public void keepInBound() {
		if (x < 0) {
			x = 0;
		} else if (x > gp.screenX + gp.tileSize) {
			x = gp.screenX + gp.tileSize;
		}

		if (y < 0) {
			y = 0;
			yVel = 0;
		} else if (y > (gp.screenY - gp.tileSize - 180)) {
			y = gp.screenY - gp.tileSize - 180;
			System.out.println(gp.screenY);
			System.out.println(y);
			airborne = false;
			yVel = 0;
		}
	}

}

// spsss this is a hidden easter egg. i love you ding kai peng
// pspsppspss second easter egg || max pls do somthething :)