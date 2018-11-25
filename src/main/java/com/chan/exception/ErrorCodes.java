package com.chan.exception;

public enum ErrorCodes {
	SUCCESS("成功","0"),
	RESULT_SYS_ERR("系统服务异常,请稍后再试","-1"),
	;
	
	private String msg;
	
	private String code;
	
	private ErrorCodes(String msg, String code){
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return this.getCode() + "  :  " + this.getMsg();
	} 
	
}
