import java.util.LinkedList;

import lejos.nxt.LCD;

public class Map extends LinkedList<LinkedList<MapTile>> {
	// zuert reihen (y) , dann spalten (x)

	Point startPoint = new Point(0, 0);

	Point offset = new Point(0, 0);
	
	public void addTile(int x, int y, MapTile tiel) {
		int newX = rearrangeXOffset(x);
		int newY = rearrangeYOffset(y);
				
		while(this.size() <= newY) {
			this.add(new LinkedList<MapTile>());
		}
		while(this.get(newY).size() <= newX) {
			this.get(newY).add(new MapTile());
		}
		
		this.get(newY).add(newX, tiel);
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

		for (LinkedList<MapTile> line : this) {
			printHelper = new LinkedList<LinkedList<Character>>();
			for (MapTile mapTile : line) {
				int newLines = 0;
				for (Character character : mapTile.toString().toCharArray()) {
					if (printHelper.size() <= (newLines + 1)) {
						printHelper.add(new LinkedList<Character>());
					}
					if (!character.equals('\n')) {
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

}
