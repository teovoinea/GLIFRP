package backend;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SortingTest {

	City[] a;	

	@Before
	public void setUp() throws Exception {
		a = new City[3];
		a[0] = new City();
		a[0].setName("New York City");
		a[0].setState("New York");
		a[0].setLat("40");
		a[0].setLong("-73");
		a[0].setArson(30);
		a[0].setScore();
		
		a[1] = new City();
		a[1].setName("Philadelphia");
		a[1].setState("New Jersey");
		a[1].setLat("39.9526");
		a[1].setLong("-75.1652");
		a[1].setArson(23);
		a[1].setScore();
		
		a[2] = new City();
		a[2].setName("Washington DC");
		a[2].setState("District of Columbia");
		a[2].setLat("38.9072");
		a[2].setLong("-77.0369");
		a[2].setArson(8);
		a[2].setScore();
	}

	@Test
	public void sortByNameTest() {
		Sorting.SortByType(6, a);
		for(int i = 0; i < a.length-1;i++){
			assert(a[i].getName().compareTo(a[i+1].getName()) < 0);
		}
	}
	
	@Test
	public void sortByStateTest() {
		Sorting.SortByType(7, a);
		for(int i = 0; i < a.length-1;i++){
			assert(a[i].getState().compareTo(a[i+1].getState()) < 0);
		}
	}
	
	@Test
	public void sortByScoreTest() {
		Sorting.SortByType(5, a);
		for(int i = 0; i < a.length-1;i++){
			assert(a[i].getScore() < a[i+1].getScore());
		}
	}
	

}
