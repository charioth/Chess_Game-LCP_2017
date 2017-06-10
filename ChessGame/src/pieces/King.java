package pieces;

import java.util.ArrayList;
import java.util.List;

import game.ColorInfo;
import game.Point;
import game.Square;

public class King extends Piece {

	List<Point> kingMovements;
	
	public King() {
		super();
		
		kingMovements = new ArrayList<Point>(8);
		
		kingMovements.add(new Point(1,1));
		kingMovements.add(new Point(1,-1));
		kingMovements.add(new Point(1,0));
		
		kingMovements.add(new Point(-1,1));
		kingMovements.add(new Point(-1,-1));
		kingMovements.add(new Point(-1,0));
		
		kingMovements.add(new Point(0,1));
		kingMovements.add(new Point(0,-1));
		
	}
	
	public King(Point actualPosition, PieceInfo type, boolean moved, ColorInfo color) {
		super(actualPosition, type, moved, color);
		
		kingMovements = new ArrayList<Point>(8);
		
		kingMovements.add(new Point(1,1));
		kingMovements.add(new Point(1,-1));
		kingMovements.add(new Point(1,0));
		
		kingMovements.add(new Point(-1,1));
		kingMovements.add(new Point(-1,-1));
		kingMovements.add(new Point(-1,0));
		
		kingMovements.add(new Point(0,1));
		kingMovements.add(new Point(0,-1));
	}	

	public void move(List<Point> validMoves, List<Point> validAtack, final Square board[][]) {
		
	}

}