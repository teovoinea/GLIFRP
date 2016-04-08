package backend;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UsaTest {

	private static Usa usa = new Usa();
	private static State NY;
	
	/**
	 * Load the state object that will be used for the testing
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		NY = usa.findStateByStateName("New York");
	}

	/**
	 * Test the BFS methods by searching for neighbouring states from the starting state new york
	 */
	@Test
	public void testBFSNeighbours() {
		ArrayList<State> states = usa.getNeighbouringStates(NY, 5);
		assertTrue(testLimitedBFS(NY,states,5));
	}
	
	/**
	 * Assuming the neighbour method works, get two states from the previous method and check to see whether they have 
	 * the right distance in between them
	 */
	@Test
	public void testBFSDistance(){
		ArrayList<State> states = usa.getNeighbouringStates(NY, 5);
		assertEquals(usa.getHops(NY,states.get(states.size()-1)),5);
	}
	
	/**
	 * Find a state by the state name test, this implements DFS internally and searches the graph for the state with that name
	 */
	@Test
	public void testRDFSFindStateByCity(){
		assertEquals(usa.findStateByStateName("New York"), NY);
	}
	
	/**
	 * Test DFS by running to find neighbouring states and making sure that finding the lowest common cities in those states
	 * only explores those states (implying that the DFS works correctly)
	 */
	@Test
	public void testDFSCorrectCities(){
		ArrayList<State> states = usa.getNeighbouringStates(NY, 5);
		ArrayList<City> cities = usa.findLowestCrimeRate(states, 5);
		assertTrue(correctCities(cities,states));
	}

	/**
	 * Check whether the neighbour states found by BFS are within the correct distance
	 * @param state - start state
	 * @param states - neighbouring states
	 * @param length - distance from start states
	 * @return boolean - true if all states are within range, false if not
	 */
	private boolean testLimitedBFS(State state,ArrayList<State> states, int length){
		for (int i =0; i < states.size();i++){
			if (usa.getHops(state,states.get(i)) > length){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Check whether cities with the lowest crime rates in the given states are actually within those given states 
	 * (tests DFS for lowest crime rates)
	 * 
	 * @param cities - the cities you are checking
	 * @param states - the states you are checking
	 * @return boolean - True if all the cities belong to these states, false if any cities don't belong to one of these states
	 */
	private boolean correctCities(ArrayList<City> cities, ArrayList<State> states){
		boolean contained = true;
		for (City city : cities){
			for (State state : states){
				if (state.contains(city)){
					contained = false;
				}else{
					state.setMarked(true);
				}
			}
			if (!contained){
				return false;
			}
		}
		
		for (State state : states){
			if (!state.isMarked()){
				return false;
			}
		}
		
		usa.resetMarked();
		
		return true;
		
	}
	
}
