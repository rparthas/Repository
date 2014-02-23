package amazon;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sorting.Run;

public class SubSegment {

	public final static String NO_SEGMENT = "NO SUBSEGMENT FOUND";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		StringBuffer result = new StringBuffer();// Final Result Variable
		try {
			List<String> words = new ArrayList<>();
			String actualText = "";// input text to be searched
			int noOfSegments = 0;
			// no of segments to be processed

			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			actualText = br.readLine();
			noOfSegments = Integer.parseInt(br.readLine());

			for (int loop = 0; loop < noOfSegments; loop++) {
				words.add(br.readLine().toLowerCase().replaceAll("[^a-z]", ""));
			}

			Run.getTime();
			result = new StringBuffer();
			String text = actualText.toLowerCase().replaceAll("[^a-z]", " ");
			String[] tokens = text.split("\\s+");
			String[] actualTokens = actualText.replaceAll("[^a-zA-Z]", " ")
					.split("\\s+");

			/**
			 * Builds the weighted graph for all the words
			 */
			Map<Integer, String> wordMap = new TreeMap<>();

			for (int i = 0; i < tokens.length; i++) {
				String token = tokens[i];
				if (words.contains(token)) {
					wordMap.put(i, token);
				}
			}

			Map<Integer, MatchedWord> matchMap = new LinkedHashMap<>();
			MatchedWord.maxWord = noOfSegments;
			int counter = 1;

			List<Integer> indexList = new ArrayList<>(wordMap.keySet());

			for (int i = 0; i < indexList.size(); i++) {
				Integer index = indexList.get(i);
				String word = wordMap.get(index);
				MatchedWord matchedWord = new MatchedWord();
				matchedWord.addWord(word, index);
				matchMap.put(counter, matchedWord);
				for (int j = 1; j < counter; j++) {
					matchedWord = matchMap.get(j);
					matchedWord.addWord(word, index);
				}
				counter++;
			}

			int bestDiff = -1;
			List<Integer> bestIndices = null;
			for (int j = 1; j < counter; j++) {
				MatchedWord matchedWord = matchMap.get(j);
				if (matchedWord.isCompleted) {
					// int diff = Math.abs(matchedWord.indices
					// .get(noOfSegments - 1)
					// - matchedWord.indices.get(0));
					// if (bestDiff == -1 || diff < bestDiff) {
					// bestDiff = diff;
					// bestIndices = matchedWord.indices;
					// }
				}
			}

			for (int loopPosition = bestIndices.get(0); loopPosition <= bestIndices
					.get(bestIndices.size() - 1); loopPosition++) {
				result.append(actualTokens[loopPosition] + " ");
			}

			Run.printRunningTime();
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
			e.printStackTrace();
		} finally {
			if (result.length() == 0) {
				result.append(NO_SEGMENT);
			}
			System.out.println(result);
		}
	}
}

class MatchedWord {
	public static int maxWord = 0;
	public List<String> words = new ArrayList<>();
	int start = 0;
	int end = 0;
	public boolean isCompleted;

	public void addWord(String word, int index) {
		if (!isCompleted) {
			words.add(word);
			if (words.size() == 1) {
				start = index;
			}
			if (maxWord == words.size()) {
				isCompleted = true;
				end = index;
			}
		}
	}

	public String toString() {
		return words + " present between" + start + " and " + end;
	}
}
