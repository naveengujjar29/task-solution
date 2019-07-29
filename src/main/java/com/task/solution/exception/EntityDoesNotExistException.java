package com.task.solution.exception;

public class EntityDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityDoesNotExistException() {
		super();
	}

	public EntityDoesNotExistException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public EntityDoesNotExistException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EntityDoesNotExistException(String arg0) {
		super(arg0);
	}

	public EntityDoesNotExistException(Throwable arg0) {
		super(arg0);
	}

}
