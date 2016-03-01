import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class MST implements Searchable{
	Node root;

	public MST(Graph g,int start){
		root = g.getNode(start);
		// set up MST
	}

	/* SEARCHABLE */
	public void BFS(Node n){
		Queue<Node> nQ = new LinkedList<Node>();
		n.setMarked(true);
		nQ.add(n);
		while (!nQ.isEmpty()){
			Node current = nQ.remove();
			System.out.println(current);
			for(Node w : current.getAdjacent()){
				if (!w.isMarked()){
					w.setMarked(true);
					nQ.add(w);
				}
			}
		}
	}

	public void DFS(Node n){
		n.setMarked(true);
		System.out.println(n);
		for(Node w : n.getAdjacent()){
			if (!w.isMarked()){
				DFS(w);
			}
		}
	}

	/**
	 * Check whether a given node is connected to another node
	 * 
	 * @param The node you want to check for
	 * @param  The other node you want to check for
	 * @return Whether or not the two nodes are connected
	 */
	public boolean hasPathBetween(Node n, Node n2){
		return true;
	}
	
	/**
	 * Return the path of nodes from one node to another
	 * 
	 * @param The start node
	 * @param  The end node 
	 * @return Iterable array of all the nodes
	 */
	public Iterable pathBetween(Node n, Node n2){
		return new ArrayList();
	}
}