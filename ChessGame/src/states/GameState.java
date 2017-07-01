package states;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import DAO.LoadGame;
import DAO.SaveGame;
import game.ColorInfo;
import game.Game;
import game.BoardMoviments;
import game.Coordinates;
import game.Square;
import graphics.ButtonAction;
import graphics.UIButton;
import graphics.UIList;
import graphics.UIScrollScreen;
import pieces.PieceInfo;
import pieces.PieceList;
import tempassets.Assets;

public class GameState extends State {
	/* Game screen state it is the game screen where all the movements and action occurs*/
	
	//Game logic
	private ColorInfo actualTurn; //Turn of the actual player, since there is only two color than a color is what is need to control the turn
	private Square[][] board = new Square[8][8]; // Table of the game
	private PieceList pieceBox[] = new PieceList[2]; //PieceBox has the a array list with all the white pieces and other with the black pieces
	
	//Screen control
	private UIList subMenuButtons;
	private UIList drawButtons;
	private boolean subMenu; //if true it show on the screen the exit menu (open with ESC key)
	private boolean drawOption; // if the person select the draw option active to change the buttons
	private boolean endGame; // Used to render the image
	private boolean promoteMenu;
	// Depending of the ending this image change (possible screens white victory, black victory and draw messages)
	private BufferedImage winnerMessage;
	private List<Rectangle> promotionChoices;

	public GameState(Game game) {
		super(game);
		newGame();
		BoardMoviments.initializePieceMovements(); // Pieces movement rules
		initPromotionSquares();
		initSubMenuButtons();
		initDrawMenuButtons();
	}

	@Override
	public UIScrollScreen getScreen() {
		return null;
	}
	
	@Override
	public void tick() {
		/*tick method that runs 60 times per second*/
		
		if (State.newGame) { //If newGame boolean attribute is set true than it means that on the menu state, the new game button was pressed
			newGame(); //initialize a new game table (all piece position at the start condition)
		} else if (State.loadGame) { //if the loadGame was set than on the menu state the load button was pressed, so load the game that was been played
			loadGame(State.savedGames.get(lastButtonIndex)); // load the game
		}
		
		if(promoteMenu)
		{
			if (game.getMouse().isLeftButtonPressed()) { //Wait left mouse button click
				int counter = 1;
				for(Rectangle position : promotionChoices)
				{
					if(position.contains(game.getMouse().getMouseX(), game.getMouse().getMouseY()))
					{
						BoardMoviments.selectedPiece.setType(PieceInfo.values()[counter]);
						BoardMoviments.selectedPiece = null;
						actualTurn = actualTurn == ColorInfo.WHITE ? ColorInfo.BLACK : ColorInfo.WHITE; // Change turn
						promoteMenu = false;
					}
					counter++;
				}
			}
			return;
		}
		
		if (endGame) { //If endGame is true than it waits util left mouse button is clicked to change back to menu state
			if (game.getMouse().isLeftButtonPressed()) { //Wait left mouse button click
				game.getMouse().setLeftButtonPressed(false);
				game.getKeyboard().mESC = false;
				endGame = false;
				State.setCurrentState(game.getMenuState()); //Change state
			}
		} else if (!subMenu) { //If submenu is false then the game is been played
			subMenu = game.getKeyboard().mESC; // If the ESC key was pressed then active the subMenu
			if (BoardMoviments.selectedPiece == null) // If there is not a piece selected
				BoardMoviments.selectPiece(game.getMouse(), pieceBox, board, actualTurn); //executes the function that select a piece
			else if (BoardMoviments.isValidMove(game.getMouse())) { // if there is a piece selected then wait until the player click on a valid position
				BoardMoviments.movePiece(game.getMouse(), BoardMoviments.selectedPiece, board, pieceBox, actualTurn);
				if(!(promoteMenu = BoardMoviments.promotePawn(BoardMoviments.selectedPiece))) { // if promote not true than chance turn
					System.out.println(promoteMenu);
					BoardMoviments.selectedPiece = null; //Deselect piece
					actualTurn = actualTurn == ColorInfo.WHITE ? ColorInfo.BLACK : ColorInfo.WHITE; // Change turn
				}
			}
		} 
	}

	@Override
	public void render(Graphics graph) {
		/*Method responsible to render the game*/
		
		graph.drawImage(Assets.background, 0, 0, game.width, game.height, null); //Draw the background
		
		if (BoardMoviments.selectedPiece != null) { // if there is a piece selected then draw the highlight positions
			renderHighlightPath(graph);
		}
		renderPieces(graph); // Draw all the pieces

		if(promoteMenu)
		{
			renderPromoteChoices(graph);
		}
		
		if (subMenu)
			renderSubMenu(graph); // Draw the subMenu if esc key was pressed

		if (endGame) { // Draw the winner msg
			graph.drawImage(winnerMessage, 0, 0, (int) (winnerMessage.getWidth() * game.scale),
					(int) (winnerMessage.getHeight() * game.scale), null);
		}
	}

	private void renderPromoteChoices(Graphics graph){
		int i  = 1;
		for(Rectangle position : promotionChoices)
		{
			graph.drawImage(Assets.promote_square, (int)position.getX(), (int)position.getY(), (int)position.getWidth(), (int)position.getHeight(), null);
			if(actualTurn == ColorInfo.WHITE)
			{				
				graph.drawImage(Assets.whitePiece[i], (int)position.getX(), (int)position.getY(), (int)position.getWidth(), (int)position.getHeight(), null);
			}
			else
			{
				graph.drawImage(Assets.blackPiece[i], (int)position.getX(), (int)position.getY(), (int)position.getWidth(), (int)position.getHeight(), null);
			}
			i++;
		}
	}
	
	private void renderHighlightPath(Graphics graph) {
		/*Method that highlight valid, attack and selected positions*/
		
		//Get selected piece position to hightlight the purple square on the same spot
		int row = BoardMoviments.selectedPiece.getActualPosition().getRow();
		int column = BoardMoviments.selectedPiece.getActualPosition().getColumn();
		
		//Draw purple square
		graph.drawImage(Assets.select_square, board[row][column].getRenderSquare().x, board[row][column].getRenderSquare().y,
				Assets.getSquareSize(), Assets.getSquareSize(), null);

		//For every valid position draw a blue square on the position
		for (Coordinates valid : BoardMoviments.validMoves) {
			graph.drawImage(Assets.move_square, board[valid.getRow()][valid.getColumn()].getRenderSquare().x,
					board[valid.getRow()][valid.getColumn()].getRenderSquare().y, Assets.getSquareSize(),
					Assets.getSquareSize(), null);
		}
		
		//For every valid attack position draw a red square on the position
		for (Coordinates valid : BoardMoviments.validAttack) {
			graph.drawImage(Assets.attack_square, board[valid.getRow()][valid.getColumn()].getRenderSquare().x,
					board[valid.getRow()][valid.getColumn()].getRenderSquare().y, Assets.getSquareSize(),
					Assets.getSquareSize(), null);
		}
	}

	private void renderPieces(Graphics graph) {
		/*Method that draw all pieces on the screen*/
	
		for (int j = 0; j < 2; j++) { // j = 0 Draw white pieces, j = 1 white pieces
			
			for (int i = 0; i < 16; i++) { //Draw all pieces on the pieceBox
				//Get pieces info to draw
				PieceInfo type = pieceBox[j].getPieces()[i].getType();
				Coordinates piecePoint = pieceBox[j].getPieces()[i].getActualPosition();

				//If the color is null than this piece is death so continue
				if (type == PieceInfo.DEAD)
					continue;

				if (j == 0) //Draw a white piece
					graph.drawImage(Assets.whitePiece[type.value],
							board[piecePoint.getRow()][piecePoint.getColumn()].getRenderSquare().x,
							board[piecePoint.getRow()][piecePoint.getColumn()].getRenderSquare().y, Assets.getPieceSize(),
							Assets.getPieceSize(), null);
				else //Draw a black piece
					graph.drawImage(Assets.blackPiece[type.value],
							board[piecePoint.getRow()][piecePoint.getColumn()].getRenderSquare().x,
							board[piecePoint.getRow()][piecePoint.getColumn()].getRenderSquare().y, Assets.getPieceSize(),
							Assets.getPieceSize(), null);
			}
		}
	}

	private void renderSubMenu(Graphics graph) {
		/*Draw the subMenu*/
		graph.drawImage(Assets.gameLogo, 0, (int)(100 * game.scale), Assets.getLogoWidth(), Assets.getLogoHeight(), null);
		if (drawOption) // if draw was selected then show the message "Accept draw request"
			graph.drawImage(Assets.acceptDraw, (int) (60 * game.scale), (int) ((530 * game.scale)), 
					Assets.acceptDraw.getWidth(), Assets.acceptDraw.getHeight(), null);
		getUIButtons().render(graph); // render buttons on the screen
	}

	@Override
	public UIList getUIButtons() {
		/*Control what button should be executed*/
		if(drawOption)
		{
			return drawButtons;
		} else if(subMenu)
		{
			return subMenuButtons;
		} 
		return null;
	}

	private int move(int line) {
		/*Internal method used to map the squares of the table in screen coordinates*/
		return Assets.getEdge() + (line * Assets.getMoveDistance());
	}

	private void initPromotionSquares()
	{
		int step = game.width/4;
		promotionChoices = new ArrayList<>();
		
		for(int i = step - (int)(190 * game.scale) ; i < game.width ; i += step)
		{
			promotionChoices.add(new Rectangle(i, (int)(game.height/2) - (Assets.getPieceSize()/2), Assets.getSquareSize(), Assets.getSquareSize()));
		}
	}
	
	private void initDrawMenuButtons()
	{
		drawButtons = new UIList();

		float buttonWidth = Assets.buttonYes[0].getWidth() * game.scale;
		float buttonHeight = Assets.buttonYes[0].getHeight() * game.scale;

		/*Add to the draw button list the yes button, this button is used to accept the draw proposal when draw option was selected
		 * if active the end game, close the subMenu and set the winnerMessage to a draw message*/
		drawButtons.getButtons()
				.add(new UIButton((int) (((game.width / 2) - 155) * game.scale), (int) ((670 * game.scale)),
						(int) (buttonWidth), (int) (buttonHeight), Assets.buttonYes, -1, new ButtonAction() {
							public void action() { //If this button was selected
									endGame = true; //Set the game to end
									subMenu = false; // Set the menu to close
									winnerMessage = Assets.draw; // Set ending message to a draw
									drawOption = false; //Set draw option to close
									game.getKeyboard().mESC = false; //Set ESC key to not pressed
							}

						}));

		buttonWidth = Assets.buttonNo[0].getWidth() * game.scale;
		buttonHeight = Assets.buttonNo[0].getHeight() * game.scale;

		/*Add to the draw button list the no button, this button is used negate the draw proposal when draw option was selected
		 * if clicked the no button closes the draw message and the menu and go back to the game*/
		drawButtons.getButtons()
				.add(new UIButton((int) (((game.width / 2) + 230) * game.scale), (int) ((670 * game.scale)),
						(int) (buttonWidth), (int) (buttonHeight), Assets.buttonNo, -1, new ButtonAction() {
							public void action() { //If this button was selected
									drawOption = false; //Set draw option to close
									subMenu = false;  // Set the menu to close
									game.getKeyboard().mESC = false; //Set ESC key to not pressed
							}
						}));

	}
	
	private void initSubMenuButtons() {
		/*Method that initialize all buttons used on the game state*/
		float buttonWidth = Assets.buttonQuit[0].getWidth() * game.scale;
		float buttonHeight = Assets.buttonQuit[0].getHeight() * game.scale;

		subMenuButtons = new UIList();
		/*Add to the subMenu list the Quit button, this button is used to give up and quit the game screen
		 * for every button a Button action is defined when passing the argument, this way is possible to program the button when creating it*/
		subMenuButtons.getButtons().add(new UIButton((int) (150 * game.scale), (int) ((550 * game.scale)),
						(int) (buttonWidth), (int) (buttonHeight), Assets.buttonQuit, -1, new ButtonAction() {
							public void action() { //If this button was selected
								/*Quit button action implements*/
								//If exitMenu is true it means that ESC key was pressed than it can active this action if pressed on button
								if (subMenu) {
									subMenu = false;
									
									//Active the end game, if the turn is white and it clicked on the quit it means white piece loses, else
									//black pieces clicked the white piece wins
									endGame = true;
									if (actualTurn == ColorInfo.WHITE)
										winnerMessage = Assets.congratz[ColorInfo.BLACK.value]; //white piece victory image
									else
										winnerMessage = Assets.congratz[ColorInfo.WHITE.value]; //black piece victory image
									
									game.getKeyboard().mESC = false;//Set ESC key to not pressed
								}
							}
		
						}));
		
		buttonWidth = Assets.buttonSave[0].getWidth() * game.scale;
		buttonHeight = Assets.buttonSave[0].getHeight() * game.scale;

		/*Add to the subMenu list the Save button, this button is used to save the game and go back to the menu state*/
		subMenuButtons.getButtons().add(new UIButton((int) ((game.width / 2) - (buttonWidth / 2)), (int) ((550 * game.scale)),
						(int) (buttonWidth), (int) (buttonHeight), Assets.buttonSave, -1, new ButtonAction() {
							public void action() { //If this button was selected
								/*Save button action implements*/
								//System.out.println("Save a game name");
								try {
									SaveGame.save(actualTurn.value, pieceBox);
								} catch (Exception e) {
									e.printStackTrace();
								}
								subMenu = false;
								game.getKeyboard().mESC = false; //Set ESC key to not pressed
								
							}

						}));
		
		buttonWidth = Assets.buttonDraw[0].getWidth() * game.scale;
		buttonHeight = Assets.buttonDraw[0].getHeight() * game.scale;
		
		/*Add to the subMenu list the Draw button, this button is used to propose a draw game. When selected it change the buttons on the screen*/
		subMenuButtons.getButtons().add(new UIButton((int) (740 * game.scale), (int) ((550 * game.scale)),
				(int) (buttonWidth), (int) (buttonHeight), Assets.buttonDraw, -1, new ButtonAction() {
							public void action() { //If this button was selected
								drawOption = true; //Active draw msg and change what buttons will be active (Yes and No will active)
								game.getKeyboard().mESC = false;  //Set ESC key to not pressed
							}
		
						}));

		buttonWidth = Assets.buttonContinue[0].getWidth() * game.scale;
		buttonHeight = Assets.buttonContinue[0].getHeight() * game.scale;

		/*Add to the subMenu list the continue button, this button is used to close the subMenu and go back to the game*/
		subMenuButtons.getButtons()
				.add(new UIButton((int) ((game.width / 2) - (buttonWidth / 2)), (int) ((670 * game.scale)),
						(int) (buttonWidth), (int) (buttonHeight), Assets.buttonContinue, -1, new ButtonAction() {
							public void action() { //If this button was selected
								subMenu = false; //Close the submenu
								game.getKeyboard().mESC = false; //Set ESC key to not pressed
							}

						}));
	}

	private void newGame() {
		/*Method that initialize all pieces at default position*/
		State.newGame = false;
		actualTurn = ColorInfo.WHITE;
		pieceBox[0] = new PieceList(ColorInfo.WHITE);
		pieceBox[1] = new PieceList(ColorInfo.BLACK);
		initBoard();
	}

	private void loadGame(String gameName) {
		//Chamar a fun��o que busca por nome
		//System.out.println(State.lastButtonIndex);
		//System.out.println("SEARCH FOR SAVED GAME NAME: " + State.savedGames.get(State.lastButtonIndex));
		try {
			System.out.println("Load game: ");
			actualTurn = LoadGame.loadGame(gameName, pieceBox);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		State.savedGames.clear();
		State.loadGame = false;
		initBoard();
	}

	private void initBoard() {
		//Init all squares and pieces positions
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new Square();
				board[i][j].setRenderSquare(new Rectangle(move(j), move(i), Assets.getPieceSize(), Assets.getPieceSize()));
			}
		}

		//Get the piece position and set the square ID and color to the piece that is there 
		//(Obs: ID of a piece is the position on the array, example king is 0, queen is 1 etc..)
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
