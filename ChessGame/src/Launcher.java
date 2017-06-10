
import java.sql.SQLException;
import DAO.connectionSelect;
import game.Game;

public class Launcher {
	public static void main(String[] args) throws  Exception
	{

		//Inicializando o Acesso ao banco
		//*********PASSAR ESSA INSTANCIA PARA O M�TODO RESPONS�VEL PELO LOAD*******************
		connectionSelect teste = new connectionSelect();
		System.out.println("Conex�o Aberta!");

		
		Game game  = new Game("ChessGame", 0.8f);
		
		game.start();
		
	}
}
