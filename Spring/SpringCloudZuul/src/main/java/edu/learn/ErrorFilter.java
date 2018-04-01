package edu.learn;

import com.netflix.zuul.ZuulFilter;

public class ErrorFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		System.out.println("Inside "+filterType()+" filter");
		return null;
	}

	@Override
	public String filterType() {
		return "Error";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
