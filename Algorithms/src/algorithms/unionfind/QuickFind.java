package algorithms.unionfind;

import java.util.HashMap;
import java.util.Map;

public class QuickFind implements Finder {


	Map<Integer,Integer> numbers = new HashMap<Integer,Integer>();

	public QuickFind(int number) {
		for (int i = 0; i < number; i++) {
			numbers.put(i, i);
		}
	}
	
	public void union(int a, int b) {
		int aIndex = numbers.get(a);
		int bIndex = numbers.get(b);
		for(Integer num:numbers.keySet()){
			int numValue = numbers.get(num);
			if(numValue == aIndex || numValue == bIndex){
				numbers.put(num, aIndex);
			}
		}
	}

	public boolean isConnected(int a, int b) {
		boolean connected = numbers.get(a)==numbers.get(b);
		return connected;
	}

	@Override
	public void print() {
		System.out.println("Connected set"+numbers);
	}
}
