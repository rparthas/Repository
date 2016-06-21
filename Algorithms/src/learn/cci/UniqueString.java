package learn.cci;

import java.util.HashMap;
import java.util.Map;

public class UniqueString {

	public static void main(String[] args) {
		String str = "sudha";
		str = str.toLowerCase();
		Map<Character, Character> map = new HashMap<Character, Character>();
		for (int i = 0; i < str.length(); i++) {
			Character character = str.charAt(i);
			if (map.containsKey(character)) {
				System.out.println("non unique");
				break;
			}
			map.put(character, character);
		}

	}

}
