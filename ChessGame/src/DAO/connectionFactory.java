package DAO;


import java.sql.*;


public class connectionFactory {
	//Método para pegar a conexão com o banco
	public Connection getConnection() {
		try {
	        return DriverManager.getConnection("Caminho", "user", "password");
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    }
	}
	
}
