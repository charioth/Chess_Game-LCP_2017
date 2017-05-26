package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{
	private boolean []keys;
	public boolean mESC;
	
	public KeyManager()
	{
		keys = new boolean[256];
	}
	
	public void tick()
	{
		mESC = keys[KeyEvent.VK_ESCAPE];
	}
	
	public void render()
	{
		
	}

	@Override
	public void keyPressed(KeyEvent key) 
	{
		keys[key.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent key)
	{
		keys[key.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
	}
}
