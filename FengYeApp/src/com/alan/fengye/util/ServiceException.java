package com.alan.fengye.util;

public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	private String code;
	private String desc;
	private Object object;
	
	public ServiceException(String code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}
	public ServiceException(String desc) {
		this.desc = desc;
	}
	public ServiceException(String code, String desc,Object object) {
		super();
		this.code = code;
		this.desc = desc;
		this.object = object;
	}
	public ServiceException(String code,Object object) {
		this(code,"",object);
	}
	public ServiceException(String s, Throwable throwable)
    {
        super(s, throwable);
    }
 
    public ServiceException(Throwable throwable)
    {
        super(throwable);
    }
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}