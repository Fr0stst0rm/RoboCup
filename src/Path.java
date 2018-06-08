
import java.util.Stack;

public class Path extends Stack<Point> {

	public Path(Point p) {
		this.push(p);
	}
	
	public Path(int x, int y) {
		this(new Point(x, y));
	}

	public boolean hasAlternativePath(){
		Point p = this.peek();
		MapTile tile = BotStatus.mazeMap.getMapTile(p);
		
		if(tile.isStart) return false; // TODO Hat start walls oder nicht? 
		
		if(!tile.wallNorth) {
			if(!BotStatus.mazeMap.getMapTile(p,Direction.NORTH).visited) {
				return true;
			}
		}
		
		if(!tile.wallEast) {
			if(!BotStatus.mazeMap.getMapTile(p,Direction.EAST).visited) {
				return true;
			}
		}
		
		if(!tile.wallSouth) {
			if(!BotStatus.mazeMap.getMapTile(p,Direction.SOUTH).visited) {
				return true;
			}
		}
		
		if(!tile.wallWest) {
			if(!BotStatus.mazeMap.getMapTile(p,Direction.WEST).visited) {
				return true;
			}
		}
		
			return false;
	}
	
	public void followPath() {
		while(!this.isEmpty()) {
			Point p = this.pop();
			
		}
	}
	
}
