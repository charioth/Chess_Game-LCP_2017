package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseManager implements MouseListener{
	private boolean leftButton;
	private int x, y;
	
	public MouseManager()
	{
	}
	
	public boolean isLeftButtonPressed()
	{
		return leftButton;
	}
	
	public int getMouseX()
	{
		return x;
	}
	
	public int getMouseY()
	{
		return y;
	}
	
	@Override
	public void mousePressed(MouseEvent mouse) 
	{
		if(mouse.getButton() == MouseEvent.BUTTON1)
		{
			leftButton = true;
			x = mouse.getX();
			y = mouse.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent mouse) 
	{
		if(mouse.getButton() == MouseEvent.BUTTON1)
			leftButton = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent mouse) 
	{
	}

	@Override
	public void mouseEntered(MouseEvent mouse)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent mouse)
	{
	}

}