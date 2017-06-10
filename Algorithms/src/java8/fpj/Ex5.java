package java8.fpj;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Ex5 {

	public static void main(String[] args) throws Throwable {
		FileWriterEAM.use("output.txt", writer -> writer.writeStuff("cool stuff"));
		RodCutter rodCutter = new RodCutter();
		rodCutter.setPrices(null);
		Throwable ex = assertThrows(RodCutterException.class, () -> rodCutter.maxProfit(1));
		if (ex != null)
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

class FileWriterEAM {

	private final FileWriter writer;

	private FileWriterEAM(final String fileName) throws IOException {
		writer = new FileWriter(fileName);
	}

	private void close() throws IOException {
		System.out.println("close called automatically...");
		writer.close();

	}

	public void writeStuff(final String message) throws IOException {
		writer.write(message);
	}

	public static void use(final String fileName, final UseInstance<FileWriterEAM, IOException> block)
			throws IOException {

		final FileWriterEAM writerEAM = new FileWriterEAM(fileName);
		try {
			block.accept(writerEAM);
		} finally {
			writerEAM.close();
		}

	}

}

@FunctionalInterface
interface UseInstance<T, X extends Throwable> {
	void accept(T instance) throws X;
}