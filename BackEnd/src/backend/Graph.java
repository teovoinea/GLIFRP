package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

abstract class Graph implements Searchable{
	protected ArrayList<Node> nodes;
	protected ArrayList<Edge> edges;

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
		edges = new ArrayList<Edge>();
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
		edges = new ArrayList<Edge>();
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

	/**
	 * Connect 2 node objects with an edge
	 *
	 * Takes in two nodes and if they don't already have an edge between them, it will create an edge between them and save it in the edge list
	 * This method also increases the edge list size counter
	 *
	 * @param node to connect
	 * @param node to connect
	 */
	public void connectNodes(int weight, Node i, Node j){
		if (this.edgeWith(i, j) == null){
			i.addAdjacent(j);
			j.addAdjacent(i);
			edges.add(new Edge(weight,i,j));
		}
	}

	/**
	 * Generate an MST from the Source node
	 * @param source - node you start from
	 */
	private void generateMST(Node source){
		ArrayList<Node[]> sets = new ArrayList<>();
		HashMap<Node,Node> parent = new HashMap<>();
		HashMap<Node,Integer> key = new HashMap<>();
		ArrayList<Node> q = new ArrayList<>();
		for(int i = 0; i < nodes.size();i++){
    	key.put(nodes.get(i),Integer.MAX_VALUE);
    	parent.put(nodes.get(i), null);
			q.add(nodes.get(i));
    }
		key.put(source,0);
		while(!q.isEmpty()){
			Node u = minkey(q,key);
			q.remove(u);
			if(parent.get(u) != null){
				sets.add(new Node[]{u,parent.get(u)});
			}
			for(Node v: u.getAdjacent()){
				Edge edge = edgeWith(u,v);
				if(q.contains(v) && edge.getWeight() < key.get(u)){
					key.put(v,edge.getWeight());
					parent.put(v,u);
				}
			}
		}
	}

	/**
	 * Check whether two nodes have an edge and return it if they do
	 * @param v1 - the first node
	 * @param v2 - the second node
	 * @return Edge - the edge they have in common, null if they don't
	 */
	private Edge edgeWith(Node v1, Node v2){
		if (edges.size() == 0){
			return null;
		}
		
		for(Edge e : edges){
			if(e.hasVertex(v1) && e.hasVertex(v2)){
				return e;
			}
		}
		return null;
	}

	/**
	 * Get the minimum key from the MST
	 * 
	 * @param q - the List of all the nodes
	 * @param key - the Nodes with their corresponding keys
	 * @return the minimum key from the MST
	 */
	private Node minkey(ArrayList<Node> q, HashMap<Node,Integer> key){
		if(q.size() == 1) return q.get(0);
		Node min = q.get(0);
		for(Node i : q){
      if(key.get(i) < key.get(min)){
				min = i;
      }
    }
    return min;
	}
	
	/**
	 * Reset all the marked nodes to unmarked
	 */
	protected void resetMarked(){
		for (int i= 0; i < this.getNodeCount();i++){
			this.getNode(i).setMarked(false);
		}
	}
	
	/**
	 * Check whether the graph contains a specific node
	 * @param state - the node you are checking for 
	 * @return boolean - whether the node is contained or not
	 */
	public boolean contains(Node state){
		for (int i = 0; i < nodes.size(); i ++){
			if ( nodes.get(i).equals(state)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get whether or not the graph is empty
	 * @return boolean - whether there are any nodes or not
	 */
	public boolean isEmpty(){
		return this.getNodeCount() == 0;
	}

}
