
public class Point {

	public int x,y;
	
	public Point(int y, int x) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean equals(Point p){
		return p.getX() == this.x && p.getY() == this.y;
	}
	
	
	
}
