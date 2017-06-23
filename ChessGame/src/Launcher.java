
//import DAO.connectionSelect;


import java.sql.Connection;

import DAO.DatabaseConnection;
import game.Game;

public class Launcher {
	public static void main(String[] args) throws  Exception
	{
		
		//Inicializando o Acesso ao banco
		//*********PASSAR ESSA INSTANCIA PARA O MÉTODO RESPONSÁVEL PELO LOAD*******************
		/*Connection con = null;
		con = DatabaseConnection.newConnection();
		if(con != null) System.out.println("AEE PORRA");*/
		Game game  = new Game("ChessGame", 0.8f);
		
		game.start();
		
	}
}
