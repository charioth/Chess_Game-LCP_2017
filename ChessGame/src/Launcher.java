
import game.Game;

public class Launcher {
	public static void main(String[] args) throws  Exception
	{
		Game game  = new Game("The Last Chess Game", 1f);
		
		game.start();	
	}
}
