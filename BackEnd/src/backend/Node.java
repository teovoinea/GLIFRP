package backend;
import java.util.ArrayList;

public abstract class Node{
	private ArrayList<Node> adjacent;
	private int adjacentCount;
	private final int id;
	private boolean visited;

	/**
	 * Constructor for a node just with an id
	 * @param id - the id of the node
	 */
	public Node(int id){
		adjacent = new ArrayList<Node>();
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

	/**
	 * Set this node as marked or not
	 * @param mark - whether this node should be marked or not
	 */
	public void setMarked(boolean mark){
		this.visited = mark;
	}

	/**
	 * check whether this node is marked or not
	 * @return boolean - whether this node is marked or not
	 */
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
	 * String representation of this node
	 */
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
	
	/**
	 * Check whether a node has a specific neighbour 
	 * @param node - the neighbour node
	 * @return boolean - whether or not this node has the neighbour node
	 */
	public boolean hasNeighbour(Node node){
		for (int i = 0; i < adjacent.size();i++){
			if (adjacent.get(i).equals(node)){
				return true;
			}
		}
		return false;
	}
}