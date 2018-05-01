package com.tw.cashier;

public class Client {

	public static void main(String[] args) {
		Project project = new Project();
		project.name="Kroger";
		Task task = new Story();
		task.setAssignedTo("Raja");
		task.setDescr("asd");
		task.setIteration("1");
		task.setNumber(1);
		project.add(task);
		Task spikeTask = new Spike();
		spikeTask.setAssignedTo("Raja");
		spikeTask.setDescr("asd");
		spikeTask.setIteration("1");
		spikeTask.setNumber(2);
		project.add(spikeTask);
		project.play(spikeTask);
		project.close(spikeTask);
		project.play(task);
		project.close(task);
		

	}

}
