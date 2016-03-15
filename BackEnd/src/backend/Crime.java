package backend;

public class Crime implements Comparable
{
	//Class used to hold data from the fbi crime database queries
	public String state, city;
	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public int getId() {
		return id;
	}

	public int getPopulation() {
		return population;
	}

	public int getViolentCrime() {
		return violentCrime;
	}

	public int getMurder() {
		return murder;
	}

	public int getRape() {
		return rape;
	}

	public int getRobbery() {
		return robbery;
	}

	public int getAssault() {
		return assault;
	}

	public int getProperty() {
		return property;
	}

	public int getBurglary() {
		return burglary;
	}

	public int getLarceny() {
		return larceny;
	}

	public int getMotor() {
		return motor;
	}

	public int getArson() {
		return arson;
	}

	public int id, population, violentCrime, murder, rape, 
		robbery, assault, property, burglary, larceny, motor, arson;

	public Crime(int id, String state, String city, int population, int violentCrime, int murder, int rape, int robbery, int assault, int property, int burglary, int larceny, int motor, int arson)
	{

		this.id = id;
		this.state = state; 
		this.city = city;
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
	}

	public int compareTo(Object o) 
	{
		if(((Crime)o).getMurder() > this.murder)
			return -1;
		else if(((Crime)o).getMurder() < this.murder)
			return 1;
		return 0;
	}
	
	public int compareTo(Crime o,int sortBy) 
	{
		if (sortBy == 3){
			if(o.getViolentCrime() > this.violentCrime)
				return -1;
			else if(o.getViolentCrime() < this.violentCrime)
				return 1;
		}
		return 0;
	}
}
