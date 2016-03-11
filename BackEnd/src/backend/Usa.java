package backend;
import java.util.LinkedList;
import java.util.Queue;

public class Usa extends Graph {
	private static int currentId = 0;
	public static final int FIND_STATE_BY_STATE_FLAG = 1;
	public static final int FIND_STATE_BY_CITY_FLAG = 2;
	public static final int FIND_LOWEST_CRIME_RATE_FLAG = 3;
	public static final int ADD_CITY_FLAG = 4;
	
	
	public Usa(){
		super();
	}
	
	public State findStateByState(State s){
		State state = (State) nodes.get(0);
		State result = (State) rDFS(s,state,FIND_STATE_BY_STATE_FLAG);
		if (result != null){
			return result;
		}
		return null;
	}
	
	public State findStateByCity(City c){
		// DFS/BFS, state.compareState(c), if true return State
		State state = (State) nodes.get(0);
		State result = (State) rDFS(c,state,FIND_STATE_BY_CITY_FLAG);
		if (result != null){
			return result;
		}
		return null;
	}
	
	public City findLowestCrimeRate(){
		// DFS/BFS, int min, state.findLowestCrimeRate() if lower then min, set as new min, return min
		return null;
	}
	
	public void addCity(City c){
		State state = (State) nodes.get(0);
		DFS(c,state,ADD_CITY_FLAG);
		this.resetMarked();
	}
	
	public void addState(State c){
		assert !this.contains(c);
		addNode(c);
	}
	
	public boolean contains(State state){
		for (int i = 0; i < nodes.size(); i ++){
			if ( ((State) nodes.get(i)).equalsName(state)){
				return false;
			}
		}
		return true;
	}

	public Object rDFS(Object target,Node curNode,int flag){
		curNode.setMarked(true);
		for(Node w : curNode.getAdjacent()){
			if (!w.isMarked()){ 
				w.setMarked(true);
				switch(flag){
					case FIND_STATE_BY_STATE_FLAG:
						if (((State) w).equalsName((State) target)){
							return target;
						}
						break;
					case FIND_STATE_BY_CITY_FLAG:
						if (((State) w).compareState((City) target)){
							return  w;
						}
				}
				rDFS(target,w,flag);
			}
		}
		return null;
	}

	@Override
	public void DFS(Object target, Node n, int flag) {
		n.setMarked(true);
		for(Node sub : n.getAdjacent()){
			if (!sub.isMarked()){ 
				sub.setMarked(true);
				if (flag == ADD_CITY_FLAG){
					if (((State) sub).compareState((City) target)){
						((State) sub).insertCity((City) target);
						return;
					}
				}
				DFS(target, sub,flag);
			}
		}
		if (flag == ADD_CITY_FLAG && !this.contains((State) n)){
			this.addState(new State(currentId,((City) target).getState()));
			((State) this.getNode(this.getNodeCount())).insertCity((City) target);
			return;
		}	
	}
	
	private void resetMarked(){
		for (int i= 0; i < this.getNodeCount();i++){
			this.getNode(0).setMarked(false);
		}
	}
}
