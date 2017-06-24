package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import pieces.PieceList;

public class SaveGame {

	private Connection connection;

	public SaveGame() {

	}

	public void save(int actualTurn, PieceList pieceBox[]) throws Exception {
		this.connection = DatabaseConnection.newConnection();
		Statement stmt = connection.createStatement();

		String gameName;
		Date date = new Date();
		try {
			gameName = "SaveGame" + String.format("%T ", date);
			saveGame(gameName, actualTurn, stmt, String.format("%D", date));

			savePieces(gameName, pieceBox, stmt);
			if (stmt != null)
				connection.close();
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

	public void saveGame(String gameName, int actualTurn, Statement stmt, String saveDate) throws Exception {

		try {

			String sql = "INSERT INTO save_game(name, save_date, turn) VALUES (" + gameName + ", " + saveDate + ", "
					+ actualTurn + ");";
			stmt.executeUpdate(sql);
			System.out.println("Saved game");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void savePieces(String gameName, PieceList pieceBox[], Statement stmt) throws Exception {

		try {

			int coord_row;
			int coord_column;
			int piece_type;
			boolean moved;
			int piece_color;
			int index;
			// TODO - Comentar -> Pegando todas as pecas do conjunto de pecas
			for (PieceList pieces : pieceBox) {
				for (int i = 0; i < 16; i++) {
					coord_row = pieces.getPieces()[i].getActualPosition().getRow();
					coord_column = pieces.getPieces()[i].getActualPosition().getColumn();
					piece_type = pieces.getPieces()[i].getType().value;
					moved = pieces.getPieces()[i].isMoved();
					piece_color = pieces.getPieces()[i].getColor().value;
					index = pieces.getPieces()[i].getIndex();

					String sql = "INSERT INTO piece (game_name, coord_row, coord_column, piece_type, moved, piece_color, index2)"
							+ " VALUES (" + gameName + ", " + coord_row + ", " + coord_column + ", " + piece_type + ", "
							+ moved + ", " + piece_color + ", " + index + ");";

					stmt.executeUpdate(sql);
					System.out.println("saved");

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
