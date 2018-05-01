package com.tw.milk;

import java.util.stream.IntStream;

public class Application {

	public static void main(String args[]) {

		MilkMan milkMan = new MilkMan();
		Customer customer = new Customer("A", 1000);
		milkMan.add(customer);

		IntStream.range(1, 4).forEach(token -> {
			milkMan.deliver();
			milkMan.bill();
		});

	}
}
