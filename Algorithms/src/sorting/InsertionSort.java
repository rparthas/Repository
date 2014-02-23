package sorting;

import java.util.ArrayList;
import java.util.List;

public class InsertionSort implements Sort {

	@Override
	public List<Integer> sort(List<Integer> input) {
		List<Integer> output = new ArrayList<>(input);
		for (int i = 1; i < output.size(); i++) {
			int key = output.get(i);
			int positionToMove = i;
			for (int j = i - 1; j >= 0; j--) {
				if (output.get(j) > key) {
					replace(output, j + 1, output.get(j));
					positionToMove = j;
				}
			}
			replace(output, positionToMove, key);
		}
		return output;
	}

	private void replace(List<Integer> output, int removePosition,
			Integer addElement) {
		Integer removeObject = output.get(removePosition);
		output.remove(removeObject);
		output.add(removePosition, addElement);
	}
}
