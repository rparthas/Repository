package com.tw.cashier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Streamer {

	
	public String read(InputStream source) throws Exception {
		Scanner scanner = new Scanner(source);
		return scanner.nextLine();
	}

	public void write(OutputStream dest, String str) throws IOException {
		dest.write(str.getBytes());
		
	}

}
