package states;

import java.awt.Graphics;

import game.Game;

public abstract class State {
	private static State mCurrentState = null;
	protected Game mGame;
	
	State(Game game)
	{
		mGame = game;
	}
	
	public static void setCurrentState(State state)
	{
		mCurrentState = state;
	}
	
	public static State getCurrentState()
	{
		return mCurrentState;
	}
	
	public abstract void tick();
	public abstract void render(Graphics graph);
}
