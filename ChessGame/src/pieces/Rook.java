package pieces;

import java.util.List;

import game.Point;
import game.Square;

public class Rook extends Piece {

	public Rook() {
		super();
	}

	public Rook(Point actualPosition, PieceInfo type, boolean moved) {
		super(actualPosition, type, moved);
	}

	public void move(List<Point> validMoves, List<Point> validAtack, final Square board[][]) {

	}
}
