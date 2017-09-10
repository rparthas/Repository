package learn.hk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NumberOfWays {

	public static void main(String[] args) {
		long K = 2;
		long N = 2;

		List<Integer> list = new ArrayList<>();
		for(int i=1;i<=K;i++){
			list.add(i);
		}
		int count = findSum(list, N);
		System.out.println(count);
	}

	static int findSum(List<Integer> list, long k) {
		int globalCount = 0;
		if (k == 0) {
			globalCount++;
			return globalCount;
		} else if (k < 0)
			return globalCount;

		for (int i = 0; i < list.size(); i++) {
			long tmp = k;
			list.remove(i);
			globalCount += findSum(list, tmp);
			while (tmp >= 0) {
				tmp = tmp - i - 1;
				globalCount += findSum(list,tmp);
			}
		}
		return globalCount;
	}
}
