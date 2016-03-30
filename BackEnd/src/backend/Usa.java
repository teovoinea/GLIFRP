package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Usa extends Graph {
	private static int currentId = 0;
	public static final int FIND_STATE_BY_STATE_FLAG = 1;
	public static final int FIND_STATE_BY_CITY_FLAG = 2;
	public static final int FIND_LOWEST_CRIME_RATE_FLAG = 3;
	public static final int ADD_CITY_FLAG = 4;
	public static final int FIND_STATE_BY_STATE_NAME_FLAG = 5;
	private Query q;

	public Usa() {
		super();
		q = new Query();
		generateStates();
		State newYork = this.findStateByStateName("New York");
		ArrayList<State> neighbours = this.getNeighbouringStates(newYork, 2);
		for (State n : neighbours){
			System.out.println(n);
		}
		
		//System.out.println(newYork.findLowestCrimeRate(1).get(0).getCity());
		//this.printUSA();
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
	
	public State findStateByStateName(String s) {
		if (this.isEmpty()){
			System.out.println("Your list is empty");
			return null;
		}
		
		for (int i =0; i < this.nodes.size();i++){
			if (((State)this.nodes.get(i)).equalsName(s)){
				return ((State) this.nodes.get(i));
			}	
		}
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
	
	public ArrayList<State> getNeighbouringStates(State state, int distance){
		ArrayList<State> result = limitedBFSSearch(state,distance);
		this.resetMarked();
		return result;
	}
	
	public ArrayList<State> limitedBFSSearch(State state, int distance){
		ArrayList<State> states = new ArrayList<State>();
	    Queue<State> queue = new LinkedList<State>();
	    int outerCount = 0;
	    int innerCount = 0;
	    int curAdjacencySize = 1;
	    queue.add(state);
	    states.add(state);
	    state.setMarked(true);
	    
	    while(!queue.isEmpty() && outerCount < distance) {
	        State node = queue.remove();
	        innerCount++;
	        for (Node s : node.getAdjacent()){
	        	s.setMarked(true);
	        	states.add((State) s);
	        	queue.add((State) s);
	        }
	        if (innerCount == curAdjacencySize){
	        	outerCount++;
	        	innerCount = 0;
	        	curAdjacencySize = queue.size();
	        }
	        if (queue.isEmpty() && outerCount < distance){
	        	return states;
	        }
	    }
	    return states;
	}

	public ArrayList<Crime> findLowestCrimeRate(int length) {
		if (this.isEmpty()){
			System.out.println("Your list is empty");
			return null;
		}
		State state = (State) this.getNode(0);
		ArrayList<Crime> lcmCities = new ArrayList<Crime>();
		lcmHelper(lcmCities, length, state);
		this.resetMarked();
		
		
		Crime[] cities2 = lcmCities.toArray(new Crime[lcmCities.size()]);
		Sorting.SortByType(3, cities2);
		
		lcmCities =new ArrayList<Crime>(Arrays.asList(cities2));	
		
		return new ArrayList<Crime>(lcmCities.subList(0, length));
	}
	
	public void lcmHelper(ArrayList<Crime> cities, int length, State state){
		state.setMarked(true);
		ArrayList<Crime> c = state.findLowestCrimeRate(length);
		if (c != null){
			cities.addAll(c);
		}
		for (Node sub : state.getAdjacent()) {
			if (!sub.isMarked()) {
				sub.setMarked(true);
				lcmHelper(cities,length,(State) sub);
			}
		}
	}

	public void addCity(String c) {
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
			case FIND_STATE_BY_STATE_NAME_FLAG:
				if (((State) curNode).equalsName((State) target)) {
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

	
	public void DFS(Object target, Node n, int flag) {
		n.setMarked(true);
		if (flag == ADD_CITY_FLAG) {
			if (((State) n).compareState((City) target)) {
				((State) n).insertCity((String) target);
				return;
			}
		}
		for (Node sub : n.getAdjacent()) {
			if (!sub.isMarked()) {
				sub.setMarked(true);
				DFS(target, sub, flag);
			}
		}
	}

	/**
     * https://github.com/ubikuity/List-of-neighboring-states-for-each-US-state
	 * uses information from this link in the form a csv file to determine which states neighbour which
	 */
	private void generateStates() {
		ArrayList<String> fileLines = new ArrayList<String>();
		ArrayList<String> queriedCities;
		ArrayList<City> cityObjs;
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
    				queriedCities = q.getCityNames(newStates[0].getCode());
    				newStates[0].insertCity(queriedCities);
    				this.addState(newStates[0]);
    			}

    			if (this.containsState(newStates[1])) {
    				newStates[1] = this.findStateByState(newStates[1]);
    			} else {
    				queriedCities = q.getCityNames(newStates[1].getCode());
    				newStates[1].insertCity(queriedCities);
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
				result[i] = new State(currentId,"Alaska","AK");
			else if (newStates[i].equals("AR"))
				result[i] = new State(currentId, "Arkansas","AR");
			else if (newStates[i].equals("AL"))
				result[i] = new State(currentId,"Alabama","AL");
			else if (newStates[i].equals("AZ"))
				result[i] = new State(currentId,"Arizona","AZ");
			else if (newStates[i].equals("CA"))
				result[i] = new State(currentId,"California","CA");
			else if (newStates[i].equals("CO"))
				result[i] = new State(currentId,"Colorado","CO");
			else if (newStates[i].equals("CT"))
				result[i] = new State(currentId,"Connecticut","CT");
			else if (newStates[i].equals("DC"))
				result[i] = new State(currentId,"District of Columbia","DC");
			else if (newStates[i].equals("DE"))
				result[i] = new State(currentId,"Delaware","DE");
			else if (newStates[i].equals("FL"))
				result[i] = new State(currentId,"Florida","FL");
			else if (newStates[i].equals("GA"))
				result[i] = new State(currentId,"Georgia","GA");
			else if (newStates[i].equals("HI"))
				result[i] = new State(currentId,"Hawaii","HI");
			else if (newStates[i].equals("IA"))
				result[i] = new State(currentId,"Iowa","IA");
			else if (newStates[i].equals("ID"))
				result[i] = new State(currentId,"Idaho","ID");
			else if (newStates[i].equals("IL"))
				result[i] = new State(currentId,"Illinois","IL");
			else if (newStates[i].equals("IN"))
				result[i] = new State(currentId,"Indiana","IN");
			else if (newStates[i].equals("KS"))
				result[i] = new State(currentId,"Kansas","KS");
			else if (newStates[i].equals("KY"))
				result[i] = new State(currentId,"Kentucky","KY");
			else if (newStates[i].equals("LA"))
				result[i] = new State(currentId,"Louisiana","LA");
			else if (newStates[i].equals("MA"))
				result[i] = new State(currentId,"Massachusetts","MA");
			else if (newStates[i].equals("MD"))
				result[i] = new State(currentId,"MaryLand","MD");
			else if (newStates[i].equals("ME"))
				result[i] = new State(currentId,"Maine","ME");
			else if (newStates[i].equals("MI"))
				result[i] = new State(currentId,"Michigan","MI");
			else if (newStates[i].equals("MN"))
				result[i] = new State(currentId,"Minnesota","MN");
			else if (newStates[i].equals("MO"))
				result[i] = new State(currentId,"Missouri","MO");
			else if (newStates[i].equals("MS"))
				result[i] = new State(currentId,"Mississippi","MS");
			else if (newStates[i].equals("MT"))
				result[i] = new State(currentId,"Montana","MT");
			else if (newStates[i].equals("NC"))
				result[i] = new State(currentId,"North Carolina","NC");
			else if (newStates[i].equals("ND"))
				result[i] = new State(currentId,"North Dakota","ND");
			else if (newStates[i].equals("NE"))
				result[i] = new State(currentId,"Nebraska","NE");
			else if (newStates[i].equals("NH"))
				result[i] = new State(currentId,"New Hampshire","NH");
			else if (newStates[i].equals("NJ"))
				result[i] = new State(currentId,"New Jersey","NJ");
			else if (newStates[i].equals("NM"))
				result[i] = new State(currentId,"New Mexico","NM");
			else if (newStates[i].equals("NV"))
				result[i] = new State(currentId,"Nevada","NV");
			else if (newStates[i].equals("NY"))
				result[i] = new State(currentId,"New York","NY");
			else if (newStates[i].equals("OH"))
				result[i] = new State(currentId,"Ohio","OH");
			else if (newStates[i].equals("OK"))
				result[i] = new State(currentId,"Oklahoma","OK");
			else if (newStates[i].equals("OR"))
				result[i] = new State(currentId,"Oregon","OR");
			else if (newStates[i].equals("PA"))
				result[i] = new State(currentId,"Pennsylvania","PA");
			else if (newStates[i].equals("RI"))
				result[i] = new State(currentId,"Rhode Island","RI");
			else if (newStates[i].equals("SC"))
				result[i] = new State(currentId,"South Carolina","SC");
			else if (newStates[i].equals("SD"))
				result[i] = new State(currentId,"South Dakota","SD");
			else if (newStates[i].equals("TN"))
				result[i] = new State(currentId,"Tennessee","TN");
			else if (newStates[i].equals("TX"))
				result[i] = new State(currentId,"Texas","TX");
			else if (newStates[i].equals("UT"))
				result[i] = new State(currentId,"Utah","UT");
			else if (newStates[i].equals("VA"))
				result[i] = new State(currentId,"Virginia","VA");
			else if (newStates[i].equals("VT"))
				result[i] = new State(currentId,"Vermont","VT");
			else if (newStates[i].equals("WA"))
				result[i] = new State(currentId,"Washington","WA");
			else if (newStates[i].equals("WI"))
				result[i] = new State(currentId,"Wisconsin","WI");
			else if (newStates[i].equals("WV"))
				result[i] = new State(currentId,"West Virginia","WV");
			else if (newStates[i].equals("WY"))
				result[i] = new State(currentId,"Wyoming","WY");
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

	}
	
	public static void main(String[] args){
		Usa usa = new Usa();	
	}
}
