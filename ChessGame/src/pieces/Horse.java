package pieces;

import java.util.List;

import game.Point;
import game.Square;

public class Horse extends Piece {

	public Horse() {
		super();
	}

	public Horse(Point actualPosition, PieceInfo type, boolean moved) {
		super(actualPosition, type, moved);
	}

	public void move(List<Point> validMoves, List<Point> validAtack, final Square board[][]) {

	}
}