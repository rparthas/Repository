package learn.hk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Rajagopal
 * Sample Input
 * 16
 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
 * 2
 * 1 14
 * 14 16
 * 1 2 3 13 12 11 10 9 8 7 6 5 4 14 15 16
 */
public class ProfessorHoneyWell {

	public static void main(String args[]) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String line = br.readLine();
		int N = Integer.parseInt(line);
		int[] numArray = new int[N + 1];
		numArray[0] = Integer.MIN_VALUE;
		line = br.readLine();
		for (int i = 1; i <= N; i++) {
			numArray[i] = Integer.parseInt(line.split(" ")[i - 1]);

		}
		line = br.readLine();
		List<Integer> lefts = new ArrayList<>();
		List<Integer> rights = new ArrayList<>();
		int testCases = Integer.parseInt(line);
		for (int i = 0; i < testCases; i++) {
			line = br.readLine();
			int left = Integer.parseInt(line.split(" ")[0]);
			int right = Integer.parseInt(line.split(" ")[1]);
			lefts.add(left);
			rights.add(right);
		}

		for (int i = 1; i <= N; i++) {
			boolean changed = false;
			int repl = N - i + 1;
			for (int j = 0; j < lefts.size(); j++) {

				int left = lefts.get(j);
				int right = rights.get(j);
				if ((i >= left && i <= right) || (repl >= left && repl <= right)) {
					changed = !changed;
				}
			}
			if (changed) {
				System.out.print(numArray[N - i + 1] + " ");
			} else {
				System.out.print(numArray[i] + " ");
			}

		}
	}
}
