package algorithms.sorting;

public class MergeSort extends Sort {

	@Override
	public void sort(Comparable[] arr) {
		recursive(arr, 0, arr.length);
	}

	public void merge(Comparable[] arr, int lo, int high, int mid) {
		int i = lo;
		int j = mid;
		int end = high - lo;

		Comparable[] aux = new Comparable[end];

		for (int k = 0; k < end; k++) {
			if (j == high) {
				aux[k] = arr[i++];
			} else if (i == mid) {
				aux[k] = arr[j++];
			} else if (less(arr[i], arr[j])) {
				aux[k] = arr[i++];
			} else {
				aux[k] = arr[j++];
			}
		}
		for (int k = 0; k < end; k++) {
			arr[lo + k] = aux[k];
		}

	}

	public void recursive(Comparable[] arr, int low, int high) {
		int mid = low + (high - low) / 2;
		if (low >= mid || high <= mid) {
			return;
		}
		recursive(arr, low, mid);
		recursive(arr, mid, high);
		merge(arr, low, high, mid);
	}

}
