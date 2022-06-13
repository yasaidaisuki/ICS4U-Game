import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;

public class Character {

	protected int worldX, worldY, hp, dmg;
	protected double speed, xVel, yVel;

	public BufferedImage left, right, left_w1, left_w2, left_w3, left_w4, right_w1, right_w2, right_w3, right_w4, left_up, right_up, left_atk, right_atk;
	public String direction;

	public int spriteCounter = 0;
	public int spriteNum = 1;

}
