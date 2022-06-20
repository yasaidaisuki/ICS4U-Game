import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class KeyHandler implements KeyListener {

	private Timer timer;
	boolean jump, left, right, attack; // check for player input
	long startAtk = System.currentTimeMillis();

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// Name: keyPressed
	// Purpose: check for keypress
	// Param: KeyEvent
	// Return: void
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
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
			if (attack == false && currentTime - startAtk >= 1000) {
				attack = true;
				jump = false;
				right = false;
				left = false;
				startAtk = System.currentTimeMillis();
			}
			else {
				attack = false;
			}

		}
	}

	// Name: keyReleased
	// Purpose: check for key released
	// Param: KeyEvent
	// Return: void
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			left = false;
		} else if (key == KeyEvent.VK_D) {
			right = false;
		} else if (key == KeyEvent.VK_W) {
			jump = false;
		} else if (key == KeyEvent.VK_J) {
			attack = false;
		}
	}

}
