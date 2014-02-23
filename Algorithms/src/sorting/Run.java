package sorting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Run {

	/**
	 */
	public static void main(String[] args) {
		List<Integer> input = new ArrayList<>();
		// input = Arrays.asList(new Integer[] { 1, 5, 10, 9, 8,
		// 7,
		// 4, 3, 6, 2 });
		for (int i = 1000; i > 0; i--) {
			input.add(i);
		}

		System.out.println("Input:" + input);
		runAlgorithm(input, new InsertionSort(), InsertionSort.class.toString());
		runAlgorithm(input, new BubbleSort(), BubbleSort.class.toString());
		runAlgorithm(input, new MergeSort(), MergeSort.class.toString());
		runAlgorithm(input, new HeapSort(), HeapSort.class.toString());
		runAlgorithm(input, new QuickSort(), QuickSort.class.toString());
		runAlgorithm(input, new SelectionSort(), SelectionSort.class.toString());
		runAlgorithm(input, new RadixSort(), RadixSort.class.toString());
	}

	private static void runAlgorithm(List<Integer> input, Sort sort, String name) {
		long startTime = System.nanoTime();
		List<Integer> output = sort.sort(input);
		double endTime = (System.nanoTime() - startTime) / Math.pow(10, 9);
		System.out.println("Output:" + output);
		System.out.println("Running Time for " + name + " is " + endTime);
	}

	static long time = 0;

	public static void getTime() {
		time = System.nanoTime();
	}

	public static void printRunningTime() {
		time = System.nanoTime() - time;
		System.out.println("Running Time is" + (time / Math.pow(10, 9))
				+ " seconds");
		time = 0;
	}

	public static Map<Integer, List<String>> buildWords() {
		String[] worder = { "this", "programming", "test", "a" };
		int counter = 0;
		Map<Integer, List<String>> wordBuilder = new HashMap<>();
		List<String> words = new ArrayList<>();
		words.add(worder[0]);
		words.add(worder[1]);
		words.add(worder[2]);
		words.add(worder[3]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[0]);
		words.add(worder[1]);
		words.add(worder[3]);
		words.add(worder[2]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[0]);
		words.add(worder[2]);
		words.add(worder[1]);
		words.add(worder[3]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[0]);
		words.add(worder[2]);
		words.add(worder[3]);
		words.add(worder[1]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[0]);
		words.add(worder[3]);
		words.add(worder[2]);
		words.add(worder[1]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[0]);
		words.add(worder[3]);
		words.add(worder[1]);
		words.add(worder[2]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[1]);
		words.add(worder[0]);
		words.add(worder[2]);
		words.add(worder[3]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[1]);
		words.add(worder[0]);
		words.add(worder[3]);
		words.add(worder[2]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[1]);
		words.add(worder[3]);
		words.add(worder[0]);
		words.add(worder[2]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[1]);
		words.add(worder[3]);
		words.add(worder[2]);
		words.add(worder[0]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[1]);
		words.add(worder[2]);
		words.add(worder[3]);
		words.add(worder[0]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[1]);
		words.add(worder[2]);
		words.add(worder[0]);
		words.add(worder[3]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[2]);
		words.add(worder[1]);
		words.add(worder[0]);
		words.add(worder[3]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[2]);
		words.add(worder[1]);
		words.add(worder[3]);
		words.add(worder[0]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[2]);
		words.add(worder[3]);
		words.add(worder[1]);
		words.add(worder[0]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[2]);
		words.add(worder[3]);
		words.add(worder[0]);
		words.add(worder[1]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[2]);
		words.add(worder[0]);
		words.add(worder[3]);
		words.add(worder[1]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[2]);
		words.add(worder[0]);
		words.add(worder[1]);
		words.add(worder[3]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[3]);
		words.add(worder[0]);
		words.add(worder[1]);
		words.add(worder[2]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[3]);
		words.add(worder[0]);
		words.add(worder[2]);
		words.add(worder[1]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[3]);
		words.add(worder[2]);
		words.add(worder[0]);
		words.add(worder[1]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[3]);
		words.add(worder[2]);
		words.add(worder[1]);
		words.add(worder[0]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[3]);
		words.add(worder[1]);
		words.add(worder[2]);
		words.add(worder[0]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[3]);
		words.add(worder[1]);
		words.add(worder[0]);
		words.add(worder[2]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[3]);
		words.add(worder[0]);
		words.add(worder[1]);
		words.add(worder[2]);
		wordBuilder.put(counter++, words);
		words = new ArrayList<>();
		words.add(worder[3]);
		words.add(worder[0]);
		words.add(worder[2]);
		words.add(worder[1]);
		wordBuilder.put(counter++, words);
		return wordBuilder;
	}

}
