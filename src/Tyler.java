import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class Tyler extends Character {
	GamePanel gp;
	private int screenX;
	private int screenY;
	boolean airborne;
	int actionLockCounter = 0;

	public Tyler(GamePanel gp) {
		this.gp = gp;

		setDefaultValues();
		getImg();
	}

	public void setDefaultValues() {
		xVel = 0;
		speed = 1;
		player = new Rectangle((int) (gp.tileSize * 10), (int) (gp.tileSize * 15), gp.tileSize * 2, gp.tileSize * 2);
		maxHp = 5;
		hp = maxHp;
		dmg = 2;
		direction = "left";

	}

	public void getImg() {
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

	public void move() {
		if (direction.equals("left")) {
			xVel = -speed;
		} else if (direction.equals("right")) {
			xVel = speed;
		} else {
			xVel = 0;
		}
		player.x += xVel;
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

		int x = player.x - gp.max.player.x + gp.max.getScreenX();
		int y = player.y - gp.max.player.y + gp.max.getScreenY();

		if ((player.x >= (gp.max.player.x - gp.screenX) && (player.x <= gp.max.player.x + gp.screenX))) {
			System.out.println("yes");
			g2.drawImage(image, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
		} else
			System.out.println("no");
		// draw enemy

	}

	public boolean checkCollision(Tile t) {
		Rectangle block = t.getHitbox();
		if (player.intersects(block)) {
			double left1 = player.getX();
			double right1 = player.getX() + player.getWidth();
			double top1 = player.getY();
			double bottom1 = player.getY() + player.getHeight();
			double left2 = block.getX();
			double right2 = block.getX() + block.getWidth();
			double top2 = block.getY();
			double bottom2 = block.getY() + block.getHeight();
			if (right1 > left2 &&
					left1 < left2 &&
					right1 - left2 < bottom1 - top2 &&
					right1 - left2 < bottom2 - top1) {
				// rect collides from left side of the wall
				if (t.isCollision()) {
					player.x = block.x - player.width;
					return true;
				}
			} else if (left1 < right2 &&
					right1 > right2 &&
					right2 - left1 < bottom1 - top2 &&
					right2 - left1 < bottom2 - top1) {
				// rect collides from right side of the wall
				if (t.isCollision()) {
					player.x = block.x + block.width;
					return true;
				}
			} else if (bottom1 > top2 && top1 < top2) {
				// rect collides from top side of the wall
				if (t.isCollision()) {
					airborne = false;
					yVel = 0;
					player.y = block.y - player.height;
					return true;
				}
			} else if (top1 < bottom2 && bottom1 > bottom2) {
				// rect collides from bottom side of the wall
				if (t.isCollision()) {
					player.y = block.y + block.height;
					airborne = true;
				} else
					airborne = true;
			}
		}
		if ((player.y + gp.tileSize * 2) >= gp.tileSize * 17) {
			airborne = false;
		}
		return false;

	}

	public void keepInBound() {
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
