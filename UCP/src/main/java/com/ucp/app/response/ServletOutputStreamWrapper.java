package com.ucp.app.response;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class ServletOutputStreamWrapper extends ServletOutputStream {
	private StringBuilder body = new StringBuilder();
	private ServletOutputStream target;
	
	public ServletOutputStreamWrapper(ServletOutputStream target) {
		this.target = target;
	}

	@Override
	public boolean isReady() {
		return this.target.isReady();
	}

	@Override
	public void setWriteListener(WriteListener listener) {
		this.target.setWriteListener(listener);		
	}

	@Override
	public void write(int b) throws IOException {
		this.target.write(b);
		byte [] value = new byte[] {(byte)b};
		String temp = new String(value);
		body.append(temp);		
	}
	
	public String getBody() {
		return this.body.toString();
	}
}
