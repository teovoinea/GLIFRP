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
	
	//Constructors (lat + long) OR zip code
	public City(int id, String lati, String longi){
		super(id);
		lat = lati;
		lon = longi;
		String[] ll = {lat, lon};
		zip = mapwrap.getZipCode(ll);
	}
	
	public City(int id, String zip_code){
		super(id);
		zip = zip_code;
		String[] ll = mapwrap.getLatLong(zip);
		lat = ll[0];
		lon = ll[1];
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
	
	///////////////////////////////////SETTERS////////////////////////////////////////
	public void setCrime(double d){
		crime = d;
	}
	
	public void setPrice(double d){
		price = d;
	}
	
	public void fillMissingZipOrLatLong(){
		
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
