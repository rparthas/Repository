package algorithms.search;

public class UnOrderedSymbolTable<T extends Comparable<T>, S > implements
		SymbolTable<T, S> {
	private Node start = null;

	class Node {
		T key;
		S value;
		Node next;

		public Node(T key, S value, Node next) {
			super();
			this.key = key;
			this.value = value;
			this.next = next;
		}

	}

	@Override
	public void put(T key, S value) {
		Node node = null;
		for (Node i = start; i != null; i = i.next) {
			if (i.key.equals(key)) {
				node = i;
				node.value = value;
				break;
			}
		}
		if (node == null) {
			node = new Node(key, value, start);
		}
		start = node;

	}

	@Override
	public S get(T key) {
		S value = null;
		for (Node i = start; i != null; i = i.next) {
			if (i.key.equals(key)) {
				value = i.value;
				break;
			}
		}
		return value;
	}

	@Override
	public boolean delete(T key) {
		Node prev = null;
		for (Node i = start; i != null; i = i.next) {
			if (i.key.equals(key)) {
				if (prev == null) {
					start = i.next;
				} else {
					prev.next = i.next;
				}
				i = null;
				return true;
			}
			prev = i;
		}
		return false;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		for (Node i = start; i != null; i = i.next) {
			System.out.print(i.key + "==" + i.value);
		}
	}

}
