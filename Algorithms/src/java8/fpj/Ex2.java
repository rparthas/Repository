package java8.fpj;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Ex2 {

	public static void main(String[] args) {
		final List<String> names = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
		final Optional<String> foundName = names.stream().filter(name -> name.startsWith("B")).findFirst();
		System.out.println(String.format("A name starting with %s: %s", "B", foundName.orElse("No name found")));

		final Optional<String> aLongName = names.stream()
				.reduce((name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
		aLongName.ifPresent(System.out::println);
		
		String longName = names.stream()
				.reduce("Steve",(name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
		System.out.println(longName);
		
		System.out.println(names.stream().map(String::toUpperCase).collect(Collectors.joining(", ")));
	}
}
