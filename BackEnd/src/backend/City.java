package backend;

import com.google.gson.annotations.SerializedName;

public class City implements Comparable<City>{
	//web wrapper for open street map, allows for easy access
	private static OpenStreetMapWrapper mapwrap = new OpenStreetMapWrapper();
	
	//Private Variables
	@SerializedName("lat")
	private String lat = ""; //latitude of city
	@SerializedName("lon")
	private String lon = ""; //longitude of city
	@SerializedName("zip")
	private String zip = ""; //zip if available
	@SerializedName("crime")
	private double crime;    //crime rate
	@SerializedName("price")
	private double price;    //price
	@SerializedName("score")
	private double score;    //calculated score
	@SerializedName("state")
	private String state;    //state of city
	@SerializedName("name")
	private String name;	 //name of city
	
	/**
	 * Build City by latitude & longitude
	 * @param  ll [lat, lon]
	 * @return    City
	 */
	public City(String[] ll){
		lat = ll[0];
		lon = ll[1];
		mapwrap.buildByLatLong(ll);
		zip = mapwrap.getZip();
		name = mapwrap.getName();
		state = mapwrap.getState();
	}
	
	/**
	 * Build City by zip code
	 * @param  zip_code zip code
	 * @return          City
	 */
	public City(String zip_code){
		zip = zip_code;
		mapwrap.buildByZip(zip);
		lat = mapwrap.getLat();
		lon = mapwrap.getLon();
		name = mapwrap.getName();
		state = mapwrap.getState();
	}

	/**
	 * Build City by city name & state name
	 * @param  city_name  Name of city
	 * @param  city_state Name of state
	 * @return            City
	 */
	public City(String city_name, String city_state){
		mapwrap.buildByCityState(city_name, city_state);
		// zip = mapwrap.getZip();
		lat = mapwrap.getLat();
		lon = mapwrap.getLon();
		name = mapwrap.getName();
		state = mapwrap.getState();
	}
	
	/////////////////////////////////////GETTERS//////////////////////////////////////
	/**
	 * Returns latitude
	 * @return latitude
	 */
	public String getLat(){
		return lat;
	}
	
	/**
	 * Returns longitude
	 * @return longitude
	 */
	public String getLong(){
		return lon;
	}
	
	/**
	 * Returns Zip Code
	 * @return Zip Code
	 */
	public String getZip(){
		return zip;
	}
	
	/**
	 * Returns Crime Rate
	 * @return Crime Rate
	 */
	public double getCrime(){
		return crime;
	}
	
	/**
	 * Returns Price
	 * @return Price
	 */
	public double getPrice(){
		return price;
	}
	
	/**
	 * Returns Score
	 * @return Score
	 */
	public double getScore(){
		return score;
	}

	/**
	 * Returns Name
	 * @return Name
	 */
	public String getName(){
		return name;
	}

	/**
	 * Returns State
	 * @return State
	 */
	public String getState(){
		return state;
	}
	
	///////////////////////////////////SETTERS////////////////////////////////////////
	
	/**
	 * Set the Crime rate of the city
	 * @param d Crime rate
	 */
	public void setCrime(double d){
		crime = d;
	}
	
	/**
	 * Set the Price of living in the city
	 * @param d Price
	 */
	public void setPrice(double d){
		price = d;
	}
	
	///////////////////////////////////PRIVATE FUNCTIONS//////////////////////////////
	
	/**
	 * Generate a Score for a city. *(Simple version rn)*    
	 * 
	 * @param city - the city that is going to get a score
	 * @param crimeRate - the crime in a city  *(percentage ?????, not sure what crime rate Stats are going to be like)*
	 * @param housingPriceIndex -the inflation rate/ housing prices of the city, where the HSI is a percentage, from the Housing Price Index(HSI) data set
	 */
	private double calculateScore(double crimeWeight, double priceWeight){
		//temporary "weights"
		//Sig(weights) should = 1 
		crimeWeight = 0.6;
		priceWeight = 0.4;
		
		//crime rates can range from (0 to 100 )?
		double crimeRate = this.getCrime();
		
		//housing prices, (0 to 100)?
		//although the inflation rate can go over 100% it is highly unlikely,... if we take the inflation between last year and this year. 
		//inflation could easily go over 100% if we decide to take inflation into account for a longer period of time ie (over 5 years of inflation) 
		//for testing purposes I will assume a range of 0 to 100% of inflation rate
		double housingPrices = this.getPrice();
		
		//In the Future we may want to include when calculating scores:
		// a more indepth crime statistics ( violent crime, rape, murder)?
		
		
		double calcScore = crimeWeight*crimeRate + priceWeight*housingPrices;
		
		//score = calcScore;
		return calcScore;
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
	/**
	 * Overridden compareTo <br> <b>Deprecated DO NOT USE</b>
	 * @param  o Object to compare to
	 * @return   -1, 0, 1
	 */
	@Deprecated
	public int compareTo(City o) {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("Don't use this");
		return 0;
	}
	

	
}
