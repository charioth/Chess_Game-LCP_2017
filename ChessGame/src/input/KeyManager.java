package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	private boolean[] keys;
	public boolean mESC;
	private boolean once = true;

	public KeyManager() {
		keys = new boolean[256];
	}

	public void tick() {
		if (keys[KeyEvent.VK_ESCAPE] & once) {
			mESC = true;
			once = false;
		}
	}

	public void render() {

	}

	@Override
	public void keyPressed(KeyEvent key) {
		keys[key.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent key) {
		keys[key.getKeyCode()] = false;
		once = true;
	}

	@Override
	public void keyTyped(KeyEvent key) {
	}
}
