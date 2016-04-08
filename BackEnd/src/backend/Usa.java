package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Usa extends Graph {
	private static int currentId = 0;
	public static final int FIND_STATE_BY_STATE_FLAG = 1;
	public static final int FIND_STATE_BY_CITY_FLAG = 2;
	public static final int FIND_LOWEST_CRIME_RATE_FLAG = 3;
	public static final int ADD_CITY_FLAG = 4;
	public static final int FIND_STATE_BY_STATE_NAME_FLAG = 5;
	private Query q;

	/**
	 * Construct the USA object
	 */
	public Usa() {
		super();
		q = new Query();
		generateStates();
	}

	/**
	 * Find a State by the state
	 * @param s - the state object
	 * @return - the state
	 */
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
	
	/**
	 * Find state by the name of the state
	 * @param s - string representation of the states name
	 * @return - state object
	 */
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

	/**
	 * Find a state by a city name
	 * @param c - the city within your state
	 * @return - the state with that city
	 */
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
	
	/**
	 * Get the neighbouring states up to a certain distance from a start state
	 * @param state - start state
	 * @param distance - how many states away
	 * @return ArrayList<State> The states within a certain distance from your state
	 */
	public ArrayList<State> getNeighbouringStates(State state, int distance){
		ArrayList<State> result = limitedBFSSearch(state,distance);
		this.resetMarked();
		return result;
	}
	
	/**
	 * Get the amount of hops between two states
	 * @param state - start state
	 * @param state2 - end state 
	 * @return the integer that is the number of hops between these two states
	 */
	public int getHops(State state, State state2) {
		int distance = this.findDistance(state, state2);
		this.resetMarked();
		return distance;
	}
	
	/**
	 * BFS implementation that only searches for a specific distance
	 * @param state - start state
	 * @param distance - how many states away you are going to look 
	 * @return - the states within distance hops from your start state
	 */
	private ArrayList<State> limitedBFSSearch(State state, int distance){
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
	        	if (!s.isMarked()){
		        	s.setMarked(true);
		        	states.add((State) s);
		        	queue.add((State) s);
	        	}
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
	
	/**
	 * BFS implementation that gets distance between two points
	 * @param state - start state
	 * @param distance - how many states away you are going to look 
	 * @return - the distance between these two states
	 */
	private int findDistance(State state, State end){		
		if (state.equalsName(end)){
    		return 0;
    	}
		Queue<State> queue = new LinkedList<State>();
	    int outerCount = 1;
	    int innerCount = 0;
	    int curAdjacencySize = 1;
	    queue.add(state);
	    state.setMarked(true);
	    
	    while(!queue.isEmpty()) {
	        State node = queue.remove();
	        innerCount++;
	        for (Node s : node.getAdjacent()){
	        	if (!s.isMarked()){
		        	s.setMarked(true);
		        	if (((State) s).equalsName(end)){
		        		return outerCount;
		        	}
		        	queue.add((State) s);
	        	}
	        }
	        if (innerCount == curAdjacencySize){
	        	outerCount++;
	        	innerCount = 0;
	        	curAdjacencySize = queue.size();
	        }
	    }
	    return 50;
	}

	/**
	 * Find the lowest crime rate cities in a group of states
	 * @param states - the states you are looking in
	 * @param length - the amount of cities you want
	 * @return ArrayList<City> - the list of Cities
	 */
	public ArrayList<City> findLowestCrimeRate(ArrayList<State> states,int length){
		ArrayList<City> lcmCities = new ArrayList<City>();
		for (int i = 0; i < states.size();i++){
			lcmCities.addAll(states.get(i).findLowestCrimeRate(length));
		}
		
		City[] cities2 = lcmCities.toArray(new City[lcmCities.size()]);
		Sorting.SortByType(3, cities2);
		
		lcmCities =new ArrayList<City>(Arrays.asList(cities2));
		
		return new ArrayList<City>(lcmCities.subList(0, length));
		
	}
	
	/**
	 * Find the lowest crime rate cities in the USA
	 * 
	 * @param length - how many lowest crime rate cities you want to find
	 * @return ArrayList<City> - list of the lcm cities
	 */
	public ArrayList<City> findLowestCrimeRate(int length) {
		if (this.isEmpty()){
			System.out.println("Your list is empty");
			return null;
		}
		State state = (State) this.getNode(0);
		ArrayList<City> lcmCities = new ArrayList<City>();
		lcmHelper(lcmCities, length, state);
		this.resetMarked();
		
		
		City[] cities2 = lcmCities.toArray(new City[lcmCities.size()]);
		Sorting.SortByType(3, cities2);
		
		lcmCities =new ArrayList<City>(Arrays.asList(cities2));	
		
		return new ArrayList<City>(lcmCities.subList(0, length));
	}
	
	/**
	 * Helper function for the lowest crime rate search
	 * @param cities - the list of lowest crime rate cities
	 * @param length - how many cities you are looking for
	 * @param state - the current state you are in
	 */
	private void lcmHelper(ArrayList<City> cities, int length, State state){
		state.setMarked(true);
		ArrayList<City> c = state.findLowestCrimeRate(length);
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

	/**
	 * Add a City to the state
	 * @param c - city name that you are adding
	 */
	public void addCity(String c) {
		if (this.isEmpty()){
			System.out.println("Your list is empty");
			return;
		}
		
		State state = (State) nodes.get(0);
		DFS(c, state, ADD_CITY_FLAG);
		this.resetMarked();
	}

	/**
	 * Add a state to the Graph
	 * @param c - the state you are adding
	 */
	private void addState(State c) {
		if (this.containsState(c)) {
			return;
		}
		addNode(c);
	}

	/**
	 * Check whether a state is contained within the graph
	 * @param state - State you are checking for
	 * @return boolean - whether or not this state is contained
	 */
	public boolean containsState(State state) {
		for (int i = 0; i < nodes.size(); i++) {
			if (((State) nodes.get(i)).equalsName(state)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Do a DFS with a return object
	 * 
	 * @param target - the extra object you are passing in (I.E the city when you are find state by a city)
	 * @param n - the node you are doing your DFS from
	 * @param flag - the flag for what action you want to do
	 * @return Object - whatever the object you are return is (state or city)
	 */
	private Object rDFS(Object target, Node curNode, int flag) {
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

	/**
	 * Run a DFS with a certain flag
	 * 
	 * @param target - the extra object you are passing in (I.E the city when you are adding a city)
	 * @param n - the node you are doing your DFS from
	 * @param flag - the flag for what action you want to do (The only one for this DFS is adding a city)
	 */
	private void DFS(Object target, Node n, int flag) {
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
	 * uses information from this link in the form a csv file to determine which states neighbour which states
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
	 * Get State object from the String of the States code
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
	
	/**
	 * Print all the state objects in the country
	 */
	public void printUSA(){
		for (int i =0; i < this.getNodeCount(); i++){
			System.out.println(this.getNode(i));
		}

	}
	
	public static void main(String[] args){
		Usa usa = new Usa();	
	}
}
