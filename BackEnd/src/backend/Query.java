package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Query
{
	private String dbc, dbh, queryCityData, queryCrimeData, queryHousingData, queryNames;
	private City city;
	private City d;
	
	private ArrayList<String> stateNames = new ArrayList<String>();
	private ArrayList<String> cityNames = new ArrayList<String>();
	private ArrayList<City> lowestCrime = new ArrayList<City>();

	Map<String, String> states = new HashMap<String, String>();
	
	
	public City getCityData(String city) 
	{
		city = city.replace("\'", "\\\'");
		
		queryCityData = "SELECT * FROM Cities WHERE city=\'" + city + "\';";
		queryHousingData = "SELECT id, place_name, place_id, period, index_nsa, index_sa, MAX(year) FROM Houses WHERE place_name LIKE \'%" + city + "\' ;";
		queryCrimeData = "SELECT * FROM Crime WHERE City=\'" + city + "\';";
		
		runQuery(dbh, queryCityData, "City", -1);//query all three databases
		runQuery(dbh, queryHousingData, "Housing", -1);
		runQuery(dbc, queryCrimeData, "Crime", -1);
		
		return this.city;
	}
	
	private void getStateCode()
	{
		
		states.put("alabama","AL");
		states.put("alaska","AK");
		states.put("alberta","AB");
		states.put("american Samoa","AS");
		states.put("arizona","AZ");
		states.put("arkansas","AR");
		states.put("armed Forces (AE)","AE");
		states.put("armed Forces Americas","AA");
		states.put("armed Forces Pacific","AP");
		states.put("british Columbia","BC");
		states.put("california","CA");
		states.put("colorado","CO");
		states.put("connecticut","CT");
		states.put("delaware","DE");
		states.put("district of columbia","DC");
		states.put("florida","FL");
		states.put("georgia","GA");
		states.put("guam","GU");
		states.put("hawaii","HI");
		states.put("idaho","ID");
		states.put("illinois","IL");
		states.put("indiana","IN");
		states.put("iowa","IA");
		states.put("kansas","KS");
		states.put("kentucky","KY");
		states.put("louisiana","LA");
		states.put("maine","ME");
		states.put("manitoba","MB");
		states.put("maryland","MD");
		states.put("massachusetts","MA");
		states.put("michigan","MI");
		states.put("minnesota","MN");
		states.put("mississippi","MS");
		states.put("missouri","MO");
		states.put("montana","MT");
		states.put("nebraska","NE");
		states.put("nevada","NV");
		states.put("new brunswick","NB");
		states.put("new hampshire","NH");
		states.put("new jersey","NJ");
		states.put("new mexico","NM");
		states.put("new uork","NY");
		states.put("newfoundland","NF");
		states.put("north carolina","NC");
		states.put("north dakota","ND");
		states.put("northwest nerritories","NT");
		states.put("nova scotia","NS");
		states.put("nunavut","NU");
		states.put("ohio","OH");
		states.put("oklahoma","OK");
		states.put("ontario","ON");
		states.put("oregon","OR");
		states.put("pennsylvania","PA");
		states.put("prince edward island","PE");
		states.put("puerto rico","PR");
		states.put("quebec","QC");
		states.put("rhode island","RI");
		states.put("saskatchewan","SK");
		states.put("south carolina","SC");
		states.put("south dakota","SD");
		states.put("tennessee","TN");
		states.put("texas","TX");
		states.put("utah","UT");
		states.put("vermont","VT");
		states.put("virgin islands","VI");
		states.put("virginia","VA");
		states.put("washington","WA");
		states.put("west Virginia","WV");
		states.put("wisconsin","WI");
		states.put("wyoming","WY");
		states.put("yukon territory","YT");
		
	}

	public ArrayList<City> getLowestCrime(String state, int n)
	{
		lowestCrime.clear();
		queryCrimeData = "SELECT * FROM Crime WHERE state=\'" + state.toUpperCase() + "\' ORDER BY Violent_crime LIMIT " + Integer.toString(n) + ";";
		runQuery(dbc,queryCrimeData,"Crime", n);
		
		return lowestCrime;
	}
	
	public City getCityByCrime(City c)
	{
		this.city = c;
		String name = c.getName();
		
		name = name.replace("\'", "\'\'");
		
		
		queryCityData = "SELECT * FROM Cities WHERE city=\'" + name + "\' AND state=\'" + states.get(c.getState()) + "\';";
		queryHousingData = "SELECT id, place_name, place_id, period, index_nsa, index_sa, MAX(year) FROM Houses WHERE place_name LIKE \'%" + name + "%" + states.get(c.getState()) + "%\';";
		
		runQuery(dbh, queryCityData, "City", -1);//query all three databases
		runQuery(dbh, queryHousingData, "Housing", -1);
		
		return this.city;
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
		getStateCode();
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
/*<<<<<<< HEAD
				
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
>>>>>>> f3720602f817cdc1521a2a0bdefa7b1db72fe100*/
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
		//int year = rs.getInt("year");
		if(n == -1)
		{
			this.city.setIndexNSA(nsa);
			this.city.setIndexSA(sa);
			//this.city.setYear(year);
		}
		else
		{
			this.lowestCrime.get(n).setIndexNSA(nsa);
			this.lowestCrime.get(n).setIndexSA(sa);
			//this.lowestCrime.get(n).setYear(year);
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
		
		this.city = new City();
		
		this.city.setUState(state);
		this.city.setPopulation(population);
		this.city.setName(city);
		
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