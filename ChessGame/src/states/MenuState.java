package states;

import java.awt.Graphics;

import game.Game;
import graphics.ButtonAction;
import graphics.UIButton;
import graphics.UIList;
import tempassets.Assets;

public class MenuState extends State{
	/* Menu screen state it is the initial screen of the game
	 * it control the new button and the load button*/
	private UIList UIButtons;
	
	public MenuState(Game game) {
		super(game);
		initUIButton();
	}
	
	@Override
	public UIList getUIButtons()
	{
		return UIButtons;
	}
	
	@Override
	public void tick() {
		//If ESC is clicked on the menu screen then the game closes
		if(game.getKeyboard().mESC == true)
		{
			game.stop();
		}
	}

	@Override
	public void render(Graphics graph) 
	{
		//Draw the menu background image and render the UI buttons
		graph.drawImage(Assets.menuBackground, 0, 0, game.width, game.height, null);
		UIButtons.render(graph);
	}
	
	private void initUIButton()
	{
		//Resize the button depending of the scale attribute of the game class
				float buttonWidth = Assets.buttonLoadGame[0].getWidth() * game.scale;
				float buttonHeight = Assets.buttonLoadGame[0].getHeight() * game.scale;
				
				UIButtons = new UIList();
				
				/* Creates the load button and add to the UI button list, the first two parameters has the position of the button on the screen
				 * it uses the game.width to centralize the button and the game.height to control the y position on the screen
				 * for every button a Button action is defined when passing the argument, this way is possible to program the button when creating it*/
				UIButtons.getButtons().add(new UIButton( (int)((game.width / 2) - (buttonWidth/2)), (int)((game.height - game.height / 3) + buttonHeight), (int)(buttonWidth), 
						(int)(game.scale * buttonHeight), Assets.buttonLoadGame, 
						new ButtonAction()
						{
							public void action() {
								State.setCurrentState(game.getMenuState());
							}
					
						})); //MUDAR PARA LOAD

				//Resize the button depending of the scale attribute of the game class
				buttonWidth = Assets.buttonNewGame[0].getWidth() * game.scale;
				buttonHeight = Assets.buttonNewGame[0].getHeight() * game.scale;
				
				/* Creates the game button and add to the UI button list, the first two parameters has the position of the button on the screen
				 * it uses the game.width to centralize the button and the game.height to control the y position on the screen
				 * for every button a Button action is defined when passing the argument, this way is possible to program the button when creating it*/
				UIButtons.getButtons().add(new UIButton((int)((game.width / 2) - (buttonWidth/2)), (int)((game.height - game.height / 3)), (int)(buttonWidth), 
						(int)(buttonHeight), Assets.buttonNewGame,
						new ButtonAction() {
							public void action() {
								State.newGame = true;
								State.setCurrentState(game.getGameState());
							}
						}));
	}

}
