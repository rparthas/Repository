package learn.edx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * 
Input:
7
()()
([])
([)]
((]]
)(
)
(
Output:
YES
YES
NO
NO
NO
NO
NO
 * @author ram
 *
 */
public class BracketSequence {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
		PrintWriter pw = new PrintWriter("output.txt");
		String line = br.readLine();
		int count = Integer.parseInt(line);
		for (int i = 0; i < count; i++) {
			line = br.readLine();
			Stack<String> stack = new Stack<String>();
			boolean match = true;
			loop: for (int loop = 0; loop < line.length(); loop++) {
				String token = line.charAt(loop) + "";
				String element = "";
				switch (token.trim()) {
				case "]":
					element = stack.pop();
					if (!"[".equals(element)) {
						match = false;
						break loop;
					}
					break;
				case "}":
					element = stack.pop();
					if (!"{".equals(element)) {
						match = false;
						break loop;
					}
					break;
				case ")":
					element = stack.pop();
					if (!"(".equals(element)) {
						match = false;
						break loop;
					}
					break;
				case "":
					break;
				default:
					stack.push(token);
					break;
				}
			}
			match=match && stack.isEmpty();
			if (match) {
				pw.println("YES");
			} else {
				pw.println("NO");
			}
		}

		pw.close();
		br.close();
	}

}

class Stack<T> {

	Node<T> first = null;

	public void push(T str) {
		Node<T> element = new Node<T>();
		element.t = str;
		element.next = first;
		first = element;

	}

	public boolean isEmpty() {
		return first == null;
	}

	public T pop() {
		if (first != null) {
			Node<T> temp = first;
			first = first.next;
			return temp.t;
		}
		return null;

	}

}

class Node<T> {
	T t;
	Node<T> next;
}