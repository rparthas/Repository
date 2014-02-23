import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

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
			// actualText = br.readLine();
			actualText =   br.readLine();
			noOfSegments = Integer.parseInt( br.readLine());

			Map<String, Integer> wordCnt = new HashMap<>();
			for (int loop = 0; loop < noOfSegments; loop++) {
				String word =   br.readLine().toLowerCase().replaceAll("[^a-z]",
						"");
				words.add(word);
			}

			String text = actualText.toLowerCase().replaceAll("[^a-z]", " ");
			String[] tokens = text.split("\\s+");
			String[] actualTokens = actualText.replaceAll("[^a-zA-Z]", " ")
					.split("\\s+");

			Map<String, List<Integer>> wordMap = new HashMap<>();

			for(String word:words){
				int count=0;
				if (wordCnt.containsKey(word)) {
					count = wordCnt.get(word);
				}
				count++;
				wordCnt.put(word, count);
			}
			
			
			for (int i = 0; i < tokens.length; i++) {
				String token = tokens[i];
				if (words.contains(token)) {
					List<Integer> indexes = null;
					if (wordMap.containsKey(token)) {
						indexes = wordMap.get(token);
					} else {
						indexes = new ArrayList<>();
					}
					indexes.add(i);
					wordMap.put(token, indexes);
				}
			}

			boolean proceed = true;
			for(String word:wordCnt.keySet()){
				int occurSize =wordCnt.get(word);
				if(wordMap.get(word)==null || occurSize > wordMap.get(word).size()){
					proceed= false;
					break;
				}
			}
			

			if (proceed) {
				int bestMax = -1;
				int bestMin = -1;
				int leastDiff = -1;
				String firstWord = words.get(0);
				List<Integer> indexes = wordMap.get(firstWord);

				for (Integer index : indexes) {

					List<String> tempWords = new ArrayList<>();
					tempWords.addAll(words);
					tempWords.remove(0);
					int min = index;
					int max = index;

				
					List<Integer> obtainedIndexes = new ArrayList<>();
					obtainedIndexes.add(index);

					for (int runningCounter = 0; runningCounter < words.size()-1; runningCounter++) {
						List<Integer> iteratedIndexes = new ArrayList<>();
						for (int wordLoop = 0; wordLoop < tempWords.size(); wordLoop++) {
							String iteratedWord = tempWords.get(wordLoop);
							iteratedIndexes.addAll(wordMap.get(iteratedWord));
						}
						int gotIndex = -1;
						int diff = -1;
						for (Integer runningIndex : iteratedIndexes) {
							int maxDiff = Math.abs(runningIndex - max);
							int minDiff = Math.abs(runningIndex - min);
							int bestDiff = maxDiff >= minDiff ? minDiff
									: maxDiff;
							if ((diff == -1 || bestDiff < diff)
									&& !obtainedIndexes.contains(runningIndex)) {
								diff = bestDiff;
								gotIndex = runningIndex;
							}
						}
						obtainedIndexes.add(gotIndex);
						String currWord = tokens[gotIndex];
						int removeIndex = tempWords.indexOf(currWord);
						tempWords.remove(removeIndex);
						min = min > gotIndex ? gotIndex : min;
						max = max > gotIndex ? max : gotIndex;
					}
					if (leastDiff == -1 || (max - min) < leastDiff) {
						bestMax = max;
						bestMin = min;
						leastDiff=max-min;
					}
				}

				for (int loopPosition = bestMin; loopPosition <= bestMax; loopPosition++) {
					result.append(actualTokens[loopPosition] + " ");
				}
			} else {
				result.append(NO_SEGMENT);
			}

			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
