package algorithms.sorting;

import java.util.Arrays;

import algorithms.adt.PriorityQueue;
import algorithms.adt.StopWatch;
import algorithms.search.BST;
import algorithms.search.SymbolTable;

public class Run {

	public static void main(String[] args) {
		StopWatch watch = new StopWatch();
		watch.start();
		// runSort();
		SymbolTable<Integer, Integer> st = new BST<>();
		// SymbolTable<Integer, Integer> st = new UnOrderedSymbolTable<>();
		// SymbolTable<Integer, Integer> st = new BinarySearchSymbolTable<>();
		int cap = 10;
		for (int i = cap; i > 0; i--) {
			st.put(i, i);
		}
		st.print();
		for (int i = cap; i > 0; i--) {
			st.delete(i);
		}
		st.print();
		watch.end();
		// System.out.println("Running time is["+watch.getInterval()+"] nano seconds");
	}

	private static void runSort() {
		int[] size = { 100, 1000, 10000, 20000 };
		String types[] = { "N", "R", "S" };
		for (int sizeForRun : size) {
			for (String type : types) {
				Comparable[] originalArr = makeInput(sizeForRun, type);
				print(originalArr);
				runSorting(new InsertionSort(),
						Arrays.copyOf(originalArr, originalArr.length),
						InsertionSort.class.toString());
				runSorting(new SelectionSort(),
						Arrays.copyOf(originalArr, originalArr.length),
						SelectionSort.class.toString());
				runSorting(new IterativeQuickSort(),
						Arrays.copyOf(originalArr, originalArr.length),
						QuickSort.class.toString());
				runSorting(new ShellSort(),
						Arrays.copyOf(originalArr, originalArr.length),
						ShellSort.class.toString());
				runSorting(new MergeSort(),
						Arrays.copyOf(originalArr, originalArr.length),
						MergeSort.class.toString());
				PriorityQueue sort = new PriorityQueue();
				runSorting(sort,
						Arrays.copyOf(originalArr, originalArr.length),
						PriorityQueue.class.toString());
			}

		}
	}

	private static void runSorting(Sort sort, Comparable[] input,
			String sortName) {
		long time = System.nanoTime();
		sort.sort(input);
		time = System.nanoTime() - time;
		// System.out
		// .println("\n-------------------------After sorting------------------------------");
		print(input);
		// System.out.println("\nRunning time for " + sortName + "  is " + time
		// / 1e9 + " seconds");
		System.out.println(time / 1e3);
	}

	private static Integer[] makeInput(int cap, String type) {
		Integer[] input = new Integer[cap];
		for (int i = 0; i < cap; i++) {
			if ("R".equals(type)) {
				input[i] = cap - i;
			} else {
				input[i] = i + 1;
			}
		}
		if ("S".equals(type)) {
			new InsertionSort().shuffle(input);
		}
		return input;
	}

	public static void print(Comparable[] input) {
		if (input.length > 0)
			return;
		for (Comparable num : input) {
			System.out.print(num.toString() + "\t");
		}
	}

}