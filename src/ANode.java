

public class ANode {
	
	private ANode parent;
	
	private int ID; 
	
	private MapTile mapTile;
	
	private Point point;
	
	private int manhattenDistanceToGoal;//H Value
	private int G_Value = 0;
	
	public ANode(Point point, MapTile mapTile) {
		this.point = point;
		this.mapTile = mapTile;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public int getG_Value() {
		return G_Value;
	}


	public void setG_Value(int g_Value) {
		G_Value = g_Value;
	}
	
	public ANode getParent() {
		return parent;
	}

	public void setParent(ANode parent) {
		this.parent = parent;
	}
	
	public boolean passable() {
		return mapTile.visited&&!(mapTile.isChasm);
	}
	
	public boolean wallNorth() {
		return mapTile.wallNorth;
	}
	
	public boolean wallSouth() {
		return mapTile.wallSouth;
	}
	
	public boolean wallWest() {
		return mapTile.wallWest;
	}
	
	public boolean wallEast() {
		return mapTile.wallEast;
	}
	
	public Point getPoint() {
		return this.point;
	}

	public int getManhattenDistanceToGoal() {
		return manhattenDistanceToGoal;
	}

	public void setManhattenDistanceToGoal(int manhattenDistanceToGoal) {
		this.manhattenDistanceToGoal = manhattenDistanceToGoal;
	}


	public int getCost() {
		return manhattenDistanceToGoal + G_Value;
	}
}
