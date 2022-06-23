import javax.swing.*;

import java.awt.*;
import javax.imageio.ImageIO;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Iterator;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// screen font
	Font font;

	// Screen settings
	int ogTileSize = 16; // 16 pxl tile size
	int scale = 3; // scale multiplier
	int tileSize = ogTileSize * scale; // tile size
	int maxScreenCol = 24; // max screen x
	int maxScreenRow = 17; // max screen y
	int screenX = tileSize * maxScreenCol; // actual screen dimensions
	int screenY = tileSize * maxScreenRow; // actual screen dimensions

	int maxWorldCol = 168;
	int maxWorldRow = 22;
	int worldHeight = maxWorldCol * tileSize;
	int worldWidth = maxWorldRow * tileSize;

	int FPS = 60; // fps

	// Tyler arrayList
	ArrayList<Tyler> tylerList = new ArrayList<>();

	// leaderboard
	int attempt;
	int score;
	HashMap<Integer, Integer> leaderboard = new HashMap<>();
	ArrayList<Record> sortedScores = new ArrayList<>();
	boolean recordScore = true;

	// Controls class
	TileManager tileM = new TileManager(this); // tile manager object
	KeyHandler keyH = new KeyHandler(this); // control handler object
	Thread thread; // thread
	Sound sound = new Sound();

	// player
	Max max = new Max(this, keyH);
	Wong wong = new Wong(this);

	// Title Screen
	Image titleImg;

	// image background
	Image background;

	// Game State
	int gameState;
	int titleState = 0;
	int playState = 1;
	int leaderBState = 2;
	int helpState = 3;
	int creditState = 4;
	int winState = 69;
	int endState = 420;
	int map2 = 5;
	int death = 6;
	public int mapNum = 1;

	// boolean change world
	boolean changeWord = false;

	// Title menu commands
	int commandNum = 0;

	// death timer
	long startDeath;
	boolean canAlive = false;

	// Name: GamePanel
	// Purpose: game constructor
	// Param: n/a
	// Return: n/a
	public GamePanel() {
		// set dimensions
		setPreferredSize(new Dimension(screenX, screenY - 2 * tileSize));
		setVisible(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		// this.setDoubleBuffered(true);
		// start threading game
		thread = new Thread(this);
		thread.start();
	}

	// Name: run
	// Purpose: constantly loop the game || makes it run
	// Param: n/a
	// Return: n/a
	public void run() {
		initialize();
		while (true) {

			update();
			this.repaint();

			try {
				Thread.sleep(1000 / FPS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Name: initialize
	// Purpose: start any variables and actions before the thread
	// Param: n/a
	// Return:void
	public void initialize() {

		// setups before the game starts running
		gameState = titleState;
		// play starting music
		playMusic(0);
		// load map 1
		tileM.loadMap();

		leaderboard.clear();
		getScore();
		Collection<Integer> score = leaderboard.values();
		Iterator<Integer> attempt = leaderboard.keySet().iterator();
		for (Integer i : score) {
			sortedScores.add(new Record(attempt.next(), i));

		}
		Collections.sort(sortedScores);

		// if add 2 tylers to the tyler list
		// if map 1
		tylerList.add(new Tyler(this, (int) (tileSize * 8), (int) (tileSize * 15), 15));
		tylerList.add(new Tyler(this, (int) (tileSize * 96), (int) (tileSize * 7), 8));
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/background/map1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Name: update
	// Purpose: updates values inside the run
	// Param: n/a
	// Return: void
	public void update() {
		// if game state is map 1 then load max and tyler
		if (gameState == playState) {
			maxAction();
			tylerAction();
			checkCollision();
			// if game state is map 2 then load max and wong
		} else if (gameState == map2) {
			maxAction();
			wongAction();
			checkCollision();
		}
		// if switch map, then remove the tylers and change the map
		if (changeWord) {
			for (int i = 0; i < tylerList.size(); i++) {
				tylerList.remove(i);
			}
			tylerList.clear();
			// change state and change map
			gameState = map2;
			mapNum = 2;
			tileM.loadMap();
			max.player.x = 0;
			max.player.y = 0;
			// set it back to false
			changeWord = false;
		}

	}

	// Name: maxAction
	// Purpose: allows max to move and keep in bound
	// Param: n/a
	// Return: void
	public void maxAction() {
		max.xVel = 0;
		max.move();
		max.keepInBound();
	}

	// Name: tylerAction
	// Purpose: allows tyler to move and keep in bound
	// Param: n/a
	// Return: void
	public void tylerAction() {
		// checks collision for all tylers
		for (int i = 0; i < tylerList.size(); i++) {
			for (int j = tylerList.size() - 1; j >= 0; j--) {
				tylerList.get(j).checkTylerCollision(tylerList.get(i));
			}
		}
		for (int i = 0; i < tylerList.size(); i++) {
			if (Math.abs(tylerList.get(i).player.x - max.player.x) < screenX / 2) {
				tylerList.get(i).move();
			}
			tylerList.get(i).setAction();
			tylerList.get(i).checkPlayerCollision(max, keyH);
			tylerList.get(i).keepInBound();
		}
	}

	// Name: maxAction
	// Purpose: allows tyler to move and keep in bound
	// Param: n/a
	// Return: void
	public void wongAction() {
		wong.move();
		wong.setAction();
		wong.keepInBound();
		// checks if wong comes in contact with max
		wong.checkPlayerCollision(max, keyH);
	}

	// Name: checkCollision
	// Purpose: checks colision for all characters
	// Param: n/a
	// Return: void
	public void checkCollision() {
		// player collision
		boolean flag = false;
		for (int i = 0; i < tileM.getTiles().size(); i++) {
			if (max.checkCollision(tileM.getTiles().get(i))) {
				flag = true;
			}
			// tyler collision
			for (int j = 0; j < tylerList.size(); j++) {
				max.checkTylerCollision(tylerList.get(j));
				if (tylerList.get(j).checkCollision(tileM.getTiles().get(i), tylerList.get(j).getyLoc())) {
					flag = true;
				}
			}
			// wong collision
			for (int k = 0; i < wong.projList.size(); i++) {
				max.checkProjCollision(wong.proj);
				wong.checkProjCollision(tileM.getTiles().get(i), k, max);
			}
			if (wong.checkCollision(tileM.getTiles().get(i))) {
				flag = true;
			}
		}
	}

	// Name: paintComponent
	// Purpose: draw the game || characters, background, ect.
	// Param: Graphics
	// Return: void
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// title Screen & menu
		if (gameState == titleState) {
			drawTitle(g2);
		} else if (gameState == helpState) {
			drawHelp(g2);
		} else if (gameState == creditState) {
			drawCredit(g2);
		} else if (gameState == leaderBState) {
			drawLeader(g2);
		} else if (gameState == map2) {
			g2.drawImage(background, 0, 0, screenX + 200, screenY, null);
			tileM.draw(g2);
			max.draw(g2);
			if (!wong.dead) {
				wong.draw(g2);
			}
		}

		// Death state
		if (max.dead) {
			keyH.left = false;
			keyH.right = false;
			keyH.jump = false;
			tylerList.removeAll(tylerList);
			stopSound(0);
			max.player.x = 0;
			max.player.y = 15 * tileSize;
			gameState = endState;
			drawDeath(g2);
			mapNum = 1;
			initialize();

		}

		// win state
		if (gameState == winState && recordScore) {
			tylerList.removeAll(tylerList);
			stopSound(0);
			drawWin(g2);
			Collections.sort(sortedScores);
			Record r = new Record(attempt, score);
			try {
				PrintWriter output = new PrintWriter(new FileWriter("highscore.txt", true));
				sortedScores.add(r);
				output.println("\n" + r.getattempt() + "/" + r.getscore());
				output.close();
				System.out.println(r);
			} catch (IOException e) {
				System.out.println("someting wong with my code");
			}
			recordScore = false;
		}

		// Game Screen
		else if (gameState == playState) {
			g2.drawImage(background, 0, 0, screenX + 200, screenY, null);
			tileM.draw(g2);
			max.draw(g2);

			// draws tyler if alive

			for (int i = 0; i < tylerList.size(); i++) {
				if (!tylerList.get(i).dead)
					tylerList.get(i).draw(g2);
				else
					tylerList.remove(i);
			}
		}
	}

	// Name: playMusic
	// Purpose: plays sound and loops it
	// Param: n/a
	// Return: void
	public void playMusic(int i) {
		// takes the pre inputted wav file and loops it
		sound.setFile(i);
		sound.play();
		sound.loop();

	}

	// Name: stopSound
	// Purpose: stops sound
	// Param: n/a
	// Return: void
	public void stopSound(int i) {
		sound.stop();
	}

	// Name: soundEffect
	// Purpose: plays sound and but doesnt loop
	// Param: n/a
	// Return: void
	public void soundEffect(int i) {
		sound.setFile(i);
		sound.play();
	}

	// Name: drawTitle
	// Purpose: draw the title screen
	// Param: Graphics2D
	// Return: void
	public void drawTitle(Graphics2D g2) {

		titleImg = new ImageIcon("ICS4U_title.jpg").getImage();

		// background
		g2.drawImage(titleImg, -440, -200, null);

		try {
			// Title
			font = Font.createFont(Font.TRUETYPE_FONT, new File("OptimusPrinceps.ttf"));
			g2.setFont(font.deriveFont(80f));
			String title = "Max Souls";
			float x = (float) (tileSize * 7.5);
			float y = (float) (tileSize * 2);

			g2.setColor(Color.white);
			g2.drawString(title, x, y);

			// Menu
			String text = "Play";
			g2.setFont(font.deriveFont(70f));
			x = (float) (tileSize * 9.6);
			y += tileSize * 3;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - tileSize, y);
			}

			text = "Leaderboard";
			x = (float) (tileSize * 7);
			y += tileSize * 2;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - tileSize, y);
			}

			text = "Help";
			x = (float) (tileSize * 9.6);
			y += tileSize * 2;
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x - tileSize, y);
			}

			text = "Credits";
			x = (float) (tileSize * 8.5);
			y += tileSize * 2;
			g2.drawString(text, x, y);
			if (commandNum == 3) {
				g2.drawString(">", x - tileSize, y);
			}

			text = "Quit";
			x = (float) (tileSize * 9.6);
			y += tileSize * 2;
			g2.drawString(text, x, y);
			if (commandNum == 4) {
				g2.drawString(">", x - tileSize, y);
			}

		} catch (IOException | FontFormatException e) {
		} catch (Exception e) {
		}

	}

	// Name: drawCredit
	// Purpose: draw the credit screen
	// Param: Graphics2D
	// Return: void
	public void drawLeader(Graphics2D g2) {

		// background
		g2.drawImage(titleImg, -440, -200, null);

		// Title Name
		g2.setFont(font.deriveFont(Font.BOLD, 60F));
		String text = "CREDITS";
		float x = (float) (tileSize * 9);
		float y = (float) (tileSize * 1.5);
		g2.setColor(Color.white);
		g2.drawString(text, x, y + 5);

		g2.setFont(font.deriveFont(50F));
		text = "Developers:";
		x = (float) (tileSize * 9.25);
		y += tileSize * 2;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "Ming Luo";
		x = (float) (tileSize * 9.8);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "Dami Peng";
		x = (float) (tileSize * 9.6);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "Sami Peng";
		x = (float) (tileSize * 9.7);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(50F));
		text = "VOICE ACTORS:";
		x = (float) (tileSize * 7.8);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "Tyler Zeng";
		x = (float) (tileSize * 9.6);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "Yunji Zhang";
		x = (float) (tileSize * 9.3);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 50F));
		text = "| PRESS ESCAPE TO EXIT |";
		x = (float) (tileSize * 6.2);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
	}

	// Name: drawCredit
	// Purpose: draw the credit screen
	// Param: Graphics2D
	// Return: void
	public void drawCredit(Graphics2D g2) {

		// background
		g2.drawImage(titleImg, -440, -200, null);

		// Title Name
		g2.setFont(font.deriveFont(Font.BOLD, 60F));
		String text = "CREDITS";
		float x = (float) (tileSize * 9);
		float y = (float) (tileSize * 1.5);
		g2.setColor(Color.white);
		g2.drawString(text, x, y + 5);

		g2.setFont(font.deriveFont(50F));
		text = "Developers:";
		x = (float) (tileSize * 9.25);
		y += tileSize * 2;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "Ming Luo";
		x = (float) (tileSize * 9.8);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "Dami Peng";
		x = (float) (tileSize * 9.6);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "Sami Peng";
		x = (float) (tileSize * 9.7);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(50F));
		text = "VOICE ACTORS:";
		x = (float) (tileSize * 7.8);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "Tyler Zeng";
		x = (float) (tileSize * 9.6);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "Yunji Zhang";
		x = (float) (tileSize * 9.3);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 50F));
		text = "| PRESS ESCAPE TO EXIT |";
		x = (float) (tileSize * 6.2);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
	}

	// Name: drawHelp
	// Purpose: draw the help screen
	// Param: Graphics2D
	// Return: void
	public void drawHelp(Graphics2D g2) {

		// background
		g2.drawImage(titleImg, -440, -200, null);

		// draw rectangle
		g2.setColor(Color.black);
		g2.fillRoundRect(tileSize * 3, tileSize, screenX - tileSize * 6, screenY - tileSize * 2, 50, 50);

		// Title Name
		g2.setFont(font.deriveFont(Font.BOLD, 45F));
		String text = "GOAL:";
		float x = (float) (tileSize * 10.3);
		float y = (float) (tileSize * 2);
		g2.setColor(Color.white);
		g2.drawString(text, x, y + 5);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "1. Progress Through the Map";
		x = (float) (tileSize * 6);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "2. Defeat Ms. Wong !!";
		x = (float) (tileSize * 8);
		y += tileSize;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		g2.setFont(font.deriveFont(Font.BOLD, 40F));
		text = "3. Earn Points By Defeating Enemies !";
		x = (float) (tileSize * 5);
		y += tileSize;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 45F));
		text = "CONTROLS:";
		x = (float) (tileSize * 9.1);
		y += tileSize * 2;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		g2.setFont(font.deriveFont(Font.BOLD, 40F));

		g2.setFont(font.deriveFont(Font.BOLD, 35F));
		text = "Press W, A, D, to control your character";
		x = (float) (tileSize * 4.5);
		y += tileSize * 2;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		g2.setFont(font.deriveFont(Font.BOLD, 40F));

		text = "Press J to attack";
		x = (float) (tileSize * 8.5);
		y += tileSize * 1.5;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		g2.setFont(font.deriveFont(Font.BOLD, 50F));
		text = "| PRESS ESCAPE TO EXIT |";
		x = (float) (tileSize * 6.2);
		y += tileSize * 3;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
	}

	// Name: drawDeath
	// Purpose: draw the death screen
	// Param: Graphics2D
	// Return: void
	public void drawDeath(Graphics2D g2) {

		// draw rectangle
		g2.setColor(Color.black);
		g2.fillRect(0, 0, screenX, screenY);

		g2.setFont(font.deriveFont(60F));
		String text = "YOU DIED";
		float x = (float) (tileSize * 9);
		float y = (float) tileSize * 7;
		g2.setColor(Color.red);
		g2.drawString(text, x, y + 5);

		long currentTime = System.currentTimeMillis();
		if (currentTime - startDeath >= 3000) {
			canAlive = true;
			text = "> Retry";
			x = (float) (tileSize * 9.7);
			y += tileSize * 3;
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
		}
	}

	// Name: drawWin
	// Purpose: draw the win screen
	// Param: Graphics2D
	// Return: void
	public void drawWin(Graphics2D g2) {

		// draw rectangle
		g2.setColor(Color.black);
		g2.fillRect(0, 0, screenX, screenY);

		g2.setFont(font.deriveFont(60F));
		String text = "YOU WIN";
		float x = (float) (tileSize * 9);
		float y = (float) tileSize * 7;
		g2.setColor(Color.white);
		g2.drawString(text, x, y + 5);

		long currentTime = System.currentTimeMillis();

		canAlive = true;
		text = "> Exit";
		x = (float) (tileSize * 10);
		y += tileSize * 3;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

	}

	public void getScore() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("highscore.txt"));
			String line = "";
			int highestAttempt = 0;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "/", false);
				while (st.hasMoreTokens()) {
					attempt = Integer.parseInt(st.nextToken());
					score = Integer.parseInt(st.nextToken());
				}
				if (attempt > highestAttempt) {
					highestAttempt = attempt;
				}
				leaderboard.put(attempt, score);
			}
			attempt = highestAttempt + 1;
		} catch (FileNotFoundException e) {
			System.out.println("file gone");
		} catch (IOException e) {
			System.out.println("bad exeption");
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Max Souls");
		GamePanel myPanel = new GamePanel();
		frame.add(myPanel);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}