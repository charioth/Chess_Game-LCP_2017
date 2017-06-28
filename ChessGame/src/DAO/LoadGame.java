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

	private static Connection connection;

	public LoadGame() {

	}

	public static void loadGame(String gameName, PieceList pieceBox[], ColorInfo actualTurn) throws Exception {
		connection = DatabaseConnection.newConnection(); //Connect to database
		Statement stmt = connection.createStatement(); //Creates a Statement object for sending SQL statements to the database

		try {
			/*Load whose turn it is*/
			String sql  = "SELECT turn FROM save_game WHERE game_name = '" + gameName + "'" ;
			ResultSet rs = stmt.executeQuery(sql);
			actualTurn = ColorInfo.values()[rs.getInt("turn")];
			
			/*Load all pieces*/
			sql = "SELECT * FROM piece WHERE game_name = '" + gameName + "'" ;
			rs = stmt.executeQuery(sql);
			
			/*Initialize the piecebox with each piece information*/
			while (rs.next())
			{
				int coord_row = rs.getInt("coord_row");
				int coord_column = rs.getInt("coord_column");
				int piece_type = rs.getInt("piece_type");
				boolean moved = rs.getBoolean("moved");
				int piece_color = rs.getInt("piece_color");
				int index = rs.getInt("index");
				
				pieceBox[piece_color].getPieces()[index] = new Piece(new Coordinates(coord_row, coord_column),
																PieceInfo.values()[piece_type], moved, ColorInfo.values()[piece_color]);
			}
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					connection.close(); //Close database connection
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			try {
				if (connection != null) {
					connection.close(); //Close database connection
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public static List<String> loadNames() throws Exception{
		
		List<String> games = new ArrayList<String>();
		
		connection = DatabaseConnection.newConnection(); //Connect to database

		Statement stmt = connection.createStatement(); //Creates a Statement object for sending SQL statements to the database
		
		try{
			/*Get all saved games*/
			String sql = " SELECT * FROM save_game";
			ResultSet rs = stmt.executeQuery(sql);
			
			/*Add to the list all saved game names and dates*/
			while(rs.next()){
				games.add(rs.getString("name") + "	" + rs.getString("save_date"));
			}
			rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally {
			try {
				if (stmt != null) {
					connection.close(); //Close database connection
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			try {
				if (connection != null) {
					connection.close(); //Close database connection
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		return games; //Return the list
	}
}
