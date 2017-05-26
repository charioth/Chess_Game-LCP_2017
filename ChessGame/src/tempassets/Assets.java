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
	
	public static void init()
	{
		piece = new BufferedImage[MAX_PIECES];
		try {
			background = ImageIO.read(Assets.class.getResource("/background/board.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			for(int i = 0 ; i < HALF ; i++)
			{
					piece[i] = ImageIO.read(Assets.class.getResource("/pieces/w_" + i + ".png"));
					piece[i+HALF] = ImageIO.read(Assets.class.getResource("/pieces/b_" + i + ".png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
