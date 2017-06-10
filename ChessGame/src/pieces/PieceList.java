package pieces;
import game.ColorInfo;
import game.Point;

public class PieceList {
	private Piece[] pieces;

	public PieceList()
	{
		
	}
	
	public PieceList(ColorInfo color)
	{
		pieces = new Piece[16];
		initPieces(color);
	}

	private void initPieces(ColorInfo color)
	{
		int pos = 0;
		
		if(color == ColorInfo.WHITE) pos += 7;
		
		pieces[0] = new King(new Point(pos,4), PieceInfo.KING, false, color);
		pieces[1] = new Queen(new Point(pos,3), PieceInfo.QUEEN, false, color);
		pieces[2] = new Horse(new Point(pos,1), PieceInfo.HORSE, false, color);
		pieces[3] = new Horse(new Point(pos,6), PieceInfo.HORSE, false, color);
		pieces[4] = new Bishop(new Point(pos,2), PieceInfo.BISHOP, false, color);
		pieces[5] = new Bishop(new Point(pos,5), PieceInfo.BISHOP, false, color);
		pieces[6] = new Rook(new Point(pos,0), PieceInfo.ROOK, false, color);
		pieces[7] = new Rook(new Point(pos,0), PieceInfo.ROOK, false, color);
		
		if(color == ColorInfo.WHITE) pos = 5;
		for(int i = 0 ; i < 8 ; i++)
		{
			pieces[i+8] = new Pawn(new Point(pos+1, i), PieceInfo.PAWN, false, color);
		}
	}

	public Piece[] getPieces()
	{
		return this.pieces;
	}
}
