package sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RadixSort implements Sort {

	@Override
	public List<Integer> sort(List<Integer> input) {
		int numOfDigits = 4;
		List<String> output = new ArrayList<>();
		for(Integer number:input){
			output.add(padZeroes(number+"", numOfDigits));
		}
		

		while (numOfDigits >= 1) {
			Map<Integer, List<String>> bucket = formBucket(numOfDigits, output);
			output.clear();
			for (List<String> bucketVal : bucket.values()) {
				output.addAll(bucketVal);
			}
			numOfDigits--;
		}
		
		List<Integer> temp = new ArrayList<>();
		for(String num:output){
			temp.add(Integer.valueOf(num));
		}
		return temp;
	}

	private Map<Integer, List<String>> formBucket(int numOfDigits,
			List<String> output) {
		Map<Integer, List<String>> bucket = new TreeMap<>();
		for (String number : output) {
			int num = Integer.parseInt(number.substring(numOfDigits - 1,
						numOfDigits));

			List<String> numList = bucket.get(num);
			if (numList == null) {
				numList = new ArrayList<>();
			}
			numList.add(number+"");
			bucket.put(num, numList);
		}
		return bucket;
	}
	
	public String padZeroes(String number,int noOfDigits){
		if(number.length()< noOfDigits){
			int diff = noOfDigits-number.length();
			String temp ="0";
			for(int i=1;i<diff;i++){
				temp=temp+"0";
			}
			number=temp+number;
		}
		return number;
	}

}
