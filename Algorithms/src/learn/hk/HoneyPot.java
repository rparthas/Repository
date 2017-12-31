package learn.hk;

import java.util.ArrayList;
import java.util.List;

public class HoneyPot {

	public static void main(String[] args) {
		int n = 161;
		int count = 0;
		int remCount = 0;
		List<Integer> remainder = new ArrayList<>();
		int mod = 0;
		while (n > 1) {
			mod = n % 2;
			n = n / 2;
			remCount++;
			if (mod == 1) {
				count++;
				remainder.add(remCount);
			}
		}
		remCount++;
		if (n == 1) {
			remainder.add(remCount);

		}
		int[] indices = new int[remainder.size() + 1];
		indices[0] = remainder.size();
		for (int i = remainder.size() - 1; i >= 0; i--) {
			indices[remainder.size() - i] = remCount + 1 - remainder.get(i);
		}

		// return indices;

	}

	static String[] twins(String[] a, String[] b) {
		String[] result = new String[a.length];
		for (int i = 0; i < a.length; i++) {
			result[i] = "No";
			String ai = a[i];
			String bi = b[i];
			if (ai.length() == bi.length()) {
				for (int j = 0; j < ai.length(); j=j+2) {
					for (int k = 0; k < bi.length(); k=k+2) {
						if(ai.charAt(j) == bi.charAt(k)){
							//ai=ai.substring(0,j)+ai.substring(j+1,)
						}
					}
				}
			}
		}

		return result;
	}

}
