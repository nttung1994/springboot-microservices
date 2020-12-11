package com.linhtinhstuff.rest.webservice.restfulwebservice.helloworld;

public class HelloWorldBean {
	
	private String message;

	public HelloWorldBean(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
