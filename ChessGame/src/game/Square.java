package game;

import java.awt.Rectangle;

public class Square {
	/* Represents each square of the board */
	private Rectangle renderSquare; // Position to be rendered in the screen
	private int pieceID; // -1 if there is no piece in the square
	private ColorInfo color; // null if there is no piece in the square

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
