package com.ucp.app.response;

import java.io.PrintWriter;
import java.io.Writer;

public class PrintWriterWrapper extends PrintWriter {

	private StringBuilder body = new StringBuilder();
	
	public PrintWriterWrapper(Writer out) {
		super(out);
	}
	
	@Override
	public void write(int c) {
		super.write(c);
		this.body.append((char)c);
	}
	
	@Override
	public void write(char[] chars, int offset, int length) {
		super.write(chars, offset, length);
		this.body.append(chars, offset, length);
	}
	
	public void write(String string, int offset, int length) {
		super.write(string, offset, length);
		this.body.append(string, offset, length);
	}
	
	public String getBody() {
		return this.body.toString();
	}
}
