import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class Wong extends Character {
	GamePanel gp;
	private int screenX;
	private int screenY;
	boolean airborne;
	int actionLockCounter = 0;
	Rectangle wong;

	// projectile
	int proj_Speed;

	// if projectile exists in the world
	// how long the projectile exists
	int projHp;
	int projMaxHP;
	Rectangle proj;
	ArrayList<Rectangle> projList = new ArrayList<>();
	// projectile direction
	String projDirection = "left";

	int bullet_Speed = 0;

	BufferedImage projectile;

	public Wong(GamePanel gp) {
		this.gp = gp;

		setDefaultValues();
		getImg();
	}

	public void setDefaultValues() {
		xVel = 0;
		speed = 2.5;
		wong = new Rectangle((int) (gp.tileSize * 5), (int) (gp.tileSize * 10), gp.tileSize * 2, gp.tileSize * 2);
		maxHp = 5;
		hp = maxHp;
		direction = "idle_l";

		// for projectile
		proj_Speed = 5;
		dmg = 2;
		projMaxHP = 80;
		projHp = projMaxHP;
	}

	public void getImg() {
		try {
			projectile = ImageIO.read(getClass().getResourceAsStream("/wong/mark.png"));
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

		if (actionLockCounter == 20) {

			// random number generator
			Random random = new Random();
			int i = random.nextInt(50) + 1;

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
			} else if (i % 4 == 0) {
				if (projList.size() < 5) {
					proj = new Rectangle(wong.x, wong.y, gp.tileSize, gp.tileSize);
					projList.add(proj);
				}
			} else if (i % 17 == 0) {
				if (projList.size() < 2) {
					proj = new Rectangle(wong.x + gp.tileSize, wong.y, gp.tileSize, gp.tileSize);
					projList.add(proj);
					proj = new Rectangle(wong.x, wong.y, gp.tileSize, gp.tileSize);
					projList.add(proj);
					proj = new Rectangle(wong.x - gp.tileSize, wong.y, gp.tileSize, gp.tileSize);
					projList.add(proj);
				}
			} else if (i % 19 == 0) {
				if (projList.size() < 2) {
					proj = new Rectangle(wong.x, wong.y + gp.tileSize, gp.tileSize, gp.tileSize);
					projList.add(proj);
					proj = new Rectangle(wong.x, wong.y, gp.tileSize, gp.tileSize);
					projList.add(proj);
					proj = new Rectangle(wong.x, wong.y - gp.tileSize, gp.tileSize, gp.tileSize);
					projList.add(proj);
				}
			} else {
				direction = "left";
			}

			actionLockCounter = 0;
		}

		int i = new Random().nextInt(50) + 1;
		if (i > 49) {
			if (direction.equals("left") || direction.equals("idle_l")) {
				direction = "left_atk";
				projDirection = "left";
			} else if (direction.equals("right") || direction.equals("idle_r")) {
				direction = "right_atk";
				projDirection = "right";
			}
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

		// projectile
		for (int i = 0; i < projList.size(); i++) {
			if (projDirection.equals("left")) {
				projList.get(i).x -= proj_Speed;
			} else if (projDirection.equals("right")) {
				projList.get(i).x += proj_Speed;
			}
		}

		if (direction.equals("left")) {
			xVel = -speed;
		} else if (direction.equals("right")) {
			xVel = speed + 1;
		} else {
			xVel = 0;
		}
		wong.x += xVel;

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
		} else if (direction.equals("left_atk")) {
			image = left_atk;
		} else if (direction.equals("right_atk")) {
			image = right_atk;
		}

		// debug
		if (image == null) {
			System.out.println("null");
		}

		int xPosition = wong.x - gp.max.player.x + gp.max.getScreenX();
		int yPosition = wong.y - gp.max.player.y + gp.max.getScreenY();

		if (gp.max.getScreenX() > gp.max.player.x)
			xPosition = wong.x;
		if (gp.max.getScreenY() > gp.max.player.y)
			yPosition = wong.y;

		// draw enemy
		g2.drawImage(image, xPosition, yPosition, gp.tileSize * 2, gp.tileSize * 2, null);

		for (int i = 0; i < projList.size(); i++) {
			int projX = projList.get(i).x - gp.max.player.x + gp.max.getScreenX();
			int projY = projList.get(i).y - gp.max.player.y + gp.max.getScreenY();

			if (gp.max.getScreenX() > gp.max.player.x)
				projX = proj.x;
			if (gp.max.getScreenY() > gp.max.player.y)
				projY = proj.y;

			// draw projectile
			// if (proj_alive) {
			g2.drawImage(projectile, projX, projY, gp.tileSize, gp.tileSize, null);
		}
	}

	public boolean checkCollision(Tile t) {
		Rectangle block = t.getHitbox();
		if (wong.intersects(block)) {
			double left1 = wong.getX();
			double right1 = wong.getX() + wong.getWidth();
			double top1 = wong.getY();
			double bottom1 = wong.getY() + wong.getHeight();
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
					wong.x = block.x - wong.width;
					return true;
				}
			} else if (left1 < right2 &&
					right1 > right2 &&
					right2 - left1 < bottom1 - top2 &&
					right2 - left1 < bottom2 - top1) {
				// rect collides from right side of the wall
				if (t.isCollision()) {
					wong.x = block.x + block.width;
					return true;
				}
				// } else if (bottom1 > top2 && top1 < top2) {
				// // rect collides from top side of the wall
				// if (t.isCollision()) {
				// airborne = false;
				// yVel = 0;
				// wong.y = block.y - wong.height;
				// return true;
				// }
				// } else if (top1 < bottom2 && bottom1 > bottom2) {
				// // rect collides from bottom side of the wall
				// if (t.isCollision()) {
				// wong.y = block.y + block.height;
				// airborne = true;
				// } else
				// airborne = true;
			}
		}
		if ((wong.y + gp.tileSize * 2) >= gp.tileSize * 17) {
			airborne = false;
		}
		return false;

	}

	public void keepInBound() {
		if (wong.x < 0) {
			wong.x = 0;
		}

		if (wong.x > 8064 - 16 * 3 * 13) {
			wong.x = 8064 - 16 * 3 * 13;
		}

		// if (wong.y < 0) {
		// wong.y = 0;
		// yVel = 0;
		// } else if (wong.y > gp.screenY - wong.height) {
		// wong.y = gp.screenY - wong.height;
		// airborne = false;
		// yVel = 0;
		// }
	}

	// projectile collision
	public void checkProjCollision(Tile t, int i, Max m) {
		Rectangle block = t.getHitbox();
		if (proj.intersects(block) || proj.intersects(m.player)) {
			projList.remove(i);
		}
	}

	public void keepInBoundProj() {
		for (int i = 0; i < projList.size(); i++) {
			if (proj.x < 0) {
				proj.x = 0;
			}

			if (proj.x > 8064 - 16 * 3 * 13) {
				proj.x = 8064 - 16 * 3 * 13;
			}
		}
	}

}
