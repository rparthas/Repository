package java8.fpj;

import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Ex3 {

	public static void main(String[] args) {
		"Ram0".chars().filter(Character::isAlphabetic).mapToObj(ch -> Character.valueOf((char) ch))
				.forEach(System.out::println);

		final List<Person> people = Arrays.asList(new Person("John", 20), new Person("Sara", 21),
				new Person("Jane", 21), new Person("Greg", 35));

		System.out.println(people.stream().sorted((person1, person2) -> person2.ageDifference(person1))
				.collect(Collectors.toList()));

		people.stream().min(Person::ageDifference).ifPresent(youngest -> System.out.println("Youngest: " + youngest));
		people.stream().max(Person::ageDifference).ifPresent(oldest -> System.out.println("Oldest: " + oldest));

		final Function<Person, String> byName = person -> person.getName();
		final Function<Person, Integer> byAge = person -> person.getAge();
		System.out.println(people.stream().sorted(comparing(byAge).thenComparing(byName)).collect(Collectors.toList()));

		List<Person> olderThan20 = people.stream().filter(person -> person.getAge() > 20).collect(ArrayList::new,
				ArrayList::add, ArrayList::addAll);
		System.out.println("People older than 20: " + olderThan20);

		Map<Integer, List<Person>> peopleByAge = people.stream().collect(Collectors.groupingBy(Person::getAge));
		System.out.println("Grouped by age: " + peopleByAge);

	}
}
