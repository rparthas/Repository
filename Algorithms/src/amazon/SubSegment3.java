package amazon;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SubSegment3 {

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
			actualText = args[0];
			noOfSegments = Integer.parseInt(args[1]);

			for (int loop = 0; loop < noOfSegments; loop++) {
				words.add(args[2 + loop].toLowerCase().replaceAll("[^a-z]", ""));
			}

			String text = actualText.toLowerCase().replaceAll("[^a-z]", " ");
			String[] tokens = text.split("\\s+");
			String[] actualTokens = actualText.replaceAll("[^a-zA-Z]", " ")
					.split("\\s+");

			List<Word> wordList = new ArrayList<>();
			int bestSum = 0;
			Word bestWord = null;

			boolean proceed = true;
			for (String word : words) {
				if (!text.contains(word)) {
					proceed = false;
					break;
				}
			}

			if (proceed) {
				for (int index = 0; index < tokens.length; index++) {
					String token = tokens[index];

					if (words.contains(token)) {
						Word newWord = new Word();
						newWord.remWords.addAll(words);
						wordList.add(newWord);
						for (int wordLoop = 0; wordLoop < wordList.size(); wordLoop++) {
							Word word = wordList.get(wordLoop);
							if (word.remWords.contains(token)) {
								addWordsToRemove(words, token, word,index);
							}
							boolean remove = false;
							if (word.reachMax()) {
								if (bestSum == 0 || word.getDiff()  < bestSum) {
									bestSum = word.getDiff();
									bestWord = word;
								}
								remove = true;
							} else if (bestSum != 0
									&& word.remWords.size() > noOfSegments && word.getDiff() > bestSum) {
								remove = true;
							}
							if (remove) {
								wordList.remove(wordLoop);
								wordLoop--;
							}
						}
					}
				}

				if (bestWord != null) {
					int start = bestWord.getMin();
					int end = bestWord.getMax();
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

	private static void addWordsToRemove(List<String> words, String token,
			Word word,Integer index) {
		int removeIndex = word.remWords.indexOf(token);
		if (removeIndex != -1) {
			word.remWords.remove(removeIndex);
		}
		word.addIndex(index);
	}
}

class Word {

	public Integer getMin() {
		return indexes.get(0);
	}

	public Integer getMax() {
		return indexes.get(indexes.size() - 1);
	}

	public void addIndex(Integer i) {
		indexes.add(i);
	}
	
	public Integer getDiff(){
		return indexes.get(indexes.size() - 1) -indexes.get(0);
	}

	public String toString() {
		return indexes.toString() + "---" + remWords.toString();
	}
	
	public boolean reachMax(){
		if (remWords.size() == 0) {
			return true;
		}
		return false;
	}

	List<Integer> indexes = new ArrayList<>();
	List<String> remWords = new ArrayList<>();
}
