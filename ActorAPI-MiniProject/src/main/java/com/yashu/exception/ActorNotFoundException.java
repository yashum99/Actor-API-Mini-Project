package com.yashu.exception;



@SuppressWarnings("serial")
public class ActorNotFoundException extends RuntimeException {

	public ActorNotFoundException() {
		super();
	}
	
	public ActorNotFoundException(String msg) {
		super(msg);
	}
	
}
