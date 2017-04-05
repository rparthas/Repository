package learn.edx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MergeSort {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
		PrintWriter out = new PrintWriter("output.txt");
		int count = Integer.parseInt(br.readLine());
		int arr[] = new int[count + 1];
		StringTokenizer tokenizer = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= count; i++) {
			arr[i] = Integer.parseInt(tokenizer.nextToken());
		}
		List<String> output = new ArrayList<String>();
		merge(arr, 1, count, output);
		for (String str : output) {
			out.println(str);
		}
		for (int i = 1; i <= count; i++) {
			out.print(arr[i] + " ");
		}
		out.close();
		br.close();

	}

	public static void merge(int a[], int left, int right, List<String> output) {

		if (left == right || left >= right) {
			return;
		} else {
			int mid = left + ((right - left) / 2);
			merge(a, left, mid, output);
			merge(a, mid + 1, right, output);
			int i = left;
			int j = mid + 1;

			while (i <= mid && j <= right) {
				if (a[i] < a[j])
					i++;
				else {
					int tmp = a[j];
					System.arraycopy(a, i, a, i + 1, j - i);
					a[i] = tmp;
					i++;
					j++;
					mid++;
				}
			}

			output.add(left + " " + right + " " + a[left] + " " + a[right]);
		}

	}

}
