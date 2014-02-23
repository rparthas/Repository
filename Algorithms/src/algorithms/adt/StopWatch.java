package algorithms.adt;

public class StopWatch {

	public long startTime = 0;
	public long endTime = 0;

	public void start() {
		startTime = System.nanoTime();
	}

	public void end() {
		endTime = System.nanoTime();
	}

	public long getInterval() {
		return endTime - startTime;
	}
}
