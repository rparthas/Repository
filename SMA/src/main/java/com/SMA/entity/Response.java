package com.SMA.entity;

public class Response {

	
	@Override
	public String toString() {
		return "Response [message=" + message + "]";
	}

	public String message;

	public String getMessage() {
		return message;
	}

	public Response(String message) {
		super();
		this.message = message;
	}

	public Response() {
		super();
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
