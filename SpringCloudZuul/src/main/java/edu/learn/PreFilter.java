package edu.learn;

import com.netflix.zuul.ZuulFilter;

public class PreFilter extends ZuulFilter {

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
		return "Pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
