package learn.edx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 
 Input:
 7
 8 9 + 1 7 - *
 output:
 -102
 * @author Raja
 *
 */
public class Posix {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
		PrintWriter pw = new PrintWriter("output.txt");
		String line = br.readLine();
		int count = Integer.parseInt(line);
		line = br.readLine();
		Stack<Integer> stack = new Stack<Integer>();
		StringTokenizer tokenizer = new StringTokenizer(line, " ");
		while (tokenizer.hasMoreTokens()) {
			String str = tokenizer.nextToken();
			switch (str) {
			case "+":
				Integer b = stack.pop();
				Integer a = stack.pop();
				int result = 0;
				if (a != null && b != null) {
					result = a + b;
				}
				stack.push(result);
				break;
			case "-":
				b = stack.pop();
				a = stack.pop();
				result = 0;
				if (a != null && b != null) {
					result = a - b;
				}
				stack.push(result);
				break;
			case "*":
				b = stack.pop();
				a = stack.pop();
				result = 0;
				if (a != null && b != null) {
					result = a * b;
				}
				stack.push(result);
				break;
			case "/":
				b = stack.pop();
				a = stack.pop();
				result = 0;
				if (a != null && b != null) {
					result = a / b;
				}
				stack.push(result);
				break;
			default:
				stack.push(Integer.parseInt(str));
				break;
			}
		}
		pw.println(stack.pop());
		pw.close();
		br.close();
	}

}

