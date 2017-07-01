
import game.Game;

public class Launcher {
	public static void main(String[] args) throws  Exception
	{
		Game game  = new Game("ChessGame", 0.6f);
		
		game.start();	
	}
}
