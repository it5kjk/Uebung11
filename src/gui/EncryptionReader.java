package gui;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class EncryptionReader extends FilterReader {

	public EncryptionReader(Reader in) {
		super(in);
	}
	
	public int read() throws IOException {
		return super.read() - 1;
	}
	
	public int read(char[] c, int offset, int length) throws IOException {
		// read original data
		int result = super.read(c, offset, length);
		
		// decode
		for (int i = 0; i < result; i++) {
			c[i + offset] = (char) ((c[i + offset]) -1);
		}
		return result;
	}
}
