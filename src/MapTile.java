
public class MapTile {

	public boolean wallNorth = false;
	public boolean wallEast = false;
	public boolean wallSouth = false;
	public boolean wallWest = false;

	public boolean isCheckPoint = true;

	public boolean hasVictim = false;

	public boolean filled = false;

	public boolean visited = false;

	/*
	 * + corner - tile edge (horizontal) | tile edge (vertical) # wall C checkpoint ? not visited
	 */

	@Override
	public String toString() {
		String str = "";

		int size = 3;
		
		if(!visited) {
			for(int x = 0; x < size; x++) {
				for(int y = 0; y < size; y++) {
					if(isCorner(x,y,size)) {
						str += "+";
					} else {
						str += "?";
					}
				}	
			}
			return str;
		}
		
		char filler = ' ';
		
		if(isCheckPoint) {
			filler = 'C';
		}

		if (wallNorth) {
			str += "+";
			for (int x = 0; x < size; x++) {
				str += "#";
			}
			str += "+\n";
		} else {
			str += "+";
			for (int x = 0; x < size; x++) {
				str += "-";
			}
			str += "+\n";
		}

		for (int y = 0; y < size; y++) {
			if (wallWest) {
				str += "#";
			} else {
				str += "|";
			}

			for (int x = 0; x < size; x++) {
				str += filler;
			}

			if (wallEast) {
				str += "+";
				for (int x = 0; x < size; x++) {
					str += "#";
				}
				str += "+\n";
			} else {
				str += "|";
			}
			str += "\n";
		}

		if (wallSouth) {
			str += "+";
			for (int x = 0; x < size; x++) {
				str += "#";
			}
			str += "+\n";
		} else {
			str += "+";
			for (int x = 0; x < size; x++) {
				str += "-";
			}
			str += "+\n";
		}

		return super.toString();
	}

	private boolean isCorner(int x, int y, int size) {
		if ((x == 0) && (y == 0)) {
			return true;
		}
		if ((x == 0) && (y == size - 1)) {
			return true;
		}
		if ((x == size - 1) && (y == 0)) {
			return true;
		}
		if ((x == size - 1) && (y == size - 1)) {
			return true;
		}
		return false;
	}

}
