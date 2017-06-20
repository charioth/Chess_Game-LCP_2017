package DAO;


import java.sql.*;


public class DatabaseConnection {
	//Método para pegar a conexão com o banco
	
	public DatabaseConnection() {
		
	}
	
	public static Connection newConnection() {
		try {
			// Load JDBC driver
			Class.forName("com.mysql.jdbc.Driver"); // Talvez seja desnecessario: https://docs.oracle.com/javase/8/docs/api/java/sql/DriverManager.html
	        return DriverManager.getConnection("jdbc:mysql://localhost:3306/TheLastChessGame", "user", "password");
	    } catch (Exception e) {
	    	System.out.println("Problem connecting with database");
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    }
	}
	
}
