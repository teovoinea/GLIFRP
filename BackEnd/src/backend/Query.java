package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Query
{
	private String dbc, dbh, queryCityData, queryCrimeData, queryHousingData, queryNames;
	private City city;
	private Crime d;
	
	private ArrayList<String> stateNames = new ArrayList();
	private ArrayList<String> cityNames = new ArrayList();
	private ArrayList<Crime> lowestCrime = new ArrayList();
	
	public City getCityData(String city) 
	{
		queryCityData = "SELECT * FROM Cities WHERE city=\'" + city + "\';";
		queryHousingData = "SELECT id, place_name, placeid, period, index_nsa, index_sa, MAX(year) FROM Houses WHERE place_name LIKE \'%" + city + "\' ;";
		queryCrimeData = "SELECT * FROM Crime WHERE City=\'" + city + "\';";
		
		runQuery(dbh, queryCityData, "City");//query all three databases
		runQuery(dbh, queryHousingData, "Housing");
		runQuery(dbc, queryCrimeData, "Crime");
		
		return this.city;
	}

	public ArrayList<Crime> getLowestCrime(String state, int n)
	{
		lowestCrime.clear();
		queryCrimeData = "SELECT * FROM Crime WHERE state=\'" + state.toUpperCase() + "\' ORDER BY Violent_crime LIMIT " + Integer.toString(n) + ";";
		String x = "LowestCrime";
		runQuery(dbc,queryCrimeData,x);
		
		return lowestCrime;
	}

	public ArrayList<String> getStateNames()
	{
		stateNames.clear();
		this.queryNames = "SELECT DISTINCT state FROM Cities";
		runQuery(dbh, queryNames, "stateNames");
		
		return stateNames;
	}
	
	public ArrayList<String> getCityNames(String state)
	{
		cityNames.clear();
		this.queryNames = "SELECT city FROM cities WHERE state=\'" + state + "\';";
		runQuery(dbh, queryNames, "cityNames");
		
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
	private void runQuery(String conn, String query, String type)
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
					String zip = rs.getString("zip");
					String state = rs.getString("state");
					String city = rs.getString("city");
					String lat = rs.getString("lat");
					String lng = rs.getString("lng");
					
					this.city = new City(city, state);
					this.city.setLat(lat);
					this.city.setLong(lng);
					this.city.setZip(zip);
				}
				
				if(type.equals("Housing"))
				{
					int id = rs.getInt("id");
					String place_name = rs.getString("place_name");
					String place_id = rs.getString("place_id");
					int period = rs.getInt("period");
					int nsa = rs.getInt("index_nsa");
					int sa = rs.getInt("index_sa");
					int year = rs.getInt("year");

					this.city.setIndexNSA(nsa);
					this.city.setIndexSA(sa);
					this.city.setYear(year);
					
					//priceData.add(pi);
				}
				
				if(type.equals("Crime"))
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

					d = new Crime(id, state, city, population, violent_Crime, murder, rape, robbery, assault, property, burglary, larceny, motor, arson);
				
					lowestCrime.add(d);
				}
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
}