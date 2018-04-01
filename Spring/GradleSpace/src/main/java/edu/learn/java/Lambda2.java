package edu.learn.java;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Lambda2 {

	public static void main(String[] args) {
		
		// Lambda version of Runnable (no arguments)
		Runnable r1 = () -> System.out.println("run 1");

		// Start both threads
		r1.run();
	
		// using an existing functional interface BiFunction
		BiFunction<String, String, String> concat = (a, b) -> a + b;
		System.out.println(concat.apply("Today is ", "a great day"));

		// example of the Consumer functional interface
		Consumer<String> hello = name -> System.out.println("Hello, " + name);
		for (String name : Arrays.asList("Duke", "Mickey", "Minnie")) {
			hello.accept(name);
		}

		// example of passing one value
		GreetingFunction greeting = message -> System.out.println("Java Programming " + message);
		greeting.sayMessage("Rocks with lambda expressions");
	}

	// custom functional interface
	@FunctionalInterface
	interface GreetingFunction {
		void sayMessage(String message);
	}

}
