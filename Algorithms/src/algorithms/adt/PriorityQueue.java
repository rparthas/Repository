package algorithms.adt;

import java.util.Comparator;
import java.util.List;

import algorithms.sorting.Sort;

public class PriorityQueue extends Sort {

	Comparable[] arr = null;
	int indexPosition = -1;

	public PriorityQueue(int m) {
		arr = new Comparable[m];
	}

	public PriorityQueue() {

	}

	public void swim(int position) {
		while (true) {
			int parent = (position / 2) - 1;
			if (position % 2 != 0) {
				parent = (position - 1) / 2;
			}
			if (position == parent || parent == -1)
				break;
			if (less(arr[position], arr[parent])) {
				exchange(parent, position, arr);
				position = parent;
			} else {
				break;
			}
		}

	}

	public void sink(int position) {
		while (true) {
			int child1 = (position * 2) + 1;
			int child2 = (position * 2) + 2;
			int childToCompare = child1;
			if (child1 >= indexPosition && child2 >= indexPosition)
				break;
			if (child1 < indexPosition && child2 < indexPosition
					&& less(arr[child2], arr[child1]))
				childToCompare = child2;

			if (childToCompare < indexPosition
					&& less(arr[childToCompare], arr[position])) {
				exchange(childToCompare, position, arr);
				position = childToCompare;
			} else {
				break;
			}
		}
	}

	public void insert(Comparable t) {
		// TODO Auto-generated method stub
		indexPosition++;
		if (!isFull()) {
			arr[indexPosition] = t;
			swim(indexPosition);
		}
	}

	private boolean isFull() {
		// TODO Auto-generated method stub
		return indexPosition >= arr.length;
	}

	public Comparable remove() {
		// TODO Auto-generated method stub
		Comparable t = null;
		if (!isEmpty()) {
			exchange(0, indexPosition, arr);
			t = arr[indexPosition];
			arr[indexPosition] = null;
			indexPosition--;
			sink(0);
		}
		return t;
	}

	private boolean isEmpty() {
		return indexPosition == -1;
	}

	public void print() {
		for (int i = 0; i <= indexPosition; i++) {
			Comparable num = arr[i];
			System.out.println("Parent:" + arr[i]);
			if (2 * i + 1 <= indexPosition) {
				System.out.println("Child 1:" + arr[2 * i + 1]);
			}
			if (2 * i + 2 <= indexPosition) {
				System.out.println("Child 2:" + arr[2 * i + 2]);
			}
		}
	}

	public static void main(String args[]) {

		PriorityQueue pq = new PriorityQueue(10);
		for (int i = 0; i < 10; i++) {
			pq.insert(i + 1);
		}
		pq.print();
		System.out
				.println("---------------------------------------------------------------------------------------");
		for (int i = 0; i < 10; i++) {
			System.out.println("Item removed[" + pq.remove().toString() + "]");
			pq.print();
			System.out
					.println("---------------------------------------------------------------------------------------");
		}

		/*
		 * PriorityQueue pq = new PriorityQueue(); Comparable[] arr = new
		 * Comparable[10]; for (int i = 0; i < 10; i++) { arr[i] = i + 1; }
		 * pq.sort(arr); pq.print();
		 */
	}

	@Override
	public void sort(Comparable[] arr) {
		this.arr = arr;
		this.indexPosition = arr.length - 1;
		for (int i = arr.length - 2; i >= 1; i--) {
			swim(i);
		}

	}

	public void extract() {
		Comparable[] aux = new Comparable[arr.length];
		int len = arr.length;
		for (int i = 0; i < len; i++) {
			aux[i] = remove();
		}
		arr = aux;
	}

}
