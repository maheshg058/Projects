package com.mahesh.messenger.exception;

public class DataNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8065852948245119951L;

	public DataNotFoundException(String errMsg) {
		super(errMsg);
	}
}
