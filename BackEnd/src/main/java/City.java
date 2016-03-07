import com.google.gson.annotations.SerializedName;

public class City extends Node implements Comparable<City>{
	private static OpenStreetMapWrapper mapwrap = new OpenStreetMapWrapper();
	
	//Pirvate Variables
	@SerializedName("lat")
	private String lat = "";
	@SerializedName("lon")
	private String lon = "";
	@SerializedName("zip")
	private String zip = "";
	@SerializedName("crime")
	private double crime;
	@SerializedName("price")
	private double price;
	@SerializedName("score")
	private double score;
	@SerializedName("state")
	private String state;
	@SerializedName("name")
	private String name;
	
	//Constructors (lat + long) OR zip code
	public City(int id, String[] ll){
		super(id);
		lat = ll[0];
		lon = ll[1];
		mapwrap.buildByLatLong(ll);
		zip = mapwrap.getZip();
		name = mapwrap.getName();
		state = mapwrap.getState();
	}
	
	public City(int id, String zip_code){
		super(id);
		zip = zip_code;
		mapwrap.buildByZip(zip);
		lat = mapwrap.getLat();
		lon = mapwrap.getLon();
		name = mapwrap.getName();
		state = mapwrap.getState();
	}

	public City(int id, String city_name, String city_state){
		super(id);
		name = city_name;
		state = city_state;
		mapwrap.buildByCityState(city_name, city_state);
		// zip = mapwrap.getZip();
		lat = mapwrap.getLat();
		lon = mapwrap.getLon();
	}
	
	/////////////////////////////////////GETTERS//////////////////////////////////////
	public String getLat(){
		return lat;
	}
	
	public String getLong(){
		return lon;
	}
	
	public String getZip(){
		return zip;
	}
	
	public double getCrime(){
		return crime;
	}
	
	public double getPrice(){
		return price;
	}
	
	public double getScore(){
		return score;
	}

	public String getName(){
		return name;
	}

	public String getState(){
		return state;
	}
	
	///////////////////////////////////SETTERS////////////////////////////////////////
	public void setCrime(double d){
		crime = d;
	}
	
	public void setPrice(double d){
		price = d;
	}
	
	///////////////////////////////////PRIVATE FUNCTIONS//////////////////////////////
	private double calculateScore(){
		return 0.0;
	}
	
	//////////////////////////////////IMPLEMENTED FUNCTIONS////////////////////////////
	@Override
	public int compareTo(City o){
		return  0;
	}
}
