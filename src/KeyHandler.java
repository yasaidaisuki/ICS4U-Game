import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	boolean jump, left, right, attack;
	
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
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
        	attack = true;
        	jump = false;
        	right = false;
            left = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            left = false;
        } else if (key == KeyEvent.VK_D) {
            right = false;
        } else if (key == KeyEvent.VK_W) {
            jump = false;
        }
    }

}
