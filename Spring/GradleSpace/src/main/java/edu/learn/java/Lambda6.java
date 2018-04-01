package edu.learn.java;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lambda6 {
	public static void main(String[] args) {

		Arrays.asList("red", "green", "blue").stream().sorted().findFirst().ifPresent(System.out::println);

		// example of Stream.of with a filter
		Stream.of("apple", "pear", "banana", "cherry", "apricot").filter(fruit -> {
			System.out.println("filter: " + fruit);
			return fruit.length() <5; // predicate
		})
				// if the foreach is removed, nothing will print,
				// the foreach makes it a terminal event
				.forEach(fruit -> System.out.println("Matched condn: " + fruit));

		// using a stream and map operation to create a list of words in caps
		Set<String> collected = Stream.of("Java", " Rocks").map(string -> string.toUpperCase()).collect(Collectors.toSet());
		System.out.println(collected.toString());

	}
}
