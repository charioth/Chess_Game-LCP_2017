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
		int side = this.getColor() == ColorInfo.WHITE ? -1 : 1;
		int boundaries = this.getColor() == ColorInfo.WHITE ? -7 : 0;

		if (this.isMoved() == false) { //A pawn that was not moved can be moved 2 spaces forward
			if (board[row + (2 * side)][column].getPieceID() == -1) { // There is no piece in the square
				if (board[row + side][column].getPieceID() == -1) { // There is no piece between the pawn and the square
					validMoves.add(point(row + (2 * side), column));
				}
			}
		}
		
		// Inside boundaries is, depending on the pawn side, > 0 or < 7 (or > -7)
		if (side * this.getActualPosition().getRow() > boundaries) {
			// There is no piece in the square
			if (board[row + side][column].getPieceID() == -1) {
				validMoves.add(point(row + side, column));
			}
			// Pawns capture diagonally
			if (column > 0) {
				// There is a piece to capture
				if (board[row + side][column - 1].getPieceID() != -1 && board[row + side][column - 1].getColor() != this.getColor()) {
					validAttack.add(point(row + side, column - 1));
				}
			}
			// Pawns capture diagonally
			if (column < 7) {
				// There is a piece to capture
				if (board[row + side][column + 1].getPieceID() != -1 && board[row + side][column + 1].getColor() != this.getColor()) {
					validAttack.add(point(row + side, column + 1));
				}
			}
		}
	}
	
	private static Coordinates point(int row, int column) {
		return new Coordinates(row, column);
	}
	
	public void pieceMovements(List<Coordinates> validMoves, List<Coordinates> validAttack, final Square board[][],
			List<List<Coordinates>> possibleMoves) {
		int row, column, i;

		for (List<Coordinates> direction : possibleMoves) { // For each possible direction

			i = 0;
			row = this.actualPosition.getRow() + direction.get(i).getRow();
			column = this.actualPosition.getColumn() + direction.get(i).getColumn();
			
			// While there is movements in that direction and its inside the board
			while (i < direction.size() && row < 8 && column < 8 && row >= 0 && column >= 0) {
				if (board[row][column].getPieceID() == -1) { // If there is no piece, its a valid destination
					validMoves.add(new Coordinates(row, column));
				} else {
					if (board[row][column].getColor().value != this.color.value) { // If there is an enemy piece, its a valid destination
						validAttack.add(new Coordinates(row, column));
					}
					break; // If there is a piece, its not possible to keep going in that direction
				}
				i++;
				if(i < direction.size()) {
					row = this.actualPosition.getRow() + direction.get(i).getRow();
					column = this.actualPosition.getColumn() + direction.get(i).getColumn();
				}
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
