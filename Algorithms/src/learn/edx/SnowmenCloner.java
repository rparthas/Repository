package learn.edx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class SnowmenCloner {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
		PrintWriter pw = new PrintWriter("output.txt");
		String line = br.readLine();
		int count = Integer.parseInt(line);
		long total = 0;
		Snowmen[] arr = new Snowmen[count + 1];
		arr[0] = new Snowmen(0, null, 0);
		// System.out.println(arr[0]);
		for (int i = 1; i <= count; i++) {
			line = br.readLine();
			StringTokenizer tokenizer = new StringTokenizer(line, " ");
			int snowMenIndex = Integer.valueOf(tokenizer.nextToken());
			Long mass = Long.valueOf(tokenizer.nextToken());
			arr[i] = new Snowmen(mass, arr[snowMenIndex], i);
			System.out.println(arr[i]);
			total += arr[i].getTotalMass();
		}
		pw.println(total);
		pw.close();
		br.close();
	}

}

class Snowmen {
	private long totalMass = 0;
	private long mass = 0;
	private Snowmen prev = null;
	private int id = 0;

	public Snowmen(long mass, Snowmen prev, int id) {
		this.id = id;
		this.mass = mass;
		this.prev = prev;
		if (prev == null) {
			totalMass = mass;
		} else {
			if (mass != 0) {
				totalMass = prev.totalMass + mass;
			} else {
				int count = 0;
				Snowmen prevLoop = prev;
				while (prevLoop != null) {
					if (prevLoop.mass != 0) {
						if (count == 0) {
							totalMass = prevLoop.totalMass - prevLoop.mass;
							break;
						} else {
							count--;
						}
					} else {
						count++;
					}
					prevLoop = prevLoop.prev;

				}
			}
		}

	}

	@Override
	public String toString() {
		return "Snowmen [id=" + id + ", totalMass=" + totalMass + ", mass=" + mass + ", prev.id="
				+ (prev != null ? prev.id : -1) + "]";
	}

	public long getTotalMass() {
		return totalMass;
	}

}
