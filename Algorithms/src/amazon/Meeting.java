package amazon;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Meeting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> timeLimits = new ArrayList<>();
		int duration = 0;

		try {
			timeLimits.add("0:1440");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String line = br.readLine();
			List<String> meetingTime = new ArrayList<>();
			duration = Integer.parseInt(line.split(" ")[1]);
			int runs = Integer.parseInt(line.split(" ")[0]);
			for (int i = 0; i < runs; i++) {
				line = br.readLine();
				String time[] = line.split(" ");
				int startTime = convertToMinutes(time[0], time[1]);
				int endTime = convertToMinutes(time[2], time[3]);
				meetingTime.add(startTime + ":" + endTime);
			}
			for (String time : meetingTime) {
				returnTimes(timeLimits, time, duration);
			}
			for (String time : timeLimits) {
				String times[] = time.split(":");
				String startTime = convertToTime(Integer.parseInt(times[0]));
				String endTime = convertToTime(Integer.parseInt(times[1]));
				System.out.println(startTime + " " + endTime);
			}
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
			e.printStackTrace();
		}

	}

	public static void returnTimes(List<String> timeLimits, String time,
			int duration) {
		int startTime = Integer.parseInt(time.split(":")[0]);
		int endTIme = Integer.parseInt(time.split(":")[1]);
		for (int i = 0; i < timeLimits.size(); i++) {
			String timeLimit = timeLimits.get(i);
			int startTimeLimit = Integer.parseInt(timeLimit.split(":")[0]);
			int endTImeLimit = Integer.parseInt(timeLimit.split(":")[1]);

			if (endTIme <= startTimeLimit) {
				continue;
			}
			if (startTime >= endTImeLimit) {
				continue;
			}
			if (startTime < startTimeLimit) {
				if (endTIme > endTImeLimit) {
					timeLimits.remove(i);
					i--;
				} else if (endTIme < endTImeLimit) {
					timeLimits.remove(i);
					timeLimits.add(i, endTIme + ":" + endTImeLimit);
				} else {
					timeLimits.remove(i);
					i--;
				}
			} else if (startTime > startTimeLimit) {
				if (endTIme > endTImeLimit) {
					timeLimits.remove(i);
					timeLimits.add(i, startTimeLimit + ":" + startTime);
				} else if (endTIme < endTImeLimit) {
					timeLimits.remove(i);
					timeLimits.add(i, startTimeLimit + ":" + startTime);
					timeLimits.add(i + 1, endTIme + ":" + endTImeLimit);
					i++;
				} else {
					timeLimits.remove(i);
					timeLimits.add(i, startTimeLimit + ":" + startTime);
				}
			} else {
				if (endTIme > endTImeLimit) {
					timeLimits.remove(i);
					i--;
				} else if (endTIme < endTImeLimit) {
					timeLimits.remove(i);
					timeLimits.add(i, endTIme + ":" + endTImeLimit);
				} else {
					timeLimits.remove(i);
					i--;
				}
			}
		}

		for (int i = 0; i < timeLimits.size(); i++) {
			String timeLimit = timeLimits.get(i);
			int startTimeLimit = Integer.parseInt(timeLimit.split(":")[0]);
			int endTImeLimit = Integer.parseInt(timeLimit.split(":")[1]);
			if (endTImeLimit - startTimeLimit < duration) {
				timeLimits.remove(i);
				i--;
			}
		}
	}

	public static int convertToMinutes(String hourPart, String minPart) {
		int time = 0;
		int hour = Integer.parseInt(hourPart);
		int minute = Integer.parseInt(minPart);
		if (hour == 0 && minute == 0) {
			time = 1440;
		} else {
			time = (hour * 60) + minute;
		}
		return time;
	}

	public static String convertToTime(int minutes) {
		String time = "";

		if (minutes == 1440) {
			time = "00 00";
		} else {
			int hour = minutes / 60;
			int minute = minutes % 60;
			String hr = hour + "";
			String min = minute + "";
			hr = convertTo2Char(hr);
			min = convertTo2Char(min);
			time = hr + " " + min;
		}
		return time;
	}

	public static String convertTo2Char(String str) {
		if (str.length() == 1) {
			str = "0" + str;
		}
		return str;
	}

}
