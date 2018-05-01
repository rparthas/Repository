package com.tw.cashier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamApplication {

	public static void main(String[] args) throws Exception {

		Streamer  streamer = new Streamer();
		InputStream source=System.in;
		OutputStream dest=new FileOutputStream(new File("out.txt"));
		String str=streamer.read(source);
		while(str != null) {
			streamer.write(dest, str);
			str=streamer.read(source);
		}
		dest.close();

		
	}

}
