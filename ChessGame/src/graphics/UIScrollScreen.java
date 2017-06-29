package graphics;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UIScrollScreen {

	private ArrayList<UIButton> buttons;
	private Rectangle screen;
	private boolean onScreen;
	private int speed;
	private BufferedImage screenImage;

	public UIScrollScreen(BufferedImage screenImage, int x, int y, int width, int height, int speed) {
		buttons = new ArrayList<>();
		screen = new Rectangle(x, y, width, height);
		this.screenImage = screenImage;
		this.speed = speed;
	}

	public ArrayList<UIButton> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<UIButton> buttons) {
		this.buttons = buttons;
	}

	public void tick() {
		for (int i = 0; i < buttons.size(); i++) {
			if(screen.contains(buttons.get(i).getX(), buttons.get(i).getY()) && (screen.contains(buttons.get(i).getX(), buttons.get(i).getY() + buttons.get(i).getHeight())))
				buttons.get(i).tick();
		}
	}

	public void render(Graphics graph) {
		graph.drawImage(screenImage, (int)screen.getX(), (int)screen.getY(), (int)screen.getWidth(), (int)screen.getHeight(), null);
		for (int i = 0; i < buttons.size(); i++) {
			if(screen.contains(buttons.get(i).getX(), buttons.get(i).getY()) && (screen.contains(buttons.get(i).getX(), buttons.get(i).getY() + buttons.get(i).getHeight())))
				buttons.get(i).render(graph);
		}
	}

	public void sMouseMoved(MouseEvent mouse) {
		if (screen.contains(mouse.getX(), mouse.getY()) ) {
			onScreen = true;
			for (int i = 0; i < buttons.size(); i++) {
				if(screen.contains(buttons.get(i).getX(), buttons.get(i).getY()) && (screen.contains(buttons.get(i).getX(), buttons.get(i).getY() + buttons.get(i).getHeight())))
				buttons.get(i).bMouseMoved(mouse);;
			}
		}
		else
		{
			onScreen = false;
		}
	}

	public void sMouseRelease() {
		if (onScreen) {
			onScreen = false;
			for (int i = 0; i < buttons.size(); i++) {
				if(screen.contains(buttons.get(i).getX(), buttons.get(i).getY()) && (screen.contains(buttons.get(i).getX(), buttons.get(i).getY() + buttons.get(i).getHeight())))
					buttons.get(i).bMouseRelease();
			}
		}
	}


	public void sMouseScroll(MouseWheelEvent mouse) {
		int rotation = mouse.getWheelRotation();
		if(onScreen)
		{
			if(buttons.size() > 0)
			{
				boolean holdScrollUp = (screen.contains(buttons.get(buttons.size() - 1).getX(), buttons.get(buttons.size() - 1).getY() + buttons.get(buttons.size() - 1).getHeight())) && (rotation < 0);
				boolean holdScrollDown = (screen.contains(buttons.get(0).getX(), buttons.get(0).getY()) && (rotation > 0));
				if(holdScrollDown || holdScrollUp)
					return;
			}
			for (int i = 0; i < buttons.size(); i++) {
				buttons.get(i).setY(buttons.get(i).getY() + (rotation * speed));
				buttons.get(i).getText().setY(buttons.get(i).getText().getY() + (rotation * speed));
				buttons.get(i).getBound().setLocation(buttons.get(i).getX(), buttons.get(i).getY());
			}			
		}
	}

}
