import java.util.*;

/**
  * @author Travis Martin
  * @version 2.0
  * @category Path finding algorithms
  */
public class node {
	
	String name;
	ArrayList<vertex> vertices = new ArrayList<vertex>();
	
	public node(String name, ArrayList<vertex> vertices){
		this.name = name;
		this.vertices = vertices;
	}
	public node() {
		// TODO Auto-generated constructor stub
	}
	public String getName(){
		return name;
	}
	public ArrayList<vertex> getVertices(){
		return vertices;
	}
	public static void main(String[] args){
	}
}
