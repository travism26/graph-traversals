import java.util.*;
public class dijkstra{
	node town;
	String isVisited ="no";
	ArrayList<node> path;
	int distance;
	

	
	public dijkstra(node town, String isVisited, int distance, ArrayList<node> path){
		this.town = town;
		this.isVisited = isVisited;
		this.path = path;
		this.distance = distance;
	}
	public dijkstra() {
		// TODO Auto-generated constructor stub
	}
	public void setVisited(){
		isVisited = "no";
	}
	public void justVisited(){
		isVisited = "yes";
	}
	public void setDistance(int newValue){
		distance = newValue;
	}
	public node getTown(){
		return town;
	}
	public ArrayList<node> getPath(){
		return path;
	}
	public int getDistance(){
		return distance;
	}
	public String getIsVisited(){
		return isVisited;
	}
	public boolean isComplete(ArrayList<dijkstra> map){
		for(int i=0; i<map.size(); i++){
			if(map.get(i).getIsVisited() =="no"){
				return false;
			}
		}
		return true;
	}
	public boolean existShortest(ArrayList<dijkstra> dijkstraPath, dijkstra object){
		node object_node = object.getTown();
		
		for(int i =0; i<dijkstraPath.size(); i++){
			//assuming that the town_name is a unique value. otherwise you can put in an additional condition.
			if(dijkstraPath.get(i).getTown().getName() == object_node.getName()){
				return true;
			}
		}
		return false;
	}
	public boolean alreadyVisited(ArrayList<dijkstra> dijkstraPath, dijkstra object){
		for(int i=0; i<dijkstraPath.size(); i++){
			if(object.getTown().getName() == dijkstraPath.get(i).getTown().getName() && object.getIsVisited()=="yes"){
				return true;
			}
		}
		return false;
	}
	public void printResults(ArrayList<dijkstra> results){
		for(int i=0; i<results.size();i++){
			System.out.print("The minimum distance required to reach "+ results.get(i).getTown().getName()+" " + results.get(i).getDistance()+", and the path is: {");
			for(int j =0; j<results.get(i).getPath().size();j++){
				System.out.print(""+results.get(i).getPath().get(j).getName()+"-->");
			}
			//System.out.print(results.get(i).getTown().getName());
			System.out.print("}");
			System.out.println();
		}
	}
	public ArrayList<node> appendList(ArrayList<node> home, ArrayList<node> previous){
		ArrayList<node> append = new ArrayList<node>();
		for(int i = 0; i<previous.size(); i++){
			append.add(previous.get(i));
		}
		for(int i = 0; i<home.size(); i++){
			append.add(home.get(i));
		}
		return append;
		
	}
	public ArrayList<dijkstra> traverseGraph(node start_node){

		ArrayList<dijkstra> cloud = new ArrayList<dijkstra>();
		
		ArrayList<node> route = new ArrayList<node>();
		route.add(start_node);
		
		String didVisit = "yes";
		String didntVisit = "no";
		
		dijkstra shortpath = new dijkstra(start_node, didVisit, 0, route);
		
		/*
		 * This loop will run until we backtrack back to the starting node.
		 * This means we searched every node and found all the connecting 
		 * nodes.
		 */
		
		ArrayList<dijkstra> shortestPath = new ArrayList<dijkstra>();
		shortestPath.add(shortpath);
		cloud.add(shortpath);
		boolean complete = false;
		while(complete == false){
			int shortestDistance = 0;
			/*
			 * I need to search through the shortest path list to find the first value in the list that has
			 * a value didVisite == 'yes' if we have one this means we need to look through all the vertices
			 * and choose one with the smaller value and change the from: didVisite = 'no' => didVisite = 'yes'
			 */
			ArrayList<node> currentPath = new ArrayList<node>();
			for(int i=0; i<shortestPath.size(); i++){
				currentPath.add(shortestPath.get(i).getTown());
				if(shortestPath.get(i).getIsVisited() =="yes"){
					ArrayList<vertex> vertices = shortestPath.get(i).getTown().getVertices();
					for(int j =0; j<vertices.size(); j++){
							int addDistance = shortestPath.get(i).getDistance() + vertices.get(j).getDistance();
							ArrayList<node> addPath = new ArrayList<node>();
							addPath.add(vertices.get(j).getadjacent());
							addPath = appendList(addPath, shortestPath.get(i).getPath());
							node addTown = vertices.get(j).getadjacent();
							dijkstra addObject = new dijkstra(addTown, didntVisit, addDistance, addPath);
							//check to see if the object already exists in the array if not we add it
							if(existShortest(shortestPath, addObject) == false){
								shortestPath.add(addObject);								
							}
							/*
							 * If the node does exist in the list we need to update the
							 * distance IFF the distance < current_distance
							 */
							else{
								if(alreadyVisited(shortestPath, addObject)){
									;
								}else{
									/*
									 * we need to ensure that the existing path has not already been visited if it was we do NOT
									 * change the current distance because it is already the shortest distance.
									 */
									for(int l=0; l<shortestPath.size(); l++){
										if(shortestPath.get(l).getTown().getName() == addObject.getTown().getName()){
											if(addObject.getDistance() < shortestPath.get(l).getDistance()){
												shortestPath.get(l).setDistance(addObject.getDistance());
												shortestPath.get(l).path = addObject.getPath();
											}
										}
									}
									
								}
							}//else							
					}//for J
					//
				}//if shortestpath ==yes
			}//for i
			/*
			 * We need to loop through the shortestpath list and find the shortest distance and
			 * sent the isVisited value to "yes".		
			 */
			for(int i=0; i<shortestPath.size(); i++){
				//we will use -1 as an indicator that we need to change it to the first un-visited path
				if(shortestDistance == 0 && shortestPath.get(i).getIsVisited() == "no"){
					shortestDistance = i;
				}
				if(shortestDistance != 0 && shortestPath.get(i).getDistance() < shortestPath.get(shortestDistance).getDistance() && (shortestPath.get(i).getIsVisited() == "no")){
					shortestDistance = i;
				}
			}
			shortestPath.get(shortestDistance).justVisited();
			complete = isComplete(shortestPath);
		}//while loop
		printResults(shortestPath);
	return shortestPath;
	}
	public ArrayList<node> traverseGraph(node start_node, node finish_node){

		ArrayList<dijkstra> cloud = new ArrayList<dijkstra>();
		
		ArrayList<node> route = new ArrayList<node>();
		route.add(start_node);
		
		String didVisit = "yes";
		String didntVisit = "no";
		
		dijkstra shortpath = new dijkstra(start_node, didVisit, 0, route);
		
		/*
		 * This loop will run until we backtrack back to the starting node.
		 * This means we searched every node and found all the connecting 
		 * nodes.
		 */
		
		ArrayList<dijkstra> shortestPath = new ArrayList<dijkstra>();
		shortestPath.add(shortpath);
		cloud.add(shortpath);
		boolean complete = false;
		while(complete == false){
			int shortestDistance = 0;
			/*
			 * I need to search through the shortest path list to find the first value in the list that has
			 * a value didVisite == 'yes' if we have one this means we need to look through all the vertices
			 * and choose one with the smaller value and change the from: didVisite = 'no' => didVisite = 'yes'
			 */
			ArrayList<node> currentPath = new ArrayList<node>();
			for(int i=0; i<shortestPath.size(); i++){
				currentPath.add(shortestPath.get(i).getTown());
				if(shortestPath.get(i).getIsVisited() =="yes"){
					ArrayList<vertex> vertices = shortestPath.get(i).getTown().getVertices();
					for(int j =0; j<vertices.size(); j++){
							int addDistance = shortestPath.get(i).getDistance() + vertices.get(j).getDistance();
							ArrayList<node> addPath = new ArrayList<node>();
							addPath.add(vertices.get(j).getadjacent());
							addPath = appendList(addPath, shortestPath.get(i).getPath());
							node addTown = vertices.get(j).getadjacent();
							dijkstra addObject = new dijkstra(addTown, didntVisit, addDistance, addPath);
							//check to see if the object already exists in the array if not we add it
							if(existShortest(shortestPath, addObject) == false){
								shortestPath.add(addObject);								
							}
							/*
							 * If the node does exist in the list we need to update the
							 * distance IFF the distance < current_distance
							 */
							else{
								if(alreadyVisited(shortestPath, addObject)){
									;
								}else{
									/*
									 * we need to ensure that the existing path has not already been visited if it was we do NOT
									 * change the current distance because it is already the shortest distance.
									 */
									for(int l=0; l<shortestPath.size(); l++){
										if(shortestPath.get(l).getTown().getName() == addObject.getTown().getName()){
											if(addObject.getDistance() < shortestPath.get(l).getDistance()){
												shortestPath.get(l).setDistance(addObject.getDistance());
												shortestPath.get(l).path = addObject.getPath();
											}
										}
									}
									
								}
							}//else							
					}//for J
					//
				}//if shortestpath ==yes
			}//for i
			/*
			 * We need to loop through the shortestpath list and find the shortest distance and
			 * sent the isVisited value to "yes".		
			 */
			for(int i=0; i<shortestPath.size(); i++){
				//we will use -1 as an indicator that we need to change it to the first un-visited path
				if(shortestDistance == 0 && shortestPath.get(i).getIsVisited() == "no"){
					shortestDistance = i;
				}
				if(shortestDistance != 0 && shortestPath.get(i).getDistance() < shortestPath.get(shortestDistance).getDistance() && (shortestPath.get(i).getIsVisited() == "no")){
					shortestDistance = i;
				}
			}
			shortestPath.get(shortestDistance).justVisited();
			complete = isComplete(shortestPath);
		}//while loop
		//printResults(shortestPath);
		ArrayList<node> finish_path = new ArrayList<node>();
		for(int i=0; i<shortestPath.size(); i++){
			if(shortestPath.get(i).getTown().getName()== finish_node.getName()){
				finish_path = shortestPath.get(i).getPath();
			}
			
		}
		System.out.println("The following shortest path from "+ start_node.getName()+" to " + finish_node.getName()+ " is");
		for(int i=0; i<finish_path.size(); i++){
			System.out.print(finish_path.get(i).getName()+" --> ");
		}
			System.out.print(" Finished");
	return finish_path;
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
		ArrayList<dijkstra> shortestPath = null;
		shortestPath = dobj.traverseGraph(fredericton);
	}
}