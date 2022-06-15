import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public abstract class Character {

	// worldX & worldY are the positions of each respective character
	// each character has current hp, max hp, and dmg.
	// each character also have speed xVel, yVel
	protected int worldX, worldY, hp, maxHp, dmg;
	protected double speed, xVel, yVel;
	// draws a rectangle for player
	protected Rectangle player;

	// images for each frame sprite
	public BufferedImage left, right, idle_left, idle_right, left_w1, left_w2, left_w3, left_w4, right_w1, right_w2,
			right_w3, right_w4, left_atk, right_atk;
	public String direction;

	// for animation
	public int spriteCounter = 0;
	public int spriteNum = 1;

}