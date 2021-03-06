package backend;

import java.util.Random;

public class Sorting{
	private static int sortBy = 0; //attribute by which to work
	private static Comparable[] aux;
	/**
	 * Sorts the comparable array by any of the following attributes
	 * 0. Lat
	 * 1. Long
	 * 2. Zip
	 * 3. Crime
	 * 4. Price
	 * 5. Score
	 * 6. Name
	 * 7. State
	 * @param attribute ^read description
	 * @param a comparable array
	 */
	public static void SortByType(int attribute, Comparable[] a){
		sortBy = attribute;
		if (attribute == 0){
			aux = new Comparable[a.length];
			mergeSort(a, aux, 0, a.length);
		}
		if (attribute == 1){
			aux = new Comparable[a.length];
			mergeSort(a, aux, 0, a.length);
			//StdRandom.shuffle(a);
			//quickSort(a, 0, a.length - 1);
		}
		if (attribute == 2){
			aux = new Comparable[a.length];
			mergeSort(a, aux, 0, a.length);
		}
		if (attribute == 3){
			shuffle(a);
			quickSort3way(a, 0, a.length - 1);
			//shellSort(a);
		}
		if (attribute == 4){
			shuffle(a);
			quickSort3way(a, 0, a.length - 1);
			//heapSort(a);
		}
		if (attribute == 5){
			shuffle(a);
			quickSort3way(a, 0, a.length - 1);
		}
		if (attribute == 6){
			shuffle(a);
			quickSort3way(a, 0, a.length - 1);
		}
		if (attribute == 7){
			shuffle(a);
			quickSort3way(a, 0, a.length - 1);
		}
	}

	/**
	 * Shuffles the given array
	 * @param a
	 */
	private static void shuffle(Comparable[] a){
		Random rand = new Random();
		for(int i = 0; i < a.length;i++){
			int index = rand.nextInt(a.length);
			Comparable temp = a[index];
			a[index] = a[i];
			a[i] = temp;
		}
	}
	
	/**
	 * Merge sort
	 * @param a   Array to sort
	 * @param aux Temporary copy array
	 * @param lo  low index
	 * @param hi  high index
	 */
	private static void mergeSort(Comparable[] a, Comparable[] aux, int lo, int hi){
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		mergeSort(a, aux, lo, mid);
		mergeSort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi - 1);
	}

	/**
	 * Quick sort
	 * @param a  Array to quicksort
	 * @param lo low index
	 * @param hi high index
	 */
	private static void quickSort(Comparable[] a, int lo, int hi){
		if (hi <= lo) return;
		int j = partition(a, lo, hi);
		quickSort(a, lo, j-1);
		quickSort(a, j+1, hi);
	}

	/**
	 * 3-way quicksort
	 * @param a  Array to sort
	 * @param lo low index
	 * @param hi high index
	 */
	private static void quickSort3way(Comparable[] a, int lo, int hi){
		if (hi <= lo) return;
		int lt = lo, gt = hi;
		Comparable v = a[lo];
		int i = lo;
		while (i <= gt){
			int cmp = ((City)a[i]).compareTo((City)v, sortBy);
			if (cmp < 0) exch(a, lt++, i++);
			else if (cmp > 0) exch(a, i, gt--);
			else i++;
		}

		quickSort3way(a, lo, lt-1);
		quickSort3way(a, gt+1, hi);
	}

	/**
	 * Shell Sort
	 * @param a Array to sort
	 */
	private static void shellSort(Comparable[] a){
		int N = a.length;
		int h = 1;
		while (h >= 1){
			for(int i = h; i < N; i++){
				for(int j = i; j >= h && less(a[j], a[j-h]); j -= h){
					exch(a, j, j-h);
				}
			}
			h/= 3;
		}
	}

	/**
	 * Heap Sort
	 * @param pq Array to sort
	 */
	private static void heapSort(Comparable[] pq){
		int N = pq.length;
		for(int k = N/2; k >= 1; k--)
			sink(pq, k, N);
		while (N > 1){
			exch(pq, 1, N--);
			sink(pq, 1, N);
		}
	}

	/////////////////////////////helper functions
	
	/**
	 * sinks the item at an index
	 * @param pq comparable array
	 * @param k  index to sink
	 * @param N  size of pq
	 */
	private static void sink(Comparable[] pq, int k, int N){
		while (2*k <= N){
			int j = 2 * k;
			if (j < N && less(pq, j, j+1)) j++;
			if (!less(pq, k, j)) break;
			exch(pq, k, j);
			k = j;
		}
	}

	/**
	 * exchanges 2 objects in an object array
	 * @param a array containing objects to exchance
	 * @param i index of the first to change
	 * @param j index of the 2nd to change
	 */
	private static void exch(Object[] a, int i, int j){
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	/**
	 * [partition description]
	 * @param  a  [description]
	 * @param  lo [description]
	 * @param  hi [description]
	 * @return    [description]
	 */
	private static int partition(Comparable[] a, int lo, int hi){
		int i = lo;
		int j = hi + 1;
		Comparable v = a[lo];
		while (true){

			while (less(a[++i], v)){
				if (i == hi) break;
			}

			while (less(v, a[--j])){
				if (j == lo) break;
			}

			if (i >= j) break;

			exch(a, lo, j);
		}

		exch(a, lo, j);

		return j;
	}

	/**
	 * Checks which of the 2 objects are less
	 * @param  v object to compare
	 * @param  w object to compare
	 * @return   true if 1 is less than 2
	 */
	private static boolean less(Comparable v, Comparable w){
		return ((City)v).compareTo(w, sortBy) < 0;
	}

	/**
	 * Checks which of 2 objects are less
	 * @param  pq array the objects are in
	 * @param  i  index of the first object
	 * @param  j  index of the second object
	 * @return    true if pq[i] < pq[j]
	 */
	private static boolean less(Comparable[] pq, int i, int j){
		return ((City)pq[i-1]).compareTo(pq[j-1], sortBy) < 0;
	}

	/**
	 * Merges 2 parts of an array into 1
	 * @param a   array to merge in
	 * @param aux copy of array a
	 * @param lo  low index to merge
	 * @param mid where the 2 arrays meet
	 * @param hi  upper bound index
	 */
	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi){
		for(int k = lo; k <= hi; k++){
			aux[k] = a[k];
		}

		int i = lo; 
		int j = mid+1;
		for(int k = lo; k<= hi; k++){
			if (i > mid) a[k] = aux[j++];
			else if (j > hi) a[k] = aux[i++];
			else if (less(aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
		}
	}
	
	/**
	 * puts a new object in an array
	 * @param arr  array in which the objects are
	 * @param elem new object
	 * @param pos  index of where to put it
	 */
	public static void exch(Object[] arr,Object elem, int pos){
		arr[pos]=elem;
	}
	
}