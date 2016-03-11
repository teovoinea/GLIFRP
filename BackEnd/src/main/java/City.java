package main.java;

import com.google.gson.annotations.SerializedName;

public class City implements Comparable<City>{
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
	public City(String[] ll){
		lat = ll[0];
		lon = ll[1];
		mapwrap.buildByLatLong(ll);
		zip = mapwrap.getZip();
		name = mapwrap.getName();
		state = mapwrap.getState();
	}
	
	public City(String zip_code){
		zip = zip_code;
		mapwrap.buildByZip(zip);
		lat = mapwrap.getLat();
		lon = mapwrap.getLon();
		name = mapwrap.getName();
		state = mapwrap.getState();
	}

	public City(String city_name, String city_state){
		mapwrap.buildByCityState(city_name, city_state);
		// zip = mapwrap.getZip();
		lat = mapwrap.getLat();
		lon = mapwrap.getLon();
		name = mapwrap.getName();
		state = mapwrap.getState();
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
	/**
	 * Sorts the comparable array by any of the following attributes
	 * 0. Lat
	 * 1. Long
	 * 2. Zip
	 * 3. Crime
	 * 4. Price
	 * 5. Score
	 * 6. Name
	 * 7. State
	 * @param City to compare
	 * @param Int attribute to compare by ^ look above
	 */
	public int compareTo(Comparable o, int attribute){
		if (attribute == 0){
			return this.lat.compareTo(((City)o).getLat());
		}
		if (attribute == 1){
			return this.lon.compareTo(((City)o).getLong());
		}
		if (attribute == 2){
			return this.zip.compareTo(((City)o).getZip());
		}
		if (attribute == 3){
			if (this.crime > ((City)o).getCrime())
				return 1;
			else if (this.crime == ((City)o).getCrime())
				return 0;
			else
				return -1;
		}
		if (attribute == 4){
			if (this.price > ((City)o).getPrice())
				return 1;
			else if (this.price == ((City)o).getPrice())
				return 0;
			else
				return -1;
		}
		if (attribute == 5){
			if (this.score > ((City)o).getScore())
				return 1;
			else if (this.score == ((City)o).getScore())
				return 0;
			else
				return -1;
		}
		if (attribute == 6){
			return this.name.compareTo(((City)o).getName());
		}
		if (attribute == 7){
			return this.state.compareTo(((City)o).getState());
		}
		throw new IllegalArgumentException("Invalid attribute argument");
	}

	@Override
	public int compareTo(City o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
