package tempassets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {
	/*Piece Width/Height*/
	
	public static final int P_SIZE = 114;
	public static final int EDGE_SIZE = 52;
	public static final int BLINE_SIZE = 5;
	public static final int MAX_PIECES = 6;
	
	public static BufferedImage[] blackPiece;
	public static BufferedImage[] whitePiece;
	public static BufferedImage background;
	public static BufferedImage menuBackground;
	public static BufferedImage[] buttonNewGame;
	public static BufferedImage[] buttonLoadGame;
	public static BufferedImage move_square;
	public static BufferedImage attack_square;
	
	
	public static void init()
	{
		whitePiece = new BufferedImage[MAX_PIECES];
		blackPiece = new BufferedImage[MAX_PIECES];
		buttonNewGame = new BufferedImage[2];
		buttonLoadGame = new BufferedImage[2];
		try {
			background = ImageIO.read(Assets.class.getResource("/background/board.png"));
			menuBackground = ImageIO.read(Assets.class.getResource("/background/menu_backgroud.png"));
			move_square = ImageIO.read(Assets.class.getResource("/background/blue_square.png"));
			attack_square = ImageIO.read(Assets.class.getResource("/background/red_square.png"));
			buttonLoadGame[0] = ImageIO.read(Assets.class.getResource("/button/load_game.png"));
			buttonNewGame[0] = ImageIO.read(Assets.class.getResource("/button/new_game.png"));
			buttonLoadGame[1] = ImageIO.read(Assets.class.getResource("/button/load_game_b.png"));
			buttonNewGame[1] = ImageIO.read(Assets.class.getResource("/button/new_game_b.png"));
			for(int i = 0 ; i < MAX_PIECES ; i++)
			{
					whitePiece[i] = ImageIO.read(Assets.class.getResource("/pieces/w_" + i + ".png"));
					blackPiece[i] = ImageIO.read(Assets.class.getResource("/pieces/b_" + i + ".png"));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
