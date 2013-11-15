import java.util.ArrayList;


public class graphTraversal {
	
	
	public boolean isVisited(node current, ArrayList<node> visitedList){
		for(int i =0; i<visitedList.size(); i++){
			if(current.getName() == visitedList.get(i).getName()){
				return true;
			}
		}
		return false;
	}
	
	public node getPrevious(node current, ArrayList<node> visited){
		
		int position=0;
		for(int i=0; i<visited.size(); i++){
			if(current.getName() == visited.get(i).getName()){
				position = i-1;
			}
		}
		if(visited.get(position)==null){
			return visited.get(0);
		}
		return visited.get(position);
		
	}
	public boolean deadEnd(node current, ArrayList<node> visited){
		int counter =0;
		for(int i =0; i<current.getVertices().size(); i++){
			
			for(int j =0; j<visited.size(); j++){
				
				if(current.getVertices().get(i).adjacentNode.getName() 
						== visited.get(j).getName()){
					counter++;
				}
			}
			if(counter == current.getVertices().size()){
				return true;
			}
		}
		return false;
	}
	/*
	 * This method is used to determine when we backtrack to the starting node
	 * we do not miss any other connections from the starting node.
	 */
	public boolean allVisited(ArrayList<node> visited, node current){
		String currentName;
		boolean isFinished = true;
		//top loop check the vertices
		for(int i =0; i<current.getVertices().size(); i ++){
			currentName = current.getVertices().get(i).getadjacent().getName();
			//inner loop checks the visited list
			for(int j = 0; j<visited.size(); j++){
				if(visited.get(j).getName() == currentName){
					isFinished = true;
					break;
				}else{
					isFinished = false;
				}
			}
			if(isFinished == false){
				return false;
			}
		}
		return isFinished;
	}
	/*
	 * Traverse graph
	 */
	public ArrayList<node> traverseGraph(node start_node){
		ArrayList<node> visited = new ArrayList<node>();
		node current =null;
		node starting;
		starting = start_node;
		boolean finished = false;
		while(finished ==false){
			node temp = null;
			int smallestDistance= 10000;
			//set the current node to starting for the first pass;
			if(current == null){
				current = starting;
			}
			
			//this is to ensure we do not add a node twice, if this condition is not met it will end the loop and return the list
			if(isVisited(current, visited)==false){
				visited.add(current);
			}else
				break;
			/*
			 * we need to find the vertex with the smallest distance AND make 
			 * sure that the adjacent node is not in the visited list.
			 */
			for(int j = 0; j<current.getVertices().size(); j++){				
				
				if(smallestDistance >= current.getVertices().get(j).getDistance() 
				&& isVisited(current.getVertices().get(j).adjacentNode, visited)==false){
					smallestDistance = current.getVertices().get(j).getDistance();
					temp = current.getVertices().get(j).adjacentNode;
				}
				//if there are no more nodes to visit we need to backtrack 
				//set j = 0 and change the current node to the previous one
				else if(deadEnd(current, visited)){
					j=0;
					current = getPrevious(current, visited);
					if(current.getName() == starting.getName() && allVisited(visited, current)){
						finished = true;
						break;
					}
				}
			}
				current = temp;
		}//searching forloop
	return visited;
	}
	public static void main(String[] args){
		vertex obj = new vertex();
		graphTraversal nobj = new graphTraversal();
		dijkstra dobj = new dijkstra();
		
		/*
		 * This is where we create our vertices for each node
		 */
		ArrayList<vertex> firstList = new ArrayList<vertex>();
		ArrayList<vertex> secondList = new ArrayList<vertex>();
		ArrayList<vertex> thirdList = new ArrayList<vertex>();
		ArrayList<vertex> forthList = new ArrayList<vertex>();
		ArrayList<vertex> fifthList = new ArrayList<vertex>();
		ArrayList<vertex> sixthList = new ArrayList<vertex>();
		ArrayList<vertex> seventhList = new ArrayList<vertex>();
		ArrayList<vertex> eighthList = new ArrayList<vertex>();
		
		/*
		 * This is where we create the nodes / locations
		 */
		node fredericton = new node("Fredericton", firstList);
		node saintJohn = new node("Saint John", secondList);
		node miramichi = new node("Miramichi", thirdList);
		node bathurst = new node("Bathurst", forthList);		
		node moncton = new node("Moncton", fifthList);
		node oromocto = new node("Oromocto", sixthList);
		node saintLeonard = new node("Saint Leonard", seventhList);
		node woodStock = new node("Wood Stock", eighthList);
				
		/*
		 * This is where we add the vertices to the nodes aka connecting the
		 * nodes together.
		 */
		firstList = obj.addToList(fredericton,oromocto, 22);
		firstList = obj.addToList(fredericton,miramichi, 168);
		firstList = obj.addToList(fredericton, woodStock, 93);
		
		secondList = obj.addToList(saintJohn, oromocto, 90);
		secondList = obj.addToList(saintJohn, moncton, 154);
		
		thirdList = obj.addToList(miramichi, moncton, 148);
		thirdList = obj.addToList(miramichi, bathurst, 78);
		
		forthList = obj.addToList(bathurst, saintLeonard, 263);
		
		fifthList = obj.addToList(moncton, oromocto, 161);
		
		/*
		 * oromocto has all its vertices filled however if you want to 
		 * add them it will no difference the addToList can handle duplicate 
		 * entries it will take the most recent entry as a new value and update
		 * all vertices so there are no inconsistent data.
		 */
		//incorrent data to test addToList duplicate exception
		seventhList = obj.addToList(saintLeonard, woodStock, 150);
		
		//example of duplicate data this is the correct data.
		eighthList = obj.addToList(woodStock, saintLeonard, 148);
		
		//lets traverse the graph starting at Fredericton
		ArrayList<node> traverse = new ArrayList<node>();
		traverse = nobj.traverseGraph(fredericton);
		
		System.out.println("Here is the graph traversal it choose the smallest vertex in terms of \"distance\".");
		for(int i=0; i<traverse.size(); i++){
			System.out.print(traverse.get(i).getName()+", ");
		}
	}
}
