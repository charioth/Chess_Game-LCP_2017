package states;

import java.awt.Graphics;

import game.Game;
import graphics.UIList;

public abstract class State {
	private static State currentState = null;
	protected Game game;
	
	State(Game game)
	{
		this.game = game;
	}
	
	public static void setCurrentState(State state)
	{
		currentState = state;
	}
	
	public static State getCurrentState()
	{
		return currentState;
	}
	
	public abstract UIList getUIButtons();
	public abstract void tick();
	public abstract void render(Graphics graph);
}
