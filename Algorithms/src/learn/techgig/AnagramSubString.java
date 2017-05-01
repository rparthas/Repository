package learn.techgig;

import java.util.LinkedHashMap;
import java.util.Map;

public class AnagramSubString {

	public static void main(String[] args) throws Exception {
		int matchCount = 0;
		String pattern = "cAda";
		String test = "AbrAcadAbRa";
		Map<String, Integer> patternCount = new LinkedHashMap<String, Integer>();
		Map<String, Integer> testCount = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < pattern.length(); i++) {
			String ind = pattern.charAt(i) + "";
			int count = 1;
			if (patternCount.containsKey(ind)) {
				count = count + patternCount.get(ind);
			}
			patternCount.put(ind, count);
		}

		for (String key : patternCount.keySet()) {
			testCount.put(key, 0);
		}

		for (int i = 0; i < pattern.length(); i++) {
			String ind = test.charAt(i) + "";
			if (testCount.containsKey(ind)) {
				testCount.put(ind, testCount.get(ind) + 1);
			}
		}

		if (compare(patternCount, testCount)) {
			matchCount++;
		}

		for (int i = pattern.length(); i < test.length(); i++) {
			String remove = test.charAt(i - pattern.length()) + "";
			String add = test.charAt(i) + "";
			if (testCount.containsKey(remove)) {
				testCount.put(remove, testCount.get(remove) - 1);
			}
			if (testCount.containsKey(add)) {
				testCount.put(add, testCount.get(add) + 1);
			}
			if (compare(patternCount, testCount)) {
				matchCount++;
			}
		}
		System.out.println(matchCount);

	}

	private static boolean compare(Map<String, Integer> patternCount, Map<String, Integer> testCount) {
		boolean match = true;
		for (String key : patternCount.keySet()) {
			match = match && patternCount.get(key) == (testCount.get(key));
		}
		return match;
	}

}
