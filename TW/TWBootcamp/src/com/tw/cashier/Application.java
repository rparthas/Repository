package com.tw.cashier;

public class Application {

	public static void main(String[] args) throws Exception {
		Cart cart = new Cart();
		Item product = new Item();
		product.setSku("1");
		product.setDescr("jelly");
		product.setMeasure(100);
		product.setPrice(12.00);
		product.setUnit("gm");
		product.setName("jelly");
		product.setDescr("jelly");
		
		cart.addToCart(product, 2);
		cart.addToCart(product, 4);
		cart.print();
	
	}

}
