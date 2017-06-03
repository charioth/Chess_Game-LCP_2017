package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener{
	private boolean leftButton;
	private int x, y;
	private int moveX, moveY;

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
	
	public int getMoveY() {
		return moveY;
	}

	public void setMoveY(int moveY) {
		this.moveY = moveY;
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

	@Override
	public void mouseDragged(MouseEvent mouse) {
		
	}

	@Override
	public void mouseMoved(MouseEvent mouse) {
		moveX = mouse.getX();
		moveY = mouse.getY();
	}

}