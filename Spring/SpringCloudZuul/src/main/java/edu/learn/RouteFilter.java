package edu.learn;

import com.netflix.zuul.ZuulFilter;

public class RouteFilter extends ZuulFilter {

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
		return "Route";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
