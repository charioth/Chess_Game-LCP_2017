package graphics;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIList {

	private ArrayList<UIButton> buttons;
	
	public UIList()
	{
		buttons = new ArrayList<UIButton>();
	}
	
	public ArrayList<UIButton> getButtons() {
		return buttons;
	}
	
	public void tick()
	{
		for(UIButton b : buttons)
		{
			b.tick();
		}
	}
	
	public void render(Graphics graph)
	{
		for(UIButton b : buttons)
		{
			b.render(graph);
		}
	}

	public void bMouseMoved(MouseEvent mouse) {
		for(UIButton b : buttons)
		{
			b.bMouseMoved(mouse);
		}
	}
	
	public void bMouseRelease() 
	{
		for(UIButton b : buttons)
		{
			b.bMouseRelease();
		}
	}

}
