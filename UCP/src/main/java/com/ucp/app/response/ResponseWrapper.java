package com.ucp.app.response;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper {
	private ServletResponse response;
	private PrintWriterWrapper writerEx;
	private ServletOutputStreamWrapper outputStreamEx;
	
	public ResponseWrapper(HttpServletResponse response) {
		super(response);
		this.response = response;
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {

		ServletOutputStream stream = this.response.getOutputStream();
		
		if (null == this.outputStreamEx) {
			this.outputStreamEx = new ServletOutputStreamWrapper(stream);
		}
		
		return this.outputStreamEx;
	}
	
	@Override
	public PrintWriter getWriter() throws IOException {
		PrintWriter writer = this.response.getWriter();
		this.writerEx = new PrintWriterWrapper(writer);
		return this.writerEx;
	}
	
	public String getBody() {
		String result = "";
		
		if (null != this.outputStreamEx) {
			result = this.outputStreamEx.getBody();
		}
		else if (null != this.writerEx) {
			result = this.writerEx.getBody();
		}
		
		return result;
	}
}
