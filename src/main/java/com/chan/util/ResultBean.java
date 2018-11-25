
package com.chan.util;
import com.alibaba.fastjson.JSON;
import com.chan.exception.ErrorCodes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResultBean<T> implements Serializable{

	private String msgCode;

	private String msg;

	private String isEnd;

	private T data;


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ResultBean(){
		
	}
	
	public ResultBean(String resultCode, String msg){
		this(resultCode,msg,null);
	}
	
	public ResultBean(String resultCode, String msg, T data){
		this.msgCode = resultCode;
		this.data = data;
		this.msg = msg;
	}
	
	public ResultBean(ErrorCodes error){
		this.msgCode = error.getCode();
		this.msg = error.getMsg();
	}
	
	public void setErrorCodes(ErrorCodes error){
		this.msgCode = error.getCode();
		this.msg = error.getMsg();
	}
	
	public ResultBean(ErrorCodes error, T data){
		this.msgCode = error.getCode();
		this.data = data;
		this.msg = error.getMsg();
	}
	
	public ResultBean(ErrorCodes error, T data, boolean isEnd){
		this.msgCode = error.getCode();
		this.data = data;
		this.msg = error.getMsg();
		this.isEnd = Boolean.toString(isEnd);
	}
	
	public void putSimpleValue(Object value){
		if(null!=value){
			Map<String, Object> simpleMap = new HashMap<String, Object>();
			simpleMap.put("value", value);
			this.setData((T) simpleMap);
		}
	}
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}
