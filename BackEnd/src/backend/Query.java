package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Query
{
	private String dbc, dbh, queryCityData, queryCrimeData, queryHousingData, queryNames;
	private Crime d;
	private PriceIndex pi;
	private CityData cd;
	
	private ArrayList<String> stateNames = new ArrayList();
	private ArrayList<String> cityNames = new ArrayList();
	
	public CityData getCityData(String city) 
	{
		queryCityData = "SELECT * FROM Cities WHERE city=\'" + city + "\';";
		runQuery(dbh,queryCityData,"City");
		
		return cd;
	}

	public PriceIndex getPriceData(String city) 
	{
		queryHousingData = "SELECT * FROM Houses WHERE city LIKE \'%" + city + "%\';";
		runQuery(dbh,queryCityData,"Housing");
		
		return pi;
	}

	public Crime getCrimeData(String city) 
	{
		queryCrimeData = "SELECT * FROM Crime WHERE city=\'" + city + "\';";
		
		return d;
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
		this.queryNames = "SELECT city FROM Cities";
		runQuery(dbh, queryNames, "cityNames");
		
		return cityNames;
	}
	
	public Query()
	{
		this.dbc = "jdbc:sqlite:" + "us_crime.db";
		this.dbh = "jdbc:sqlite:" + "housing.db";		
	}
	
	public void runQuery(String conn, String query, String type)
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

					d = new Crime(id, state, city, population, violent_Crime, murder, rape, robbery, assault, property, burglary, larceny, motor, arson);
				
					//crimeData.add(d);
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

					pi = new PriceIndex(id, place_name, place_id, period, nsa, sa, year);
					
					//priceData.add(pi);
				}
				
				if(type.equals("City"))
				{
					String zip = rs.getString("zip");
					String state = rs.getString("state");
					String city = rs.getString("city");
					String lat = rs.getString("lat");
					String lng = rs.getString("lng");
					
					cd = new CityData(zip, state, city, lat, lng);
					//cityData.add(cd);
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