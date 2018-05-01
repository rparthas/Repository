package com.tw.cashier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Cart {

	private int id;

	private Map<Item, Integer> items = new LinkedHashMap<>();

	public void print() {
		StringBuilder stringBf = new StringBuilder();
		for (Entry<Item, Integer> entryItem : items.entrySet()) {
			stringBf.append("Item Name[" + entryItem.getKey().getName() + "] Measure[" + entryItem.getKey().getMeasure()
					+ "] Quantity["+entryItem.getValue()+"] Value["+(entryItem.getKey().getPrice() * entryItem.getValue())+"]");
			
		}
		
		System.out.println(stringBf);
	}
	
	
	public void addToCart(Item item,int quantity) throws Exception {
		if(quantity ==0) {
			throw new Exception("Empty Quantity");
		}
		if(items.containsKey(item)) {
			items.put(item, (items.get(item).intValue()+quantity));
		}else {
			items.put(item, quantity);
		}
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Map<Item, Integer> getItems() {
		return items;
	}


	public void setItems(Map<Item, Integer> items) {
		this.items = items;
	}

}
