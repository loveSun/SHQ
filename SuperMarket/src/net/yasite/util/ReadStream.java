package net.yasite.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadStream {
	public static String read(InputStream stream){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] bs = new byte[1024];
		try {
			while((len=stream.read(bs))!=-1){
				outputStream.write(bs, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(outputStream.toByteArray());
	}
}
