package com.tw.cashier;

public class Item {
	String name;
	String descr;
	int measure;
	double price;
	String unit;
	String sku;

	@Override
	public boolean equals(Object item) {
		Item it= null;
		if(item instanceof Item) {
			it=(Item)item;
		}
		return (it != null && it.getSku()!=null && it.sku.equals(sku));
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public int getMeasure() {
		return measure;
	}

	public void setMeasure(int measure) {
		this.measure = measure;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
