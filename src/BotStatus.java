
public class BotStatus {
	
	public static Direction currentDir = Direction.NORTH;
	
	public static int blackTile = 37;
	
	public static int victimsFound = 0;
	public static int victimsToFind = 20;
	
	public static Point currentPos = new Point(0,0);
	
	public static Map mazeMap = new Map();
	
	public static boolean mapping = true;
	
	public static Path pathToStart = new Path(0,0);
	
	public static Direction getLeft() {
		switch (currentDir) {
		case NORTH:
			return Direction.WEST;
		case EAST:
			return Direction.NORTH;
		case SOUTH:
			return Direction.EAST;
		case WEST:
			return Direction.SOUTH;
		}
		return Direction.WEST;
	}
	
	public static Direction getRight() {
		switch (currentDir) {
		case NORTH:
			return Direction.EAST;
		case EAST:
			return Direction.SOUTH;
		case SOUTH:
			return Direction.WEST;
		case WEST:
			return Direction.NORTH;
		}
		return Direction.WEST;
	}
	
	public static Direction getBack() {
		switch (currentDir) {
		case NORTH:
			return Direction.SOUTH;
		case EAST:
			return Direction.WEST;
		case SOUTH:
			return Direction.NORTH;
		case WEST:
			return Direction.EAST;
		}
		return Direction.WEST;
	}

	public static Point getNextTileCoordinates(Direction dir) {
		return mazeMap.getNextTileCoordinates(dir);
	}

}
