public class Sorting{

	/**
	 * Sorts the comparable array by any of the following attributes
	 * 0.
	 * 1.
	 * 2.
	 * 3.
	 * .
	 * .
	 * .
	 * @param attribute ^read description
	 * @param a comparable array
	 */
	public static void SortByType(int attribute, Comparable[] a){
		if (attribute == 0){
			Comparable[] aux = new Comparable[a.length];
			mergeSort(a, aux, 0, a.length);
		}
		if (attribute == 1){
			StdRandom.shuffle(a);
			quickSort(a, 0, a.length - 1);
		}
		if (attribute == 2){
			StdRandom.shuffle(a);
			quickSort3way(a, 0, a.length - 1);
		}
		if (attribute == 3){
			shellSort(a);
		}
		if (attribute == 4){
			heapSort(a);
		}
	}

	private static void mergeSort(Comparable[] a, Comparable[] aux, int lo, int hi){
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		mergeSort(a, aux, lo, mid);
		mergeSort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
	}


	private static void quickSort(Comparable a, int lo, int hi){
		if (hi <= lo) return;
		int j = partition(a, lo, hi);
		quickSort(a, lo, j-1);
		quickSort(a, j+1, hi);
	}

	private static void quickSort3way(){
		if (hi <= lo) return;
		int lt = lo, gt = hi;
		Comparable v = a[lo];
		int i = lo;
		while (i <= gt){
			int cmp = a[i].compareTo(v);
			if (cmp < 0) exch(a, lt++, i++);
			else if (cmp > 0) exch(a, i, gt--);
			else i++;
		}

		quickSort3way(a, lo, lt-1);
		quickSort3way(a, gt+1, hi);
	}

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

	private static void less(Comparable v, Comparable w){
		return v.compareTo(w) < 0;
	}


	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi){
		for(int k = lo; k <= hi; k++){
			aux[k] = a[k];
		}

		int i = lo; j = mid+1;
		for(int k = lo; k<= hi; k++){
			if (i > mid) a[k] = aux[j++];
			else if (j > hi) a[k] = aux[i++];
			else if (less(aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
		}
	}
}