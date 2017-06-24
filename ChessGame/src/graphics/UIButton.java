package graphics;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UIButton {
	/*Button class it has a position on the screen, a size (width and height)
	 * a onButton attribute to control when the mouse is on the button
	 * a bufferedImage vector to hold the 2 images of the button
	 * a Rectangle to test if the mouse position is on bound with the button area
	 * and last the buttonAction that tell what the button should execute when clicked*/
	private int x, y, width, height;
	private boolean onButton;
	BufferedImage button[];
	Rectangle bound;
	ButtonAction click;

	//Passing the ButtonAction as parameter is possible to program the button when creating it
	public UIButton(int x, int y, int width, int height, BufferedImage button[], ButtonAction click) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.button = button;
		onButton = false;
		bound = new Rectangle(x, y, width, height);
		this.click = click;
	}

	//If there was a action that the button should execute it would be necessary to program here
	public void tick() {

	}

	public void render(Graphics graph) {
		/*Method responsible to render the button on the screen*/
		
		/*If onButton false it means that the mouse is not on the button so use the first image
		 * if is true than render the second image*/
		if (onButton == false) {
			graph.drawImage(button[0], x, y, width, height, null);
		} else {
			graph.drawImage(button[1], x, y, width, height, null);
		}

	}

	public void bMouseMoved(MouseEvent mouse) {
		/*Method responsible for checking if the mouse coordinates is in bounds with
		 * the button area, if true, than onButton should be true to render the second image
		 * if not then on button is false*/
		if (bound.contains(mouse.getX(), mouse.getY())) {
			onButton = true;
		} else {
			onButton = false;
		}
	}

	public void bMouseRelease() {
		//If the mouse is on button than call the action method that execute the action
		if (onButton) {
			onButton = false;
			click.action();
		}
	}
}
