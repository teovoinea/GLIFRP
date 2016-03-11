package backend;
import java.util.ArrayList;

public abstract class Node{
	private ArrayList<Node> adjacent;
	private int adjacentCount;
	private final int id;
	private boolean visited;

	public Node(int id){
		adjacent = new ArrayList<Node>();
		adjacentCount = 0;
		this.id = id;
		this.visited = false;
	}

	public Node(int id,ArrayList<Node> adjacencyList){
		adjacent = adjacencyList;
		adjacentCount = 0;
		this.id = id;
		this.visited = false;
	}
	
	/**
	 * Get the Id of a node
	 * 
	 * @return the id of the node
	 */
	public int getId(){
		return id;
	}

	public void setMarked(boolean mark){
		this.visited = mark;
	}

	public boolean isMarked(){
		return this.visited;
	}

	/**
	 * Get all the adjacent nodes as an Arraylist
	 * @return the Arraylist of adjacent nodes
	 */
	public ArrayList<Node> getAdjacent(){
		return adjacent;
	}

	/**
	 * Get node at the current position of the adjacency list and
	 * increment the pointer to which position you are at 
	 * @return the node at the current position in the adjacency list
	 */
	public Node getNextAdjacent(){
		if (adjacentCount == adjacent.size()){
			adjacentCount = 0;
		}
		Node adj = adjacent.get(adjacentCount);
		adjacentCount++;
		return adj;
	}

	/**
	 * Add a new adjacent node to this node (connect the node)
	 * 
	 * @param node to be added to the adjacency list
	 */
	public void addAdjacent(Node n){
		adjacent.add(n);
	}

	/**
	 * Add a list of adjacent nodes to this node (connect the node to multiple nodes)
	 * 
	 * @param arraylist of nodes to be connected to this node
	 */
	public void addAdjacent(ArrayList<Node> n){
		adjacent.addAll(n);
		return;
	}
	/**
	 * Return the degree of the node
	 *
	 * This is how many other nodes the node touches
	 * @return The degree of the node
	 */
	public int degree(){
		// make check to see if it touches self
		return adjacent.size();
	}
	
	public String toString(){
		return String.valueOf(this.getId());
	}

	/**
	 * Check whether two nodes are equal,based on their Id's
	 * 
	 * @param node you want to compare to
	 * @return whether or not these two nodes are the same node
	 */
	public boolean equals(Node node){
		return (this.getId() == node.getId());
	}
	
	public boolean hasNeighbour(Node node){
		for (int i = 0; i < adjacent.size();i++){
			if (adjacent.get(i).equals(node)){
				return true;
			}
		}
		return false;
	}
}