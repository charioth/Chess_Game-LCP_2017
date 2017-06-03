package pieces;

import java.util.ArrayList;
import java.util.List;

//Teste de import
import pieces.Piece;
import game.Point;
import game.Square;

public class Pawn extends Piece {

	public Pawn() {
		super();
		
		//Alocação das movimentações
		List<Point> pawnMovements = new ArrayList<Point>();
		//Movimentos dos peões
		pawnMovements.add(new Point(0, 1));	
		
	}

	public Pawn(Point actualPosition, PieceInfo type, boolean moved) {
		super(actualPosition, type, moved);
		
		/*
		//Estado inicial linha -> 2 || coluna -> 1 a 8
		public  void pawMovements(Point actualPosition, ){
			
		}
		*/
		
		//Alocação das movimentações
		List<Point> pawnMovements = new ArrayList<Point>();
		//Movimentos dos peões
		pawnMovements.add(new Point(0, 1));
		
		
	}

	public void move(List<Point> validMoves, List<Point> validAtack, final Square board[][]) {
		
	}
	

		
}
