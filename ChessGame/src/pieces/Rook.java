package pieces;

import java.util.ArrayList;
import java.util.List;

import game.ColorInfo;
import game.Point;
import game.Square;

public class Rook extends Piece {

	public Rook() {
		super();
		
		//Posi��es para a torre
		List<Point> rookMovements = new ArrayList<>();
		
		for(int i = 0; i < 8; i++){
			/*
			//Movimenta��o da torre dependendo da movimenta��o do enemy
			if(){
				
			}
			*/
			rookMovements.add(new Point(i, 0));
			rookMovements.add(new Point(0, i));
		}
	}

	public Rook(Point actualPosition, PieceInfo type, boolean moved, ColorInfo color) {
		super(actualPosition, type, moved, color);
	}

	public void move(List<Point> validMoves, List<Point> validAtack, final Square board[][]) {

	}
}
