import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class KeyHandler implements KeyListener {

	// for menu controls
	GamePanel gp;
	// for attack
	boolean flag = false;
	boolean jump, left, right, attack; // check for player input
	long startAtk = System.currentTimeMillis();

	// Name: KeyHandler
	// Purpose: constructor
	// Param: n/a
	// Return: n/a
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	// Name: keyPressed
	// Purpose: check for keypress
	// Param: KeyEvent
	// Return: void
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		// title state
		if (gp.gameState == gp.titleState) {
			// scrolling down commands
			if (key == KeyEvent.VK_SPACE) {
				gp.commandNum++;
			}
			// resets the scroll if it reaches the end
			if (gp.commandNum >= 5) {
				gp.commandNum = 0;
			}
			// enter command
			if (key == KeyEvent.VK_ENTER) {
				if (gp.commandNum == 0) {
					gp.stopSound(0);
					gp.playMusic(1);
					gp.gameState = gp.playState;
				}
				if (gp.commandNum == 2) {
					gp.gameState = gp.helpState;
				}
				if (gp.commandNum == 3) {
					gp.gameState = gp.creditState;
				}
				if (gp.commandNum == 4) {
					System.exit(0);
				}
			}
		}

		if (gp.gameState == gp.creditState || gp.gameState == gp.helpState || gp.gameState == gp.leaderBState) {
			if (key == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.titleState;
			}
		}

		// Play state
		if (gp.gameState == gp.playState) {
			if (key == KeyEvent.VK_A) {
				left = true;
				right = false;
			} else if (key == KeyEvent.VK_D) {
				right = true;
				left = false;
			} else if (key == KeyEvent.VK_W) {
				jump = true;
			} else if (key == KeyEvent.VK_J) {
				long currentTime = System.currentTimeMillis();
				if (attack == false && currentTime - startAtk >= 800 && flag == false) {
					gp.soundEffect(6);
					gp.soundEffect(3);
					attack = true;
					flag = true;
					jump = false;
					right = false;
					left = false;
					startAtk = System.currentTimeMillis();
				} else {
					attack = false;
				}
			}
		}
	}

	// Name: keyReleased
	// Purpose: check for key released
	// Param: KeyEvent
	// Return: void
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		// Play state
		if (gp.gameState == gp.playState) {
			if (key == KeyEvent.VK_A) {
				left = false;
			} else if (key == KeyEvent.VK_D) {
				right = false;
			} else if (key == KeyEvent.VK_W) {
				jump = false;
			} else if (key == KeyEvent.VK_J) {
				attack = false;
				flag = false;
			}
		}
	}

}
