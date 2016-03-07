import java.util.LinkedList;
import java.util.Queue;

public class Usa extends Graph {
	private static int currentId = 0;
	
	public Usa(){
		super();
	}
	
	public State findStateByState(State s){
		// DFS/BFS through states, if State exists, return State, else return null
		return s;
	}
	
	public State findStateByCity(City c){
		// DFS/BFS, state.compareState(c), if true return State
		return null;
	}
	
	public City findLowestCrimeRate(){
		// DFS/BFS, int min, state.findLowestCrimeRate() if lower then min, set as new min, return min
		return null;
	}
	
	public void addCity(City c){
		// DFS/BFS then if state.compareState(c), state.insertCity(c), if state not found addNode(new State(currentId,c.getName())); then add city
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
}
