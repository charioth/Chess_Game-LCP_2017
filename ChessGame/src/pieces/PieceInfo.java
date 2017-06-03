package pieces;

public enum PieceInfo {

	KING(0),
	QUEEN(1),
	ROOK(2),
	BISHOP(3),
	HORSE(4),
	PAWN(5);
	
	int value;
	
	PieceInfo(int value)
	{
		this.value = value;
	}
}
