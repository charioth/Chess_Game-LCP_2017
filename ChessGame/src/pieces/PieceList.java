package pieces;
import game.ColorInfo;

public class PieceList {
	private Piece[] pieces;
	private ColorInfo color;

	public PieceList()
	{
		
	}
	
	public PieceList(ColorInfo color)
	{
		pieces = new Piece[16];
		this.color = color;
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
