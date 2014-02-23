package algorithms.search;

import algorithms.adt.LinkedListStruct;
import algorithms.adt.Stack;

public class BST<T extends Comparable<T>, S extends Comparable<S>> implements
		SymbolTable<T, S> {

	Node root;

	class Node {
		T key;
		S value;
		Node left;
		Node right;
		int size;
	}

	private Node put(Node node, T key, S value) {
		if (node == null) {
			Node newNode = new Node();
			newNode.key = key;
			newNode.value = value;
			newNode.size = 1;
			return newNode;
		}
		int result = node.key.compareTo(key);
		if (result < 0)
			node.right = put(node.right, key, value);
		else if (result > 0)
			node.left = put(node.left, key, value);
		else
			node.value = value;
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}

	private int size(Node node) {
		if (node != null)
			return node.size;
		return 0;
	}

	@Override
	public void put(T key, S value) {
		root = put(root, key, value);
	}

	@Override
	public S get(T key) {
		Node node = get(root, key);
		if (node != null)
			return node.value;
		return null;
	}

	private Node get(Node node, T key) {
		if (node == null)
			return null;
		int result = node.key.compareTo(key);
		if (result < 0)
			return get(node.right, key);
		else if (result > 0)
			return get(node.left, key);
		else
			return node;
	}

	@Override
	public boolean delete(T key) {

		return false;
	}

	@Override
	public void print() {
		Stack<Node> stack = new LinkedListStruct<>();
		stack.push(root);
		for (Node x = stack.pop(); x != null; x = stack.pop()) {
			if (x != null) {
				System.out.print(x.key + "==" + x.value);
				if (x.left != null)
					stack.push(x.left);
				if (x.right != null)
					stack.push(x.right);
			}
		}
	}

}
