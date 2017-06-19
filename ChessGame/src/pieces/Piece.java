package pieces;

import java.util.List;

import game.ColorInfo;
import game.BoardMoviments;
import game.Coordinates;
import game.Square;

public class Piece {
	protected Coordinates actualPosition;
	protected PieceInfo type;
	protected boolean moved;
	protected ColorInfo color;

	public Piece() {

	}

	public Piece(Coordinates actualPosition, PieceInfo type, boolean moved, ColorInfo color) {
		this.actualPosition = actualPosition;
		this.type = type;
		this.moved = moved;
		this.color = color;
	}

	public void move(List<Coordinates> validMoves, List<Coordinates> validAttack, final Square board[][],
			List<List<Coordinates>> possibleMoves) {
		/* Choose a movement method based on piece type */
		if (this.type == PieceInfo.PAWN) {
			this.pawnMovements(validMoves, validAttack, board);
		} else {
			this.pieceMovements(validMoves, validAttack, board, possibleMoves);
		}
	}

	public void pawnMovements(List<Coordinates> validMoves, List<Coordinates> validAttack, final Square board[][]) {

		int row = this.getActualPosition().getRow(), column = this.getActualPosition().getColumn();
		int offset = this.getColor() == ColorInfo.WHITE ? -1 : 1;
		int boundaries = this.getColor() == ColorInfo.WHITE ? -7 : 0;

		if (this.isMoved() == false) {
			if (board[row + (2 * offset)][column].getPieceID() == -1) {
				validMoves.add(point(row + (2 * offset), column));
			}
		}

		if (offset * this.getActualPosition().getRow() > boundaries) {
			if (board[row + offset][column].getPieceID() == -1) {
				validMoves.add(point(row + offset, column));
			}
			if (column > 0) {
				if (board[row + offset][column - 1].getPieceID() != -1 && board[row + offset][column - 1].getColor() != this.getColor()) {
					validAttack.add(point(row + offset, column - 1));
				}
			}
			if (column < 7) {
				if (board[row + offset][column + 1].getPieceID() != -1 && board[row + offset][column + 1].getColor() != this.getColor()) {
					validAttack.add(point(row + offset, column + 1));
				}
			}
		}
	}
	
	private static Coordinates point(int row, int column) {
		return new Coordinates(row, column);
	}
	
	public void pieceMovements(List<Coordinates> validMoves, List<Coordinates> validAttack, final Square board[][],
			List<List<Coordinates>> possibleMoves) {
		int row, column;

		for (List<Coordinates> possibility : possibleMoves) {

			int i = 0;
			row = this.actualPosition.getRow() + possibility.get(i).getRow();
			column = this.actualPosition.getColumn() + possibility.get(i).getColumn();

			while (i < possibility.size() && row < 8 && column < 8 && row >= 0 && column >= 0) {
				//System.out.println("Row: " + row + " | Col: " + column);
				if (board[row][column].getPieceID() == -1) {
					validMoves.add(new Coordinates(row, column));
				} else {
					if (board[row][column].getColor().value != this.color.value) {
						validAttack.add(new Coordinates(row, column));
					}
					break;
				}
				row = this.actualPosition.getRow() + possibility.get(i).getRow();
				column = this.actualPosition.getColumn() + possibility.get(i).getColumn();
				i++;
			}
		}
	}

	public Coordinates getActualPosition() {
		return actualPosition;
	}

	public void setActualPosition(Coordinates actualPosition) {
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
