

public class RightHandGapFillerMapThing {
	
	private Map map;
	private Map mapCopy; //for markers
	
	private Direction direction = Direction.NORTH;
	
	int steps = 0;
	private int x;//x is horizontal
	private int y;//y is vertical
	
	public RightHandGapFillerMapThing(Map map, int startX, int startY) {
		this.map = map;
		this.mapCopy = map;
		this.y = startY;
		this.x = startX;
	}

	//richtungsstack errechnen undabarbeiten
	void searchExit(){
		//TODO: handle floating walls
		//TODO: Walls setzen
		//TODO: ask if tile null
		//TODO: if not visited ask sensors for wall
	    steps++;
		if(x==0&&y==0) {
			//System.out.println("Found starting point");
			return;
		}
	    if(isdeadend(x,y)){
	        mapCopy.getMapTile(x,y).filled = true;
	    }
	    if(x >= map.size()|| y >= map.size()){
	        //System.out.println("Out of bounds");
	        return;
	    }
	    //right hand
	    if(direction.equals(Direction.NORTH)){
	        if(!mapCopy.getMapTile(x,y).wallNorth&&!mapCopy.getMapTile(x,y+1).filled){
	            y++;
	            BotUtility.moveToNextTile();
	            searchExit();
	        }else{
	        	BotUtility.rotate90DegreesRight();
	            direction = Direction.EAST;
	            searchExit();
	        }
	    }else if(direction.equals(Direction.SOUTH)){
	        if(!mapCopy.getMapTile(x,y).wallSouth&&!mapCopy.getMapTile(x,y-1).filled){
	            y--;
	            BotUtility.moveToNextTile();
	            searchExit();
	        }else{
	        	BotUtility.rotate90DegreesRight();
	            direction = Direction.WEST;
	            searchExit();
	        }
	    }else if(direction.equals(Direction.EAST)){
	        if(!mapCopy.getMapTile(x,y).wallEast&&!mapCopy.getMapTile(x-1,y).filled){
	            x--;
	            BotUtility.moveToNextTile();
	            searchExit();
	        }else{
	        	BotUtility.rotate90DegreesRight();
	            direction = Direction.SOUTH;
	            searchExit();
	            //fals drehen kein step hier step--
	        }
	    }else if(direction.equals(Direction.WEST)) {
	        if (!mapCopy.getMapTile(x,y).wallWest&&!mapCopy.getMapTile(x+1,y).filled) {
	            x++;
	            BotUtility.moveToNextTile();
	            searchExit();
	        } else {
	        	BotUtility.rotate90DegreesRight();
	            direction = Direction.NORTH;
	            searchExit();
	        }
	    }else{
	        //System.out.println("Welllllllll that was unexpected");
	    }
	}

	boolean isdeadend(int x, int y){
	    if((wallWest(x,y)+wallEast(x,y)+wallNorth(x,y)+wallSouth(x,y))>=3){
	        return true;
	    }else{
	        return false;
	    }
	}

	int wallEast(int a, int b){
	    if(mapCopy.getMapTile(x,y).wallEast){
	        return 1;
	    }else{
	        return 0;
	    }
	}
	int wallWest(int a, int b){
	    if(mapCopy.getMapTile(x,y).wallWest){
	        return 1;
	    }else{
	        return 0;
	    }
	}
	int wallNorth(int a, int b){
	    if(mapCopy.getMapTile(x,y).wallNorth){
	        return 1;
	    }else{
	        return 0;
	    }
	}
	int wallSouth(int a, int b){
	    if(mapCopy.getMapTile(x,y).wallSouth){
	        return 1;
	    }else{
	        return 0;
	    }
	}

}
