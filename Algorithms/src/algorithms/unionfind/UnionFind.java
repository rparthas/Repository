package algorithms.unionfind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnionFind implements Finder {

	List<List<Integer>> numbers = new ArrayList<>();

	public UnionFind(int number) {
		for (int i = 0; i < number; i++) {
			List<Integer> temp = new ArrayList<>();
			numbers.add(temp);
			temp.add(i);
		}
	}

	public void union(int a, int b) {
		List<Integer> addList =new ArrayList<>();
		List<Integer> removeIndex = new ArrayList<>();
		for (List<Integer> list : numbers) {
			if (list.contains(a) ) {
				addList.addAll(list);
				removeIndex.add(numbers.indexOf(list));
			}
			if (list.contains(b) ) {
				addList.addAll(list);
				removeIndex.add(numbers.indexOf(list));
			}
		}
		Collections.reverse(removeIndex);
		for(int index:removeIndex){
			numbers.remove(index);
		}
		numbers.add(0, addList);
	}

	public boolean isConnected(int a, int b) {
		boolean connected = false;
		for (List<Integer> list : numbers) {
			if (list.contains(a) && list.contains(b)) {
				connected = true;
				break;
			}
		}
		return connected;
	}

	@Override
	public void print() {
		System.out.println("Connected set"+numbers);
	}
}
