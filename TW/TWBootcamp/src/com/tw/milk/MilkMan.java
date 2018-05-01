package com.tw.milk;

import java.util.LinkedHashMap;
import java.util.Map;

public class MilkMan {

	private final Map<Customer, Double> customers = new LinkedHashMap<>();

	private double getPrice(Customer customer) {
		return customers.get(customer);
	}

	private void accept(Customer customer) {
		 // milkman will increase the balance
	}

	public void add(Customer customer) {
		customers.put(customer,Double.valueOf(500));
	}

	public void deliver() {
		// TODO Auto-generated method stub
	}

	public void bill() {
		for (Customer customer : customers.keySet()) {
			try {
				customer.pay(getPrice(customer));
				accept(customer);
			}catch(Exception e) {
				System.out.println("customer["+customer.getName()+"] is a baddie");
			}
			
		}

	}

}
