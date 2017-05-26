package game;

public class Square {
	private Point point;
	private int ID;
	private ColorInfo color;

	public Square() {

	}

	public Square(Point point) {
		this.point = point;
	}

	public Square(Point point, int ID) {
		this.point = point;
		this.ID = ID;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public ColorInfo getColor() {
		return color;
	}

	public void setColor(ColorInfo color) {
		this.color = color;
	}
}
