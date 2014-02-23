package algorithms.adt;

public class LinkedListStruct<T> implements Stack<T>, Queue<T> {

	class Node {
		T t;
		Node next;
	}

	Node first;

	@Override
	public void push(T t) {
		// TODO Auto-generated method stub
		Node node = new Node();
		node.t = t;
		if (!isEmpty()) {
			node.next = first;
		}
		first = node;
	}

	@Override
	public T pop() {
		// TODO Auto-generated method stub
		T t = null;
		if (!isEmpty()) {
			t = first.t;
			first = first.next;
		}
		return t;

	}

	private boolean isEmpty() {
		return first == null;
	}

	@Override
	public void enqueue(T t) {
		// TODO Auto-generated method stub
		Node node = new Node();
		node.t = t;
		if (!isEmpty()) {
			first.next = node;
		} else {
			first = node;
		}
	}

	@Override
	public T dequeue() {
		T t = null;
		if (!isEmpty()) {
			t = first.t;
			first = first.next;
		}
		return t;
	}

}
