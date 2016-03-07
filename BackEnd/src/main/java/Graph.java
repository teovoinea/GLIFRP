import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

abstract class Graph implements Searchable{
	private ArrayList<Node> nodes;
	private ArrayList<MST> spanningTrees;

	/**
	 * Constructor for the graph object.
	 * 
	 * Creates a list of nodes and edges, initializes both and saves list of nodes to node list.
	 * Also creates counters for size of node list and edge list
	 * 
	 * @param list of nodes that are in your graph
	 */
	public Graph(ArrayList<Node> nodes){
		this.nodes = nodes;
	}

	/**
	 * Constructor for the graph object.
	 * 
	 * Creates a list of nodes and edges.
	 * Also creates counters for size of node list and edge list
	 * 
	 * @param list of nodes that are in your graph
	 */
	public Graph(){
		nodes = new ArrayList<Node>();
	}

	/**
	 * Getter for the amount of nodes in your graph
	 * 
	 * @return gets the amount of nodes in the node list
	 */
	public int getNodeCount(){
		return nodes.size();
	}
	
	/**
	 * Get Node by Id.
	 * 
	 * Returns the node with the matching id.
	 * 
	 * @param id of the node you are looking for
	 * @return the node whose id matches the id passed in
	 */
	public Node getNode(int id){
		return nodes.get(id);
	}
	
	/**
	 * Add a node to the list of nodes and increase the counter for amount of nodes
	 * 
	 * @param node to add to the list of nodes
	 */
	public void addNode(Node n){
		nodes.add(n);
	}

	public int maxDegree(){
		int max = 0;
		for (int i = 0; i < getNodeCount();i++){
			if (this.getNode(i).degree() > max){
				max = this.getNode(i).degree();
			}
		}
		return max;
	}
	
	/**
	 * Connect 2 node objects with an edge
	 * 
	 * Takes in two nodes and if they don't already have an edge between them, it will create an edge between them and save it in the edge list
	 * This method also increases the edge list size counter
	 * 
	 * @param node to connect
	 * @param node to connect
	 */
	public void connectNodes(Node i, Node j){
		if (!i.hasNeighbour(j) || !j.hasNeighbour(i)){
			i.addAdjacent(j);
			j.addAdjacent(i);
		}
	}

	private void generateMST(int root){
		// ill add this in when we talk about it in class
	}
}