import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Solver {

	public static void main(String[] args) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("input.txt")));
		PrintWriter out = new PrintWriter("output.txt");
		int input = Integer.parseInt(bufferedReader.readLine());
		out.close();
		bufferedReader.close();

	}

}
