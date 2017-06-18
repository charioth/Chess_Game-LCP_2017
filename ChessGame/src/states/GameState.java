package states;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.ColorInfo;
import game.Game;
import game.Movements;
import game.Point;
import game.Square;
import graphics.ButtonAction;
import graphics.UIButton;
import graphics.UIList;
import pieces.PieceInfo;
import pieces.PieceList;
import tempassets.Assets;

public class GameState extends State {
	private ColorInfo actualTurn;
	private Square[][] board = new Square[8][8];
	private PieceList pieceBox[] = new PieceList[2];
	private UIList UIButtonsExit;
	private UIList UIButtonsDraw;
	private boolean exitMenu;
	private boolean draw;
	private boolean exitMsg;
	private BufferedImage exitLogo;

	public GameState(Game game) {
		super(game);
		pieceBox[0] = new PieceList(ColorInfo.WHITE);
		pieceBox[1] = new PieceList(ColorInfo.BLACK);
		actualTurn = ColorInfo.WHITE;
		Movements.initializePieceMovements(); // Pieces movement rules
		initBoard();
		initUIButton();
	}

	@Override
	public void tick() {

		if (State.newGame) {
			State.newGame = false;
			newGame();
		} else if (State.loadGame) {
			State.loadGame = false;
			loadGame();
		}
		
		if (exitMsg) {
			if (game.getMouse().isLeftButtonPressed()) {
				game.getMouse().setLeftButtonPressed(false);
				game.getKeyboard().mESC = false;
				exitMsg = false;
				State.setCurrentState(game.getMenuState());
			}
		} else if (!exitMenu) {
			exitMenu = game.getKeyboard().mESC;
			if (Movements.selectedPiece == null)
				Movements.selectPiece(game.getMouse(), pieceBox, board, actualTurn);
			else if (Movements.isValidMove(game.getMouse())) {
				Movements.movePiece(game.getMouse(), Movements.selectedPiece, board, pieceBox, actualTurn);
				actualTurn = actualTurn == ColorInfo.WHITE ? ColorInfo.BLACK : ColorInfo.WHITE;
			}
		} 

	}

	@Override
	public void render(Graphics graph) {
		graph.drawImage(Assets.background, 0, 0, game.width, game.height, null);
		if (Movements.selectedPiece != null) {
			drawPath(graph);
		}
		drawTable(graph);

		if (exitMenu)
			drawExitMenu(graph);

		if (exitMsg) {
			graph.drawImage(exitLogo, (int)((game.width/2) - (exitLogo.getWidth()/2 * game.scale)), (int)(game.height/2), (int) (exitLogo.getWidth() * game.scale),
					(int) (exitLogo.getHeight() * game.scale), null);
		}
	}

	private void drawPath(Graphics graph) {
		int row = Movements.selectedPiece.getActualPosition().getRow();
		int column = Movements.selectedPiece.getActualPosition().getColumn();
		graph.drawImage(Assets.select_square, board[row][column].renderSquare().x, board[row][column].renderSquare().y,
				Assets.getSquareSize(), Assets.getSquareSize(), null);

		for (Point valid : Movements.validMoves) {
			graph.drawImage(Assets.move_square, board[valid.getRow()][valid.getColumn()].renderSquare().x,
					board[valid.getRow()][valid.getColumn()].renderSquare().y, Assets.getSquareSize(),
					Assets.getSquareSize(), null);
		}
		for (Point valid : Movements.validAttack) {
			graph.drawImage(Assets.attack_square, board[valid.getRow()][valid.getColumn()].renderSquare().x,
					board[valid.getRow()][valid.getColumn()].renderSquare().y, Assets.getSquareSize(),
					Assets.getSquareSize(), null);
		}
	}

	private void drawTable(Graphics graph) {
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 16; i++) {
				PieceInfo type = pieceBox[j].getPieces()[i].getType();
				ColorInfo color = pieceBox[j].getPieces()[i].getColor();
				Point piecePoint = pieceBox[j].getPieces()[i].getActualPosition();

				if (color == null)
					continue;

				if (j == 0)
					graph.drawImage(Assets.whitePiece[type.value],
							board[piecePoint.getRow()][piecePoint.getColumn()].renderSquare().x,
							board[piecePoint.getRow()][piecePoint.getColumn()].renderSquare().y, Assets.getPieceSize(),
							Assets.getPieceSize(), null);
				else
					graph.drawImage(Assets.blackPiece[type.value],
							board[piecePoint.getRow()][piecePoint.getColumn()].renderSquare().x,
							board[piecePoint.getRow()][piecePoint.getColumn()].renderSquare().y, Assets.getPieceSize(),
							Assets.getPieceSize(), null);
			}
		}
	}

	private void drawExitMenu(Graphics graph) {
		graph.drawImage(Assets.gameLogo, 0, (int) (100 * game.scale), Assets.getLogoWidth(), Assets.getLogoHeight(),
				null);
		if (draw)
			graph.drawImage(Assets.acceptDraw, (int) (60 * game.scale), (int) ((530 * game.scale)),
					Assets.acceptDraw.getWidth(), Assets.acceptDraw.getHeight(), null);
		getUIButtons().render(graph);
	}

	@Override
	public UIList getUIButtons() {

		if (!draw) {
			return UIButtonsExit;
		} else {
			return UIButtonsDraw;
		}
	}

	private int move(int line) {
		return Assets.getEdge() + (line * Assets.getMoveDistance());
	}

	private void initUIButton() {
		float buttonWidth = Assets.buttonQuit[0].getWidth() * game.scale;
		float buttonHeight = Assets.buttonQuit[0].getHeight() * game.scale;

		UIButtonsExit = new UIList();
		UIButtonsDraw = new UIList();

		UIButtonsExit.getButtons().add(new UIButton((int) (150 * game.scale), (int) ((550 * game.scale)),
				(int) (buttonWidth), (int) (buttonHeight), Assets.buttonQuit, new ButtonAction() {
					public void action() {
						if (exitMenu) {
							exitMenu = false;
							
							exitMsg = true;
							if (actualTurn == ColorInfo.WHITE)
								exitLogo = Assets.congratz[ColorInfo.BLACK.value];
							else
								exitLogo = Assets.congratz[ColorInfo.WHITE.value];
							
							game.getKeyboard().mESC = false;
						}
					}

				}));
		
		buttonWidth = Assets.buttonSave[0].getWidth() * game.scale;
		buttonHeight = Assets.buttonSave[0].getHeight() * game.scale;

		UIButtonsExit.getButtons()
				.add(new UIButton((int) ((game.width / 2) - (buttonWidth / 2)), (int) ((550 * game.scale)),
						(int) (buttonWidth), (int) (buttonHeight), Assets.buttonSave, new ButtonAction() {
							public void action() {
								// ADICIONAR O SAVE AQUI
								if (exitMenu) {
									exitMenu = false;
									game.getKeyboard().mESC = false;
									State.setCurrentState(game.getMenuState());
								}
							}

						}));
		
		buttonWidth = Assets.buttonDraw[0].getWidth() * game.scale;
		buttonHeight = Assets.buttonDraw[0].getHeight() * game.scale;

		UIButtonsExit.getButtons().add(new UIButton((int) (740 * game.scale), (int) ((550 * game.scale)),
				(int) (buttonWidth), (int) (buttonHeight), Assets.buttonDraw, new ButtonAction() {
					public void action() {
						if (exitMenu) {
							draw = true;
							game.getKeyboard().mESC = false;
						}
					}

				}));

		buttonWidth = Assets.buttonContinue[0].getWidth() * game.scale;
		buttonHeight = Assets.buttonContinue[0].getHeight() * game.scale;

		UIButtonsExit.getButtons()
				.add(new UIButton((int) ((game.width / 2) - (buttonWidth / 2)), (int) ((670 * game.scale)),
						(int) (buttonWidth), (int) (buttonHeight), Assets.buttonContinue, new ButtonAction() {
							public void action() {
								if (exitMenu) {
									exitMenu = false;
									game.getKeyboard().mESC = false;
								}
							}

						}));

		buttonWidth = Assets.buttonYes[0].getWidth() * game.scale;
		buttonHeight = Assets.buttonYes[0].getHeight() * game.scale;

		UIButtonsDraw.getButtons()
				.add(new UIButton((int) (((game.width / 2) - 155) * game.scale), (int) ((670 * game.scale)),
						(int) (buttonWidth), (int) (buttonHeight), Assets.buttonYes, new ButtonAction() {
							public void action() {
								if (exitMenu) {
									exitMsg = true;
									exitMenu = false;
									exitLogo = Assets.draw;
									draw = false;
									game.getKeyboard().mESC = false;
								}
							}

						}));

		buttonWidth = Assets.buttonNo[0].getWidth() * game.scale;
		buttonHeight = Assets.buttonNo[0].getHeight() * game.scale;

		UIButtonsDraw.getButtons()
				.add(new UIButton((int) (((game.width / 2) + 230) * game.scale), (int) ((670 * game.scale)),
						(int) (buttonWidth), (int) (buttonHeight), Assets.buttonNo, new ButtonAction() {
							public void action() {
								if (exitMenu) {
									draw = false;
									exitMenu = false;
									game.getKeyboard().mESC = false;
								}
							}

						}));
	}

	private void newGame() {
		actualTurn = ColorInfo.WHITE;
		pieceBox[0] = new PieceList(ColorInfo.WHITE);
		pieceBox[1] = new PieceList(ColorInfo.BLACK);
		initBoard();
	}

	private void loadGame() {

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
