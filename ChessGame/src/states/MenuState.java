package states;

import java.awt.Graphics;

import game.Game;
import graphics.ButtonAction;
import graphics.UIButton;
import graphics.UIList;
import tempassets.Assets;

public class MenuState extends State{

	private UIList UIButtons;
	
	public MenuState(Game game) {
		super(game);
		float buttonWidth = Assets.buttonLoadGame[0].getWidth() * game.scale;
		float buttonHeight = Assets.buttonLoadGame[0].getHeight() * game.scale;
		
		UIButtons = new UIList();
		UIButtons.getButtons().add(new UIButton( (int)((game.width / 2) - (buttonWidth/2)), (int)((game.height - game.height / 3) + buttonHeight), (int)(buttonWidth), 
				(int)(game.scale * buttonHeight), Assets.buttonLoadGame, 
				new ButtonAction()
				{
					public void action() {
						State.setCurrentState(game.getMenuState());
					}
			
				})); //MUDAR PARA LOAD

		buttonWidth = Assets.buttonNewGame[0].getWidth() * game.scale;
		buttonHeight = Assets.buttonNewGame[0].getHeight() * game.scale;
		
		UIButtons.getButtons().add(new UIButton((int)((game.width / 2) - (buttonWidth/2)), (int)((game.height - game.height / 3)), (int)(buttonWidth), 
				(int)(buttonHeight), Assets.buttonNewGame,
				new ButtonAction() {
					public void action() {
						State.setCurrentState(game.getGameState());
					}
				}));
	}
	
	@Override
	public UIList getUIButtons()
	{
		return UIButtons;
	}
	
	@Override
	public void tick() {
		if(game.getKeyboard().mESC == true)
		{
			game.stop();
		}
	}

	@Override
	public void render(Graphics graph) 
	{
		graph.drawImage(Assets.menuBackground, 0, 0, game.width, game.height, null);
		UIButtons.render(graph);
	}

}
