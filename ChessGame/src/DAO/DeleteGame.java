package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteGame {

	private Connection connection;

	public DeleteGame() {

	}

	public void Delete(String gameName) throws Exception {
		this.connection = DatabaseConnection.newConnection();
		Statement stmt = connection.createStatement();
		
		try {
			String sql = " DELETE * FROM piece WHERE gameName = " + gameName;
			stmt.executeUpdate(sql);
			 sql = " DELETE * FROM save_game WHERE name = " + gameName;
			System.out.println("Deleted");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Bloqueia uso para encerramento de recursos
			try {
				if (stmt != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

}
