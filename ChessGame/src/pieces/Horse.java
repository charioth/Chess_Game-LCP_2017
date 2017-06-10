package pieces;

import java.util.ArrayList;
import java.util.List;

import game.ColorInfo;
import game.Point;
import game.Square;

public class Horse extends Piece {

	List<Point> horseMovements;
	
	public Horse() {
		super();
		
		horseMovements = new ArrayList<Point>(8);
		
		horseMovements.add(new Point(1,2));
		horseMovements.add(new Point(2,1));
		
		horseMovements.add(new Point(-1,2));
		horseMovements.add(new Point(-2,1));
		
		horseMovements.add(new Point(1,-2));
		horseMovements.add(new Point(2,-1));
				
		horseMovements.add(new Point(-1,-2));
		horseMovements.add(new Point(-2,-1));
	}

	public Horse(Point actualPosition, PieceInfo type, boolean moved, ColorInfo color) {
		super(actualPosition, type, moved, color);
		
		horseMovements = new ArrayList<Point>(8);
		
		horseMovements.add(new Point(1,2));
		horseMovements.add(new Point(2,1));
		
		horseMovements.add(new Point(-1,2));
		horseMovements.add(new Point(-2,1));
		
		horseMovements.add(new Point(1,-2));
		horseMovements.add(new Point(2,-1));
				
		horseMovements.add(new Point(-1,-2));
		horseMovements.add(new Point(-2,-1));
				
	}

	public void move(List<Point> validMoves, List<Point> validAtack, final Square board[][]) {

	
	}
}