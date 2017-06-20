package learn.techgig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkQueue {

	static Map<String, Queue<Task>> workQueue = new ConcurrentHashMap<String, Queue<Task>>();
	static Map<String, String> workTracker = Collections.synchronizedMap(new HashMap<String, String>());

	public static void main(String[] args) {

		ExecutorService threadPoolExecutor = new ThreadPoolExecutor(20, 100, 5000, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());

		for (int i = 1; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				WorkQueue.Worker worker = new WorkQueue.Worker();
				worker.skill = "S" + i;
				threadPoolExecutor.execute(worker);
			}
		}

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
			if (priority > task.priority)
				return 1;
			else if (priority < task.priority)
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
				if (!queue.isEmpty()) {
					Task task = queue.peek();
					if (!workTracker.containsKey(task.id)) {
						workTracker.put(task.id, id);
						// executes
					}
					// does not execute in else block
				}
			}

		}

	}

}
