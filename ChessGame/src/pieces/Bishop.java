package pieces;

import java.util.List;

import game.Point;
import game.Square;

public class Bishop extends Piece {

	public Bishop() {
		super();
	}
		
	public Bishop(Point actualPosition, PieceInfo type, boolean moved) {
		super(actualPosition, type, moved);
	}

	public void move(List<Point> validMoves, List<Point> validAtack, final Square board[][]) {

	}
}
