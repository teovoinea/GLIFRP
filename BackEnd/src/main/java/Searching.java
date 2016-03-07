import java.util.Random;

/**
 * 
 * TODO: USE ArrayLists instead of Arrays
 * 
 * Searching 
 */

public class Searching {

	//binary search, make iterative bs? Further testing needs to be done => what if its not in array at all
	/**
	 * Binary Search
	 * 
	 * This is a recursive implementation of binary search
	 * @param a - the input array
	 * @param low - the lowerbound of where we are searching
	 * @param high - the upperbound of where we are searching
	 * @param key - what we are trying to find
	 * @return - return the index of where the key is
	 */
	private static int BinarySearch(Comparable[] a, int low, int high, Comparable key){
		int middle;
		//System.out.println("low: " + low + " high: " + high);
		if (low==high){
			//System.out.println("Coud not find: " + key + " ,lower: " + low);
			return low;
		}
		middle = (low + high)/2;
		//key > a[middle]
		if (a[middle].compareTo(key) < 0){
			return BinarySearch(a, middle+1,high,key);
		//key < a[middle]
		}else if (a[middle].compareTo(key) > 0){
			return BinarySearch(a,low,middle,key);
		}
		//System.out.println(" found middle at: " + middle );
		return middle;
	}
	
	/**
	 * Linear Search
	 * 
	 * @param a - input array 
	 * @param key - what we are looking for
	 * @return - return the index if in array 
	 */
	private static int LinearSearch(Comparable[] a , Comparable key){
		for (int i = 0; i < a.length; i++){
			if (a[i] == key){
				//System.out.println( key +" found at: " + i); 
				return i;
			}
		}
		//System.out.println(key + " Not Found");
		return -1;
	}
	
	/*
	public static void main(String[] args){
		Random rand = null;
		Integer[] a = new Integer[10];
		Integer[] b = new Integer[10];
		b[0] = 1;
		b[1] = 3;
		b[2] = 4;
		b[3] = 5;
		b[4] = 5;
		b[5] = 6;
		b[6] = 7;
		b[8] = 8;
		b[9] = 20;
		
		for (int i = 0; i < a.length; i++){
			double rNum = Math.random()*(10-0);
			a[i] = (int) rNum ;
		}
		
		
		System.out.println("Linear Search Tets ");
		showArray(a);
		LinearSearch(a, 1);
		LinearSearch(a, 5);
		LinearSearch(a, 6);
		
		System.out.println("Binary Search Tets ");
		showArray(b);
		BinarySearch(b, 0, b.length, 1);
		BinarySearch(b, 0, b.length, 3);
		BinarySearch(b, 0, b.length, 6);
		BinarySearch(b, 0, b.length, 2);
		BinarySearch(b, 0,b.length, 20);
	}
	*/
	private static void showArray(Integer[] a){
		for (int i = 0; i < a.length; i++){
			System.out.print(a[i] + ", ");
		}
		System.out.println("");
	}
}
