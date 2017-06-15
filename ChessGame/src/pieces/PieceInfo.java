package pieces;

public enum PieceInfo {

	KING(0), QUEEN(1), ROOK(2), BISHOP(3), KNIGHT(4), PAWN(5);

	public int value;

	PieceInfo(int value) {
		this.value = value;
	}
}
