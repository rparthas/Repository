package algorithms.sorting;

import algorithms.adt.LinkedListStruct;
import algorithms.adt.Stack;

public class IterativeQuickSort extends Sort {

	@Override
	public void sort(Comparable[] arr) {
		Integer lo = 0;
		Integer hi = arr.length - 1;
		Stack<Integer> stack = new LinkedListStruct<>();
		shuffle(arr);
		do {
			int pivotPostion = partition(arr, lo, hi);
			if (pivotPostion - 1 > lo) {
				stack.push(pivotPostion - 1);
				stack.push(pivotPostion);
			}
			if (pivotPostion + 1 < hi) {
				stack.push(pivotPostion + 1);
				stack.push(hi);
			}
			hi = stack.pop();
			lo = stack.pop();
		} while (lo != null && hi != null);
	}

	public int partition(Comparable[] a, int lo, int hi) {
		int pivotIndex = lo;
		Comparable pivotValue = a[lo];
		int i = lo + 1;
		while (i <= hi) {
			if (less(a[i], pivotValue)) {
				exchange(pivotIndex, i, a);
				pivotIndex = i;
				i++;
			} else if (less(pivotValue, a[i])) {
				exchange(i, hi, a);
				hi--;
			} else {
				i++;
			}
		}
		return pivotIndex;
	}

	public static void main(String args[]) {
		Integer arr[] = { 5, 1, 4, 6, 7, 2, 3, 9, 10, 8 };
		IterativeQuickSort sort = new IterativeQuickSort();
		// sort.sort(arr);
		sort.partition(arr, 0, arr.length - 1);
		for (Integer num : arr) {
			System.out.print(num.toString() + "\t");
		}
	}

}
