package graphics;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UIButton {
	private int x, y, width, height;
	private boolean onButton;
	BufferedImage button[];
	Rectangle bound;
	ButtonAction click;

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

	public void tick() {

	}

	public void render(Graphics graph) {
		if (onButton == false) {
			graph.drawImage(button[0], x, y, width, height, null);
		} else {
			graph.drawImage(button[1], x, y, width, height, null);
		}

	}

	public void bMouseMoved(MouseEvent mouse) {
		if (bound.contains(mouse.getX(), mouse.getY())) {
			onButton = true;
		} else {
			onButton = false;
		}
	}

	public void bMouseRelease() {
		if (onButton) {
			onButton = false;
			click.action();
		}
	}
}
