package utility;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Test {

	public static void main(String args[]) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				"tasks.txt"))) {
			for (int i = 0; i < 100; i++) {
				writer.append(1000 + "");
				writer.newLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

	}
}
