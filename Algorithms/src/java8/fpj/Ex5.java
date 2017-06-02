package java8.fpj;

import java.util.List;

public class Ex5 {

	public static void main(String[] args) throws Throwable {
		RodCutter rodCutter = new RodCutter();
		rodCutter.setPrices(null);
		Throwable ex=assertThrows(RodCutterException.class, () -> rodCutter.maxProfit(1));
		if(ex!=null)
			throw ex;

	}

	public static <X extends Throwable> Throwable assertThrows(final Class<X> exceptionClass, final Runnable block) {
		try {
			block.run();
		} catch (Throwable ex) {
			if (exceptionClass.isInstance(ex))
				return ex;
		}
		return null;

	}

}

class RodCutter {

	public void setPrices(final List<Integer> prices) {

	}

	public int maxProfit(final int length) {
		if (length == 0)
			throw new RodCutterException();
		return 0;

	}

}

class RodCutterException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3226261000008985293L;

}