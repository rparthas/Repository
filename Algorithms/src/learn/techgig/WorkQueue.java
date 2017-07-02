package learn.techgig;

import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.IntStream;
/**
 * Run using java 8
 * @author Rajagopal
 *
 */
public class WorkQueue {

	/**
	 * Concurrent HashMap to support multi threading. 
	 */
	static Map<String, Queue<Task>> workQueue = new ConcurrentHashMap<String, Queue<Task>>();
	

	public static void main(String[] args) throws Exception {

		int skillCount = 10;
		int workerCountPerSkill = 10;
		int taskCount = 1000000;
		ExecutorService workerPool = Executors.newFixedThreadPool(skillCount * workerCountPerSkill);//Thread pool executor to handle many different threads
		Random randomSkillGenerator = new Random();
		Random randomPriorityGenerator = new Random();

		String[] skills = new String[skillCount];
		IntStream.rangeClosed(1, skillCount).forEach(counter -> skills[counter - 1] = "S" + counter);

		for (int j = 0; j < skillCount; j++) {
			for (int i = 1; i < workerCountPerSkill; i++) {
				WorkQueue.Worker worker = new WorkQueue.Worker();
				worker.skill = "S" + j;
				workerPool.execute(worker);
			}
		}

		IntStream.rangeClosed(1, taskCount).forEach(counter -> {
			int randIndex = randomSkillGenerator.nextInt(skills.length);
			Task task = new Task();
			task.skill = skills[randIndex];
			task.priority = randomPriorityGenerator.nextInt(200);
			Queue<Task> taskQueue = workQueue.get(task.skill);
			if (taskQueue == null) {
				taskQueue = new PriorityBlockingQueue<>();// A priority queue to handle the min and max priority 
				workQueue.put(task.skill, taskQueue);
			}
			taskQueue.add(task);
		});

	}

	static class Task implements Comparable<Task> {
		String id;
		String skill;
		int priority;

		public Task() {
			id = UUID.randomUUID().toString();
		}

		@Override
		public int compareTo(Task task) {
			if (priority < task.priority)
				return 1;
			else if (priority > task.priority)
				return -1;
			return 0;
		}
	}

	static class Worker implements Runnable {
		String skill;
		String id;

		public Worker() {
			id = UUID.randomUUID().toString();
		}

		@Override
		public void run() {
			while (true) {
				Queue<Task> queue = workQueue.get(skill);
				if (queue != null && !queue.isEmpty()) {
					Task task = queue.poll();
					if (task != null) {
						System.out.println("Worker[" + id + "] executing [" + task.id + "]");
					}

				}
			}

		}

	}

}
