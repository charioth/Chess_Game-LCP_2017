package pieces;

import java.util.List;

import game.ColorInfo;
import game.Movements;
import game.Point;
import game.Square;

public class Piece {
	protected Point actualPosition;
	protected PieceInfo type;
	protected boolean moved;
	protected ColorInfo color;

	public Piece() {

	}

	public Piece(Point actualPosition, PieceInfo type, boolean moved, ColorInfo color) {
		this.actualPosition = actualPosition;
		this.type = type;
		this.moved = moved;
		this.color = color;
	}

	public void move(List<Point> validMoves, List<Point> validAttack, final Square board[][],
			List<List<Point>> possibleMoves, PieceInfo type) {

		if (this.type == PieceInfo.PAWN) {
			Movements.movePawn(validMoves, validAttack, board, this);
		} else {
			this.move(validMoves, validAttack, board, possibleMoves);
		}
	}

	public void move(List<Point> validMoves, List<Point> validAttack, final Square board[][],
			List<List<Point>> possibleMoves) {
		int row, column;

		for (List<Point> possibility : possibleMoves) {

			int i = 0;
			row = this.actualPosition.getRow() + possibility.get(i).getRow();
			column = this.actualPosition.getColumn() + possibility.get(i).getColumn();

			while (i < possibility.size() && row < 8 && column < 8 && row >= 0 && column >= 0) {
				//System.out.println("Row: " + row + " | Col: " + column);
				if (board[row][column].getPieceID() == -1) {
					validMoves.add(new Point(row, column));
				} else {
					if (board[row][column].getColor().value != this.color.value) {
						validAttack.add(new Point(row, column));
					}
					break;
				}
				row = this.actualPosition.getRow() + possibility.get(i).getRow();
				column = this.actualPosition.getColumn() + possibility.get(i).getColumn();
				i++;
			}
		}
	}

	public Point getActualPosition() {
		return actualPosition;
	}

	public void setActualPosition(Point actualPosition) {
		this.actualPosition = actualPosition;
	}

	public PieceInfo getType() {
		return type;
	}

	public void setType(PieceInfo type) {
		this.type = type;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public ColorInfo getColor() {
		return this.color;
	}

	public void setColor(ColorInfo color) {
		this.color = color;
	}
}
