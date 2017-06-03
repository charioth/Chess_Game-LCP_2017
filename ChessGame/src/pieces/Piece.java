package pieces;

import java.util.List;

import game.Point;
import game.Square;

public abstract class Piece {
	protected Point actualPosition;
	protected PieceInfo type;
	protected boolean moved;

	public Piece() {

	}

	public Piece(Point actualPosition, PieceInfo type, boolean moved) {
		this.actualPosition = actualPosition;
		this.type = type;
		this.moved = moved;
	}

	public abstract void move(List<Point> validMoves, List<Point> validAtack, final Square board[][]);

	public Point getActualPosition() {
		return actualPosition;
	}

	public void setActualPosition(Point actualPosition) {
		this.actualPosition = actualPosition;
	}

	public PieceInfo getType() {
		return type;
	}

	public void setType(PieceInfo type) {
		this.type = type;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
}