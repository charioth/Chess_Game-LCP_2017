package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.Game;
import graphics.ButtonAction;
import graphics.Text;
import graphics.UIButton;
import graphics.UIList;
import graphics.UIScrollScreen;
import tempassets.Assets;

public class MenuState extends State {
	/*
	 * Menu screen state it is the initial screen of the game it control the new
	 * button and the load button
	 */
	private UIList UIButtons;
	private UIScrollScreen loadScreen;
	private boolean loadMenu;
	
	public MenuState(Game game) {
		super(game);
		initUIButton();
		initLoadScreen();
	}

	@Override
	public UIList getUIButtons() {
		return UIButtons;
	}

	@Override
	public UIScrollScreen getScreen() {
		if(loadMenu)
		 return loadScreen;
		else
			return null;
	}

	@Override
	public void tick() {
		// If ESC is clicked on the menu screen then the game closes
		if (game.getKeyboard().mESC == true) {
			if(!loadMenu){				
				game.stop();
			} else {
				loadMenu = false;
				game.getKeyboard().mESC = false;
			}
		}
	}

	@Override
	public void render(Graphics graph) {
		// Draw the menu background image and render the UI buttons
		graph.drawImage(Assets.menuBackground, 0, 0, game.width, game.height, null);
		UIButtons.render(graph);
		
		if(loadMenu)
			loadScreen.render(graph);
	}
	
	private void initLoadScreen() {
		loadScreen = new UIScrollScreen(Assets.loadScreen, (game.width)/2 - (Assets.loadScreen.getWidth()/2), (game.height)/2 - (Assets.loadScreen.getHeight()/2) + 100, 
				Assets.loadScreen.getWidth(), Assets.loadScreen.getHeight()/2, 10);
		
		float buttonWidth = Assets.loadButton[0].getWidth() * game.scale;
		float buttonHeight = Assets.loadButton[0].getHeight() * game.scale;

		Font font = new Font("Verndana", Font.PLAIN, 20);
		for(int i = 0, accumulator = 0 ; (int) i < savedGames.size() ; i++ ) {
			loadScreen.getButtons()
			.add(new UIButton((int) ((game.width / 2) - (buttonWidth / 2)), accumulator, (int) (buttonWidth),
					(int) (game.scale * buttonHeight), Assets.loadButton, new Text(savedGames.get(i), font, Color.blue, (int)((game.width / 2) - (buttonWidth / 2) + 10 * game.scale), accumulator + (int)(buttonHeight/2)), 
					new ButtonAction() {
						public void action() {
							System.out.println("Clicked");
							State.setCurrentState(game.getMenuState());
						}
					})); // MUDAR PARA LOAD
			accumulator += buttonHeight;
		}
	}

	private void initUIButton() {
		// Resize the button depending of the scale attribute of the game class
		float buttonWidth = Assets.buttonLoadGame[0].getWidth() * game.scale;
		float buttonHeight = Assets.buttonLoadGame[0].getHeight() * game.scale;

		UIButtons = new UIList();

		/*
		 * Creates the load button and add to the UI button list, the first two
		 * parameters has the position of the button on the screen it uses the
		 * game.width to centralize the button and the game.height to control
		 * the y position on the screen for every button a Button action is
		 * defined when passing the argument, this way is possible to program
		 * the button when creating it
		 */
		UIButtons.getButtons()
				.add(new UIButton((int) ((game.width / 2) - (buttonWidth / 2)),
						(int) ((game.height - game.height / 3) + buttonHeight), (int) (buttonWidth),
						(int) (game.scale * buttonHeight), Assets.buttonLoadGame, new ButtonAction() {
							public void action() {
								loadMenu = true;
							}

						})); // MUDAR PARA LOAD

		// Resize the button depending of the scale attribute of the game class
		buttonWidth = Assets.buttonNewGame[0].getWidth() * game.scale;
		buttonHeight = Assets.buttonNewGame[0].getHeight() * game.scale;

		/*
		 * Creates the game button and add to the UI button list, the first two
		 * parameters has the position of the button on the screen it uses the
		 * game.width to centralize the button and the game.height to control
		 * the y position on the screen for every button a Button action is
		 * defined when passing the argument, this way is possible to program
		 * the button when creating it
		 */
		UIButtons.getButtons()
				.add(new UIButton((int) ((game.width / 2) - (buttonWidth / 2)), (int) ((game.height - game.height / 3)),
						(int) (buttonWidth), (int) (buttonHeight), Assets.buttonNewGame, new ButtonAction() {
							public void action() {
								State.newGame = true;
								State.setCurrentState(game.getGameState());
							}
						}));
	}

}
