package algorithms.adt;

import java.util.Scanner;

public class Run {

	public static void main(String[] args) {

		boolean stack = true;
		if (stack) {
			runStack();
		} else {
			runQueue();
		}
	}

	private static void runStack() {
		try (Scanner scanner = new Scanner(System.in)) {
			// Stack<String> stack = new LinkedListStruct<>();
			ArrayStruct stack = new ArrayStruct();
			boolean run = true;
			while (run) {
				System.out.println("Press 1:Push 2:Pop 3:Exit");
				String choice = scanner.nextLine();
				switch (choice) {
				case "1":
					System.out.println("Please enter item to push");
					String item = scanner.nextLine();
					if (item != null) {
						stack.push(item);
						print(true, true, item);
					} else {
						System.out.println("item is null");
					}
					break;
				case "2":
					item = stack.pop();
					print(true, false, item);
					break;
				case "3":
					run = false;
					break;

				default:
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in union find" + e);
		}
	}

	private static void runQueue() {
		try (Scanner scanner = new Scanner(System.in)) {
			// Queue<String> queue = new LinkedListStruct<>();
			ArrayStruct queue = new ArrayStruct();
			boolean run = true;
			while (run) {
				System.out.println("Press 1:Enqueue 2:Dequeue 3:Exit");
				String choice = scanner.nextLine();
				switch (choice) {
				case "1":
					System.out.println("Please enter item to Queue");
					String item = scanner.nextLine();
					if (item != null) {
						queue.enqueue(item);
						print(false, true, item);
					} else {
						System.out.println("item is null");
					}
					break;
				case "2":
					item = queue.dequeue();
					print(false, false, item);
					break;
				case "3":
					run = false;
					break;

				default:
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in union find" + e);
		}
	}

	

	public static void print(boolean isStack, boolean insert, String val) {
		String str = "";
		if (isStack) {
			if (insert) {
				str = "Item pushed onto stack[" + val + "]";
			} else {
				str = "Item popped from stack[" + val + "]";
			}
		} else {
			if (insert) {
				str = "Item enqueued onto queue[" + val + "]";
			} else {
				str = "Item dequeued from queue[" + val + "]";
			}
		}
		System.out.println(str);
	}

}
