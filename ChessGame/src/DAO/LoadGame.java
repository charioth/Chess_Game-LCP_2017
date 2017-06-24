package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.ColorInfo;
import game.Coordinates;
import pieces.Piece;
import pieces.PieceInfo;
import pieces.PieceList;

public class LoadGame {

	Connection connection;

	public LoadGame() {

	}

	// Mudar o void para os dados que v�o ser retornados, int, e etc...
	public void loadPieces(String gameName, PieceList pieceBox[]) throws Exception {
		this.connection = DatabaseConnection.newConnection();
		// Executando a query
		Statement stmt = connection.createStatement();

		try {
			// Select do estado inicial do game e do game com save(table -> nome
			// da tabela)
			String sql = "SELECT * FROM piece WHERE game_name = '" + gameName + "'" ;

			ResultSet rs = stmt.executeQuery(sql);

			// Extraindo data do resultado
			while (rs.next()) {
				/*
				 * Pegando o nome das colunas, exemplo int id = rs.getInt("id");
				 * 
				 */
				int piece = rs.getInt("piece");
				int coord_row = rs.getInt("coord_row");
				int coord_column = rs.getInt("coord_column");
				int piece_type = rs.getInt("piece_type");
				boolean moved = rs.getBoolean("moved");
				int piece_color = rs.getInt("piece_color");
				int index = rs.getInt("index");
				
				//TODO - Verificar o enum
				pieceBox[piece_color].getPieces()[index] = new Piece(new Coordinates(coord_row, coord_column) , 1, moved, piece_color);
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
	
	public List<String> loadNames()throws Exception{
		
		List<String> names = new ArrayList<String>(); 
		
		this.connection = DatabaseConnection.newConnection();
		// Executando a query
		Statement stmt = connection.createStatement();
		
		try{
			String sql = " SELECT * FROM save_game";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				names.add(rs.getString("name") + " " + rs.getString("save_date"));
			}
			rs.close();
			connection.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
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
		
		return names;
	}

}
