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
	private String state;    //state of city(abbreviated)
	@SerializedName("uState")
	private String uState;
	@SerializedName("name")
	private String name;	 //name of city
	
	//Data pulled from a query
	//Crime
	private int population, violentCrime, murder, rape, 
		robbery, assault, property, burglary, larceny, motor, arson;
	
	//Price Index
	private String place_name, place_id;
	private double index_nsa, index_sa, inflation;
	private int period,year; 
	
	
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
	
	public City() {}
	
	public City(int id, String state, String city, int population, int violentCrime, int murder, int rape, int robbery, int assault, int property, int burglary, int larceny, int motor, int arson)
	{

		this.place_id = Integer.toString(id);
		this.state = state; 
		this.place_name = city;
		this.population = population;
		this.violentCrime = violentCrime;
		this.murder = murder;
		this.rape = rape;
		this.robbery = robbery;
		this.assault = assault;
		this.property = property;
		this.burglary = burglary;
		this.larceny = larceny;
		this.motor = motor;
		this.arson = arson;
		this.score = 10;
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
	
	/**
	 * @return the Unabbreviated state names
	 */
	public String getUState() {
		return uState;
	}

	/**
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}

	/**
	 * @return the violentCrime
	 */
	public int getViolentCrime() {
		return violentCrime;
	}

	/**
	 * @return the murder
	 */
	public int getMurder() {
		return murder;
	}

	/**
	 * @return the rape
	 */
	public int getRape() {
		return rape;
	}

	/**
	 * @return the robbery
	 */
	public int getRobbery() {
		return robbery;
	}

	/**
	 * @return the assault
	 */
	public int getAssault() {
		return assault;
	}

	/**
	 * @return the property
	 */
	public int getProperty() {
		return property;
	}

	/**
	 * @return the burglary
	 */
	public int getBurglary() {
		return burglary;
	}

	/**
	 * @return the larceny
	 */
	public int getLarceny() {
		return larceny;
	}

	/**
	 * @return the motor
	 */
	public int getMotor() {
		return motor;
	}

	/**
	 * @return the arson
	 */
	public int getArson() {
		return arson;
	}

	/**
	 * @return the place_name
	 */
	public String getPlace_name() {
		return place_name;
	}

	/**
	 * @return the place_id
	 */
	public String getPlace_id() {
		return place_id;
	}

	/**
	 * @return the index_nsa
	 */
	public double getIndex_nsa() {
		return index_nsa;
	}

	/**
	 * @return the index_sa
	 */
	public double getIndex_sa() {
		return index_sa;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * calculates the price inflation given a certain time period.
	 * @param priceIndex - holds information on housing prices over the years.
	 * @return -the inflation over a certain period of time
	 */
	public double getpriceInflation(){
		return inflation;
	}
	
	
	///////////////////////////////////SETTERS////////////////////////////////////////
	
	/**
	 * Set the Inflation, 
	 */
	public void setInflation(){
		//if (index_nsa != 0.0 && index_sa !=0.0){
			inflation  = (((index_nsa - index_sa)/index_sa) * 100);
		//}else {
			//System.out.println("Indeces have to be set first.");
		//}
			
	}
	
	/**
	 * Set the Crime rate of the city
	 * @param d Crime rate
	 */
	public void setCrime(double d){
		crime = d;
	}
	
	/**
	 * Set zip code of the city
	 * @param zip - the new zip code
	 */
	public void setZip(String zip)
	{
		this.zip = zip;
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
		//for prototype we will have this weighting of Crime and housing
		
		// if crime and Price have values
		//if ((getCrime() != 0.0) && (getPrice() != 0.0 )){
		
		//}else {
		//	System.out.println(" Either Crime or Housing Prices have to be set ");
		//}
		
		this.score  = (int) calculateScore(6,4);
	}
	
	public void setLat(String lat){
		this.lat = lat;
	}
	
	public void setLong(String longe){
		this.lon = longe;
	}
	
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param uState the uState to set
	 */
	public void setUState(String uState) {
		this.uState = uState;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param population the population to set
	 */
	public void setPopulation(int population) {
		this.population = population;
	}

	/**
	 * @param violentCrime the violentCrime to set
	 */
	public void setViolentCrime(int violentCrime) {
		this.violentCrime = violentCrime;
	}

	/**
	 * @param murder the murder to set
	 */
	public void setMurder(int murder) {
		this.murder = murder;
	}

	/**
	 * @param rape the rape to set
	 */
	public void setRape(int rape) {
		this.rape = rape;
	}

	/**
	 * @param robbery the robbery to set
	 */
	public void setRobbery(int robbery) {
		this.robbery = robbery;
	}

	/**
	 * @param assault the assault to set
	 */
	public void setAssault(int assault) {
		this.assault = assault;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(int property) {
		this.property = property;
	}

	/**
	 * @param burglary the burglary to set
	 */
	public void setBurglary(int burglary) {
		this.burglary = burglary;
	}

	/**
	 * @param larceny the larceny to set
	 */
	public void setLarceny(int larceny) {
		this.larceny = larceny;
	}

	/**
	 * @param motor the motor to set
	 */
	public void setMotor(int motor) {
		this.motor = motor;
	}

	/**
	 * @param arson the arson to set
	 */
	public void setArson(int arson) {
		this.arson = arson;
	}

	/**
	 * @param place_name the place_name to set
	 */
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}

	/**
	 * @param place_id the place_id to set
	 */
	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * @param index_nsa the index_nsa to set
	 */
	public void setIndexNSA(int index_nsa) {
		this.index_nsa = index_nsa;
	}

	/**
	 * @param index_sa the index_sa to set
	 */
	public void setIndexSA(int index_sa) {
		this.index_sa = index_sa;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	
	
	///////////////////////////////////PRIVATE FUNCTIONS//////////////////////////////
	/**
	 * Generate a Score for a city. A Score that the City receives based on crimes and housing inflation
	 * @param crimes - holds information on different type of crime.
	 * @param priceIndex - holds information on the housing market.
	 * @return 
	 */
	private double calculateScore(int crimeWeight, int indexWeight ){
		
		//variables
		double houseScore  = 0;
		if (index_sa != 0){
			houseScore = (((this.index_nsa - this.index_sa)/this.index_sa) * 100)*indexWeight ;
		}
		double crimeScore = 0.0;
		double maxScore = 500;
		double cityScore = 0.0;						//returning Score
		
		//System.out.println("Index nsa: " + this.index_nsa + " , indes sa: " + this.index_sa);
	
		//System.out.println("Crime S: " + crimeScore + " , cityScore: " + cityScore  + " , maxScore:" + maxScore ) ;
		
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
		double wtArson = 8;
		double wtAssualt = 8;
		int wtBurglary = 5;
		double wtLarceny = 7;
		double wtMotor = 5 ;
		int wtMurder = 15;
		double wtProperty = 7;
		double wtRape = 10;
		int wtRobbery = 5;
		int wtViolentCrime = 15;
		
		crimeScore = this.arson*wtArson + this.assault*wtAssualt + this.burglary*wtBurglary + this.larceny*wtLarceny +  this.motor*wtMotor + this.murder*wtMurder + this.property*wtProperty + this.rape*wtRape + this.robbery*wtRobbery + this.violentCrime*wtViolentCrime;
		crimeScore = (crimeScore/this.population) * 100;
		crimeScore = crimeScore*crimeWeight;
		
		cityScore = maxScore - crimeScore - houseScore;
		//System.out.println("---- DONE CALC ----------");
		//System.out.println("Crime S: " + crimeScore + " , cityScore: " + cityScore + " , maxScore:" + maxScore ) ;
		
		return cityScore;
	}
	
	/**
	 * Calculates the crime rate of a city. 
	 * @param crimes
	 * @return - Total crimes/population in percent 
	 */
	private double calculateCrimeRate(){
		double totalCrime = totalCrime();
		double crimeRate = (totalCrime/getPopulation())*100;		//percentage 
		return crimeRate;
	}
	
	/**
	 * Gets the Total Crime
	 * @param crimes
	 * @return - the sum of all the crimes
	 */
	private double totalCrime(){
		double totalCrime = arson + assault + burglary + larceny  + motor + murder + property + rape + robbery + violentCrime;
		return totalCrime;
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
