package algorithms.sorting;

public class QuickSort extends Sort {

	@Override
	public void sort(Comparable[] arr) {
		recursiveSort(arr, 0, arr.length-1);
	}

	public int partition(Comparable[] a, int lo, int hi) {
		int i = lo, j = hi + 1; // left and right scan indices
		Comparable v = a[lo]; // partitioning item
		while (true) { // Scan right, scan left, check for scan complete, and
						// exchange.
			while (less(a[++i], v))
				if (i == hi)
					break;
			while (less(v, a[--j]))
				if (j == lo)
					break;
			if (i >= j)
				break;
			exchange(i, j, a);
		}
		exchange(lo, j, a); // Put v = a[j] into position
		return j;

	}

	public void recursiveSort(Comparable[] arr, int low, int high) {
		if (high <= low) {
			return;
		}
		int pivot = partition(arr, low, high);
		boolean last = pivot-1-low > high-pivot+1 ?true:false;
		if(last){
			recursiveSort(arr, pivot + 1, high);
			recursiveSort(arr, low, pivot - 1);
		}else{
			recursiveSort(arr, low, pivot - 1);
			recursiveSort(arr, pivot + 1, high);
		}
		

	}

	public static void main(String args[]) {
		Integer arr[] = { 1, 5, 4, 6, 7, 2, 3, 9, 10, 8 };
		QuickSort sort = new QuickSort();
		sort.sort(arr);
		for (Integer num : arr) {
			System.out.print(num.toString() + "\t");
		}
	}

}
