package sorting;

import java.util.ArrayList;
import java.util.List;

public class SelectionSort implements Sort {

	List<Integer> output = null;

	@Override
	public List<Integer> sort(List<Integer> input) {
		output = new ArrayList<>();
		List<Integer> tempList = new ArrayList<>(input);
		while (tempList.size() > 1) {
			Integer comparer = tempList.get(0);
			int compareIndex = 0;
			for (int i = 1; i < tempList.size(); i++) {
				Integer number = tempList.get(i);
				if (number < comparer) {
					comparer = number;
					compareIndex = i;
				}
				if (i == tempList.size() - 1) {
					if (compareIndex != tempList.size() - 1) {
						tempList.remove(compareIndex);
						tempList.add(compareIndex, number);
					}
					output.add(comparer);
					tempList.remove(tempList.size() - 1);
				}
			}
		}
		output.addAll(tempList);
		return output;
	}

}
