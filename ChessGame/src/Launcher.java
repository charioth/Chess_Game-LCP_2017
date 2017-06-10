
import java.sql.SQLException;
import DAO.connectionSelect;
import game.Game;

public class Launcher {
	public static void main(String[] args) throws  Exception
	{

		//Inicializando o Acesso ao banco
		//*********PASSAR ESSA INSTANCIA PARA O MÉTODO RESPONSÁVEL PELO LOAD*******************
		connectionSelect teste = new connectionSelect();
		System.out.println("Conexão Aberta!");

		
		Game game  = new Game("ChessGame", 0.8f);
		
		game.start();
		
	}
}
