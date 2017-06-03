package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Point;
import game.Square;

public class Bishop extends Piece {

	public Bishop() {
		super();
		
		//ArrayList para o bispo
		List<Point> bishopMovements = new ArrayList<>();
		
		//Percorrendo prováveis movimentos para o bispo
			for(int i = 1; i <= 8; i++){
				bishopMovements.add(new Point(i, i));
				bishopMovements.add(new Point(-i, -i));
				bishopMovements.add(new Point(-i, i));
				bishopMovements.add(new Point(i, -i));
			}
		
	}
		
	public Bishop(Point actualPosition, PieceInfo type, boolean moved) {
		super(actualPosition, type, moved);
		
		//ArrayList para o bispo
		List<Point> bishopMovements = new ArrayList<>();
		
		//Percorrendo prováveis movimentos para o bispo
			for(int i = 1; i <= 8; i++){
				bishopMovements.add(new Point(i, i));
				bishopMovements.add(new Point(-i, -i));
			}
		
	}

	public void move(List<Point> validMoves, List<Point> validAtack, final Square board[][]) {

	}
}
