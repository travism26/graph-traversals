import java.util.*;
public class vertex{
	
	node currentNode;
	node adjacentNode;
	int distance;
	public vertex(node currentNode, node adjacentNode, int distance){
		this.currentNode = currentNode;
		this.adjacentNode = adjacentNode;
		this.distance = distance;
	}
	
	public vertex() {
		// this just a default constructor
	}

	/*
	 * This will add a new vertex to the list and return it.
	 */
	public ArrayList<vertex> addToList(node currentNode, node adjacentNode, int distance){
		
		//Create a new list add the new vertex and return the list
		ArrayList<vertex> newList = currentNode.getVertices();
		if(doesExist(currentNode, adjacentNode)){
			System.out.println("There already is a connection between these two cities");
			
			for(int i =0; i<currentNode.getVertices().size(); i++){
				
				if(adjacentNode.getVertices().get(i).getadjacent().getName() == currentNode.getName()){
					System.out.println("/-----------WARNING OVERLAPPING VERTICES-------------/");
					System.out.println("I found the connection between " +currentNode.getName()+ " and " + 
					adjacentNode.getName()+" with a distance of "+ currentNode.getVertices().get(i).getDistance());
					System.out.println("Old distance overwritten with new Distance: " + distance);
					System.out.println("/-----------Old Distance Overwritten-----------------/");
					
					ArrayList<vertex> adjacentList = adjacentNode.getVertices();
					
					adjacentList.get(i).setDistance(distance);
					newList.get(i).setDistance(distance);
					return newList;
				}
				else{
					;
				}					
			}			
			//return newList;
		}
		else{
			System.out.println("There is no connection I will add a connection between them now.");
			vertex newVertex = new vertex(currentNode, adjacentNode, distance);
			newList.add(newVertex);
			vertex newVertex2 = new vertex(adjacentNode, currentNode, distance);
			ArrayList<vertex> adjacentList = adjacentNode.getVertices();
			adjacentList.add(newVertex2);
		}
		return newList;
	}
	/* 
	 * search through the adjacent vertices list and look to see
	 * if a similar path exists return true if so. if a similar path exist
	 * we need to make sure that there are no path between 2 cities
	 * with 2 different paths ie: distance to QC from MTL = 200KM
	 * and flip side Distance from MTL to QC = 100KM distance is
	 * inconsistent.
	 */
	public boolean doesExist(node current, node adjacent){
		
		for(int i = 0; i<adjacent.getVertices().size(); i++){
			
			if(adjacent.getVertices().get(i).getadjacent().getName() == current.getName()){
				return true;
			}
		}
		return false;
	}
	public node getCurrent(){
		return currentNode;
	}
	public node getadjacent(){
		return adjacentNode;
	}
	public int getDistance(){
		return distance;
	}
	public void setDistance(int newDistance){
		distance = newDistance;
	}
	public static void main(String[] args){
	
	
	}

}
