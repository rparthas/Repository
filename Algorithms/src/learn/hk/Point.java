package learn.hk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Point {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int size = scanner.nextInt();
		int bucketSize = scanner.nextInt();
		List<Coordinates> bucket = new ArrayList<Coordinates>();
		for (int i = 0; i < size; i++) {
			Coordinates coordinates = new Coordinates();
			coordinates.index = scanner.nextInt();
			coordinates.x = scanner.nextDouble();
			coordinates.y = scanner.nextDouble();
			coordinates.z = scanner.nextDouble();
			bucket.add(coordinates);
		}
		
		StringBuffer queries = new StringBuffer();
		String input = scanner.nextLine();
		while (input!=null) {
			String[] split = input.split(" ");
			queries.append(split[0]);
			queries.append(",");
			queries.append(split[1]);
			queries.append(":");
			input = scanner.nextLine();
		}
		Collections.sort(bucket);
		String[] query = queries.toString().split(":");
		for (String operation : query) {
			String[] values = operation.split(",");
			int val = Integer.valueOf(values[1]);
			if ("F".equals(values[0])) {
				int ind = findVal(size, bucket, val);
				if (ind == -1) {
					System.out.println("Point doesn't exist in the bucket.");
				} else {
					System.out.println(bucket.get(ind).toString());
				}
			} else if ("R".equals(values[0])) {
				int ind = findVal(size, bucket, val);
				if (ind == -1) {
					System.out.println("Point doesn't exist in the bucket.");
				} else if (bucket.size() == size) {
					System.out.println("No more points can be deleted.");
				} else {
					System.out.println("Point id " + bucket.get(ind).index + " removed");
				}
			}
		}

	}

	private static int findVal(int size, List<Coordinates> bucket, int val) {
		int index = -1;
		for (int i = 0; i < size; i++) {
			if (bucket.get(i).index == val) {
				index = i;
				break;
			}
		}
		return index;
	}

}

class Coordinates implements Comparable<Coordinates> {
	int index;
	double x;
	double y;
	double z;

	@Override
	public int compareTo(Coordinates coordinates) {
		if (this.z > coordinates.z) {
			return -1;
		} else if (this.z < coordinates.z) {
			return 1;
		}
		return 0;
	}

	public String toString() {
		return index + " = (" + x + "," + y + "," + z + ")";
	}

}
