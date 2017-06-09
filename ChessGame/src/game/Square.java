package game;

import java.awt.Rectangle;

public class Square {
	private Rectangle renderPosition;
	private int pieceID;
	private ColorInfo color;

	public Square() 
	{
		renderPosition = null;
		pieceID = -1;
		color = null;
	}

	public Square(Rectangle renderPosition) 
	{
		this.renderPosition = renderPosition;
		pieceID = -1;
	}

	public Square(Rectangle renderPosition, int ID) 
	{
		this.renderPosition = renderPosition;
		this.pieceID = ID;
	}

	public Rectangle getRenderPos()
	{
		return renderPosition;
	}
	
	public void setRenderPos(Rectangle renderPosition)
	{
		this.renderPosition = renderPosition;
	}
	
	public int getPieceID() 
	{
		return pieceID;
	}

	public void setPieceID(int ID)
	{
		this.pieceID = ID;
	}

	public ColorInfo getColor()
	{
		return color;
	}

	public void setColor(ColorInfo color) 
	{
		this.color = color;
	}
}
