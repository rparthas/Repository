package sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuickSort implements Sort {

	List<Integer> output = null;

	@Override
	public List<Integer> sort(List<Integer> input) {
		output = quickSort(input);
		return output;
	}

	public List<Integer> partition(int pivot, List<Integer> input,
			boolean smaller) {
		List<Integer> result = new ArrayList<>();
		for (Integer number : input) {
			boolean add = smaller ? number < pivot ? true : false
					: number >= pivot ? true : false;
			if (add) {
				result.add(number);
			}
		}
		return result;
	}

	public List<Integer> quickSort(List<Integer> input) {
		if (input.size() <= 2) {
			if (input.size() > 0 && input.get(0) > input.get(input.size() - 1)) {
				Collections.reverse(input);
				return input;
			}
			return input;
		}
		int middle = input.size() / 2;
		int pivot = input.get(middle);
		List<Integer> smaller = quickSort(partition(pivot, input, true));
		List<Integer> bigger = quickSort(partition(pivot, input, false));
		smaller.addAll(bigger);
		return smaller;
	}
}
