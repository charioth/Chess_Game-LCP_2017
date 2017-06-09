package pieces;
import game.ColorInfo;
import game.Point;

public class PieceList {
	private Piece[] pieces;
	private ColorInfo color;

	public PieceList()
	{
		
	}
	
	public PieceList(ColorInfo color)
	{
		pieces = new Piece[16];
		if(color == ColorInfo.BLACK) initBlack();
		else initWhite();
		this.color = color;
	}

	private void initBlack()
	{
		pieces[0] = new King(new Point(0,4), PieceInfo.KING, false);
		pieces[1] = new Queen(new Point(0,3), PieceInfo.QUEEN, false);
		pieces[2] = new Horse(new Point(0,1), PieceInfo.HORSE, false);
		pieces[3] = new Horse(new Point(0,6), PieceInfo.HORSE, false);
		pieces[4] = new Bishop(new Point(0,2), PieceInfo.BISHOP, false);
		pieces[5] = new Bishop(new Point(0,5), PieceInfo.BISHOP, false);
		pieces[6] = new Rook(new Point(0,0), PieceInfo.ROOK, false);
		pieces[7] = new Rook(new Point(0,7), PieceInfo.ROOK, false);
		
		for(int i = 0 ; i < 8 ; i++)
		{
			pieces[i+8] = new Pawn(new Point(1,i), PieceInfo.PAWN, false);
		}
	}
	
	private void initWhite()
	{
		pieces[0] = new King(new Point(7,4), PieceInfo.KING, false);
		pieces[1] = new Queen(new Point(7,3), PieceInfo.QUEEN, false);
		pieces[2] = new Horse(new Point(7,1), PieceInfo.HORSE, false);
		pieces[3] = new Horse(new Point(7,6), PieceInfo.HORSE, false);
		pieces[4] = new Bishop(new Point(7,2), PieceInfo.BISHOP, false);
		pieces[5] = new Bishop(new Point(7,5), PieceInfo.BISHOP, false);
		pieces[6] = new Rook(new Point(7,0), PieceInfo.ROOK, false);
		pieces[7] = new Rook(new Point(7,7), PieceInfo.ROOK, false);
		
		for(int i = 0 ; i < 8 ; i++)
		{
			pieces[i+8] = new Pawn(new Point(6,i), PieceInfo.PAWN, false);
		}
	}
	
	public ColorInfo getColor()
	{
		return this.color;
	}

	public void setColor(ColorInfo color)
	{
		this.color = color;
	}
	
	public Piece[] getPieces()
	{
		return this.pieces;
	}
}
