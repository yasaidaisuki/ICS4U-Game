import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.*;

public class Max extends Character {

	GamePanel gp;
	KeyHandler keyH;
	int jumpSpeed;

	public Max(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
	}

	public void setDefaultValues() {
		x = 100;
		y = 100;
		speed = 5;
		jumpSpeed = 20;
		hp = 4;
		dmg = 1;
	}

	public void update() {
		if (keyH.jump == true) {
			if (y < 0) {
				y = 0;
			} else {
				y -= jumpSpeed;
			}
		} else if (keyH.left == true) {
			if (x == 0) {
				speed = 0;
			} else {
				speed = 5;
				x -= speed;
			}
		} else if (keyH.right == true) {
			if (x > gp.screenX - gp.tileSize) {
				speed = 0;
			} else {
				speed = 5;
				x += speed;
			}
		}
		if (y > gp.screenY - gp.tileSize) {
			y = gp.screenY - gp.tileSize;
		}
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
	}

}

//spsss this is a hidden easter egg. i love you ding kai peng