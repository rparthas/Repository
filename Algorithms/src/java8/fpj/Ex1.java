package java8.fpj;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Ex1 {

	public static void main(String[] args) {
		final List<BigDecimal> prices = Arrays.asList(new BigDecimal("10"), new BigDecimal("30"), new BigDecimal("17"),
				new BigDecimal("20"), new BigDecimal("15"), new BigDecimal("18"), new BigDecimal("45"),
				new BigDecimal("12"));
		final BigDecimal total = prices.stream().filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
				.map(price -> price.multiply(BigDecimal.valueOf(0.9))).reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println(total);

		final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

		friends.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				if (!"Brian".equals(t))
					System.out.println(t);
			}
		});
		System.out.println();
		friends.forEach(System.out::println);
		System.out.println();
		friends.stream().map(String::toUpperCase).forEach(System.out::println);
		System.out.println();
		final Predicate<String> nameFilter = name -> name.startsWith("N");
		friends.stream().map(String::toUpperCase).filter(nameFilter).collect(Collectors.toList())
				.forEach(System.out::println);
		System.out.println();
		friends.stream().map(String::toUpperCase).filter(checkIfStartsWith("N")).collect(Collectors.toList())
				.forEach(System.out::println);
		System.out.println();
		final Function<String, Predicate<String>> startsWithLetter = letter -> name -> name.startsWith(letter);
		friends.stream().map(String::toUpperCase).filter(startsWithLetter.apply("N")).collect(Collectors.toList())
				.forEach(System.out::println);

	}

	public static Predicate<String> checkIfStartsWith(final String letter) {
		return name -> name.startsWith(letter);
	}
}
