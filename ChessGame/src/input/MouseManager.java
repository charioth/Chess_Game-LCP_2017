package input;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import states.State;

public class MouseManager implements MouseListener, MouseMotionListener {
	private boolean leftButton;
	private int x = 0, y = 0;

	public MouseManager() {
	}

	public boolean isLeftButtonPressed() {
		return leftButton;
	}

	public boolean setLeftButtonPressed(boolean leftButton) {
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
		if ((mouse.getButton() == MouseEvent.BUTTON1)) {
			x = mouse.getX();
			y = mouse.getY();
			leftButton = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {
		if (mouse.getButton() == MouseEvent.BUTTON1) {
			leftButton = false;
		}

		if (State.getCurrentState().getUIButtons() != null) {
			State.getCurrentState().getUIButtons().bMouseRelease();
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
		if (State.getCurrentState().getUIButtons() != null) {
			State.getCurrentState().getUIButtons().bMouseMoved(mouse);
		}
	}

}