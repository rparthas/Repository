package com.tw.cashier;

import java.util.ArrayList;
import java.util.List;

public class Project {

	String name;
	
	
	List<Task> tasks = new ArrayList<>();
	
	public void add(Task task) {
		tasks.add(task);
		task.status="TO BE DONE";
	}
	
	public void play(Task task) {
		task.play();
		task.status="BEGAN";
	}
	
	public void close(Task task) {
		task.close();
	}
}
