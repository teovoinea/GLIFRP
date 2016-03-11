package main.java;
import java.util.ArrayList;
import java.util.Random;

/**
 *  Searching 
 * 
 * TODO: Search Based on Attribute
 * Sorts the comparable array by any of the following attributes
 * 0. Lat
 *  1. Long
 *  2. Zip
 *  3. Crime
 *  4. Price
 *  5. Score
 *  6. Name
 *  7. State 
 *
 */

public class Searching {
	
	/**
	 * Binary Search     
	 * 
	 * This is a recursive implementation of binary search
	 * @param a - the input, an ArrayList<Comparable>
	 * @param key - what you are searching for
	 * @return - return the index of where the key is
	 */
	public static int BinarySearch(ArrayList<Comparable> a, Comparable key){
		return BinarySearch(a, 0, a.size(), key);
	}
	
	//called by public method BinarySearch( , ) Above ^ 
	private static int BinarySearch(ArrayList<Comparable> a, int low, int high, Comparable key){
		
		int middle;
		//System.out.println("low: " + low + " high: " + high);
		
		//If the key we are looking for is not In Our ArrayList
		if (low==high){
			System.out.println("Coud not find: " + key + " ,lower: " + low);
			return low;
		}
		
		middle = (low + high)/2;									//the new Middle
		
		//key > a[middle]
		if (a.get(middle).compareTo(key) < 0){
			return BinarySearch(a, middle+1,high,key);
		//key < a[middle]
		}else if (a.get(middle).compareTo(key) > 0){
			return BinarySearch(a,low,middle,key);
		}
		System.out.println(" found " + key + " at: " + middle );
		return middle;
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
	
	//prints out array onto console
	private static void showArray(ArrayList<Comparable> a){
		for (int i = 0; i < a.size(); i++){
			System.out.print(a.get(i) + ", ");
		}
		System.out.println("");
	}
}
