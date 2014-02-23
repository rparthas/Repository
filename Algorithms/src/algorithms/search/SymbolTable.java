package algorithms.search;


public interface SymbolTable<T  extends Comparable<T>,S>{

	public void put(T key, S value);

	public S get(T key);

	public boolean delete(T key);
	
	public void print();

}


