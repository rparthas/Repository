package algorithms.sorting;

import java.util.Random;

public abstract class Sort {

	public abstract void sort(Comparable[] arr);

	public boolean less(Comparable a, Comparable b) {
		int res = a.compareTo(b);
		if (res <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void exchange(int i, int j, Comparable[] arr) {
		Comparable temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	public void shuffle(Comparable[] arr) {
		for (int i = 1; i < arr.length; i++) {
			int randInt = (int) (Math.random() * i);
			exchange(i, randInt, arr);
		}
	}
}
