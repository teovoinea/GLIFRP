package backend;
import java.util.ArrayList;
import java.util.Arrays;

public class State extends Node {
	
	private String name;
	private String code;
	private ArrayList<City> cities;
	private Sorting s;
	private Query q;
	
	public State(int id, String name, String code)
	{
		super(id);
		q = new Query();
		this.name = name;
		this.code = code;
		cities =new ArrayList<City>();
		s = new Sorting();
	}
	
	public State(int id, ArrayList<Node> adjacencyList,String name){
		super(id,adjacencyList);
		this.name = name;
		cities =new ArrayList<City>();
		s = new Sorting();
	}
	
	public void insertCity(City c){
		assert compareState(c);
		cities.add(c);
	}
	
	public void insertCity(ArrayList<City> c){
		//for (int i = 0; i < c.size();i++){
		//	if (this.compareState(c.get(i)) && !this.contains(c.get(i))){
		//		System.out.println(c.get(i).getName());
		cities.addAll(c);
		//	}
		//}
	}
	
	public boolean compareState(City c){
		return name.equals(c.getState());
	}
	
	public boolean contains(City c){
		for (int i = 0; i < cities.size();i++){
			if (cities.get(i).equals(c)){
				return true;
			}
		}
		return false;
	}
	
	public City findLowestCrimeRate(){
		// sort cities by Crime rate
		
		return cities.get(0);
	}
	
	public ArrayList<City> findLowestCrimeRate(int length){
		if (cities.size() == 0){
			return null;
		}

		// sort cities by Crime rate
		City[] cities2 = cities.toArray(new City[cities.size()]);
		Sorting.SortByType(3, cities2);
		cities = new ArrayList<City>(Arrays.asList(cities2));
		
		
		assert length <= cities.size();
		ArrayList<City> c = new ArrayList<City>();
		for (int i =0; i < cities.size(); i++){
			c.add(cities.get(i));
		}
		return c;
	}
	
	public boolean equalsName(State node){
		return (this.getName().equals(node.getName()));
	}
	
	public boolean equalsCode(State node){
		return (this.getCode().equals(node.getCode()));
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String toString(){
		String message =this.getId() + ": " +  this.getName() + "\n\t";
		ArrayList<Node> adjacents = this.getAdjacent();
		for (int i =0; i < adjacents.size(); i++){
			message += ((State) adjacents.get(i)).getName() + " ";
		}
		message += "\n\t";
		for (int i =0; i < cities.size(); i++){
			message += cities.get(i).getName();
		}
		
		return message;
	}
}