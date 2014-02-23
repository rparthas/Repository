package sorting;

import java.util.Arrays;
import java.util.List;

public class BubbleSort implements Sort {

	@Override
	public List<Integer> sort(List<Integer> input) {
		Integer[] output = new Integer[input.size()];
		for (int i = 0; i < input.size(); i++) {
			output[i] = input.get(i);
		}
		for (int i = 0; i < output.length; i++) {
			for (int j = output.length - 1; j > i; j--) {
				if (output[j - 1] > output[j]) {
					swap(output, j, j - 1);
				}
			}
		}
		return Arrays.asList(output);
	}

	private void swap(Integer[] output, int index1, int index2) {
		int temp = output[index1];
		output[index1] = output[index2];
		output[index2] = temp;
	}

}
