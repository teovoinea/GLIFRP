package backend;
import java.util.ArrayList;
import java.util.Random;

/**
 *  Searching 
 * 
 * TODO: Search Based on Attribute
 *
 */

public class Searching {
	
	/**
	 * Binary Search     
	 * Searches the comparable ArrayList by any of the following attributes:
	 * 0. Lat
	 * 1. Long
	 * 2. Zip
	 * 3. Crime
	 * 4. Price
	 * 5. Score
	 * 6. Name
	 * 7. State 
	 * 
	 * This is a recursive implementation of binary search
	 * @param a - the input, an ArrayList<Comparable>
	 * @param key - what you are searching for
	 * @return - return the index of where the key is
	 */
	public static int BinarySearch(ArrayList<Comparable> a, Comparable key, int attribute){
		return BinarySearch(a, 0, a.size(), key, attribute);
	}
	
	//called by public method BinarySearch( , ) Above ^ 
	/**
	 * Private Binary Search 
	 * Searches the comparable ArrayList by any of the following attributes:
	 * 0. Lat
	 * 1. Long
	 * 2. Zip
	 * 3. Crime
	 * 4. Price
	 * 5. Score
	 * 6. Name
	 * 7. State 
	 * 
	 * @param a - the input ArrayList
	 * @param low - the lower bound (starts at 0)
	 * @param high - the upper bound (starts at the end of arraylist)
	 * @param key -  what we are searching for 
	 * @param attribute -searching by attribute
	 * @return
	 */
	private static int BinarySearch(ArrayList<Comparable> a, int low, int high, Comparable key, int attribute){
		
		int middle;
		//System.out.println("low: " + low + " high: " + high);
		
		//If the key we are looking for is not In Our ArrayList
		if (low==high){
			System.out.println("Coud not find: " + key + " ,lower: " + low + " ,Attribute: " + attribute);
			return low;
		}
		
		middle = (low + high)/2;									//the new Middle
		
		//key > a[middle]
		if (((City)a.get(middle)).compareTo(key, attribute) < 0){
			return BinarySearch(a, middle+1,high,key, attribute);
		//key < a[middle]
		}else if (((City)a.get(middle)).compareTo(key, attribute) > 0){
			return BinarySearch(a,low,middle,key, attribute);
		}
		System.out.println(" found " + key + " at index : " + middle + " ,Attribute: " + attribute );
		return middle;											 	// Return answer
	}

	
	/**
	 * getLowestCrime
	 * Returns the cities with the lowest crime rates. Requires an already sorted ArrayList(based on crime).
	 * 
	 * @param cities - an already sorted ArrayList(based on crimes) of cities
	 * @param amountLowest - how many of the lowest do you want? (lowest 3 cities, lowest 5 cities based on crime)
	 * @return - an ArrayList of the cities with the lowest crime rate
	 */
	public ArrayList<Comparable> getLowestCrime(ArrayList<Comparable> cities, int amountLowest){
		ArrayList<Comparable> retArray = new ArrayList<Comparable>();
		for (int i = 0; i < amountLowest; i++){
			retArray.add(cities.get(i));
		}
		return retArray;
	}
	
	/**
	 * getLowestHousing
	 * Returns the cities with the lowest housing Inflation. Requires an already sorted ArrayList(based on HSI).
	 * 
	 * @param cities - an already sorted ArrayList(based on housing inflation) of cities
	 * @param amountLowest - how many of the lowest you want (lowest 3 cities, lowest 5 cities)
	 * @return - an ArrayList of the cities with the lowest housing inflation
	 */
	public ArrayList<Comparable> getLowestHousing(ArrayList<Comparable> cities, int amountLowest){
		ArrayList<Comparable> retArray = new ArrayList<Comparable>();
		for (int i = 0; i < amountLowest; i++){
			retArray.add(cities.get(i));
		}
		return retArray;
	}
	
	/**
	 * Linear Search     
	 * 
	 * @param a - input array 
	 * @param key - what we are looking for
	 * @return - return the index if in array 
	 */
	private static int LinearSearch(ArrayList<Comparable> a , Comparable key){
		for (int i = 0; i < a.size(); i++){
			if (a.get(i).equals(key)){
				System.out.println( key +" found at: " + i); 
				return i;
			}
		}
		System.out.println(key + " Not Found");
		return -1;
	}
	
	
	public static void main(String[] args){
		
		/////// Make Cities + testing/seeing the scoring algorithm ////////////////
		City a = new City("Ashville", "ALABAMA");
		a.setCrime(15.4);
		a.setPrice(9);
		//showCityInfo(a);
		
		/*  //Error When I make this City For Some Reason??
		City b = new City("Bear Creek", "ALABAMA");
		b.setCrime(30.2);
		b.setPrice(2);
		showCityInfo(b);
		*/
		
		City b = new City("Brent", "ALABAMA");
		b.setCrime(30.2);
		b.setPrice(2);
		//showCityInfo(b);
		
		City c = new City("Brewton", "ALABAMA");
		c.setCrime(5.2);
		c.setPrice(20);
		//showCityInfo(c);
		
		City d = new City("Centre", "ALABAMA");
		d.setCrime(25.2);
		d.setPrice(10);
		//showCityInfo(d);
		
		///////////    Testing compareTo for cities     ////////////////////////////////////
		/*
		//attribute 3 - crime
		System.out.println("TEST compareTo Attribute 3 ");
		System.out.println(a.compareTo(b , 3));  //should ret -1
		System.out.println(b.compareTo(a , 3));  //should ret 1
		
		
		//attribute 4 - price (housing inflation)
		System.out.println("TEST compareTo Attribute 4 ");
		System.out.println(a.compareTo(b , 3));  //should ret -1
		System.out.println(b.compareTo(a , 3));  //should ret 1
		
		//attribute 6 - Name 
		System.out.println("TEST compareTo Attribute 6 ");
		System.out.println(a.compareTo(b , 3));  //should ret -1
		System.out.println(b.compareTo(a , 3));  //should ret 1
		*/
	 	
		////////////////////      Test Searching By Name      ///////////////////////
		//BinarySearch(ArrayList<Comparable> a, Comparable key, int attribute){
		/*
		System.out.println("TEST Searching by Name ");
		ArrayList<Comparable> cities = new ArrayList<Comparable>();
		cities.add(a);
		cities.add(b);
		cities.add(c);
		cities.add(d);
		
		//Should find these cities in ArrayList
		City lookin4City1 = new City ("Ashville", "ALABAMA");
		BinarySearch(cities, lookin4City1, 6);
		City lookin4City2 = new City ("aSHVILLE", "ALABAMA");
		BinarySearch(cities, lookin4City2, 6);
		
		City lookin4City3 = new City ("Brent", "ALABAMA");
		BinarySearch(cities, lookin4City3, 6);
		City lookin4City4 = new City ("Brewton", "ALABAMA");
		BinarySearch(cities, lookin4City4, 6);
		City lookin4City5 = new City ("Centre", "ALABAMA");
		BinarySearch(cities, lookin4City5, 6);
		
		
		//Shouldn't
		//Search for something that is not in ArrayList
		 
		//Getting ERRORS //
		City lookin4City6 = new City ("Boston", "ALABAMA");
		BinarySearch(cities, lookin4City6, 6);
		
		City lookin4City7 = new City ("", "ALABAMA");
		BinarySearch(cities, lookin4City7, 6);
		*/
		
		/////////////////// Test Search by Crime   ////////////////////////////
		/*
		System.out.println("TEST Searching by Crime ");
		ArrayList<Comparable> crimes = new ArrayList<Comparable>();
		crimes.add(c);
		crimes.add(a);
		crimes.add(d);
		crimes.add(b);
		
		City crmCity1 = new City ("Abbeville", "ALABAMA");
		crmCity1.setCrime(25.2);
		BinarySearch(crimes, crmCity1, 3);			
		
		City crmCity2 = new City ("Abbeville", "ALABAMA");
		crmCity2.setCrime(15.4);
		BinarySearch(crimes, crmCity2, 3);		
		
		//should find this
		//City crmCity6 = new City ("SweetHome", "ALABAMA");
		//crmCity6.setCrime(25.2);
		//BinarySearch(crimes, crmCity6, 3);
		*/
		
		///////////// Testing the Binary Searching     /////////////////////////////////////////
		/*
		Random rand = null;
		ArrayList<Comparable> a = new ArrayList<Comparable>();
		ArrayList<Comparable> b = new ArrayList<Comparable>();
		b.add(1);
		b.add(3);
		b.add(4);
		b.add(5);
		b.add(5);
		b.add(6);
		b.add(7);
		b.add(8);
		b.add(20);
		
		for (int i = 0; i < 10; i++){
			double rNum = Math.random()*(10-0);
			a.add((int)rNum) ;
		}
		
		
		System.out.println("Linear Search Tets ");
		showArray(a);
		
		LinearSearch(a, 1);
		LinearSearch(a, 5);
		LinearSearch(a, 6);
		
		System.out.println("Binary Search Tets ");
		showArray(b);
		
		BinarySearch(b, 0, b.size(), 1);
		BinarySearch(b, 0, b.size(), 3);
		BinarySearch(b, 0, b.size(), 6);
		BinarySearch(b, 0, b.size(), 2);
		BinarySearch(b, 0,b.size(), 20);	
		*/
	}
	
	//////////////////////             Methods Used for Testing               //////////////////////////
	
	// shows city information
	private static void showCityInfo(City c){
		System.out.print(c.getName()+ " , ");
		System.out.println(c.getState());
		System.out.println("Crime: " + c.getCrime() + " , Price:" + c.getPrice());
		c.calculateScore(6, 4);
		System.out.println(c.getScore());
		System.out.println(" ------------ ");
		System.out.println();
	}
	
	//prints out array onto console
	private static void showArray(ArrayList<Comparable> a){
		for (int i = 0; i < a.size(); i++){
			System.out.print(a.get(i) + ", ");
		}
		System.out.println("");
	}
}
