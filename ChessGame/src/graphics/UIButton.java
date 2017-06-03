package graphics;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import states.State;
import tempassets.Assets;

public class UIButton {
	private int x, y, width, height;
	private boolean onButton;
	BufferedImage button[];
	Rectangle bound;
	State changeToState;
	
	public UIButton(int x, int y, int width, int height, BufferedImage button[], State state) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.button = button;
		onButton = false;
		changeToState = state;
		bound = new Rectangle(x, y, width, height);
	}
	
	void tick()
	{
	
	}
	
	void render(Graphics graph)
	{
		if(onButton == false)
		{
			graph.drawImage(button[0], x, y , width, height, null);
		}
		else
		{
			graph.drawImage(button[1], x, y, width, height, null);
		}
		
	}

	public void bMouseMoved(int x, int y) {
		if(bound.contains(x, y))
		{
			onButton = true;
		}
		else
		{
			onButton = false;
		}
	}
	
	public void bMouseRelease() 
	{
		State.setCurrentState(changeToState);
	}
}
