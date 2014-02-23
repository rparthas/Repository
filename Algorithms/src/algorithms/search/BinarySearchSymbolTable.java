package algorithms.search;

public class BinarySearchSymbolTable<T extends Comparable<T>, S extends Comparable<S>>
		implements SymbolTable<T, S> {
	int N = 1000;
	int indexPos = 0;
	Comparable<T> keys[] = new Comparable[N];
	Comparable<S> vals[] = new Comparable[N];

	private int rank(Comparable key, int lo, int hi) {
		if (hi <= lo)
			return lo;
		int mid = lo + (hi - lo) / 2;
		int cmp = key.compareTo(keys[mid]);
		if (cmp < 0)
			return rank(key, lo, mid - 1);
		else if (cmp > 0)
			return rank(key, mid + 1, hi);
		else
			return mid;
	}

	@Override
	public void put(T key, S value) {
		// TODO Auto-generated method stub
		int rank = rank(key, 0, indexPos);
		if (keys[rank] == null) {
			keys[rank] = key;
			vals[rank] = value;
			indexPos++;
		} else {
			if (keys[rank].compareTo(key) != 0) {
				shift(rank, true);
				keys[rank] = key;
				vals[rank] = value;
				indexPos++;
			}
			vals[rank] = value;
		}
	}

	@Override
	public S get(T key) {
		// TODO Auto-generated method stub
		int rank = rank(key, 0, indexPos);
		if (keys[rank].compareTo(key) == 0) {
			S s = (S) vals[rank];
			return s;
		}
		return null;
	}

	@Override
	public boolean delete(T key) {
		// TODO Auto-generated method stub
		int rank = rank(key, 0, indexPos);
		if (keys[rank].compareTo(key) == 0) {
			shift(rank, false);
			indexPos--;
			keys[indexPos] = null;
			vals[indexPos] = null;
			return true;
		}
		return false;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		for (int i = 0; i < indexPos; i++) {
			System.out.println(keys[i] + "==" + vals[i]);
		}
	}

	public void shift(int pos, boolean up) {
		if (up) {
			for (int i = indexPos - 1; i >= pos; i--) {
				keys[i + 1] = keys[i];
				vals[i + 1] = vals[i];
			}
		} else {
			for (int i = pos; i < indexPos-1; i++) {
				keys[i] = keys[i + 1];
				vals[i] = vals[i + 1];
			}
		}
	}
}
