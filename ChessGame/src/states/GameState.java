package states;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.ColorInfo;
import game.Game;
import game.Movements;
import game.Point;
import game.Square;
import graphics.UIList;
import pieces.PieceList;
import tempassets.Assets;

public class GameState extends State {
	private ColorInfo actualTurn;
	private Square[][] board = new Square[8][8];
	private PieceList pieceBox[] = new PieceList[2];

	public GameState(Game game) {
		super(game);
		pieceBox[0] = new PieceList(ColorInfo.WHITE);
		pieceBox[1] = new PieceList(ColorInfo.BLACK);
		actualTurn = ColorInfo.WHITE;
		Movements.initializePieceMovements(); // Pieces movement rules
		initBoard();
	}

	@Override
	public void tick() {
		if(Movements.selectedPiece == null)
			Movements.selectPiece(game.getMouse(), pieceBox, board, actualTurn);
		else
			Movements.isValidMove(game.getMouse());
	}

	@Override
	public void render(Graphics graph) {
		graph.drawImage(Assets.background, 0, 0, game.width, game.height, null);

		if(Movements.selectedPiece != null) {
			int row = Movements.selectedPiece.getActualPosition().getRow();
			int column = Movements.selectedPiece.getActualPosition().getColumn();
			graph.drawImage(Assets.select_square, board[row][column].renderSquare().x,
					board[row][column].renderSquare().y, Assets.getSquareSize(), Assets.getSquareSize(), null);

			for(Point valid : Movements.validMoves) {
				graph.drawImage(Assets.move_square, board[valid.getRow()][valid.getColumn()].renderSquare().x,
						board[valid.getRow()][valid.getColumn()].renderSquare().y, Assets.getSquareSize(), Assets.getSquareSize(), null);
			}
			for(Point valid : Movements.validAttack) {
				graph.drawImage(Assets.attack_square, board[valid.getRow()][valid.getColumn()].renderSquare().x,
						board[valid.getRow()][valid.getColumn()].renderSquare().y, Assets.getSquareSize(), Assets.getSquareSize(), null);
			}
		}
			
		for (int j = 0; j < 2; j++) {
			for (int i = 0, row, column, value; i < 16; i++) {
				value = pieceBox[j].getPieces()[i].getType().value;
				row = pieceBox[j].getPieces()[i].getActualPosition().getRow();
				column = pieceBox[j].getPieces()[i].getActualPosition().getColumn();
				if (j == 0)
					graph.drawImage(Assets.whitePiece[value], board[row][column].renderSquare().x,
							board[row][column].renderSquare().y, Assets.getPieceSize(), Assets.getPieceSize(), null);
				else
					graph.drawImage(Assets.blackPiece[value], board[row][column].renderSquare().x,
							board[row][column].renderSquare().y, Assets.getPieceSize(), Assets.getPieceSize(), null);
			}
		}
	}

	@Override
	public UIList getUIButtons() {
		return null;
	}

	private int move(int line) {
		return Assets.getEdge() + (line * Assets.getMoveDistance());
	}

	private void initBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new Square();
				board[i][j].renderSquare(new Rectangle(move(j), move(i), Assets.getPieceSize(), Assets.getPieceSize()));
			}
		}

		for (int i = 0; i < 2; i++) {
			ColorInfo color = pieceBox[i].getPieces()[0].getColor();
			for (int j = 0, row, column; j < 16; j++) {
				row = pieceBox[i].getPieces()[j].getActualPosition().getRow();
				column = pieceBox[i].getPieces()[j].getActualPosition().getColumn();
				board[row][column].setPieceID(j);
				board[row][column].setColor(color);
			}
		}
	}
}
