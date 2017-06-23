package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Text {
	private String message;
	private Font font;
	private Color color;
	private int x, y;

	public Text(String message, Font font, Color color, int x, int y) {
		this.message = message;
		this.font = font;
		this.color = color;
		this.x = x;
		this.y = y;
	}

	public void render(Graphics graph) {
		graph.setColor(color);
		graph.setFont(font);
		graph.drawString(message, x, y);
	}
}
