package tempassets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {
	/*Piece Width/Height*/
	
	public static final int P_SIZE = 114;
	public static final int EDGE_SIZE = 52;
	public static final int BLINE_SIZE = 5;
	public static final int MAX_PIECES = 12;
	public static final int HALF = MAX_PIECES/2;
	
	public static BufferedImage[] piece;
	public static BufferedImage background;
	public static BufferedImage menuBackground;
	public static BufferedImage[] buttonNewGame;
	public static BufferedImage[] buttonLoadGame;
	public static BufferedImage move_square;
	public static BufferedImage attack_square;
	
	
	public static void init()
	{
		piece = new BufferedImage[MAX_PIECES];
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
			for(int i = 0 ; i < HALF ; i++)
			{
					piece[i] = ImageIO.read(Assets.class.getResource("/pieces/w_" + i + ".png"));
					piece[i+HALF] = ImageIO.read(Assets.class.getResource("/pieces/b_" + i + ".png"));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
