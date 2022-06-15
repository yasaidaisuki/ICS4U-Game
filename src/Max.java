
import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Max extends Character {

	GamePanel gp;
	KeyHandler keyH;
	double jumpSpeed;
	double gravity;
	boolean airborne;
	boolean isHit;
	boolean jump = false;
	int screenX;
	int screenY;
	int maxVel = 7;

	BufferedImage left_up, right_up, heart, empty_heart;

	public Max(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
		getMaxImg();
	}

	public void setDefaultValues() {
		xVel = 0;
		yVel = 0;
		speed = 1;
		jumpSpeed = 30;
		gravity = 0.8;
		player = new Rectangle((int) (gp.tileSize * 0), 0, 48, 48);
		maxHp = 4;
		hp = 4;
		dmg = 1;
		direction = "right";
		screenX = gp.screenX / 2 - (gp.tileSize / 2);
		screenY = gp.screenY / 2 - (gp.tileSize / 2);
	}

	public void getMaxImg() {
		try {
			// hp hearts
			heart = ImageIO.read(getClass().getResourceAsStream("/Heart/heart.png"));
			empty_heart = ImageIO.read(getClass().getResourceAsStream("/Heart/heart_empty.png"));
			// sprites of character
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
			xVel -= speed;
			direction = "left";
		} else if (keyH.right) {
			xVel += speed;
			direction = "right";
		}
		// if not moving left or right
		else {
			xVel = 0;
		}

		if (airborne) {
			yVel -= gravity;
		} else {
			yVel = 0;
			if (keyH.jump) {
				// sides of jump
				if (direction.equals("left")) {
					direction = "left_up";
				} else if (direction.equals("right")) {
					direction = "right_up";
				}
				airborne = true;
				yVel = jumpSpeed;
			} else {
				jump = false;
			}
		}

		player.x += xVel;
		player.y -= yVel;

		spriteCounter++;

		// idle frame || left & right
		if (xVel == 0) {
			if (direction.equals("left") || direction.equals("left_up")) {
				direction = "idle_l";
			} else if (direction.equals("right") || direction.equals("right_up")) {
				direction = "idle_r";
			}
		}

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
		// this is the test case
		// g2.setColor(Color.RED);
		// g2.fillRect(x, y, gp.tileSize, gp.tileSize);

		int hp_X = gp.tileSize / 2;
		int hp_Y = gp.tileSize / 2;

		int i = 0;

		// draw the current hp first, then fill in the missing
		// current hp
		while (i < gp.max.hp) {
			g2.drawImage(heart, hp_X, hp_Y, gp.tileSize, gp.tileSize, null);
			i++;
			// change heart position for next draw.
			hp_X += gp.tileSize;
		}

		// reset counter for next use
		i = 0;

		// missing hp
		while (i < gp.max.maxHp - gp.max.hp) {
			g2.drawImage(empty_heart, hp_X, hp_Y, gp.tileSize, gp.tileSize, null);
			i++;
			hp_X += gp.tileSize;
		}

		BufferedImage image = null;

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
		if (direction.equals("right_up")) {
			if (spriteNum < 3) {
				image = right_up;
			} else {
				image = right;
			}
		}
		if (direction.equals("left_up")) {
			if (spriteNum < 3) {
				image = left_up;
			} else {
				image = left;
			}
		}

		if (direction.equals("idle_l")) {
			image = left;
		}
		if (direction.equals("idle_r")) {
			image = right;
		}
		if (image == null) {
			System.out.println("null");
		}

		int x = screenX;
		int y = screenY;
		if (screenX > player.x) {
			x = player.x;
		}
		if (screenY > player.y) {
			y = player.y;
		}
		int rightOffSet = gp.screenX - screenX;
		if (rightOffSet > gp.screenX - player.x) {
			x = gp.screenX - gp.worldWidth + player.x;
		}

		int bottomOffSet = gp.screenY - screenY;
		if (bottomOffSet > gp.worldHeight - player.y) {
			y = gp.screenY - gp.worldHeight + player.y;
		}

		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

	}

	public void keepInBound() {
		if (player.x < 0) {
			player.x = 0;
		} else if (player.x > gp.screenX - player.width) {
			player.x = gp.screenX - player.width;
		}

		if (player.y < 0) {
			player.y = 0;
			yVel = 0;
		} else if (player.y > gp.screenY - player.height) {
			player.y = gp.screenY - player.height;
			airborne = false;
			yVel = 0;
		}
	}

}

// spsss this is a hidden easter egg. i love you ding kai peng
// pspsppspss second easter egg || max pls do somthething :)