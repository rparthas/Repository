package algorithms.sorting;

public class InsertionSort extends Sort {

	int h = 1;

	@Override
	public void sort(Comparable[] arr) {
		for (int i = h; i < arr.length; i++) {
			Comparable key = arr[i];
			int index = i;
			for (int j = i - h; j >= 0; j = j - h) {
				if (less(key, arr[j])) {
					exchange(index, j, arr);
					index = j;
				} else {
					break;
				}
			}
		}
	}

}
