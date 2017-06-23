package graphics;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UIScrollScreen {

	private ArrayList<UIButton> buttons;
	private int x, y, width, height, screenCenterX, screenCenterY, activeCount, counter;
	private boolean onScreen;
	BufferedImage screen;
	Rectangle bound;

	public UIScrollScreen(BufferedImage screen, int x, int y, int width, int height, int activeCount) {
		buttons = new ArrayList<>();
		this.screen = screen;
		bound = new Rectangle(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.activeCount = activeCount;
		screenCenterX = (width / 2) + x;
		screenCenterY = (height / 2) + y;
		counter = 0;
	}

	public void tick() {
		for (int i = counter; i < activeCount; i++) {
			buttons.get(i).tick();
		}
	}

	public void render(Graphics graph) {
		graph.drawImage(screen, x, y, width, height, null);
		for (int i = counter; i < activeCount; i++) {
			buttons.get(i).render(graph);
		}
	}

	public void sMouseMoved(MouseEvent mouse) {
		if (bound.contains(mouse.getX(), mouse.getY())) {
			onScreen = true;
			for (int i = counter; i < activeCount; i++) {
				buttons.get(i).bMouseMoved(mouse);
			}
		} else {
			onScreen = false;
		}

	}

	public void sMouseRelease() {
		if (onScreen) {
			onScreen = false;
			for (int i = counter; i < activeCount; i++) {
				buttons.get(i).bMouseRelease();
			}
		}
	}

	void rollDown() {
		for (int i = counter; i < activeCount; i++) {
			buttons.get(i).setX(buttons.get(i - 1).getX());
			buttons.get(i).setX(buttons.get(i - 1).getY());
		}
	}

	void rollUp() {
		for (int i = counter; i > 0; i++) {
			buttons.get(i - 1).setX(buttons.get(i).getX());
			buttons.get(i - 1).setX(buttons.get(i).getY());
		}
	}

	public void sMouseScroll(MouseWheelEvent mouse) {
		int rotation = mouse.getWheelRotation();
		System.out.println("SCROLL");
		if (rotation < 0) {
			if (counter != 0) {
				counter--;
				rollDown();
			}
		} else {
			if (counter < (buttons.size() - activeCount)) {
				counter++;
				rollUp();
			}
		}
	}

	public int getScreenCenterX() {
		return screenCenterX;
	}

	public void setScreenCenterX(int screenCenterX) {
		this.screenCenterX = screenCenterX;
	}

	public int getScreenCenterY() {
		return screenCenterY;
	}

	public void setScreenCenterY(int screenCenterY) {
		this.screenCenterY = screenCenterY;
	}

}
