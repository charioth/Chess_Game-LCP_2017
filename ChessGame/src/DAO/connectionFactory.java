package DAO;


import java.sql.*;


public class connectionFactory {
	//M�todo para pegar a conex�o com o banco
	public Connection getConnection() {
		try {
	        return DriverManager.getConnection("Caminho", "user", "password");
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    }
	}
	
}
