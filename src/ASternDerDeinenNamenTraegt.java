

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ASternDerDeinenNamenTraegt {
	
	private Point goal;
	private Point start;

	private ANode startNode;
	private ANode currNode;
	private ANode currNNode = null;
	private ANode currENode = null;
	private ANode currSNode = null;
	private ANode currWNode = null;

	private Map map = BotStatus.mazeMap;

	private int movementCost = 10;	//G Value

	private List<ANode> nodes = new ArrayList<>();
	private List<ANode> openList = new ArrayList<>();
	private List<ANode> closedList = new ArrayList<>();
	private boolean searching = true;

	public ASternDerDeinenNamenTraegt(Point start, Point goal) {
		this.start = start;
		this.goal = goal;
	}
	
	private void createNodes() {
		int ID = 0;
		for(int y = 0; y < map.size(); y++){
			LinkedList<MapTile> YMap = map.get(y);
			for(int x = 0; x < YMap.size(); x++){
				ANode anode = new ANode(new Point(x, y), YMap.get(x));
				if(anode.passable()) {
					anode.setManhattenDistanceToGoal(calculateManhattenDistance(anode.getPoint()));
				}
				anode.setID(ID);
				if(x==start.getX()&&y==start.getY()) {
					startNode = anode;
				}
				nodes.add(anode);
			}
		}
	}
	
	private void calculatePath() {
		//System.out.println("Adding to closed list X: " + startNode.getPoint().getX() + " Y: " + startNode.getPoint().getY() + "\n" );
		closedList.add(startNode);
		currNode = startNode;
		//set all reachable(do not forget to check for walls) from current node to open list and parent them (g value is parent g value + movement cost) to start node
		setUPReachableNodes();
		setUpNode(currNNode);
		setUpNode(currENode);
		setUpNode(currSNode);
		setUpNode(currWNode);
		
		if(currNNode!=null){
			checkNode(currNNode);
		}
		if(currSNode!=null){
			checkNode(currSNode);
		}
		if(currWNode!=null){
			checkNode(currWNode);
		}
		if(currENode!=null){
			checkNode(currENode);
		}

		//while true
		while(searching){
			//find least cost (iterate over open list, safe Point in temp variable to prevent iterating twice)
			//go to one with least cost (iterate over open list)
			//take the one with least cost from open list and add it to closed list
			currNode = getNearestNode();
			closedList.add(currNode);
			//System.out.println("Adding to closed list X: " + currNode.getPoint().getX() + " Y: " + currNode.getPoint().getY() + "\n" );
			setUPReachableNodes();
			//check reachable nodes around

			//if already on closed list -> do nothing
				//if goal node
				//parent it to curr node
				//return;
			//if already on open list -> do special check
				//if g value + movement cost from curr node < g cost open list node
				//reparent to current node
			if(currNNode!=null){
				checkNode(currNNode);
			}
			if(currSNode!=null){
				checkNode(currSNode);
			}
			if(currWNode!=null){
				checkNode(currWNode);
			}
			if(currENode!=null){
				checkNode(currENode);
			}
		}

	}

	private void checkNode(ANode node){
		if(node.getPoint().equals(goal)){
			node.setParent(currNode);
			//System.out.println("Removing from open list X: " + node.getPoint().getX() + " Y: " + node.getPoint().getY() + "\n" );
			openList.remove(node);
			//System.out.println("Adding to closed list X: " + node.getPoint().getX() + " Y: " + node.getPoint().getY() + "\n" );
			closedList.add(node);
			searching = false;
		}else if (openList.contains(node)){
			if(currNode.getG_Value()+movementCost < node.getG_Value()){
				node.setParent(currNode);
				node.setG_Value(currNode.getG_Value()+movementCost);
			}
		}else{
			setUpNode(node);
		}
	}

	private ANode getNearestNode() {
		int lowestCost = 0;
		int lowestCostIndex = -1;

		for(int i = 0; i < openList.size(); i++){
			if(lowestCost == 0){
				lowestCost = openList.get(i).getCost();
				lowestCostIndex = i;
			}else if(lowestCost > openList.get(i).getCost()){
				lowestCost = openList.get(i).getCost();
				lowestCostIndex = i;
			}
		}

		ANode temp = openList.get(lowestCostIndex);
		//System.out.println("Removing from open list X: " + temp.getPoint().getX() + " Y: " + temp.getPoint().getY() + "\n" );

		openList.remove(lowestCostIndex);

		return temp;
	}

	private void setUPReachableNodes(){
		currNNode = null;
		currENode = null;
		currSNode = null;
		currWNode = null;

		for(ANode anode : nodes){
			if(anode.getPoint().getX() == currNode.getPoint().getX()&&anode.getPoint().getY() == currNode.getPoint().getY()+1&&anode.passable()&&!currNode.wallNorth()&&!closedList.contains(anode)){
				currNNode = anode;
			}else if(anode.getPoint().getX() == currNode.getPoint().getX()+1&&anode.getPoint().getY() == currNode.getPoint().getY()&&anode.passable()&&!currNode.wallEast()&&!closedList.contains(anode)){
				currENode = anode;
			}else if(anode.getPoint().getX() == currNode.getPoint().getX()&&anode.getPoint().getY() == currNode.getPoint().getY()-1&&anode.passable()&&!currNode.wallSouth()&&!closedList.contains(anode)){
				currSNode = anode;
			}else if(anode.getPoint().getX() == currNode.getPoint().getX()-1&&anode.getPoint().getY() == currNode.getPoint().getY()&&anode.passable()&&!currNode.wallWest()&&!closedList.contains(anode)){
				currWNode = anode;
			}
		}
	}

	private void setUpNode(ANode node){
		if(node !=null){
			node.setParent(currNode);
			node.setG_Value(currNode.getG_Value()+movementCost);
			//System.out.println("Adding to open list X: " + node.getPoint().getX() + " Y: " + node.getPoint().getY() + "\n" );
			openList.add(node);
		}
	}

	
	private int calculateManhattenDistance(Point start) {


	    int X = Math.abs(start.getX()-goal.getX()); 
	    int Y = Math.abs(start.getY()-goal.getY()); 

		return X + Y;
	}
	
	private Path createPath() {
		ANode goal = null;
		for(ANode anode: closedList){
			if(anode.getPoint().equals(this.goal)){
				goal = anode;
			}
		}

		ANode temp = goal;
		Path path = new Path(goal.getPoint());
		//System.out.println("X: " + temp.getPoint().getX() + " Y: " + temp.getPoint().getY() + "\n" );

		//backtrack from start to finish via parent
		//iterate backwards
		while(temp.getParent()!=null){
			temp = temp.getParent();
			//System.out.println("X: " + temp.getPoint().getX() + " Y: " + temp.getPoint().getY() + "\n" );
			path.push(temp.getPoint());
		}
		return path;
	}
	
	public Path getPath() {
		createNodes();
		calculatePath();
		return createPath();
	}

}
