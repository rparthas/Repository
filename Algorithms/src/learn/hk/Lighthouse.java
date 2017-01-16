package learn.hk;

import java.util.Scanner;

public class Lighthouse {

	static String[][] lightHouse = null;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int dimensions = scanner.nextInt();
		lightHouse = new String[dimensions][dimensions];
		for (int row = 0; row < dimensions; row++) {
			String input = scanner.next();
			for (int column = 0; column < dimensions; column++) {
				lightHouse[row][column] = input.substring(column, column + 1);
			}
		}

		int maxRadius = 0;
		for (int row = 0; row < dimensions; row++) {
			for (int column = 0; column < dimensions; column++) {

				if (".".equals(lightHouse[row][column])) {
					int start = 0;
					while (isFree(row, column, start + 1, dimensions)) {
						start = start + 1;
					}
					if (start > maxRadius) {
						maxRadius = start;
					}
				}
			}

		}
		System.out.println(maxRadius);
		scanner.close();
	}

	public static boolean isFree(int rowPos, int colPos, int jumpPos, int dimensions) {
		boolean isFree = false;

		if (colPos - jumpPos >= 0 && ".".equals(lightHouse[rowPos][colPos - jumpPos])
				&& jumpPos >= calculateEuclid(rowPos, colPos, rowPos, colPos) - jumpPos) {
			if (colPos + jumpPos < dimensions && ".".equals(lightHouse[rowPos][colPos + jumpPos])
					&& jumpPos >= calculateEuclid(rowPos, colPos, rowPos, colPos + jumpPos)) {
				if (rowPos + jumpPos < dimensions && ".".equals(lightHouse[rowPos + jumpPos][colPos])
						&& jumpPos >= calculateEuclid(rowPos, colPos, rowPos + jumpPos, colPos)) {
					if (rowPos - jumpPos >= 0 && ".".equals(lightHouse[rowPos - jumpPos][colPos])
							&& jumpPos >= calculateEuclid(rowPos, colPos, rowPos + jumpPos, colPos)) {
						isFree = true;
					}
				}
			}

		}
		if (jumpPos == 1) {
			return true;
		} else {
			return isFree && isFree(rowPos - 1, colPos, jumpPos - 1, dimensions)
					&& isFree(rowPos + 1, colPos, jumpPos - 1, dimensions)
					&& isFree(rowPos, colPos - 1, jumpPos - 1, dimensions)
					&& isFree(rowPos, colPos + 1, jumpPos - 1, dimensions);
		}
	}

	public static double calculateEuclid(int x, int y, int a, int b) {
		double euclid = 0.0;
		euclid = Math.sqrt(Math.pow((x - y), 2) + Math.pow((a - b), 2));
		return euclid;
	}

}
