import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class Tyler extends Character {

	// initalization
	GamePanel gp;
	private int screenX;
	private int screenY;
	boolean airborne;
	int actionLockCounter = 0;

	// Name: Tyler
	// Purpose: to make a tyler
	// Param: GamePanel
	// Return: n/a
	public Tyler(GamePanel gp) {
		this.gp = gp;

		setDefaultValues();
		getImg();
	}

	// Name: setDefaultValues
	// Purpose: easy access to tyler attributes
	// Param: n/a
	// Return: void
	public void setDefaultValues() {
		xVel = 0;
		speed = 2.5;
		player = new Rectangle((int) (gp.tileSize * 20), (int) (gp.tileSize * 15), gp.tileSize * 2, gp.tileSize * 2);
		maxHp = 5;
		hp = maxHp;
		dmg = 2;
		direction = "idle_l";

	}

	// Name: getImg
	// Purpose: initialize the sprites using buffered images
	// Param: n/a
	// Return: void
	public void getImg() {
		// gets all the image
		try {
			left = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_left.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_right.png"));
			left = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_left.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_right.png"));
			left_w1 = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_leftwalk1.png"));
			left_w2 = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_leftwalk2.png"));
			left_w3 = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_leftwalk3.png"));
			left_w4 = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_leftwalk4.png"));
			right_w1 = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_rightwalk1.png"));
			right_w2 = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_rightwalk2.png"));
			right_w3 = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_rightwalk3.png"));
			right_w4 = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_rightwalk4.png"));
			left_atk = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_leftatk.png"));
			right_atk = ImageIO.read(getClass().getResourceAsStream("/tyler/tyler_rightatk.png"));
		} catch (FileNotFoundException e) {
			System.out.println("Where file");
		} catch (IOException e) {
			System.out.println("Why");
		}
	}

	// Name: setAction
	// Purpose: randomizes tyler's actions
	// Param: n/a
	// Return: void
	public void setAction() {

		actionLockCounter++;
		// random left or right
		Random random = new Random();
		int i = random.nextInt(50) + 1;

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
				direction = "right";
				// else then the tyler moves left
			} else {
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

	// Name: move
	// Purpose: check for tyler movement
	// Param: n/a
	// Return: void
	public void move() {
		if (direction.equals("left")) {
			xVel = -speed;
		} else if (direction.equals("right")) {
			xVel = speed + 1;
		} else {
			xVel = 0;
		}
		player.x += xVel;
	}

	// Name: draw
	// Purpose: draw the character sprites
	// Param: Graphics2D
	// Return: void
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

		// int x = this.player.x - gp.max.getScreenX();
		// int y = this.player.y - gp.max.getScreenY();

		// if ((player.x >= (gp.max.player.x - gp.screenX) && (player.x <=
		// gp.max.player.x + gp.screenX))) {
		// g2.drawImage(image, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
		// }
		// get position relative to player camera

		// determines the position of the enemy
		int xPosition = player.x - gp.max.player.x + gp.max.getScreenX();
		int yPosition = player.y - gp.max.player.y + gp.max.getScreenY();

		if (gp.max.getScreenX() > gp.max.player.x)
			xPosition = player.x;
		if (gp.max.getScreenY() > gp.max.player.y)
			yPosition = player.y;

		// draw enemy
		g2.drawImage(image, xPosition, yPosition, gp.tileSize * 2, gp.tileSize * 2, null);

	}

	// Name: checkCollision
	// Purpose: checks the collision around tyler with blocks
	// Param: Tile object
	// Return: boolean value of whether the block you collide with is a block that
	// is actually collidable
	public boolean checkCollision(Tile t) {
		// initalization
		Rectangle block = t.getHitbox();
		if (player.intersects(block)) {
			// attributes
			double left1 = player.getX();
			double right1 = player.getX() + player.getWidth();
			double top1 = player.getY();
			double bottom1 = player.getY() + player.getHeight();
			double left2 = block.getX();
			double right2 = block.getX() + block.getWidth();
			double top2 = block.getY();
			double bottom2 = block.getY() + block.getHeight();

			// check collision from left side of the block
			if (right1 > left2 && left1 < left2 && right1 - left2 < bottom1 - top2 && right1 - left2 < bottom2 - top1) {
				if (t.isCollision()) {
					player.x = block.x - player.width;
					return true;
				}
			}
			// check collision from right side of the block
			else if (left1 < right2 && right1 > right2 && right2 - left1 < bottom1 - top2
					&& right2 - left1 < bottom2 - top1) {
				if (t.isCollision()) {
					player.x = block.x + block.width;
					return true;
				}
			}
		}
		if ((player.y + gp.tileSize * 2) >= gp.tileSize * 17) {
			airborne = false;
		}
		return false;

	}

	public void checkPlayerCollision(Max max, KeyHandler k) {
		Rectangle m = max.player;
		// attributes
		double left1 = player.getX();
		double right1 = player.getX() + player.getWidth();
		double top1 = player.getY();
		double bottom1 = player.getY() + player.getHeight();
		double left2 = m.getX() - gp.tileSize * 1.5;
		double right2 = m.getX() + m.getWidth() + gp.tileSize * 1.5;
		double top2 = m.getY();
		double bottom2 = m.getY() + m.getHeight();

		// check collision from left side of the block
		if (right1 > left2 && left1 < left2 && right1 - left2 < bottom1 - top2 && right1 - left2 < bottom2 - top1) {
			if (k.attack) {
				System.out.println("yes");
			}
		}
		// check collision from right side of the block
		else if (left1 < right2 && right1 > right2 && right2 - left1 < bottom1 - top2
				&& right2 - left1 < bottom2 - top1) {
			if (k.attack) {
				System.out.println("yes");
			}
		}
	}

	// Name: keepInBound
	// Purpose: keep player in bound
	// Param: n/a
	// Return: void
	public void keepInBound() {
		// check x bound
		if (player.x < 0) {
			player.x = 0;
		}

		if (player.x > 8064 - 16 * 3 * 13) {
			player.x = 8064 - 16 * 3 * 13;
		}

		// if (player.y < 0) {
		// player.y = 0;
		// yVel = 0;
		// } else if (player.y > gp.screenY - player.height) {
		// player.y = gp.screenY - player.height;
		// airborne = false;
		// yVel = 0;
		// }
	}

}
