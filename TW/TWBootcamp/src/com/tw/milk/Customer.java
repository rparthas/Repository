package com.tw.milk;

public class Customer {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	private String name;
	private double balance;

	public Customer(String name, double balance) {
		super();
		this.name = name;
		this.balance = balance;
	}

	public void pay(double amount) throws Exception {
		if (balance < amount) {
			throw new Exception("Insufficient funds");
		} else {
			balance -= amount;
		}
	}
}
