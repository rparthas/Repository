package edu.learn.java;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Lambda1 {

	public static void main(String[] args) {
		Predicate<String> stringLen = (str) -> str.length() < 7;
		System.out.println(stringLen.test("Apples") + " - Apples is less than 7");

		// Consumer example uses accept method
		Consumer<String> consumerStr = (s) -> System.out.println(s.toLowerCase());
		consumerStr.accept("ABCDefghijklmnopQRSTuvWxyZ");

		// Function example
		Function<Integer, String> converter = (num) -> Integer.toString(num);
		System.out.println("length of 26: " + converter.apply(26).length());

		// Supplier example
		Supplier<String> s = () -> "Java is fun";
		System.out.println(s.get());

		// Binary Operator example
		BinaryOperator<Integer> add = (a, b) -> a + b;
		System.out.println("add 10 + 25: " + add.apply(10, 25));

		// Unary Operator example
		UnaryOperator<String> str = (msg) -> msg.toUpperCase();
		System.out.println(str.apply("This is my message in upper case"));

	}

}
