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
	@Override
	public int compareTo(City o, int attribute){
		if (attribute == 0){
			if (this.lat > o.getLat())
				return 1;
			else if (this.lat == o.getLat())
				return 0;
			else
				return -1;
		}
		if (attribute == 1){
			if (this.lon > o.getLong())
				return 1;
			else if (this.lon == o.getLong())
				return 0;
			else
				return -1;
		}
		if (attribute == 2){
			if (this.zip > o.getZip())
				return 1;
			else if (this.zip == o.getZip())
				return 0;
			else
				return -1;
		}
		if (attribute == 3){
			if (this.crime > o.getCrime())
				return 1;
			else if (this.crime == o.getCrime())
				return 0;
			else
				return -1;
		}
		if (attribute == 4){
			if (this.price > o.getPrice())
				return 1;
			else if (this.price == o.getPrice())
				return 0;
			else
				return -1;
		}
		if (attribute == 5){
			if (this.score > o.getScore())
				return 1;
			else if (this.score == o.getScore())
				return 0;
			else
				return -1;
		}
		if (attribute == 6){
			if (this.name > o.getName())
				return 1;
			else if (this.name == o.getName())
				return 0;
			else
				return -1;
		}
		if (attribute == 7){
			if (this.state > o.getState())
				return 1;
			else if (this.state == o.getState())
				return 0;
			else
				return -1;
		}
		throw new IllegalArgumentException("Invalid attribute argument");
	}
}
