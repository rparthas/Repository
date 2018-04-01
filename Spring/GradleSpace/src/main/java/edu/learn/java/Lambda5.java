package edu.learn.java;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Lambda5 {
	public static void main(String[] args) {
		List<String> names = Arrays.asList("Paul", "Jane", "Michaela", "Sam");

		Collections.sort(names, (a, b) -> b.compareTo(a));

		// total pages in your book collection
		Book book1 = new Book("Miss Peregrine's Home for Peculiar Children", "Ranson", "Riggs", 382);
		Book book2 = new Book("Harry Potter and The Sorcerers Stone", "JK", "Rowling", 411);
		Book book3 = new Book("The Cat in the Hat", "Dr", "Seuss", 45);

		List<Book> books = Arrays.asList(book1, book2, book3);
		int total = books.stream().collect(Collectors.summingInt(Book::getPages));
		System.out.println(total);

		// aggregate author first names into a list
		List<String> list = books.stream().map(Book::getAuthorLName).collect(Collectors.toList());
		System.out.println(list);

	}
}
