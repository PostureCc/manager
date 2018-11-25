package com.chan.exception;

public class ValidationException extends RuntimeException {

	private volatile Object result;

	private volatile ErrorCodes error;

    public Object getResult() {
		return result;
	}

	public ErrorCodes getError() {
		return error;
	}

	public ValidationException() {
		super();
	}

	public ValidationException(String msg) {
		super(msg);
	}

	public ValidationException(String msg, Exception e) {
		super(msg, e);
	}

	public ValidationException(ErrorCodes error) {
		super(error.getMsg());
		this.error = error;
	}

	public ValidationException(ErrorCodes error, Object result) {
		super(error.getMsg());
		this.error = error;
		this.result = result;
	}
}
