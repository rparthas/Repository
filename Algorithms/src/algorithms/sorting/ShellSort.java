package algorithms.sorting;

public class ShellSort extends InsertionSort {

	@Override
	public void sort(Comparable[] arr) {
		// TODO Auto-generated method stub

		int N = arr.length;
		int h = 1;
		while (h < N / 3) {
			h = 3 * h + 1;
		}
		while (h >= 1) {
			super.h = h;
			super.sort(arr);
			h = h / 3;
		}

	}
}
