package states;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.ColorInfo;
import game.Game;
import game.Square;
import graphics.UIList;
import pieces.PieceList;
import tempassets.Assets;

public class GameState extends State{
	private int moveDistance;
	private int pieceSize;
	private int edge;
	
	private Square[][] board = new Square[8][8];
	private PieceList pieceBox[] = new PieceList[2];
	
	public GameState(Game game) 
	{
		super(game);
		pieceSize = (int)( ((float) Assets.P_SIZE) * game.scale);
		moveDistance = (int)((float)(Assets.P_SIZE + Assets.BLINE_SIZE) * game.scale);
		edge = (int)(((float)Assets.EDGE_SIZE) * game.scale);
		pieceBox[0] = new PieceList(ColorInfo.WHITE);
		pieceBox[1] = new PieceList(ColorInfo.BLACK);
		initBoard();
	}

	@Override
	public void tick() 
	{
	}
	
	@Override
	public void render(Graphics graph)
	{
		graph.drawImage(Assets.background, 0, 0, game.width, game.height, null);
		
		for(int j = 0 ; j < 2 ; j++)
		{			
			for(int i = 0, row, column, value ; i < 16 ; i++)
			{
				value = pieceBox[j].getPieces()[i].getType().value;
				row = pieceBox[j].getPieces()[i].getActualPosition().getRow();
				column = pieceBox[j].getPieces()[i].getActualPosition().getColumn();
				if(j == 0)
					graph.drawImage(Assets.whitePiece[value], board[row][column].getRenderPos().x, board[row][column].getRenderPos().y, pieceSize, pieceSize, null);
				else
					graph.drawImage(Assets.blackPiece[value], board[row][column].getRenderPos().x, board[row][column].getRenderPos().y, pieceSize, pieceSize, null);
			}	
		}
	}
	
	@Override
	public UIList getUIButtons() {
		return null;
	}
	
	private int move(int line)
	{
		return edge + (line * moveDistance);
	}
	
	private void initBoard()
	{
		for(int i = 0 ; i < 8 ; i++)
		{
			for(int j = 0 ; j < 8 ; j++)
			{				
				board[i][j] = new Square(); 
				board[i][j].setRenderPos(new Rectangle(move(j), move(i) ,pieceSize, pieceSize));
			}
		}
		
		for(int i = 0 ; i < 2 ; i++)
		{			
			ColorInfo color = pieceBox[i].getPieces()[0].getColor();
			for(int j = 0, row, column; j < 16 ; j++)
			{
				row = pieceBox[i].getPieces()[j].getActualPosition().getRow();
				column = pieceBox[i].getPieces()[j].getActualPosition().getColumn();
				board[row][column].setPieceID(j);
				board[row][column].setColor(color);
			}
		}
	}
}
