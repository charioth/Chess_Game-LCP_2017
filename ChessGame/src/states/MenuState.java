package states;

import java.awt.Graphics;

import game.Game;

public class MenuState extends State{

	public MenuState(Game game) {
		super(game);
	}

	@Override
	public void tick() {
		if(mGame.mKeyboard.mESC == true)
		{
			mGame.stop();
		}
	}

	@Override
	public void render(Graphics graph) {

		
	}

}
