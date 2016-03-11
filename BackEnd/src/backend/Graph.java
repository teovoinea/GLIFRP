package backend;

import java.util.ArrayList;
import java.util.HashMap;
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
/*
	private void generateMST(int root){
		ArrayList<Node[]> sets = new ArrayList<>();
		HashMap<Node,Node> parent = new HashMap<>();
		HashMap<Node,Integer> key = new HashMap<>();
		ArrayList<Node> q = new ArrayList<>();
		for(int i = 0; i < nodes.size();i++){
    	key.put(nodes.get(i),Integer.MAX_VALUE);
    	parent.put(nodes.get(i), null);
			q.add(nodes.get(i));
    }
		key[source] = 0;
		while(!q.isEmpty()){
			Node u = minkey(q,key);
			q.remove(u);
			if(parent.get(u) != null){
				sets.add(new Node[]{u,parent.get(u)});
			}
			for(Node v: u.getAdjacent()){
				if(q.contains(v) & edge weight < key[u]){
					key.put(v,0);
					parent.put(v,u);
				}
			}
		}
	}

	private Node minkey(ArrayList<Node> q, HashMap<Node,Integer> key){
		if(q.size() == 1) return q.get(0);
		Node min = key.get(q.get(0));
		for(Node i : q){
      if(key.get(i) < key.get(min)){
				min = i;
      }
    }
    return min;
	}
*/
}
