import java.util.LinkedList;

import lejos.nxt.LCD;

public class Map extends LinkedList<LinkedList<MapTile>> {

	Point startPoint = new Point(0, 0);

	Point offset = new Point(0, 0);

	public Point getStartPoint() {
		return startPoint;
	}

	public MapTile getMapTile(Point p) {
		return this.getMapTile(p.x, p.y);
	}

	public MapTile getMapTile(int x, int y) {
		int newX = rearrangeXOffset(x);
		int newY = rearrangeYOffset(y);
		return this.get(newX).get(newY);
	}

	public void setMapTile(int x, int y) {
		int newX = rearrangeXOffset(x);
		int newY = rearrangeYOffset(y);

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

	public int getWidth() {
		return this.size();
	}

	public int getHeight() {
		int size = 0;
		for (LinkedList<MapTile> column : this) { //column
			if (size < column.size()) {
				size = column.size();
			}
		}
		return size;
	}

}
