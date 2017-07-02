package tempassets;


import game.Game;

public class Assets {
	/* Piece Width/Height */
	/*Contains the the all images used in the program and their info*/
	
	private static final int EDGE_SIZE = 52;
	public static final int BLUE_LINE_SIZE = 5;
	private static final int PIECE_SIZE = 114;
	private static int moveDistance;
	private static int edge;
	
	public static void init(Game game) {
			moveDistance = (int) ((float) (PIECE_SIZE + Assets.BLUE_LINE_SIZE) * game.getScale());
			edge = (int) (((float) Assets.EDGE_SIZE) * game.getScale());
	
	}
	
	public static int getMoveDistance() {
		return moveDistance;
	}

	public static int getEdge() {
		return edge;
	}

}
