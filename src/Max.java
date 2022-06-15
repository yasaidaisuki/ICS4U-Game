
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

		if (airborne) {
			yVel -= gravity;
		} else {
			yVel = 0;
			if (keyH.jump) {
				airborne = true;
				yVel = jumpSpeed;
			} else {
				jump = false;
			}
		}

		player.x += xVel;
		player.y -= yVel;

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
		// this is the test case
		// g2.setColor(Color.RED);
		// g2.fillRect(x, y, gp.tileSize, gp.tileSize);

		int hp_X =  gp.tileSize/2;
		int hp_Y =  gp.tileSize/2;
		
		int i = 0;
		
		// draw the current hp first, then fill in the missing
		// current hp
		while (i < gp.max.hp) {
			g2.drawImage(heart, hp_X, hp_Y, gp.tileSize, gp.tileSize, null);
			i++;
			// change heart position for next draw.
			hp_X +=gp.tileSize;
		}
		
		// reset counter for next use
		i =0;
		
		// missing hp
		while (i < gp.max.maxHp - gp.max.hp) {
			g2.drawImage(empty_heart, hp_X, hp_Y, gp.tileSize, gp.tileSize, null);
			i++;
			hp_X +=gp.tileSize;
		}
		
		
		
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

	void checkCollision(Tile block) {
		Rectangle tile = block.blockHitBox;
		if (player.intersects(tile)) {
			// stop the rect from moving
			double left1 = player.getX();
			double right1 = player.getX() + player.getWidth();
			double top1 = player.getY();
			double bottom1 = player.getY() + player.getHeight();
			double left2 = tile.getX();
			double right2 = tile.getX() + tile.getWidth();
			double top2 = tile.getY();
			double bottom2 = tile.getY() + tile.getHeight();

			if (right1 > left2 &&
					left1 < left2 &&
					right1 - left2 < bottom1 - top2 &&
					right1 - left2 < bottom2 - top1) {
				// rect collides from left side of the wall
				if (block.collision) {
					player.x = tile.x - player.width;
				} else
					airborne = true;
			} else if (left1 < right2 &&
					right1 > right2 &&
					right2 - left1 < bottom1 - top2 &&
					right2 - left1 < bottom2 - top1) {
				// rect collides from right side of the wall
				if (block.collision) {
					player.x = tile.x + tile.width;
				}
				airborne = true;
			} else if (bottom1 > top2 && top1 < top2) {
				// rect collides from top side of the wall
				if (block.collision) {
					player.y = tile.y - player.height;
					airborne = false;
				}
				airborne = true;

			} else if (top1 < bottom2 && bottom1 > bottom2) {
				// rect collides from bottom side of the wall

				if (block.collision) {
					player.y = tile.y + tile.height;
					airborne = true;
				}

				airborne = true;
			}
		}
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