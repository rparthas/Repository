import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Combination {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> input = new ArrayList<>();
		input.add("Raja");
		input.add("Ram");
		input.add("Raghu");
		input.add("gopal");
		List<List<String>> finalCombo = new ArrayList<>();
		List<String> originalInput = new ArrayList<>();
		for (String inputed : input) {
			originalInput.add(inputed);
		}

		for (int i = 0; i < input.size(); i++) {
			String iter = input.get(i);
			input.remove(i);
			Set<List<String>> interimCombo = combination(input, null);
			for (List<String> combo : interimCombo) {
				List<String> tempList = new ArrayList<String>();
				tempList.add(iter);
				for (String comboItem : combo) {
					tempList.add(comboItem);
				}
				if (!finalCombo.contains(tempList)) {
					finalCombo.add(tempList);
				}

				combo.add(iter);
				if (!finalCombo.contains(combo)) {
					finalCombo.add(combo);
				}

			}
			input.clear();
			input.addAll(originalInput);
		}
		for (List<String> combo : finalCombo) {
			System.out.println(combo);
		}

		System.out.println(finalCombo.size());

	}

	public static Set<List<String>> combination(List<String> input,
			Set<List<String>> finalCombo) {
		if (finalCombo == null) {
			finalCombo = new LinkedHashSet<List<String>>();
		}
		if (input.size() == 1) {
			List<String> combo = new ArrayList<String>();
			combo.add(input.get(0));
			finalCombo.add(combo);
		} else {
			for (int i = 0; i < input.size(); i++) {
				String iter = input.get(i);
				input.remove(iter);
				combination(input, finalCombo);
				List<List<String>> tempCombo = new ArrayList<List<String>>();

				for (List<String> combo : finalCombo) {
					List<String> tempList = new ArrayList<String>();
					tempList.add(iter);
					for (String comboItem : combo) {
						tempList.add(comboItem);
					}
					tempCombo.add(tempList);
					combo.add(iter);
				}
				finalCombo.addAll(tempCombo);

			}
		}

		return finalCombo;
	}
}
