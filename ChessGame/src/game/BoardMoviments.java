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

public class BoardMoviments {
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

					// Highlight the possible movements of the piece
					selectedPiece.move(validMoves, validAttack, board,
							possiblePiecesMovements.get(selectedPiece.getType()));

					// if the piece cannot be moved, ignore the selection
					if (validAttack.isEmpty() && validMoves.isEmpty()) {
						selectedPiece = null;
					}
				}
			}
		}
	}

	public static boolean isValidMove(final MouseManager mouse) {
		/* Validates the chosen movement */
		boolean isValid = false;

		if (mouse.isLeftButtonPressed()) {

			mouse.setLeftButtonPressed(false);
			int row = (mouse.getMouseY() - Assets.getEdge()) / Assets.getMoveDistance();
			int column = (mouse.getMouseX() - Assets.getEdge()) / Assets.getMoveDistance();

			// Search the validAttack list for the chosen movement
			if (validAttack.contains(point(row, column))) {
				isValid = true;
			}
			
			// Search the validMoves list for the chosen movement
			if (validMoves.contains(point(row, column))) {
				isValid = true;
			}
			
			// If the movement is valid clear the lists
			if (isValid) {
				validMoves.clear();
				validAttack.clear();
			}
		}
		return isValid;
	}

	public static void movePiece(MouseManager mouse, Piece piece, Square board[][], PieceList[] pieceBox, ColorInfo turn) {
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
		
		// Deselect piece
		selectedPiece = null;
	}

	public static boolean isChecked(final Square board[][]) {
		
		return true; // check method
	}

	public static boolean isCheckmated(final Square board[][]) {
		return true; // checkmate method
	}

	public static void initializePieceMovements() {
		/* Determines the rules of piece movements, each list of a piece is a direction */
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

	private static Coordinates point(int row, int column) {
		return new Coordinates(row, column);
	}

}
