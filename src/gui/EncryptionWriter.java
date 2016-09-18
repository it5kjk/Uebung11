package gui;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class EncryptionWriter extends FilterWriter {
	public EncryptionWriter(Writer out) {
		super(out);
	}
	
	public void write(int c) throws IOException {
		super.write(c + 1);
	}
	
	public void write(char[] c, int offset, int count) throws IOException {
		for (int i = 0; i < count; i++) {
			write(c[offset + i]);
		}
	}
	
	public void write(char[] c) throws IOException {
		write(c, 0, c.length);
	}
	
	public void write(String s, int offset, int count) throws IOException{
		for (int i = 0; i < count; i++) {
			write(s.charAt(offset) + i);
		}
	}
	
	public void write(String s) throws IOException {
		write(s, 0, s.length());
	}
}
