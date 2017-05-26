
import game.Game;

public class Launcher {
	public static void main(String[] args)
	{
		Game game  = new Game("ChessGame", 0.8f);
		
		game.start();
	}
}
