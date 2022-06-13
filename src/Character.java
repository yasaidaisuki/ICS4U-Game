import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;

public class Character {

	protected int worldX, worldY, hp, dmg;
	protected double speed, xVel, yVel;

	public BufferedImage left, right, left_w1, left_w2, right_w1, right_w2, left_up, right_up, left_atk, right_atk;
	public String direction;

	public int spriteCounter = 0;
	public int spriteNum = 1;

}
