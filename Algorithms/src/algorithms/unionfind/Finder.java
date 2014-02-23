package algorithms.unionfind;

public interface Finder {

	public boolean isConnected(int a,int b);
	
	public void union(int a,int b);
	
	public void print();
}
