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

public class Movements {
	public static List<Point> validMoves;
	public static List<Point> validAttack;
	public static Map<PieceInfo, List<List<Point>>> possiblePiecesMovements;
	public static Piece selectedPiece = null;

	public static void selectPiece(final MouseManager mouse, final PieceList pieceBox[], final Square board[][],
			ColorInfo turn) {

		int row = (mouse.getMouseY() - Assets.getEdge()) / Assets.getMoveDistance();
		int column = (mouse.getMouseX() - Assets.getEdge()) / Assets.getMoveDistance();
		if (mouse.isLeftButtonPressed()) {
			if (row >= 8 || column >= 8)
				return;

			if (board[row][column].renderSquare().contains(mouse.getMouseX(), mouse.getMouseY())) {
				mouse.setLeftButtonPressed(false);
				int pieceID = board[row][column].getPieceID();
				if ((pieceID >= 0) && (board[row][column].getColor() == turn)) {
					selectedPiece = pieceBox[turn.value].getPieces()[pieceID];
					selectedPiece.move(validMoves, validAttack, board,
							possiblePiecesMovements.get(selectedPiece.getType()), selectedPiece.getType());
				}
			}
		}
	}

	public static boolean isValidMove(final MouseManager mouse) {
		if (mouse.isLeftButtonPressed()) {
			int row = (mouse.getMouseY() - Assets.getEdge()) / Assets.getMoveDistance();
			int column = (mouse.getMouseX() - Assets.getEdge()) / Assets.getMoveDistance();
			for (Point valid : validAttack) {
				if ((valid.getRow() == row) && (valid.getColumn() == column)) {
					return true;
				}
			}
			selectedPiece = null;
			validMoves.clear();
			validAttack.clear();
		}
		return false;
	}

	public static void movePawn(List<Point> validMoves, List<Point> validAttack, final Square board[][], Piece pawn) {

		int row = pawn.getActualPosition().getRow(), column = pawn.getActualPosition().getColumn();
		int offset = pawn.getColor() == ColorInfo.WHITE ? -1 : 1;
		int boundaries = pawn.getColor() == ColorInfo.WHITE ? -7 : 0;

		if (pawn.isMoved() == false) {
			if (board[row + (2 * offset)][column].getPieceID() == -1) {
				validMoves.add(point(row + (2 * offset), column));
			}
		}

		if (offset * pawn.getActualPosition().getRow() > boundaries) {
			if (board[row + offset][column].getPieceID() == -1) {
				validMoves.add(point(row + offset, column));
			}
			if (column > 0) {
				if (board[row + offset][column - 1].getPieceID() != -1 && board[row + offset][column - 1].getColor() != pawn.getColor()) {
					validAttack.add(point(row + offset, column - 1));
				}
			}
			if (column < 7) {
				if (board[row + offset][column + 1].getPieceID() != -1 && board[row + offset][column + 1].getColor() != pawn.getColor()) {
					validAttack.add(point(row + offset, column + 1));
				}
			}
		}
	}
	
	public static void movePiece(Piece piece, Square board[][], Point selectedMove) {

	}

	public static boolean isChecked(final Square board[][]) {
		return true; // Ta aqui s� pra tirar o erro de falta de return
	}

	public static boolean isCheckmated(final Square board[][]) {
		return true; // Ta aqui s� pra tirar o erro de falta de return
	}

	public static void initializePieceMovements() {

		validMoves = new ArrayList<Point>();
		validAttack = new ArrayList<Point>();
		possiblePiecesMovements = new HashMap<PieceInfo, List<List<Point>>>();

		List<List<Point>> queenMovements = new ArrayList<List<Point>>();
		List<List<Point>> bishopMovements = new ArrayList<List<Point>>();
		List<List<Point>> rookMovements = new ArrayList<List<Point>>();
		List<List<Point>> kingMovements = new ArrayList<List<Point>>();
		List<List<Point>> knightMovements = new ArrayList<List<Point>>();

		for (int i = 0; i < 8; i++)
			queenMovements.add(new ArrayList<>());

		for (int i = 1; i < 8; i++) {
			queenMovements.get(0).add(point(i, 0)); // up
			queenMovements.get(1).add(point(-i, 0));// down
			queenMovements.get(2).add(point(0, i)); // right
			queenMovements.get(3).add(point(0, -i));// left
			queenMovements.get(4).add(point(i, i)); // diagonal up right
			queenMovements.get(5).add(point(i, -i));// diagonal down right
			queenMovements.get(6).add(point(-i, i));// diagonal up left
			queenMovements.get(7).add(point(-i, -i));// diagonal down left
		}

		for (int i = 0; i < 4; i++) {
			rookMovements.add(queenMovements.get(i));
			bishopMovements.add(queenMovements.get(i + 4));
		}

		kingMovements.add(Arrays.asList(point(1, 0)));
		kingMovements.add(Arrays.asList(point(1, 1)));
		kingMovements.add(Arrays.asList(point(0, 1)));
		kingMovements.add(Arrays.asList(point(1, -1)));
		kingMovements.add(Arrays.asList(point(-1, 0)));
		kingMovements.add(Arrays.asList(point(-1, 1)));
		kingMovements.add(Arrays.asList(point(0, -1)));
		kingMovements.add(Arrays.asList(point(-1, -1)));

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
		//possiblePiecesMovements.put(PieceInfo.PAWN, Arrays.asList(Arrays.asList(point(1, 0))));

		/*System.out.println("KING: " + kingMovements);
		System.out.println("KNIGHT: " + knightMovements);
		System.out.println("Rook: " + rookMovements);
		System.out.println("Queen: " + queenMovements);
		System.out.println("Bishop: " + bishopMovements);*/

	}

	private static Point point(int row, int column) {
		return new Point(row, column);
	}

}
