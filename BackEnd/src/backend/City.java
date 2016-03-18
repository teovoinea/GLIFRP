package backend;

import com.google.gson.annotations.SerializedName;

import backend.Crime;
import backend.PriceIndex;

public class City implements Comparable{
	//web wrapper for open street map, allows for easy access
//	private static OpenStreetMapWrapper mapwrap = new OpenStreetMapWrapper();
	
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
//		mapwrap.buildByLatLong(ll);
//		zip = mapwrap.getZip();
//		name = mapwrap.getName();
//		state = mapwrap.getState();
	}
	
	/**
	 * Build City by zip code
	 * @param  zip_code zip code
	 * @return          City
	 */
	public City(String zip_code){
		zip = zip_code;
//		mapwrap.buildByZip(zip);
//		lat = mapwrap.getLat();
//		lon = mapwrap.getLon();
//		name = mapwrap.getName();
//		state = mapwrap.getState();
	}

	/**
	 * Build City by city name & state name
	 * @param  city_name  Name of city
	 * @param  city_state Name of state
	 * @return            City
	 */
	public City(String city_name, String city_state){
//		mapwrap.buildByCityState(city_name, city_state);
//		// zip = mapwrap.getZip();
//		lat = mapwrap.getLat();
//		lon = mapwrap.getLon();
		name = city_name;
		state = city_state;
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
	
	/**
	 * Set the Score of the City
	 */
	public void setScore(){
		//for prototype we will have this weighting of Crime and housing ?
		
		// if crime and Price have values
		if ((getCrime() != 0.0) && (getPrice() != 0.0 )){
			//calculateScore(6,4);
		}else {
			System.out.println(" Either Crime or Housing Prices have to be set ");
		}
		
	}
	
	public void setLat(String lat){
		this.lat = lat;
	}
	
	public void setLong(String longe){
		this.lon = longe;
	}
	
	///////////////////////////////////PRIVATE FUNCTIONS//////////////////////////////
	
	
	//Not sure where to put these functions, CityData.java ?
	/**
	 * Generate a Score for a city.  
	 * @param crimes - holds information on different type of crime.
	 * @param priceIndex - holds information on the housing market.
	 * @return - score that the City receives based on crimes and housing inflation
	 */
	public double calculateScore(Crime crimes, PriceIndex priceIndex){
		//variables
		double houseScore = ((priceIndex.getIndex_nsa() - priceIndex.getIndex_sa())/priceIndex.getIndex_sa()) * 100 ;
		double crimeScore = 0.0;
		double maxScore = 500;
		double cityScore = 0.0;			//returning Score
		
		//Different types of crimes
		//arson
		//assault
		//burglary
		//larceny
		//motor
		//murder
		//property
		//rape
		//robbery
		//violentCrime
		
		//temporary weights
		int wtArson = 1;
		int wtAssualt = 1;
		int wtBurglary = 1;
		int wtLarceny = 1;
		int wtMotor =1 ;
		int wtMurder = 1;
		int wtProperty = 1;
		int wtRape = 1;
		int wtRobbery = 1;
		int wtViolentCrime = 1;
		int wtPriceIndex = 1;
		
		crimeScore += crimes.getArson()*wtArson + crimes.getAssault()*wtAssualt + crimes.getBurglary()*wtBurglary + crimes.getLarceny()*wtLarceny;  
		crimeScore 	+=  crimes.getMotor()*wtMotor + crimes.getMurder()*wtMurder + crimes.getProperty()*wtProperty + crimes.getRape()*wtRape + crimes.getRobbery()*wtRobbery + crimes.getViolentCrime()*wtViolentCrime;
		houseScore = houseScore * wtPriceIndex;
		cityScore = maxScore - crimeScore - houseScore;
		
		return cityScore;
		
		//Note
		//housing prices, range(0 to 100)?
		//although the inflation rate can go over 100% it is highly unlikely,... if we take the inflation between last year and this year. 
		//inflation could easily go over 100% if we decide to take inflation into account for a longer period of time ie (over 5 years of inflation) 
		//for testing purposes I will assume a range of 0 to 100% of inflation rate	
	}
	
	/**
	 * Calculates the crime rate of a city. 
	 * @param crimes
	 * @return - Total crimes/population in percent 
	 */
	public double calculateCrimeRate(Crime crimes){
		double totalCrime = totalCrime(crimes);
		double population = crimes.getPopulation();
		double crimeRate = (totalCrime/population)*100;		//percentage 
		return crimeRate;
	}
	
	/**
	 * Gets the Total Crime
	 * @param crimes
	 * @return - the sum of all the crimes
	 */
	public double totalCrime(Crime crimes){
		double totalCrime = crimes.getArson() + crimes.getAssault() + crimes.getBurglary() + crimes.getLarceny()  + crimes.getMotor() + crimes.getMurder() + crimes.getProperty() + crimes.getRape() + crimes.getRobbery() + crimes.getViolentCrime();
		return totalCrime;
	}
	
	/**
	 * calculates the price inflation given a certain time period.
	 * @param priceIndex - holds information on housing prices over the years.
	 * @return -the inflation over a certain period of time
	 */
	public double priceInflation(PriceIndex priceIndex){
		double retInflation = ((priceIndex.getIndex_nsa() - priceIndex.getIndex_sa())/priceIndex.getIndex_sa()) * 100;
		return retInflation;
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


	/**
	 * Overridden compareTo <br> <b>Deprecated DO NOT USE</b>
	 * @param  o Object to compare to
	 * @return   -1, 0, 1
	 */
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean equals(City c){
		return (this.name.equals(c.getName()) && this.state.equals(c.getState()));
	}

	
}
