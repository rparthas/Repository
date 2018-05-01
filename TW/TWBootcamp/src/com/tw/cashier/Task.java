package com.tw.cashier;

public abstract class Task {

	long number;
	String title;
	String descr;
	String iteration;
	String assignedTo;
	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getIteration() {
		return iteration;
	}

	public void setIteration(String iteration) {
		this.iteration = iteration;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	
	public void play() {
		System.out.println(number+" opened");
	}
	
	abstract public void close();

}
