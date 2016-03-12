package backend;
public class Sorting{
	private static int sortBy = 0; //attribute by which to work
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
			Comparable[] aux = new Comparable[a.length];
			mergeSort(a, aux, 0, a.length);
		}
		if (attribute == 1){
			Comparable[] aux = new Comparable[a.length];
			mergeSort(a, aux, 0, a.length);
			//StdRandom.shuffle(a);
			//quickSort(a, 0, a.length - 1);
		}
		if (attribute == 2){
			Comparable[] aux = new Comparable[a.length];
			mergeSort(a, aux, 0, a.length);
		}
		if (attribute == 3){
			StdRandom.shuffle(a);
			quickSort3way(a, 0, a.length - 1);
			//shellSort(a);
		}
		if (attribute == 4){
			StdRandom.shuffle(a);
			quickSort3way(a, 0, a.length - 1);
			//heapSort(a);
		}
		if (attribute == 5){
			StdRandom.shuffle(a);
			quickSort3way(a, 0, a.length - 1);
		}
		if (attribute == 6){
			StdRandom.shuffle(a);
			quickSort3way(a, 0, a.length - 1);
		}
		if (attribute == 7){
			StdRandom.shuffle(a);
			quickSort3way(a, 0, a.length - 1);
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
		merge(a, aux, lo, mid, hi);
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
			int cmp = ((City)a[i]).compareTo(v, sortBy);
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
	
	private static void sink(Comparable[] pq, int k, int N){
		while (2*k <= N){
			int j = 2 * k;
			if (j < N && less(pq, j, j+1)) j++;
			if (!less(pq, k, j)) break;
			exch(pq, k, j);
			k = j;
		}
	}

	private static void exch(Object[] a, int i, int j){
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

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

	private static boolean less(Comparable v, Comparable w){
		return ((City)v).compareTo(w, sortBy) < 0;
	}

	private static boolean less(Comparable[] pq, int i, int j){
		return ((City)pq[i-1]).compareTo(pq[j-1], sortBy) < 0;
	}

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
}