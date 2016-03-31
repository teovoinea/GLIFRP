package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Query
{
	private String dbc, dbh, queryCityData, queryCrimeData, queryHousingData, queryNames;
	private City city;
	
	private ArrayList<String> stateNames = new ArrayList<String>();
	private ArrayList<String> cityNames = new ArrayList<String>();
	private ArrayList<City> lowestCrime = new ArrayList<City>();
	
	public City getCityData(String city) 
	{
		queryCityData = "SELECT * FROM Cities WHERE city=\'" + city + "\';";
		queryHousingData = "SELECT id, place_name, placeid, period, index_nsa, index_sa, MAX(year) FROM Houses WHERE place_name LIKE \'%" + city + "\' ;";
		queryCrimeData = "SELECT * FROM Crime WHERE City=\'" + city + "\';";
		
		runQuery(dbh, queryCityData, "City", -1);//query all three databases
		runQuery(dbh, queryHousingData, "Housing", -1);
		runQuery(dbc, queryCrimeData, "Crime", -1);
		
		return this.city;
	}

	public ArrayList<City> getLowestCrime(String state, int n)
	{
		lowestCrime.clear();
		
		queryCrimeData = "SELECT * FROM Crime WHERE state=\'" + state.toUpperCase() + "\' ORDER BY Violent_crime LIMIT " + Integer.toString(n) + ";";
		runQuery(dbc,queryCrimeData,"LowestCrime", n);
		
		//Assuming that n is not 0 for the moment
		for(int i = 0; i < n; i++)
		{
			queryCityData = "SELECT * FROM Cities WHERE city=\'" + lowestCrime.get(i).getName() + "\';";
			queryHousingData = "SELECT id, place_name, placeid, period, index_nsa, index_sa, MAX(year) FROM Houses WHERE place_name LIKE \'%" + lowestCrime.get(i).getName() + "\' ;";
			
			runQuery(dbh, queryCityData, "City", n);//query all three databases
			runQuery(dbh, queryHousingData, "Housing", n);
		}
		
		return lowestCrime;
	}

	public ArrayList<String> getStateNames()
	{
		stateNames.clear();
		this.queryNames = "SELECT DISTINCT state FROM Cities";
		runQuery(dbh, queryNames, "stateNames", -1);
		
		return stateNames;
	}
	
	public ArrayList<String> getCityNames(String state)
	{
		cityNames.clear();
		this.queryNames = "SELECT city FROM cities WHERE state=\'" + state + "\';";
		runQuery(dbh, queryNames, "cityNames", -1);
		
		return cityNames;
	}
	
	public Query()
	{
		this.dbc = "jdbc:sqlite:" + "us_crime.db";
		this.dbh = "jdbc:sqlite:" + "housing.db";		
	}
	
	
	/**
	 * Query the database with the connection string provided
	 * @param conn
	 * @param query
	 * @param type
	 */
	private void runQuery(String conn, String query, String type, int n)
	{
		Connection connection = null;
		Statement statement = null;
		try
		{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(conn);
			connection.setAutoCommit(false);

			statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(query);

			while(rs.next())
			{
				
				if(type.equals("City"))
				{
					addCity(rs, n);
				}
				
				if(type.equals("Housing"))
				{
					addHousing(rs, n);
				}
				
				if(type.equals("Crime"))
				{
					addCrime(rs, n);
				}
				
				if(type.equals("stateNames"))
				{
					String stateName = rs.getString("state");
					stateNames.add(stateName);
				}
				
				if(type.equals("cityNames"))
				{
					String cityName = rs.getString("city");
					cityNames.add(cityName);
				}
<<<<<<< HEAD
				
				if(type.equals("LowestCrime"))
				{
					int id = rs.getInt("id");
					String state = rs.getString("State");
					String city = rs.getString("City");
					int population = rs.getInt("Population");
					int violent_Crime = rs.getInt("Violent_Crime");
					int murder = rs.getInt("Murder_and_nonnegligent_manslaughter");
					int rape = rs.getInt("Rape");
					int robbery = rs.getInt("Robbery");
					int assault = rs.getInt("Aggravated_assault");
					int property = rs.getInt("Property_crime");
					int burglary = rs.getInt("Burglary");
					int larceny = rs.getInt("Larceny_theft");
					int motor = rs.getInt("Motor_vehicle_theft");
					int arson = rs.getInt("Arson");

					d = new City(id, state, city, population, violent_Crime, murder, rape, robbery, assault, property, burglary, larceny, motor, arson);
				
					lowestCrime.add(d);
				}
=======
>>>>>>> f3720602f817cdc1521a2a0bdefa7b1db72fe100
			}

			rs.close();
			statement.close();
			connection.close();
		}
		catch(Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    		System.exit(0);
		}
	}
	
	private void addCity(ResultSet rs, int n) throws SQLException
	{
		String zip = rs.getString("zip");
		String state = rs.getString("state");
		String city = rs.getString("city");
		String lat = rs.getString("lat");
		String lng = rs.getString("lng");
		
		if(n == -1)
		{
			this.city = new City(city, state);
			this.city.setLat(lat);
			this.city.setLong(lng);
			this.city.setZip(zip);
		}
		else
		{
			this.lowestCrime.get(n).setState(state);
			this.lowestCrime.get(n).setName(city);
			this.lowestCrime.get(n).setLat(lat);
			this.lowestCrime.get(n).setLong(lng);
			this.lowestCrime.get(n).setZip(zip);
		}
	}
	
	private void addHousing(ResultSet rs, int n) throws SQLException
	{
		int id = rs.getInt("id");
		String place_name = rs.getString("place_name");
		String place_id = rs.getString("place_id");
		int period = rs.getInt("period");
		int nsa = rs.getInt("index_nsa");
		int sa = rs.getInt("index_sa");
		int year = rs.getInt("year");
		if(n == -1)
		{
			this.city.setIndexNSA(nsa);
			this.city.setIndexSA(sa);
			this.city.setYear(year);
		}
		else
		{
			this.lowestCrime.get(n).setIndexNSA(nsa);
			this.lowestCrime.get(n).setIndexSA(sa);
			this.lowestCrime.get(n).setYear(year);
		}
	}
	
	private void addCrime(ResultSet rs, int n) throws SQLException
	{
		int id = rs.getInt("id");
		String state = rs.getString("State");
		String city = rs.getString("City");
		int population = rs.getInt("Population");
		int violent_Crime = rs.getInt("Violent_Crime");
		int murder = rs.getInt("Murder_and_nonnegligent_manslaughter");
		int rape = rs.getInt("Rape");
		int robbery = rs.getInt("Robbery");
		int assault = rs.getInt("Aggravated_assault");
		int property = rs.getInt("Property_crime");
		int burglary = rs.getInt("Burglary");
		int larceny = rs.getInt("Larceny_theft");
		int motor = rs.getInt("Motor_vehicle_theft");
		int arson = rs.getInt("Arson");
		
		if(this.city == null)
		{
			this.city = new City();
		}
		
		this.city.setUState(state);
		this.city.setPopulation(population);
		
		this.city.setViolentCrime(violent_Crime);
		this.city.setMurder(murder);
		this.city.setRape(rape);
		this.city.setRobbery(robbery);
		this.city.setAssault(assault);
		this.city.setProperty(property);
		this.city.setBurglary(burglary);
		this.city.setLarceny(larceny);
		this.city.setMotor(motor);
		this.city.setArson(arson);
		
		if(n != -1)
		{
			this.lowestCrime.add(this.city);
		}
	}
}