package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Usa extends Graph {
	private static int currentId = 0;
	public static final int FIND_STATE_BY_STATE_FLAG = 1;
	public static final int FIND_STATE_BY_CITY_FLAG = 2;
	public static final int FIND_LOWEST_CRIME_RATE_FLAG = 3;
	public static final int ADD_CITY_FLAG = 4;

	public Usa() {
		super();
		generateStates();
		this.printUSA();
	}

	public State findStateByState(State s) {
		if (this.isEmpty()){
			System.out.println("Your list is empty");
			return null;
		}
		for (int i =0; i < this.getNodeCount(); i++){
			State state = (State) this.getNode(i);
			if (!state.isMarked()){
				State result = (State) rDFS(s, state, FIND_STATE_BY_STATE_FLAG);
				if (result != null) {
					this.resetMarked();
					return result;
				}
			}
		}
		this.resetMarked();
		return null;
	}

	public State findStateByCity(City c) {
		if (this.isEmpty()){
			System.out.println("Your list is empty");
			return null;
		}
		for (int i =0; i < this.getNodeCount(); i++){
			State state = (State) this.getNode(i);
			if (!state.isMarked()){
				State result = (State) rDFS(c, state, FIND_STATE_BY_CITY_FLAG);
				if (result != null) {
					this.resetMarked();
					return result;
				}
			}
		}
		this.resetMarked();
		return null;
	}

	public City findLowestCrimeRate() {
		// DFS/BFS, int min, state.findLowestCrimeRate() if lower then min, set
		// as new min, return min
		return null;
	}

	public void addCity(City c) {
		if (this.isEmpty()){
			System.out.println("Your list is empty");
			return;
		}
		
		State state = (State) nodes.get(0);
		DFS(c, state, ADD_CITY_FLAG);
		this.resetMarked();
	}

	private void addState(State c) {
		if (this.containsState(c)) {
			return;
		}
		addNode(c);
	}

	public boolean containsState(State state) {
		for (int i = 0; i < nodes.size(); i++) {
			if (((State) nodes.get(i)).equalsName(state)) {
				return true;
			}
		}
		return false;
	}

	public Object rDFS(Object target, Node curNode, int flag) {
		curNode.setMarked(true);
		switch (flag) {
			case FIND_STATE_BY_STATE_FLAG:
				if (((State) curNode).equalsName((State) target)) {
					return curNode;
				}
				break;
			case FIND_STATE_BY_CITY_FLAG:
				if (((State) curNode).compareState((City) target)) {
					return curNode;
				}
		}
		for (Node w : curNode.getAdjacent()) {
			if (!w.isMarked()) {
				w.setMarked(true);
				Object obj = rDFS(target, w, flag);
				if (obj != null){
					return obj;
				}
			}
		}
		return null;
	}

	@Override
	public void DFS(Object target, Node n, int flag) {
		n.setMarked(true);
		for (Node sub : n.getAdjacent()) {
			if (!sub.isMarked()) {
				sub.setMarked(true);
				if (flag == ADD_CITY_FLAG) {
					if (((State) sub).compareState((City) target)) {
						((State) sub).insertCity((City) target);
						return;
					}
				}
				DFS(target, sub, flag);
			}
		}
		if (flag == ADD_CITY_FLAG && !this.containsState((State) n)) {
			this.addState(new State(currentId, ((City) target).getState()));
			((State) this.getNode(this.getNodeCount())).insertCity((City) target);
			return;
		}
	}

	/**
     * https://github.com/ubikuity/List-of-neighboring-states-for-each-US-state
	 * uses information from this link in the form a csv file to determine which states neighbour which
	 */
	private void generateStates() {
		ArrayList<String> fileLines = new ArrayList<String>();
		String curLine;
		State[] newStates;
		String fileName = "Data/StatePairs.txt";
		
		try {
            FileReader fileReader =  new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((curLine = bufferedReader.readLine()) != null) {
    			newStates = getStateFromStateCode(curLine);
    			
    			if (this.containsState(newStates[0])) {
    				newStates[0] = this.findStateByState(newStates[0]);
    			} else {
    				this.addState(newStates[0]);
    			}

    			if (this.containsState(newStates[1])) {
    				newStates[1] = this.findStateByState(newStates[1]);
    			} else {
    				this.addState(newStates[1]);
    			}

    			this.connectNodes(0, newStates[0], newStates[1]);
            }   

            // Always close files.
            fileReader.close();
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file to read state pairs, Graph not created");
        }
        catch(IOException ex) {
        	System.out.println("Error reading file of state pairs, Graph not created");
        }
	}

	/**
	 * 
	 * @param s - String of two pairs of states
	 * @return the two states as objects
	 */
	private State[] getStateFromStateCode(String s){
		String[] newStates = s.split(",");
		State[] result = new State[2];
		// I'm sorry
		for (int i =0; i < newStates.length; i++){
			if (newStates[i].equals("AK"))
				result[i] = new State(currentId,"Alaska");
			else if (newStates[i].equals("AR"))
				result[i] = new State(currentId, "Arkansas");
			else if (newStates[i].equals("AL"))
				result[i] = new State(currentId,"Alabama");
			else if (newStates[i].equals("AZ"))
				result[i] = new State(currentId,"Arizona");
			else if (newStates[i].equals("CA"))
				result[i] = new State(currentId,"California");
			else if (newStates[i].equals("CO"))
				result[i] = new State(currentId,"Colorado");
			else if (newStates[i].equals("CT"))
				result[i] = new State(currentId,"Connecticut");
			else if (newStates[i].equals("DC"))
				result[i] = new State(currentId,"District of Columbia");
			else if (newStates[i].equals("DE"))
				result[i] = new State(currentId,"Delaware");
			else if (newStates[i].equals("FL"))
				result[i] = new State(currentId,"Florida");
			else if (newStates[i].equals("GA"))
				result[i] = new State(currentId,"Georgia");
			else if (newStates[i].equals("HI"))
				result[i] = new State(currentId,"Hawaii");
			else if (newStates[i].equals("IA"))
				result[i] = new State(currentId,"Iowa");
			else if (newStates[i].equals("ID"))
				result[i] = new State(currentId,"Idaho");
			else if (newStates[i].equals("IL"))
				result[i] = new State(currentId,"Illinois");
			else if (newStates[i].equals("IN"))
				result[i] = new State(currentId,"Indiana");
			else if (newStates[i].equals("KS"))
				result[i] = new State(currentId,"Kansas");
			else if (newStates[i].equals("KY"))
				result[i] = new State(currentId,"Kentucky");
			else if (newStates[i].equals("LA"))
				result[i] = new State(currentId,"Louisiana");
			else if (newStates[i].equals("MA"))
				result[i] = new State(currentId,"Massachusetts");
			else if (newStates[i].equals("MD"))
				result[i] = new State(currentId,"MaryLand");
			else if (newStates[i].equals("ME"))
				result[i] = new State(currentId,"Maine");
			else if (newStates[i].equals("MI"))
				result[i] = new State(currentId,"Michigan");
			else if (newStates[i].equals("MN"))
				result[i] = new State(currentId,"Minnesota");
			else if (newStates[i].equals("MO"))
				result[i] = new State(currentId,"Missouri");
			else if (newStates[i].equals("MS"))
				result[i] = new State(currentId,"Mississippi");
			else if (newStates[i].equals("MT"))
				result[i] = new State(currentId,"Montana");
			else if (newStates[i].equals("NC"))
				result[i] = new State(currentId,"North Carolina");
			else if (newStates[i].equals("ND"))
				result[i] = new State(currentId,"North Dakota");
			else if (newStates[i].equals("NE"))
				result[i] = new State(currentId,"Nebraska");
			else if (newStates[i].equals("NH"))
				result[i] = new State(currentId,"New Hampshire");
			else if (newStates[i].equals("NJ"))
				result[i] = new State(currentId,"New Jersey");
			else if (newStates[i].equals("NM"))
				result[i] = new State(currentId,"New Mexico");
			else if (newStates[i].equals("NV"))
				result[i] = new State(currentId,"Nevada");
			else if (newStates[i].equals("NY"))
				result[i] = new State(currentId,"New York");
			else if (newStates[i].equals("OH"))
				result[i] = new State(currentId,"Ohio");
			else if (newStates[i].equals("OK"))
				result[i] = new State(currentId,"Oklahoma");
			else if (newStates[i].equals("OR"))
				result[i] = new State(currentId,"Oregon");
			else if (newStates[i].equals("PA"))
				result[i] = new State(currentId,"Pennsylvania");
			else if (newStates[i].equals("RI"))
				result[i] = new State(currentId,"Rhode Island");
			else if (newStates[i].equals("SC"))
				result[i] = new State(currentId,"South Carolina");
			else if (newStates[i].equals("SD"))
				result[i] = new State(currentId,"South Dakota");
			else if (newStates[i].equals("TN"))
				result[i] = new State(currentId,"Tennessee");
			else if (newStates[i].equals("TX"))
				result[i] = new State(currentId,"Texas");
			else if (newStates[i].equals("UT"))
				result[i] = new State(currentId,"Utah");
			else if (newStates[i].equals("VA"))
				result[i] = new State(currentId,"Virginia");
			else if (newStates[i].equals("VT"))
				result[i] = new State(currentId,"Vermont");
			else if (newStates[i].equals("WA"))
				result[i] = new State(currentId,"Washington");
			else if (newStates[i].equals("WI"))
				result[i] = new State(currentId,"Wisconsin");
			else if (newStates[i].equals("WV"))
				result[i] = new State(currentId,"West Virginia");
			else if (newStates[i].equals("WY"))
				result[i] = new State(currentId,"Wyoming");
			else{
				result[i] = null;
				System.out.println(newStates[i]);
				System.out.println("No state matched that code");
			}
			if (!this.containsState(result[i])){
				currentId++;
			}
		}
		return result;
	}
	
	public void printUSA(){
		for (int i =0; i < this.getNodeCount(); i++){
			System.out.println(this.getNode(i));
		}
		System.out.println("");
	}
	
	public static void main(String[] args){
		Usa usa = new Usa();
	}
}
