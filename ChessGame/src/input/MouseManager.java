package input;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import states.State;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener {
	/* Mouse manager class it implement the actions on the click of the left mouse
	 * and keep track of the actual position of the mouse on the screen*/
	private boolean leftButton;
	private int x = 0, y = 0;

	public MouseManager() {
	}

	public boolean isLeftButtonPressed() {
		return leftButton;
	}

	public boolean setLeftButtonPressed(boolean leftButton) {
		/* In case that the person keeps holding the left mouse button this method can be called 
		 * out and stop manually the constant input of the holding*/
		return this.leftButton = leftButton;
	}

	public int getMouseX() {
		return x;
	}

	public int getMouseY() {
		return y;
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
		/*Method called every time the mouse is pressed*/
		//Check if the BUTTON1 was pressed button1 represent the left mouse button
		if ((mouse.getButton() == MouseEvent.BUTTON1)) {
			//if it was pressed get the mouse position
			x = mouse.getX();
			y = mouse.getY();
			leftButton = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {
		/*Method called when the mouse button is released*/
		//Check if the mouse released was the left mouse button
		if (mouse.getButton() == MouseEvent.BUTTON1) {
			leftButton = false;
		}

		//If there is a button list on the current state, call it and execute the MouseRelease of each button
		if (State.getCurrentState().getUIButtons() != null) {
			State.getCurrentState().getUIButtons().bMouseRelease();
		}
		
		if(State.getCurrentState().getScreen() != null) {
			State.getCurrentState().getScreen().sMouseRelease();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent mouse) {
	}

	@Override
	public void mouseEntered(MouseEvent mouse) {

	}

	@Override
	public void mouseExited(MouseEvent mouse) {
	}

	@Override
	public void mouseDragged(MouseEvent mouse) {

	}

	@Override
	public void mouseMoved(MouseEvent mouse) {
		/*Every mouse movement this method is called and it checks if the current
		 * state has a button list, if it is true then call the mouseMoved method of each button on the list*/
		if (State.getCurrentState().getUIButtons() != null) {
			State.getCurrentState().getUIButtons().bMouseMoved(mouse);
		}
		if(State.getCurrentState().getScreen() != null) {
			State.getCurrentState().getScreen().sMouseMoved(mouse);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mouse) {
		if(State.getCurrentState().getScreen() != null) {
			State.getCurrentState().getScreen().sMouseScroll(mouse);
		}	
	}

}