package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Point;
import game.Square;

public class Queen extends Piece {

	List<Point> queenMovements;
	
	public Queen() {
		super();
	}

	public Queen(Point actualPosition, PieceInfo type, boolean moved) {
		super(actualPosition, type, moved);
		
		queenMovements = new ArrayList<Point>(32);
		
		for(int i = 1; i < 8; i++)
		{
			queenMovements.add(new Point(i,i));
			queenMovements.add(new Point(-i,i));
			queenMovements.add(new Point(i,-i));
			queenMovements.add(new Point(-i,-i));
		}
		
	}

	public void move(List<Point> validMoves, List<Point> validAtack, final Square board[][]) {
		
			
		
		
	}
}