package backend;
import java.util.ArrayList;
import java.util.Arrays;

public class State extends Node {
	
	private String name;
	private String code;
	private ArrayList<String> cities;
	private Sorting s;
	private Query q;
	
	/**
	 * Construct the state object
	 * @param id - the id of the state node
	 * @param name - the name of the state
	 * @param code - the code of the state (IE Arizona is AZ)
	 */
	public State(int id, String name, String code)
	{
		super(id);
		q = new Query();
		this.name = name;
		this.code = code;
		cities =new ArrayList<String>();
		s = new Sorting();
	}
	
	/**
	 * Insert a city into the state
	 * @param c - city you are inserting
	 */
	public void insertCity(String c){
		//assert compareState(c);
		cities.add(c);
	}
	
	
	/**
	 * Insert multiple cities into the State
	 * @param c - the list of cities
	 */
	public void insertCity(ArrayList<String> c){
		//for (int i = 0; i < c.size();i++){
		//	if (this.compareState(c.get(i)) && !this.contains(c.get(i))){
		//		System.out.println(c.get(i).getName());
		cities.addAll(c);
		//	}
		//}
	}
	
	/**
	 * Check whether a city belongs in this state
	 * @param c - city you are checking for
	 * @return boolean - whether this is the right state for this city
	 */
	public boolean compareState(City c){
		return name.equals(c.getState());
	}
	
	/**
	 * Check whether a this state object already contains a city object
	 * @param c - the city object
	 * @return boolean - whether the city is contained or not
	 */
	public boolean contains(City c){
		for (int i = 0; i < cities.size();i++){
			if (cities.get(i).equals(c)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Find the lowest crime rate cities in the state
	 * @param length - how many lcm cities you want to find
	 * @return - the list of the cities
	 */
	public ArrayList<City> findLowestCrimeRate(int length){
		if (cities.size() == 0){
			return null;
		}

		ArrayList<City> c = q.getLowestCrime(this.getName(),length);
		return c;
	}
	
	/**
	 * Check whether this state is equal to another State (compare by statename)
	 * @param node - state you are compairing
	 * @return boolean - whether they are equal or not
	 */
	public boolean equalsName(State node){
		return (this.getName().equals(node.getName()));
	}
	
	/**
	 * Check whether this state is equal to another State (compared by StateCode)
	 * @param node - state you are comparing
	 * @return boolean - whether they are equal or not
	 */
	public boolean equalsCode(State node){
		return (this.getCode().equals(node.getCode()));
	}
	/**
	 * Check whether this state is equal to another State (compare by statename)
	 * @param node - state string you are comparing
	 * @return boolean - whether they are equal or not
	 */
	public boolean equalsName(String node){
		return (this.getName().equals(node));
	}
	
	/**
	 * Check whether this state is equal to another State (compare by statename)
	 * @param node - state code string you are compairing
	 * @return boolean - whether they are equal or not
	 */
	public boolean equalsCode(String node){
		return (this.getCode().equals(node));
	}
	
	/**
	 * Set the state name
	 * @param name - name of the state
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Get the name of the state
	 * @return - the name of the state
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Set the state code
	 * @param code - code of the state
	 */
	public void setCode(String code){
		this.code = code;
	}
	
	/**
	 * Get the state code
	 * @return - the state code
	 */
	public String getCode(){
		return this.code;
	}
	
	/**
	 * String representation of this state
	 */
	public String toString(){
		String message =this.getId() + ": " +  this.getName() + "\n\t";
		ArrayList<Node> adjacents = this.getAdjacent();
		for (int i =0; i < adjacents.size(); i++){
			message += ((State) adjacents.get(i)).getName() + " ";
		}
		message += "\n\t";
		for (int i =0; i < cities.size(); i++){
			message += cities.get(i);
			if (i < cities.size() -1){
				message += ", ";
			}
		}
		
		return message;
	}
}