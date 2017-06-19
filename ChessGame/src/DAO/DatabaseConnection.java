package DAO;


import java.sql.*;


public class DatabaseConnection {
	//Método para pegar a conexão com o banco
	
	public DatabaseConnection() {
		
	}
	
	public Connection newConnection() {
		try {
	        return DriverManager.getConnection("Caminho", "user", "password");
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    }
	}
	
}
