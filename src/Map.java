import java.util.LinkedList;

public class Map extends LinkedList<LinkedList<MapTile>>{
	
	Point startPoint = new Point(0, 0); 
	
	public Point getStartPoint() {
		return startPoint;
	}
	
	public MapTile getMapTile(Point p) {
		return this.getMapTile(p.x, p.y);
	}
	
	public MapTile getMapTile(int x, int y) {
		return this.getMapTile(x, y);
	}

	
	
}
