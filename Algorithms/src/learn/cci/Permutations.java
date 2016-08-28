package learn.cci;

import java.util.HashMap;
import java.util.Map;

public class Permutations {

	public static void main(String[] args) {
		String str1 = "napraa";
		String str2 = "aparna";
		boolean found = true;
		Map<Character, Integer> count = new HashMap<Character, Integer>();
		for (int i = 0; i < str1.length(); i++) {
			Character character = str1.charAt(i);
			int cnt = 1;
			if (count.containsKey(character)) {
				cnt = count.get(character) + 1;
			}
			count.put(character, cnt);
		}
		for (int i = 0; i < str2.length(); i++) {
			Character character = str2.charAt(i);
			if (count.containsKey(character)) {
				int cnt = count.get(character) - 1;
				if (cnt == 0)
					count.remove(character);
				else
					count.put(character, cnt);
			} else {
				found = false;
				break;
			}

		}
		if (found && count.size() == 0) {
			System.out.println("Yes");
		} else {
			System.out.println("Nope");
		}
	}

}
