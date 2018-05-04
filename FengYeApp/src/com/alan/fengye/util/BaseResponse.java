package com.alan.fengye.util;

public class BaseResponse extends GeneratBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String returnCode;
	private String msg;
	private String returnBiz;
	private Object date;
	
	public BaseResponse() {
		super();
	}
	public BaseResponse(String returnCode, String msg) {
		super();
		this.returnCode = returnCode;
		this.msg = msg;
	}
	public BaseResponse(String returnCode, String msg, String returnBiz) {
		this(returnCode,msg);
		this.returnBiz = returnBiz;
	}
	public BaseResponse(String returnCode, String msg, String returnBiz, Object date) {
		this(returnCode,msg,returnBiz);
		this.date = date;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getReturnBiz() {
		return returnBiz;
	}
	public void setReturnBiz(String returnBiz) {
		this.returnBiz = returnBiz;
	}
	public Object getDate() {
		return date;
	}
	public void setDate(Object date) {
		this.date = date;
	}
	
	

}
