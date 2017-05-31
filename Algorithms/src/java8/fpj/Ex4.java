package java8.fpj;

import java.util.function.Consumer;

public class Ex4 {

	public static void main(String[] args) {
		TestClass.print(test -> test.from("Ram"));
	}
}

interface Test {
	default void test() {
		System.out.println("Hi");
	}
}

class TestClass implements Test {

	private String from;

	private TestClass() {

	}

	public TestClass from(String from) {
		this.from = from;
		return this;
	}

	public static void print(Consumer<TestClass> consumer) {
		TestClass testClass = new TestClass();
		consumer.accept(testClass);
		testClass.test();
		System.out.println(testClass.from);
	}
}
