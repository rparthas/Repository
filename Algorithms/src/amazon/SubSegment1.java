package amazon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SubSegment1 {

	public final static String NO_SEGMENT = "NO SUBSEGMENT FOUND";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		StringBuilder result = new StringBuilder();// Final Result Variable
		try {
			List<String> words = new ArrayList<>();
			String actualText = "";// input text to be searched
			int noOfSegments = 0;
			// no of segments to be processed

			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			actualText = args[0];
			noOfSegments = Integer.parseInt(args[1]);

			for (int loop = 0; loop < noOfSegments; loop++) {
				words.add(args[2 + loop].toLowerCase().replaceAll("[^a-z]", ""));
			}

			String text = actualText.toLowerCase().replaceAll("[^a-z]", " ");
			String[] tokens = text.split("\\s+");
			String[] actualTokens = actualText.replaceAll("[^a-zA-Z]", " ")
					.split("\\s+");

			boolean proceed = true;
			for (String word : words) {
				if (!text.contains(word)) {
					proceed = false;
					break;
				}
			}

			if (proceed) {
				Map<Integer, String> wordMap = new LinkedHashMap<>();

				for (int i = 0; i < tokens.length; i++) {
					String token = tokens[i];
					wordMap.put(i, token);
				}

				int bestDiff = -1;
				String best = null;

				List<Integer> keys = new ArrayList<>(wordMap.keySet());
				for (int i = noOfSegments - 1; i < keys.size(); i++) {
					Integer index = keys.get(i);
					List<String> tempList = new ArrayList<>(words);
					int j = i;
					while (j >= 0) {
						String word = wordMap.get(keys.get(j));
						int indexToBeRemoved = tempList.indexOf(word);
						if (indexToBeRemoved != -1) {
							tempList.remove(indexToBeRemoved);
							if (tempList.size() == 0) {
								int diff = Math.abs(index - j);
								if (best == null || diff < bestDiff) {
									bestDiff = diff;
									best = j + ":" + index;
								}
								break;
							}
						}
						j--;
					}
				}

				if (best != null) {
					int start = Integer.parseInt(best.split(":")[0]);
					int end = Integer.parseInt(best.split(":")[1]);
					for (int loop = start; loop <= end; loop++) {
						result.append(actualTokens[loop]);
						result.append(" ");
					}
				}
			} else {
				result.append(NO_SEGMENT);
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
			e.printStackTrace();
		}
	}

	public class Word {
		int min;
		int max;
		List<Integer> indexes;
		String word;
	}
}
