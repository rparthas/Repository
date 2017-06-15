package java8.fpj;

import java.util.stream.Stream;

public class Ex7 {

	public static void main(String[] args) {
		System.out.println(factorial(5));
	}

	public static int factorial(final int number) {
		return factorialTailRec(1, number).invoke();
	}

	public static TailCall<Integer> factorialTailRec(final int factorial, final int number) {
		if (number == 1)
			return TailCalls.done(factorial);
		else
			return TailCalls.call(() -> factorialTailRec(factorial * number, number - 1));

	}
}

@FunctionalInterface
interface TailCall<T> {

	TailCall<T> apply();

	default boolean isComplete() {
		return false;
	}

	default T result() {
		throw new Error("not implemented");
	}

	default T invoke() {
		return Stream.iterate(this, TailCall::apply).filter(TailCall::isComplete).findFirst().get().result();
	}

}

class TailCalls {

	public static <T> TailCall<T> call(final TailCall<T> nextCall) {
		return nextCall;
	}

	public static <T> TailCall<T> done(final T value) {
		return new TailCall<T>() {
			@Override
			public boolean isComplete() {
				return true;
			}

			@Override
			public T result() {
				return value;
			}

			@Override
			public TailCall<T> apply() {
				throw new Error("not implemented");
			}
		};

	}

}