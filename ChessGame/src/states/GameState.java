package states;

import java.awt.Graphics;

import game.Game;
import tempassets.Assets;

public class GameState extends State{
	private final int B_SIZE = 8;
	private int moveDistance;
	private int pieceSize;
	private int edge;
	
	/*
	 * 0 TOWER
	 * 1 HORSE
	 * 2 BISHOP
	 * 3 QUEEN
	 * 4 KING
	 * 5 PAWN
	 * */
	
	public GameState(Game game) 
	{
		super(game);
		pieceSize = (int)( ((float) Assets.P_SIZE) * mGame.mScale);
		moveDistance = (int)((float)(Assets.P_SIZE + Assets.BLINE_SIZE) * mGame.mScale);
		edge = (int)(((float)Assets.EDGE_SIZE) * mGame.mScale);
	}

	@Override
	public void tick() 
	{
		
	}
	
	private int move(int line)
	{
		return edge + (line * moveDistance);
	}
	
	@Override
	public void render(Graphics graph)
	{
		int i = 0;
		graph.drawImage(Assets.background, 0, 0, mGame.mWidth, mGame.mHeight, null);
		for(i = 0 ; i < 3 ; i++)
		{
			//White
			graph.drawImage(Assets.piece[i], move(i), move(0) ,pieceSize, pieceSize, null);
			graph.drawImage(Assets.piece[i], move(B_SIZE-(i+1)), move(0), pieceSize, pieceSize, null);
			
			//Black
			graph.drawImage(Assets.piece[i+Assets.HALF], move(i), move(7) , pieceSize, pieceSize, null);
			graph.drawImage(Assets.piece[i+Assets.HALF], move(B_SIZE-(i+1)), move(7), pieceSize, pieceSize, null);
		}
		
		//Queen and King
		for(; i < 5 ; i++)
		{
			graph.drawImage(Assets.piece[i], move(i), move(0) , pieceSize, pieceSize, null);
			graph.drawImage(Assets.piece[i+Assets.HALF], move(i), move(7), pieceSize, pieceSize, null);
		}
		
		//PAWN
		for(i = 0 ; i < B_SIZE ; i++)
		{
			graph.drawImage(Assets.piece[5], move(i), move(1), pieceSize, pieceSize, null);
			graph.drawImage(Assets.piece[5+Assets.HALF], move(i), move(6), pieceSize, pieceSize, null);
		}
	}
}
