
public class MapTile {

	public boolean wallNorth = false;
	public boolean wallEast = false;
	public boolean wallSouth = false;
	public boolean wallWest = false;

	public boolean victimNorth = false;
	public boolean victimEast = false;
	public boolean victimSouth = false;
	public boolean victimWest = false;

	public boolean isCheckPoint = false;
	public boolean isStart = false;
	public boolean isChasm = false;

	public boolean hasVictim = false;

	public boolean filled = false;

	public boolean visited = false;

	/*
	 * + corner - tile edge (horizontal) | tile edge (vertical) # wall C checkpoint ? not visited
	 */

	@Override
	public String toString() {
		String str = "";

		String noWallHorizontal = "--";
		String noWallVertical = "|";

		String wall = "#";
		String victim = "V";

		int size = 3;

		if (!visited) {
			for (int x = 0; x < (size + 2); x++) {
				for (int y = 0; y < (size + 2); y++) {
					if (isCorner(x, y, (size + 2))) {
						str += "+";
					} else {
						if ((y == 0) || (y == size + 1)) {
							str += "?";
						} else {
							str += "??";
						}

					}
				}
				str += "\n";
			}
			return str;
		}

		String filler = "  ";

		if (isStart) {
			filler = "St";
		}

		if (isCheckPoint) {
			filler = "CC";
		}

		if (isChasm) {
			filler = "66";
		}

		if (wallNorth) {
			str += "+";
			for (int x = 0; x < size; x++) {
				if (victimNorth) {
					str += victim + "" + victim;
				} else {
					str += wall + "" + wall;
				}
			}
			str += "+\n";
		} else {
			str += "+";
			for (int x = 0; x < size; x++) {
				str += noWallHorizontal;
			}
			str += "+\n";
		}

		for (int y = 0; y < size; y++) {
			if (wallWest) {
				if (victimWest) {
					str += victim;
				} else {
					str += wall;
				}
			} else {
				str += noWallVertical;
			}

			for (int x = 0; x < size; x++) {
				str += filler;
			}

			if (wallEast) {
				if (victimEast) {
					str += victim;
				} else {
					str += wall;
				}
			} else {
				str += noWallVertical;
			}
			str += "\n";
		}

		if (wallSouth) {
			str += "+";
			for (int x = 0; x < size; x++) {
				if (victimSouth) {
					str += victim + "" + victim;
				} else {
					str += wall + "" + wall;
				}
			}
			str += "+\n";
		} else {
			str += "+";
			for (int x = 0; x < size; x++) {
				str += noWallHorizontal;
			}
			str += "+\n";
		}

		return str;
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

	public boolean isDeadEnd() {
		int wallCount = 0;

		if (wallNorth) {
			wallCount++;
		}

		if (wallSouth) {
			wallCount++;
		}

		if (wallEast) {
			wallCount++;
		}

		if (wallWest) {
			wallCount++;
		}

		return (wallCount >= 3);
	}

	public boolean checkWall(Direction dir) {
		switch (dir) {
		case NORTH:
			return wallNorth;
		case EAST:
			return wallEast;
		case SOUTH:
			return wallSouth;
		case WEST:
			return wallWest;
		}
		return false;
	}

}
