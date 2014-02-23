package sorting;

import java.util.ArrayList;
import java.util.List;

public class HeapSort implements Sort {

	Integer[] output = null;

	@Override
	public List<Integer> sort(List<Integer> input) {
		output = new Integer[input.size() + 1];
		for (int i = 1; i <= input.size(); i++) {
			output[i] = input.get(i - 1);
		}

		buildHeap();
		List<Integer> tempList = new ArrayList<>();
		for (int i = output.length - 1; i >= 1; i--) {
			Integer remove = output[1];
			tempList.add(remove);
			swap(1, i);
			Integer[] temp = new Integer[output.length - 1];
			for (int j = 1; j < output.length - 1; j++) {
				temp[j] = output[j];
			}
			output = temp;
			heapify(1);
		}
		return tempList;
	}

	private void buildHeap() {
		int pivot = (output.length - 1) / 2;
		for (int i = pivot; i >= 1; i--) {
			heapify(i);
		}
	}

	private void heapify(int index) {
		Integer left = left(index);
		Integer right = right(index);
		int smallest = index;
		if (left != null && output[left] < output[smallest]) {
			smallest = left;
		}
		if (right != null && output[right] < output[smallest]) {
			smallest = right;
		}
		if (smallest != index) {
			swap(smallest, index);
			heapify(smallest);
		}
	}

	private Integer left(int index) {
		index = 2 * index;
		if (index >= output.length) {
			return null;
		}
		return index;
	}

	private Integer right(int index) {
		index = 2 * index + 1;
		if (index >= output.length) {
			return null;
		}
		return index;
	}

	private Integer parent(int index) {
		index = (index / 2);
		if (index < 0) {
			return null;
		}
		return index;
	}

	private void swap(int index1, int index2) {
		int temp = output[index1];
		output[index1] = output[index2];
		output[index2] = temp;
	}

}
