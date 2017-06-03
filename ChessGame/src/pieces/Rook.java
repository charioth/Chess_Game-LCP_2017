package pieces;

import java.util.ArrayList;
import java.util.List;

import game.Point;
import game.Square;

public class Rook extends Piece {

	public Rook() {
		super();
		
		//Posições para a torre
		List<Point> rookMovements = new ArrayList<>();
		
		for(int i = 0; i < 8; i++){
			/*
			//Movimentação da torre dependendo da movimentação do enemy
			if(){
				
			}
			*/
			rookMovements.add(new Point(i, 0));
			rookMovements.add(new Point(0, i));
		}
	}

	public Rook(Point actualPosition, PieceInfo type, boolean moved) {
		super(actualPosition, type, moved);
	}

	public void move(List<Point> validMoves, List<Point> validAtack, final Square board[][]) {

	}
}
