package DAO;

import java.sql.*;

public class LoadGame {

	Connection connection;

	public LoadGame() {

	}

	// Mudar o void para os dados que v�o ser retornados, int, e etc...
	public void loadPieces() throws Exception {
		this.connection = DatabaseConnection.newConnection();
		// Executando a query
		Statement stmt = connection.createStatement();

		try {
			// Select do estado inicial do game e do game com save(table -> nome
			// da tabela)
			String sql = " SELECT * FROM table";
			ResultSet rs = stmt.executeQuery(sql);

			// Extraindo data do resultado
			while (rs.next()) {
				/*
				 * Pegando o nome das colunas, exemplo int id = rs.getInt("id");
				 * 
				 */
			}
			// Fechando a conexao do resultado
			rs.close();
			connection.close();
		} catch (Exception se) {
			// Lidando com os SQLExceptions
			se.printStackTrace();
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
