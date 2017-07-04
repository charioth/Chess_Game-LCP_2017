package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import input.MouseManager;
import pieces.Piece;
import pieces.PieceInfo;
import pieces.PieceList;
import tempassets.Assets;

public class BoardMovements {
	public static List<Coordinates> validMoves;
	public static List<Coordinates> validAttack;
	public static Map<PieceInfo, List<List<Coordinates>>> possiblePiecesMovements;
	public static Piece selectedPiece = null;

	public static void selectPiece(final MouseManager mouse, final PieceList pieceBox[], final Square board[][],
			ColorInfo turn) {
		/* Select a piece using the mouse input */
		int row = (mouse.getMouseY() - Assets.getEdge()) / Assets.getMoveDistance();
		int column = (mouse.getMouseX() - Assets.getEdge()) / Assets.getMoveDistance();

		if (mouse.isLeftButtonPressed()) {
			if (row >= 8 || column >= 8) // See if mouse is out of bounds
				return;

			if (board[row][column].getRenderSquare().contains(mouse.getMouseX(), mouse.getMouseY())) {
				mouse.setLeftButtonPressed(false);
				int pieceID = board[row][column].getPieceID();

				if ((pieceID >= 0) && (board[row][column].getColor() == turn)) {
					/*
					 * Check if there is a piece in the square and if it belongs
					 * to the current turn player
					 */

					// Select the piece
					selectedPiece = pieceBox[turn.value].getPieces()[pieceID];

					// Generate the possible movements of the piece
					selectedPiece.move(validMoves, validAttack, board,
							possiblePiecesMovements.get(selectedPiece.getType()), pieceBox);

					// if the piece cannot be moved, ignore the selection
					if (validAttack.isEmpty() && validMoves.isEmpty()) {
						selectedPiece = null;
					}
				}
			}
		}
	}

	public static boolean isValidMove(final MouseManager mouse) {
		/* Checks if the chosen movement is valid for the piece */
		if (mouse.isLeftButtonPressed()) {

			mouse.setLeftButtonPressed(false);
			int row = (mouse.getMouseY() - Assets.getEdge()) / Assets.getMoveDistance();
			int column = (mouse.getMouseX() - Assets.getEdge()) / Assets.getMoveDistance();

			// Search the validAttack and validMoves list for the chosen
			// movement
			if (validAttack.contains(point(row, column)) || validMoves.contains(point(row, column))) {
				validMoves.clear();
				validAttack.clear();
				return true;
			}
		}
		return false;
	}

	public static void movePiece(MouseManager mouse, Piece piece, Square board[][], PieceList[] pieceBox,
			ColorInfo turn) {
		/* Move the selected piece */
		int adversaryColor = (turn.value == ColorInfo.WHITE.value ? ColorInfo.BLACK.value : ColorInfo.WHITE.value);
		ColorInfo pieceColor = board[piece.getActualPosition().getRow()][piece.getActualPosition().getColumn()].getColor(); // Get color of selectedPiece
		int pieceID = board[piece.getActualPosition().getRow()][piece.getActualPosition().getColumn()].getPieceID();
		int row = (mouse.getMouseY() - Assets.getEdge()) / Assets.getMoveDistance();
		int column = (mouse.getMouseX() - Assets.getEdge()) / Assets.getMoveDistance();

		// Update the board parameters of old position
		board[piece.getActualPosition().getRow()][piece.getActualPosition().getColumn()].setPieceID(-1);
		board[piece.getActualPosition().getRow()][piece.getActualPosition().getColumn()].setColor(null);

		// Capture enemy piece
		if ((board[row][column].getPieceID() >= 0)) {
			// Erase from pieceBox
			pieceBox[adversaryColor].getPieces()[board[row][column].getPieceID()].setType(PieceInfo.DEAD);
		}
		piece.setMoved(true);
		piece.setActualPosition(point(row, column));

		// Update the board parameters of new position
		board[row][column].setPieceID(pieceID);
		board[row][column].setColor(pieceColor);
	}

	private static void fakeMove(Piece piece, Square copy[][], ColorInfo pieceColor, int row, int column) {
		/* fake a movement in a copy of the board to assess later*/
		copy[piece.getActualPosition().getRow()][piece.getActualPosition().getColumn()].setPieceID(-1);
		copy[piece.getActualPosition().getRow()][piece.getActualPosition().getColumn()].setColor(null);

		copy[row][column].setPieceID(piece.getIndex());
		copy[row][column].setColor(pieceColor);
	}

	private static boolean checkKing(Piece piece, final Square board[][], List<List<Coordinates>> possibleMoves) {
		/* See if a piece can reach the enemy king, if yes returns true */
		int row, column;

		if (piece.getType() == PieceInfo.PAWN) {
			row = piece.getActualPosition().getRow();
			column = piece.getActualPosition().getColumn();
			int side = piece.getColor() == ColorInfo.WHITE ? -1 : 1;
			int boundaries = piece.getColor() == ColorInfo.WHITE ? -7 : 0;

			// Inside boundaries is, depending on the pawn side, > 0 or < 7 (or
			// > -7)
			if (side * piece.getActualPosition().getRow() > boundaries) {
				// Pawns capture diagonally
				if (column > 0) {
					// There is a piece to capture
					if (board[row + side][column - 1].getPieceID() == PieceInfo.KING.value
							&& board[row + side][column - 1].getColor() != piece.getColor()) {
						return true;
					}
				}
				// Pawns capture diagonally
				if (column < 7) {
					// There is a piece to capture
					if (board[row + side][column + 1].getPieceID() == PieceInfo.KING.value
							&& board[row + side][column + 1].getColor() != piece.getColor()) {
						return true;
					}
				}
			}
		} else {
			int i;
			for (List<Coordinates> direction : possibleMoves) { // For each possible direction

				i = 0;
				row = piece.getActualPosition().getRow() + direction.get(i).getRow();
				column = piece.getActualPosition().getColumn() + direction.get(i).getColumn();

				// While there is movements in that direction and its inside the board
				while (i < direction.size() && row < 8 && column < 8 && row >= 0 && column >= 0) {
					if (board[row][column].getPieceID() == PieceInfo.KING.value
							&& board[row][column].getColor().value != piece.getColor().value) { // Found a king of opposite color
						return true;
					} else if (board[row][column].getPieceID() != -1) // Cannot move further in that direction if a piece was found
						break;

					i++;
					if (i < direction.size()) {
						row = piece.getActualPosition().getRow() + direction.get(i).getRow();
						column = piece.getActualPosition().getColumn() + direction.get(i).getColumn();
					}
				}
			}
		}

		return false;
	}

	private static boolean isChecked(final Square board[][], ColorInfo turn, PieceList[] pieceBox) {
		/* For each piece of opposite color see if it can reach the king */
		int enemyTurn = (turn.value == ColorInfo.WHITE.value ? ColorInfo.BLACK.value : ColorInfo.WHITE.value);

		for (Piece testCheck : pieceBox[enemyTurn].getPieces()) {
			if (testCheck.getType() == PieceInfo.DEAD)
				continue; // Next piece if this one is dead
			if (checkKing(testCheck, board, possiblePiecesMovements.get(testCheck.getType()))) {
				return true; // A piece threaten the king
			}
		}
		return false; // No piece threaten the king
	}

	public static void validateAttack(final Square board[][], Piece piece, ColorInfo pieceColor, PieceList[] pieceBox,
			List<Coordinates> validList, int row, int column) {
		/* Mark the piece as dead to validate the movement */
		PieceInfo temp = pieceBox[board[row][column].getColor().value].getPieces()[board[row][column].getPieceID()].getType();
		pieceBox[board[row][column].getColor().value].getPieces()[board[row][column].getPieceID()].setType(PieceInfo.DEAD);

		validateMovement(board, piece, pieceColor, pieceBox, validList, row, column);

		/* Put the piece*/
		pieceBox[board[row][column].getColor().value].getPieces()[board[row][column].getPieceID()].setType(temp);
	}

	public static void validateMovement(final Square board[][], Piece piece, ColorInfo pieceColor, PieceList[] pieceBox,
			List<Coordinates> validList, int row, int column) {
		/* Copy the board and validates the movement, seeing if the king is left checked*/
		Square copy[][];
		copy = copyBoard(board);
		fakeMove(piece, copy, pieceColor, row, column);
		if (!isChecked(copy, pieceColor, pieceBox)) { // If the king is not left checked the movement is valid
			validList.add(point(row, column));
		}
	}
	
	public static boolean isStaleMate(final Square board[][], PieceList[] pieceBox, ColorInfo turn) {
		List<Coordinates> testStalemate = new ArrayList<Coordinates>();

		for(Piece piece : pieceBox[turn.value].getPieces()) {
			if(piece.getType() == PieceInfo.DEAD) continue;
			piece.move(testStalemate, testStalemate, board, possiblePiecesMovements.get(piece.getType()), pieceBox);
			if(!testStalemate.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isCheckmate(final Square board[][], ColorInfo turn, PieceList[] pieceBox) {
		Piece king = pieceBox[turn.value].getPieces()[0];
		List<Coordinates> testCheckmate = new ArrayList<Coordinates>();
		
		king.move(testCheckmate, testCheckmate, board, possiblePiecesMovements.get(king.getType()), pieceBox);
		if(testCheckmate.isEmpty() && isStaleMate(board, pieceBox, turn)) {
			if(isChecked(board, turn, pieceBox)) {
				return true;
			}
		}
		return false;
	}

	public static boolean promotePawn(Piece movedPawn) {
		boolean blackPiecePromote = ((movedPawn.getType() == PieceInfo.PAWN)
				&& (movedPawn.getColor() == ColorInfo.BLACK) && (movedPawn.getActualPosition().getRow() == 7));
		boolean whitePiecePromote = ((movedPawn.getType() == PieceInfo.PAWN)
				&& (movedPawn.getColor() == ColorInfo.WHITE) && (movedPawn.getActualPosition().getRow() == 0));
		return (blackPiecePromote || whitePiecePromote);
	}

	public static void initializePieceMovements() {
		/*
		 * Determines the rules of piece movements, each list of a piece is a
		 * direction
		 */
		validMoves = new ArrayList<Coordinates>();
		validAttack = new ArrayList<Coordinates>();
		possiblePiecesMovements = new HashMap<PieceInfo, List<List<Coordinates>>>();

		List<List<Coordinates>> queenMovements = new ArrayList<List<Coordinates>>();
		List<List<Coordinates>> bishopMovements = new ArrayList<List<Coordinates>>();
		List<List<Coordinates>> rookMovements = new ArrayList<List<Coordinates>>();
		List<List<Coordinates>> kingMovements = new ArrayList<List<Coordinates>>();
		List<List<Coordinates>> knightMovements = new ArrayList<List<Coordinates>>();

		for (int i = 0; i < 8; i++)
			queenMovements.add(new ArrayList<>());

		// All possible queen movements
		for (int i = 1; i <= 8; i++) {
			queenMovements.get(0).add(point(i, 0)); // up
			queenMovements.get(1).add(point(-i, 0));// down
			queenMovements.get(2).add(point(0, i)); // right
			queenMovements.get(3).add(point(0, -i));// left
			queenMovements.get(4).add(point(i, i)); // diagonal up right
			queenMovements.get(5).add(point(i, -i));// diagonal down right
			queenMovements.get(6).add(point(-i, i));// diagonal up left
			queenMovements.get(7).add(point(-i, -i));// diagonal down left
		}

		// Rook and Bishop use parts of queen movements
		for (int i = 0; i < 4; i++) {
			rookMovements.add(queenMovements.get(i));
			bishopMovements.add(queenMovements.get(i + 4));
		}

		// King movements
		kingMovements.add(Arrays.asList(point(1, 0)));
		kingMovements.add(Arrays.asList(point(1, 1)));
		kingMovements.add(Arrays.asList(point(0, 1)));
		kingMovements.add(Arrays.asList(point(1, -1)));
		kingMovements.add(Arrays.asList(point(-1, 0)));
		kingMovements.add(Arrays.asList(point(-1, 1)));
		kingMovements.add(Arrays.asList(point(0, -1)));
		kingMovements.add(Arrays.asList(point(-1, -1)));

		// Knight movements
		knightMovements.add(Arrays.asList(point(1, 2)));
		knightMovements.add(Arrays.asList(point(2, 1)));
		knightMovements.add(Arrays.asList(point(-1, 2)));
		knightMovements.add(Arrays.asList(point(-2, 1)));
		knightMovements.add(Arrays.asList(point(1, -2)));
		knightMovements.add(Arrays.asList(point(2, -1)));
		knightMovements.add(Arrays.asList(point(-1, -2)));
		knightMovements.add(Arrays.asList(point(-2, -1)));

		possiblePiecesMovements = new HashMap<>();
		possiblePiecesMovements.put(PieceInfo.KING, kingMovements);
		possiblePiecesMovements.put(PieceInfo.QUEEN, queenMovements);
		possiblePiecesMovements.put(PieceInfo.ROOK, rookMovements);
		possiblePiecesMovements.put(PieceInfo.BISHOP, bishopMovements);
		possiblePiecesMovements.put(PieceInfo.KNIGHT, knightMovements);
	}

	public static Square[][] copyBoard(final Square[][] board) {
		Square copy[][] = new Square[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				copy[i][j] = new Square(board[i][j]);
			}
		}
		return copy;
	}

	private static Coordinates point(int row, int column) {
		return new Coordinates(row, column);
	}

}
