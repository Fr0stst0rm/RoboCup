

import java.util.LinkedList;

public class Map extends LinkedList<LinkedList<MapTile>> {

	// zuert reihen = zeilen = line = y = horizointal = height, dann spalten = x = vertical = senkrecht =  width

	Point startPoint = new Point(0, 0);

	Point offset = new Point(0, 0);

	public void addTile(int x, int y, MapTile tile) {
		int newX = rearrangeXOffset(x);
		int newY = rearrangeYOffset(y);

		System.out.println("X: "+x+" => "+newX);
		System.out.println("Y: "+y+" => "+newY);
		
		while (this.size() <= newY) {
			System.out.println("Adding new row to map.");
			this.add(new LinkedList<MapTile>());
		}
		while (this.get(newY).size() < newX) {
			System.out.println("Adding mew column to map.");
			this.get(newY).add(new MapTile());
		}

		System.out.println("Adding tile");
		if (newX == this.get(newY).size()) {
			this.get(newY).add(tile);
		} else {
			this.get(newY).set(newX, tile);
		}
		System.out.println("Tile added");
		
		for (LinkedList<MapTile> line : this) {
			while (line.size() < this.getWidth()) {
				System.out.println("Filling empty map tiles");
				line.add(new MapTile());
			}
		}

		//Add walls to connected tiles
		System.out.println("Adding walls to connected tiles");
		try {
			if (getDirectMapTile(newX, newY, Direction.NORTH).wallSouth == true) {
				tile.wallNorth = true;
			} else if (tile.wallNorth) {
				getDirectMapTile(newX, newY, Direction.NORTH).wallSouth = true;
				System.out.println("Tile found in direction NORTH and wall added.");
			}
		} catch (IndexOutOfBoundsException e) {
			//System.out.println("No connected tile in direction: NORTH");
			//e.printStackTrace();
		}

		try {
			if (getDirectMapTile(newX, newY, Direction.EAST).wallWest == true) {
				tile.wallEast = true;
			} else if (tile.wallEast) {
				getDirectMapTile(newX, newY, Direction.EAST).wallWest = true;
				System.out.println("Tile found in direction EAST and wall added.");
			}
		} catch (IndexOutOfBoundsException e) {
			//System.out.println("No connected tile in direction: EAST");
			//e.printStackTrace();
		}

		try {
			if (getDirectMapTile(newX, newY, Direction.SOUTH).wallNorth == true) {
				tile.wallSouth = true;
			} else if (tile.wallSouth) {
				getDirectMapTile(newX, newY, Direction.SOUTH).wallNorth = true;
				System.out.println("Tile found in direction SOUTH and wall added.");
			}
		} catch (IndexOutOfBoundsException e) {
			//System.out.println("No connected tile in direction: SOUTH");
			//e.printStackTrace();
		}

		try {
			if (getDirectMapTile(newX, newY, Direction.WEST).wallEast == true) {
				tile.wallWest = true;
			} else if (tile.wallWest) {
				getDirectMapTile(newX, newY, Direction.WEST).wallEast = true;
				System.out.println("Tile found in direction WEST and wall added.");
			}
		} catch (IndexOutOfBoundsException e) {
			//System.out.println("No connected tile in direction: WEST");
			//e.printStackTrace();
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
	public int rearrangeXOffset(int x) {
		if ((x - offset.x) < 0) {
			int offsetDif = offset.x - x;
			offset.x = x;
			shiftXRight(offsetDif);
			//LCD.drawString("New X Offset: " + offset.x, 0, 0);
			System.out.println("New X Offset: " + offset.x);
		}
		return (x - offset.x);
	}

	public int rearrangeYOffset(int y) {
		if ((y - offset.y) < 0) {
			int offsetDif = offset.y - y;
			offset.y = y;
			shiftYRight(offsetDif);
			//LCD.drawString("New Y Offset: " + offset.y, 0, 1);
			System.out.println("New Y Offset: " + offset.y);
		}
		return (y - offset.y);
	}

	private void shiftYRight(int shiftAmount) {
		for (; shiftAmount > 0; shiftAmount--) {
			LinkedList<MapTile> line = new LinkedList<>();
			for (int i = 0; i < getWidth(); i++) {
				line.add(new MapTile());
			}

			LinkedList<LinkedList<MapTile>> temp = new LinkedList<>(this);

			this.clear();

			this.add(line);

			for (LinkedList<MapTile> linkedList : temp) {
				this.add(linkedList);
			}

		}
	}

	private void shiftXRight(int shiftAmount) {
		for (; shiftAmount > 0; shiftAmount--) {
			for (LinkedList<MapTile> line : this) {

				LinkedList<MapTile> temp = new LinkedList<>(line);

				line.clear();

				line.add(new MapTile());

				for (MapTile tile : temp) {
					line.add(tile);
				}
			}
		}

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
				for (Character character : this.get(y).get(x).toString().toCharArray()) {
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

	public void addTile(Point coordinates, MapTile tile) {
		addTile(coordinates.x, coordinates.y, tile);
	}

	public MapTile getMapTile(Point p, Direction dir) {
		return getMapTile(p.x, p.y, dir);
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

	private MapTile getDirectMapTile(int x, int y, Direction dir) {
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

		//System.out.println("Get "+x+","+y+" size: "+this.getWidth() + ","+this.getHeight());
		
		return this.get(y).get(x);
	}

	public boolean hasUnvisitedNeighboring(Point p) {
		int x = rearrangeXOffset(p.x);
		int y = rearrangeYOffset(p.y);
		MapTile tile = this.get(y).get(x);

		if (!tile.wallNorth) {
			try {
				if (!getDirectMapTile(x, y, Direction.NORTH).visited) return true;
			} catch (IndexOutOfBoundsException | NullPointerException e) {
				//e.printStackTrace();
				return true;
			}
		}

		if (!tile.wallEast) {
			try {
				if (!getDirectMapTile(x, y, Direction.EAST).visited) return true;
			} catch (IndexOutOfBoundsException | NullPointerException e) {
				//e.printStackTrace();
				return true;
			}
		}
		if (!tile.wallSouth) {
			try {
				if (!getDirectMapTile(x, y, Direction.SOUTH).visited) return true;
			} catch (IndexOutOfBoundsException | NullPointerException e) {
				//e.printStackTrace();
				return true;
			}
		}
		if (!tile.wallWest) {
			try {
				if (!getDirectMapTile(x, y, Direction.WEST).visited) return true;
			} catch (IndexOutOfBoundsException | NullPointerException e) {
				//e.printStackTrace();
				return true;
			}
		}

		return false;
	}

}
