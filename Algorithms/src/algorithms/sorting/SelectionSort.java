package algorithms.sorting;

public class SelectionSort extends Sort {

	@Override
	public void sort(Comparable[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			Comparable min = arr[i];
			int minIndex = i;
			for (int j = i + 1; j < arr.length; j++) {
				if (less(arr[j], min)) {
					min = arr[j];
					minIndex = j;
				}
			}
			exchange(i, minIndex, arr);
		}
	}

}
