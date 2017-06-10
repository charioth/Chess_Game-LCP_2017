package pieces;

import java.util.ArrayList;
import java.util.List;

import game.ColorInfo;
import game.Point;
import game.Square;

public class Bishop extends Piece {

	public Bishop() {
		super();

		// ArrayList para o bispo
		List<Point> bishopMovements = new ArrayList<>();

		// Percorrendo prov�veis movimentos para o bispo
		for (int i = 1; i <= 8; i++) {
			bishopMovements.add(new Point(i, i));
			bishopMovements.add(new Point(-i, -i));
			bishopMovements.add(new Point(-i, i));
			bishopMovements.add(new Point(i, -i));
		}

	}

	public Bishop(Point actualPosition, PieceInfo type, boolean moved, ColorInfo color) {
		super(actualPosition, type, moved, color);

		// ArrayList para o bispo
		List<Point> bishopMovements = new ArrayList<>();

		// Percorrendo prov�veis movimentos para o bispo
		for (int i = 1; i <= 8; i++) {
			bishopMovements.add(new Point(i, i));
			bishopMovements.add(new Point(-i, -i));
		}

	}

	public void move(List<Point> validMoves, List<Point> validAttack, final Square board[][]) {
		boolean isValid = true;
		int row = this.actualPosition.getRow();
		int column = this.actualPosition.getColumn();

		for (int i = 1; row < 8 && column < 8 && isValid;) {
			row += i;
			column += i;
			if (board[row][column].getPieceID() == -1) {
				validMoves.add(new Point(row, column));
			} else {
				if(board[row][column].getColor().value != this.color.value) {
					validAttack.add(new Point(row, column));
				}
				isValid = false;
			}
		}
	}
}
