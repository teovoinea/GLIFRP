package backend;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CityStateTest {
	
	static City city;
	static State state;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		OpenStreetMapWrapper mapwrap = new OpenStreetMapWrapper();
		mapwrap.buildByCityState("New York City", "New York");
		city = new Query().getCityData("New York");
		city.setLat(mapwrap.getLat());
        city.setLong(mapwrap.getLon());
        city.setScore();
        city = new Query().getCityByCrime(city);
		Usa usa = new Usa();		
		state = usa.findStateByStateName("New York");					
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetLat() {				
		assertTrue(Math.floor(Double.parseDouble(city.getLat())) == 40);
	}

	@Test
	public void testGetLong() {		
		assertTrue(Math.round(Double.parseDouble(city.getLong())) == -74);				
	}

	@Test
	public void testGetName() {		
		assertEquals(city.getName(),"New York");
		assertEquals(state.getName(),"New York");
	}

}
