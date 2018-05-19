package algorithms.unionfind;

import java.util.ArrayList;
import java.util.List;

public class QuickUnion implements Finder {


	List<Tree> elements = new ArrayList<>();

	public QuickUnion(int number) {
		for (int i = 0; i < number; i++) {
			Tree tree = new Tree();
			tree.parent=null;
			tree.value=i;
			tree.size =1;
			elements.add(tree);
		}
	}

	public void union(int a, int b) {
		Tree minTree = elements.get(a);
		Tree maxTree = elements.get(b);

		if(maxTree.size <  minTree.size){
			Tree temp = minTree;
			minTree=maxTree;
			maxTree =temp;
		}
		Tree minRoot = getRoot(minTree);
		Tree maxRoot = getRoot(maxTree);
		minRoot = minRoot!=null ? minRoot:minTree;
		maxRoot = maxRoot!=null ? maxRoot:maxTree;
		minRoot.parent =maxRoot;
		Tree iterate =minTree;
		while(iterate!=null){
			iterate.size=iterate.size+1;
			iterate=iterate.parent;
		}
	}

	private Tree getRoot(Tree tree){
		Tree parent =tree;
		if(tree!=null && tree.parent!=null){
			parent=getRoot(tree.parent);
		}
		return parent;
	}

	private int getRootValue(Tree tree ){
		int val=tree.value;
		Tree root = getRoot(tree);
		if(root!=null)
			val=root.value;
		return val;
	}

	public boolean isConnected(int a, int b) {
		int aRoot = getRootValue(elements.get(a));
		int bRoot=getRootValue(elements.get(b));
		return aRoot==bRoot ;
	}

	@Override
	public void print() {
		for(Tree tree:elements){
			tree.print();
		}
	}
}

class Tree{
	int value;
	Tree parent;
	int size;
	public void print(){
		System.out.println(value+"--"+parent);
	}
	public String toString(){
		return value+"";
	}
}
