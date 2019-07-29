package com.task.solution.exception;

public class PublicationTypeDoesNotSupportedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PublicationTypeDoesNotSupportedException() {
		super();
	}

	public PublicationTypeDoesNotSupportedException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public PublicationTypeDoesNotSupportedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PublicationTypeDoesNotSupportedException(String arg0) {
		super(arg0);
	}

	public PublicationTypeDoesNotSupportedException(Throwable arg0) {
		super(arg0);
	}

}
