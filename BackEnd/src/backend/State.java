package backend;
import java.util.ArrayList;

public class State extends Node {
	
	private String name;
	private ArrayList<City> cities;
	
	public State(int id, String name){
		super(id);
		this.name = name;
		cities =new ArrayList<City>();
	}
	
	public State(int id, ArrayList<Node> adjacencyList,String name){
		super(id,adjacencyList);
		this.name = name;
		cities =new ArrayList<City>();
	}
	
	public void insertCity(City c){
		assert compareState(c);
		cities.add(c);
	}
	
	public boolean compareState(City c){
		return name.equals(c.getState());
	}
	
	public boolean contains(City c){
		// use carlos's search find whether state contains city
		return true;
	}
	
	public City findLowestCrimeRate(){
		// sort cities by Crime rate
		
		return cities.get(0);
	}
	
	public ArrayList<City> findLowestCrimeRate(int length){
		// sort cities by Crime rate
		
		assert length <= cities.size();
		ArrayList<City> c = new ArrayList<City>();
		for (int i =0; i < length; i++){
			c.add(cities.get(i));
		}
		return c;
	}
	
	public boolean equalsName(State node){
		return (this.getName().equals(node.getName()));
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		String message =this.getId() + ": " +  this.getName() + "\n\t";
		ArrayList<Node> adjacents = this.getAdjacent();
		for (int i =0; i < adjacents.size(); i++){
			message += ((State) adjacents.get(i)).getName() + " ";
		}
		message += "\n";
		for (int i =0; i < cities.size(); i++){
			message += cities.get(i).getName();
		}
		
		return message;
	}
}
