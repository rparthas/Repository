package java8.fpj;

import java.util.function.Supplier;

public class Ex6 {

	public static void main(String[] args) throws Exception {
       new Holder().getHeavy();
	}

}

class Holder {
	private Supplier<Heavy> heavy = () -> createAndCacheHeavy();

	public Holder() {
		System.out.println("Holder created");
	}

	public Heavy getHeavy() {
		return heavy.get();
	}

	private synchronized Heavy createAndCacheHeavy() {
		class HeavyFactory implements Supplier<Heavy> {
			private final Heavy heavyInstance = new Heavy();

			public Heavy get() {
				return heavyInstance;
			}
		}
		if (!HeavyFactory.class.isInstance(heavy)) {
			heavy = new HeavyFactory();
		}
		return heavy.get();

	}
}

class Heavy {
	public Heavy() {
		System.out.println("Heavy created");
	}

	public String toString() {
		return "quite heavy";
	}

}
