package game;

import java.awt.Rectangle;

public class Square {
	private Rectangle renderSquare;
	private int pieceID;
	private ColorInfo color;

	public Square() {
		renderSquare = null;
		pieceID = -1;
		color = null;
	}

	public Square(Rectangle renderPosition) {
		this.renderSquare = renderPosition;
		pieceID = -1;
	}

	public Square(Rectangle renderPosition, int ID) {
		this.renderSquare = renderPosition;
		this.pieceID = ID;
	}

	public Rectangle renderSquare() {
		return renderSquare;
	}

	public void renderSquare(Rectangle renderPosition) {
		this.renderSquare = renderPosition;
	}

	public int getPieceID() {
		return pieceID;
	}

	public void setPieceID(int ID) {
		this.pieceID = ID;
	}

	public ColorInfo getColor() {
		return color;
	}

	public void setColor(ColorInfo color) {
		this.color = color;
	}
}
