package states;

import java.awt.Graphics;

import game.Game;
import graphics.UIList;

public abstract class State {
	/*Super class that every state of the game extends
	 * it controls the current state using a static variable and control when execute the newGame and loadGame*/
	private static State currentState = null;
	protected static boolean newGame;
	protected static boolean loadGame;
	protected Game game;

	State(Game game) {
		this.game = game;
	}

	public static void setCurrentState(State state) {
		/*Change current state*/
		currentState = state;
	}

	public static State getCurrentState() {
		/*return current state*/
		return currentState;
	}

	//List of buttons that the state has to call
	public abstract UIList getUIButtons();

	public abstract void tick();

	public abstract void render(Graphics graph);
}
