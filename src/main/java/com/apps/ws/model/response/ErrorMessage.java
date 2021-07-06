
package com.apps.ws.model.response;

import java.util.Date;

public class ErrorMessage {
	
	private Date TIMESTAMP;
	private String message;
	
	public ErrorMessage() {
		
	}
	
	public ErrorMessage(Date tIMESTAMP, String message) {
		super();
		TIMESTAMP = tIMESTAMP;
		this.message = message;
	}
	public Date getTIMESTAMP() {
		return TIMESTAMP;
	}
	public void setTIMESTAMP(Date tIMESTAMP) {
		TIMESTAMP = tIMESTAMP;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
