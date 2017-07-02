package learn.techgig;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class WarehouseOperation {

	public static String[] warehouseScalability(String[] input1, String[] input2) {
		Map<String, List<Worker>> workers = new HashMap<String, List<Worker>>();
		List<Job> jobs = new ArrayList<Job>();

		// Prepare the worker map
		Arrays.asList(input1).stream().forEach(input -> {
			String[] workerString = input.split("#");
			Worker worker = new Worker();
			worker.employeeId = workerString[0];
			worker.skill = workerString[1];
			List<Worker> workerList = new ArrayList<>();
			if (workers.containsKey(worker.skill)) {
				workerList = workers.get(worker.skill);
			}
			workerList.add(worker);
			workers.put(worker.skill, workerList);
		});

		// Sort workers within each skill
		workers.keySet().forEach(key -> {
			List<Worker> workerList = workers.get(key);
			Collections.sort(workerList);
		});

		// Prepare the jobList
		Arrays.asList(input2).stream().forEach(input -> {
			String[] jobString = input.split("#");
			Job job = new Job();
			job.skill = jobString[0];
			job.priority = Long.parseLong(jobString[1]);
			job.time = Long.parseLong(jobString[2]);
			job.id = jobString[3];
			jobs.add(job);
		});

		// Sort the job list based on priority and time
		Collections.sort(jobs);

		// Iterate the job list
		jobs.stream().forEach(job -> {
			List<Worker> workerList = workers.get(job.skill);
			boolean assigned = false;
			Worker freeWorker = null;
			for (Worker worker : workerList) {
				if (!worker.occupied) {
					worker.addJob(job);
					assigned = true;
					worker.occupied = true;
					break;
				}
				if (freeWorker == null || worker.getWorkTime() < freeWorker.getWorkTime()) {
					freeWorker = worker;
				}
			}

			if (!assigned) {
				freeWorker.addJob(job);
				freeWorker.occupied = true;
			}
		});

		// Sort the workerlist by designation
		List<Worker> workerList = new ArrayList<Worker>();
		workers.values().forEach(workersList -> {
			workerList.addAll(workersList);
		});
		Collections.sort(workerList);

		// form the result
		int length = workerList.size();
		String[] result = new String[length];
		java.util.stream.IntStream.range(0, length).forEach(index -> result[index] = workerList.get(index).toString());
		return result;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String[] output = null;
		int ip1_size = 0;
		ip1_size = Integer.parseInt(in.nextLine().trim());
		String[] ip1 = new String[ip1_size];
		String ip1_item;
		for (int ip1_i = 0; ip1_i < ip1_size; ip1_i++) {
			try {
				ip1_item = in.nextLine();
			} catch (Exception e) {
				ip1_item = null;
			}
			ip1[ip1_i] = ip1_item;
		}
		int ip2_size = 0;
		ip2_size = Integer.parseInt(in.nextLine().trim());
		String[] ip2 = new String[ip2_size];
		String ip2_item;
		for (int ip2_i = 0; ip2_i < ip2_size; ip2_i++) {
			try {
				ip2_item = in.nextLine();
			} catch (Exception e) {
				ip2_item = null;
			}
			ip2[ip2_i] = ip2_item;
		}
		output = warehouseScalability(ip1, ip2);
		for (int output_i = 0; output_i < output.length; output_i++) {
			System.out.println(String.valueOf(output[output_i]));
		}
	}

	static class Worker implements Comparable<Worker> {
		String employeeId;
		String skill;
		private List<Job> jobs = new ArrayList<>();
		boolean occupied = false;
		private long workTime = 0;

		long getWorkTime() {
			return workTime;
		}

		void addJob(Job job) {
			jobs.add(job);
			workTime += job.time;
		}

		@Override
		public int compareTo(Worker worker) {
			return employeeId.compareTo(worker.employeeId);
		}

		public String toString() {
			StringBuilder jobId = new StringBuilder(this.employeeId);
			this.jobs.forEach(job -> {
				jobId.append("#");
				jobId.append(job.id);
			});
			return jobId.toString();
		}
	}

	static class Job implements Comparable<Job> {
		String id;
		long priority;
		long time;
		String skill;

		@Override
		public int compareTo(Job job) {
			int compare = priority < job.priority ? 1 : priority > job.priority ? -1 : 0;
			if (compare == 0) {
				compare = time < job.time ? -1 : time > job.time ? 1 : 0;
			}
			return compare;
		}
	}
}
