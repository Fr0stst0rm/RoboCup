import java.util.LinkedList;

public class Map extends LinkedList<LinkedList<MapTile>>{
	// zuert reihen = line = y = horizointal = height, dann spalten = x = vertical = senkrecht =  width

	Point startPoint = new Point(0, 0);

	Point offset = new Point(0, 0);

	public void addTile(int x, int y, MapTile tile) {
		int newX = rearrangeXOffset(x);
		int newY = rearrangeYOffset(y);

		while (this.size() <= newY) {
			this.add(new LinkedList<MapTile>());
		}
		while (this.get(newY).size() < newX) {
			this.get(newY).add(new MapTile());
		}

		this.get(newY).add(newX, tile);

		for (LinkedList<MapTile> line : this) {
			while (line.size() < this.getWidth()) {
				line.add(new MapTile());
			}
		}

		//Add walls to connected tiles
		
		if(tile.wallNorth) {
			try {
				getMapTile(newX, newY, Direction.NORTH).wallSouth = true;
			} catch (IndexOutOfBoundsException e) {
				
			}
		}
		
		if(tile.wallEast) {
			try {
				getMapTile(newX, newY, Direction.EAST).wallWest = true;
			} catch (IndexOutOfBoundsException e) {
				
			}
		}
		
		if(tile.wallSouth) {
			try {
				getMapTile(newX, newY, Direction.SOUTH).wallNorth = true;
			} catch (IndexOutOfBoundsException e) {
				
			}
		}
		
		if(tile.wallWest) {
			try {
				getMapTile(newX, newY, Direction.WEST).wallEast = true;
			} catch (IndexOutOfBoundsException e) {
				
			}
		}
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public MapTile getMapTile(Point p) {
		return this.getMapTile(p.x, p.y);
	}

	public MapTile getMapTile(int x, int y) {
		int newX = rearrangeXOffset(x);
		int newY = rearrangeYOffset(y);
		return this.get(newY).get(newX);
	}

	public MapTile getNextMapTile(Direction dir) {
		return getMapTile(BotStatus.currentPos, dir);
	}

	//TODO relocate all existing tiles
	private int rearrangeXOffset(int x) {
		if ((x - offset.x) < 0) {
			offset.x = x;
			//LCD.drawString("New X Offset: " + offset.x, 0, 0);
			System.out.println("New X Offset: " + offset.x);
		}
		return (x - offset.x);
	}

	private int rearrangeYOffset(int y) {
		int newY = y - offset.y;
		if ((y - offset.y) < 0) {
			offset.y = y;
			//LCD.drawString("New Y Offset: " + offset.y, 0, 1);
			System.out.println("New Y Offset: " + offset.y);
		}
		return (y - offset.y);
	}

	public int getHeight() {
		return this.size();
	}

	public int getWidth() {
		int size = 0;
		for (LinkedList<MapTile> column : this) { //column
			if (size < column.size()) {
				size = column.size();
			}
		}
		return size;
	}

	@Override
	public String toString() {
		String str = "";

		LinkedList<LinkedList<Character>> printHelper = null;

		for (int y = getHeight() - 1; y >= 0; y--) {
			printHelper = new LinkedList<LinkedList<Character>>();
			for (int x = 0; x < getWidth(); x++) {
				int newLines = 0;
				for (Character character : getMapTile(x, y).toString().toCharArray()) {
					if (printHelper.size() < (newLines + 1)) {
						printHelper.add(new LinkedList<Character>());
					}
					if (character.equals('\n')) {
						newLines++;
					} else {
						printHelper.get(newLines).add(character);
					}
				}
			}
			for (LinkedList<Character> tileLine : printHelper) {
				str += Tools.stringBuilder(tileLine) + "\n";
			}
		}

		return str;
	}

	public boolean checkWall(int x, int y, Direction dir) {

		MapTile tile = this.getMapTile(x, y);

		switch (dir) {
		case NORTH:
			return tile.wallNorth;
		case EAST:
			return tile.wallEast;
		case SOUTH:
			return tile.wallSouth;
		case WEST:
			return tile.wallWest;
		}
		return false;
	}

	public boolean checkWall(Point currentPos, Direction dir) {
		return checkWall(currentPos.x, currentPos.y, dir);
	}

	public Point getNextTileCoordinates(Direction dir) {
		int x = BotStatus.currentPos.x;
		int y = BotStatus.currentPos.y;

		switch (dir) {
		case NORTH:
			y++;
			break;
		case EAST:
			x++;
			break;
		case WEST:
			x--;
			break;
		case SOUTH:
			y--;
			break;
		}

		int newX = rearrangeXOffset(x);
		int newY = rearrangeYOffset(y);
		return new Point(newX, newY);
	}

	public void addTile(Point coordinates, MapTile chasm) {
		addTile(coordinates.x, coordinates.y, chasm);
	}

	public MapTile getMapTile(Point p, Direction north) {
		return getMapTile(p.x, p.y, north);
	}

	public MapTile getMapTile(int x, int y, Direction dir) {
		switch (dir) {
		case NORTH:
			y++;
			break;
		case EAST:
			x++;
			break;
		case WEST:
			x--;
			break;
		case SOUTH:
			y--;
			break;
		}

		int newX = rearrangeXOffset(x);
		int newY = rearrangeYOffset(y);
		return this.get(newY).get(newX);
	}

}
