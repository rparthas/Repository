package sorting;

import java.util.ArrayList;
import java.util.List;

public class MergeSort implements Sort {

	List<Integer> output = null;

	@Override
	public List<Integer> sort(List<Integer> input) {
		// TODO Auto-generated method stub
		output = divide(input);
		return output;
	}

	public List<Integer> divide(List<Integer> input) {
		int pivot = (input.size()) / 2;
		if (pivot == 0) {
			return input;
		}
		List<Integer> tempList = new ArrayList<>();
		List<Integer> startList = new ArrayList<>();
		List<Integer> endList = new ArrayList<>();
		for (int i = 0; i < pivot; i++) {
			startList.add(input.get(i));
		}
		for (int i = pivot; i < input.size(); i++) {
			endList.add(input.get(i));
		}

		startList = divide(startList);
		endList = divide(endList);

		for (int i = 0; i < startList.size(); i++) {
			Integer int1 = startList.get(i);
			for (int j = 0; j < endList.size(); j++) {
				Integer int2 = endList.get(j);
				if (int1 > int2) {
					tempList.add(int2);
					endList.remove(j);
					j--;
					continue;
				}
			}
			tempList.add(int1);
		}
		tempList.addAll(endList);
		return tempList;
	}

}
