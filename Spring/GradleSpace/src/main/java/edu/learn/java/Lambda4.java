package edu.learn.java;

public class Lambda4 {
	  public static void main(String[] args) {
	        //example of passing multiple values to a method using lambda 
	        //notice that I do NOT have to specify the data type of a and b
	        Calculate<Integer> add =(a,b)-> a + b;
	        Calculate<Integer> difference = (a,b) -> Math.abs(a-b);
	        Calculate<Integer> divide =(a,b)-> (b != 0 ? a/b : 0);
	        
	        System.out.println(add.calc(3,2));
	        System.out.println(difference.calc(5,10));
	        System.out.println(divide.calc(5, 0));
	    }
}

@FunctionalInterface
interface Calculate<T>{
	 T calc(T a,T b);
}