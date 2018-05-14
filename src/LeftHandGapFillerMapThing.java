
public class LeftHandGapFillerMapThing {
	
	private static Map map;
	private static Map mapCopy; //for markers
	
	private String direction = "N";
	
	int steps = 0;
	private int x = 0;//x ist horizontal
	private int y = 0;//y ist vertikal
	
	public LeftHandGapFillerMapThing(Map map) {
		this.map = map;
		this.mapCopy = map;
		// TODO Auto-generated constructor stub
	}

	void searchExit(){
	    steps++;
	    if(isdeadend(x,y)){
	        mapCopy.getMapTile(x,y).filled = true;
	    }
	    if(a >= mazemaze.size()){
	        std::cout<<"Potato"<<std::endl;
	        return;
	    }
	    //rechtehand
	    if(direction == "down"){
	        if(mazemaze.at(a).at(b+1)==' '){
	            b++;
	            searchExit(mazemazecopy);
	        }else{
	            direction = "left";
	            searchExit(mazemazecopy);
	        }
	    }else if(direction == "up"){
	        if(mazemaze.at(a).at(b-1)==' '){
	            b--;
	            searchExit(mazemazecopy);
	        }else{
	            direction = "right";
	            searchExit(mazemazecopy);
	        }
	    }else if(direction == "left"){
	        if(mazemaze.at(a-1).at(b)==' '){
	            a--;
	            searchExit(mazemazecopy);
	        }else{
	            direction = "up";
	            searchExit(mazemazecopy);
	            //fals drehen kein step hier step--
	        }
	    }else if(direction == "right") {
	        if (mazemaze.at(a + 1).at(b) == ' ') {
	            a++;
	            searchExit(mazemazecopy);
	        } else {
	            direction = "down";
	            searchExit(mazemazecopy);
	        }
	    }else{
	        std::cout<<"What the f?ck is happening?"<<std::endl;
	    }
	}

	boolean isdeadend(int a, int b){
	    if((wallright(a,b)+wallleft(a,b)+wallfront(a,b)+wallback(a,b))>=3){
	        return true;
	    }else{
	        return false;
	    }
	}

	int wallleft(int a, int b){
	    if(mapCopy.at(a).at(b-1)=='#'){
	        return 1;
	    }else{
	        return 0;
	    }
	}
	int wallright(int a, int b){
	    if(mapCopy.at(a).at(b+1)=='#'){
	        return 1;
	    }else{
	        return 0;
	    }
	}
	int wallfront(int a, int b){
	    if(mapCopy.at(a-1).at(b)=='#'){
	        return 1;
	    }else{
	        return 0;
	    }
	}
	int wallback(int a, int b){
	    if(mapCopy.at(a+1).at(b)=='#'){
	        return 1;
	    }else{
	        return 0;
	    }
	}

}
