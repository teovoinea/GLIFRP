package backend;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestQuery 
{
	private Query q;
	String cities[][];
	double num[][];
	@Before
	public void setUp() throws Exception 
	{
		q = new Query();
		cities = new String[27][2];
		num = new double[27][5];
		//Read file
		BufferedReader br = null;
		try
		{
			String currentLine = null;
			br = new BufferedReader(new FileReader("TestQueryData"));
			
			
			
			//skip first line
			currentLine = br.readLine();
			int i = 0;
			
			
			
			while((currentLine = br.readLine()) != null)
			{
				//city,state,nsa,sa,vc,non,re
				String splitLine[] = currentLine.split(",");
				
				cities[i][0] = splitLine[0];
				cities[i][1] = splitLine[1];
				
				
				if(!splitLine[2].equals("-"))
					num[i][0] = Double.parseDouble(splitLine[2]);
				else
					num[i][0] = -1.0f;
				
				
				if(!splitLine[3].equals("-"))
					num[i][1] = Double.parseDouble(splitLine[3]);
				else
					num[i][1] = -1.0f;
				
				if(!splitLine[4].equals("-"))
					num[i][2] = Double.parseDouble(splitLine[4]);
				else
					num[i][2] = -1.0f;
				
				if(!splitLine[5].equals("-"))
					num[i][3] = Double.parseDouble(splitLine[5]);
				else
					num[i][3] = -1.0f;
				
				if(!splitLine[6].equals("-"))
					num[i][4] = Double.parseDouble(splitLine[6]);
				else
					num[i][4] = -1.0f;
				
				i++;
				
				
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() throws Exception 
	{
		
	}
	
	@Test
	public void testGetCityData() 
	{
		for(int i = 0; i < 27; i++)
		{
			City c = q.getCityData(cities[i][0]);
			//Check
			boolean c_one = true, c_two = true, c_three = true, c_four = true, c_five = true;
			
			if(num[i][0] != -1.0f)
				c_one = c.getIndex_nsa() == num[i][0];
			if(num[i][1] != -1.0f)
				c_two = c.getIndex_sa() == num[i][1];
			if(num[i][2] != -1.0f)
				c_three = c.getViolentCrime() == num[i][2];
			if(num[i][3] != -1.0f)
				c_four = c.getMurder() == num[i][3];
			if(num[i][4] != -1.0f)
				c_five = c.getRape() == num[i][4];
			
			assertTrue
			(
				c_three &&
				c_four &&
				c_five
			);
		}
	}
	
	@Test
	public void testGetLowestCrime() 
	{
		//Arizona
		ArrayList<City> lc = q.getLowestCrime("Arizona", 5);
		//The 5 lowest crime cities for Arizona are
		/* Thatcher
		 * Pima
		 * Fredonia
		 * Huachuca City
		 * Jerome
		*/
		boolean isLow = false;
		for(City c : lc)
		{
			if(c.getName().equals("Thatcher") ||
			   c.getName().equals("Pima") ||
			   c.getName().equals("Fredonia") ||
			   c.getName().equals("Huachuca City") ||
			   c.getName().equals("Jerome"))
				isLow = true;
			else
				assertTrue(false);
		}
		assertTrue(isLow);
		
		//New York
		lc = q.getLowestCrime("New York", 5);
		//The 5 lowest crime cities for New York are
		/* 
		 * Adams Village
		 * Black River
		 * Asharoken Village
		 * Brewster
		 * Arcade Village
		*/
		isLow = false;
		for(City c : lc)
		{
			if(c.getName().equals("Adams Village") ||
			   c.getName().equals("Black River") ||
			   c.getName().equals("Asharoken Village") ||
			   c.getName().equals("Brewster") ||
			   c.getName().equals("Arcade Village"))
				isLow = true;
			else
				assertTrue(false);
		}
		assertTrue(isLow);
		
		//Michigan
		lc = q.getLowestCrime("Michigan", 5);
		//The 5 lowest crime cities for Michigan are
		/* 
		 * Eau Claire
		 * Elkton
		 * Edmore
		 * Frost Township
		 * Elsie
		*/
		isLow = false;
		for(City c : lc)
		{
			
			if(c.getName().equals("Eau Claire") ||
			   c.getName().equals("Frost Township") ||
			   c.getName().equals("Elkton") ||
			   c.getName().equals("Edmore") ||
			   c.getName().equals("Elsie"))
				isLow = true;
			else
				assertTrue(false);
		}
		assertTrue(isLow);
		
		//Idaho
		lc = q.getLowestCrime("Idaho", 5);
		//The 5 lowest crime cities for Arizona are
		/* 
		 * Bellevue
		 * Cascade
		 * Hagerman
		 * Parma
		 * Challis
		*/
		isLow = false;
		for(City c : lc)
		{
			if(c.getName().equals("Bellevue") ||
			   c.getName().equals("Cascade") ||
			   c.getName().equals("Challis") ||
			   c.getName().equals("Hagerman") ||
			   c.getName().equals("Parma"))
				isLow = true;
			else
				assertTrue(false);
		}
		assertTrue(isLow);
		
	}
	
	@Test
	public void testGetCityByCrime() 
	{
		for(int i = 0; i < 27; i++)
		{
			City c = q.getCityData(cities[i][0]);
			
			boolean c_one = true, c_two = true, c_three = true, c_four = true, c_five = true;
			
			if(num[i][0] != -1.0f)
				c_one = c.getIndex_nsa() == num[i][0];
			if(num[i][1] != -1.0f)
				c_two = c.getIndex_sa() == num[i][1];
			if(num[i][2] != -1.0f)
				c_three = c.getViolentCrime() == num[i][2];
			if(num[i][3] != -1.0f)
				c_four = c.getMurder() == num[i][3];
			if(num[i][4] != -1.0f)
				c_five = c.getRape() == num[i][4];
			
			
			//Check
			assertTrue
			(
				c_three
				&& c_four
				&& c_five
			);
		}
	}

	@Test
	public void testGetStateNames() 
	{
		ArrayList<String> stateNames = q.getStateNames();
		String states[] = {
			"AL",
			"AK",
			"AZ",
			"AR",
			"CA",
			"CO",
			"CT",
			"DE",
			"DC",
			"FL",
			"GA",
			"HI",
			"ID",
			"IL",
			"IN",
			"IA",
			"KS",
			"KY",
			"LA",
			"ME",
			"MD",
			"MA",
			"MI",
			"MN",
			"MS",
			"MO",
			"MT",
			"NE",
			"NV",
			"NH",
			"NJ",
			"NM",
			"NY",
			"NC",
			"ND",
			"OH",
			"OK",
			"OR",
			"PA",
			"RI",
			"SC",
			"SD",
			"TN",
			"TX",
			"UT",
			"VT",
			"VA",
			"WA",
			"WV",
			"WI",
			"WY"
		};
		for(int i = 0; i < states.length; i++)
			assertTrue(stateNames.contains(states[i]));
	}
	
}
