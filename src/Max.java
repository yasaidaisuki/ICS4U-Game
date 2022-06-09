import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.*;

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
	}

	public void setDefaultValues() {
		x = 0;
		y = gp.screenY + gp.ogTileSize;
		xVel = 0;
		yVel = 0;
		speed = 5;
		jumpSpeed = 10;
		gravity = 0.8;
		hp = 4;
		dmg = 1;
	}

	public void move() {
		if (keyH.left)
			xVel = -speed;
		else if (keyH.right)
			xVel = speed;
		else
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
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
	}

	public void keepInBound() {
		if (x < 0) {
			x = 0;
		} else if (x > gp.screenX - gp.tileSize) {
			x = gp.screenX - gp.tileSize;
		}

		if (y < 0) {
			y = 0;
			yVel = 0;
		} else if (y > gp.screenX - gp.tileSize) {
			y = gp.screenX - gp.tileSize;
			airborne = false;
			yVel = 0;
		}
	}

}

// spsss this is a hidden easter egg. i love you ding kai peng