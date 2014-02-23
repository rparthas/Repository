import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class Test implements Runnable {

	private static int nextSerialNum = 0;

	private static final int a;

	static {
		a = 20;
	}

	private static ThreadLocal serialNum = new ThreadLocal() {
		protected synchronized Object initialValue() {
			return new Integer(nextSerialNum++);
		}
	};

	public static int get() {
		return ((Integer) (serialNum.get())).intValue();
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws Exception
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void main(String[] args) throws ClassNotFoundException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, Exception {
		// new Thread(new Test()).start();
		// new Thread(new Test()).start();
	

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		get();
		System.out.println(nextSerialNum);
	}

}
