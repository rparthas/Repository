package algorithms.search;

public class Key< T extends Comparable<T>,S > implements Comparable<T> {

	T t=null;
	S s=null;
	@Override
	public int compareTo(T o) {
		// TODO Auto-generated method stub
		return t.compareTo(o);
	}
	
	
}
