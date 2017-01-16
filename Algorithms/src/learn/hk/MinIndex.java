package learn.hk;

import java.util.Scanner;

public class MinIndex {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int size = scanner.nextInt();
		int[] a = new int[size];
		int[] b = new int[size];
		for (int i = 0; i < size; i++) {
			a[i] = scanner.nextInt();
		}
		for (int i = 0; i < size; i++) {
			b[i] = scanner.nextInt();
		}
		int minNum = Integer.MAX_VALUE;
		int minIndex = Integer.MAX_VALUE;
		for (int i = 0; i < size; i++) {
			int comp = a[i];
			for (int j = 0; j < size; j++) {
				if (comp == b[j]) {
					int diff = Math.abs(i - j);
					if (minIndex > diff) {
						minIndex = diff;
						minNum = comp;
					} else if (minIndex == diff && comp < minNum) {
						minNum = comp;
						minIndex = diff;
					}
				}
			}
		}
		System.out.println(minNum);
	}

}
