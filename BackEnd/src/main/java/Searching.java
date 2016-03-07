/**
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
		
		if (low==high){
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
		//System.out.println(" middle: " + middle );
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
				//System.out.println(" key found at: " + i); 
				return i;
			}
		}
		return -1;
	}
}
