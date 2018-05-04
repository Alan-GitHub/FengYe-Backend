package com.alan.fengye.util;

public class BaseRequest extends GeneratBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull(value="�ӿڰ汾����Ϊ��")
	private String ver;
	@NotNull(value="�ӿ����Ʋ���Ϊ��")
	private String service;
	private String channel;
	@NotNull(value="ҵ����ˮ�Ų���Ϊ��")
	private String biz;
	@NotNull(value="���ýӿ�ʱ�䲻��Ϊ��")
	private String time;
	private Object data;
	
	public BaseRequest() {
		super();
	}
	public BaseRequest(String ver, String service, String channel, String biz, String time) {
		super();
		this.ver = ver;
		this.service = service;
		this.channel = channel;
		this.biz = biz;
		this.time = time;
	}
	public BaseRequest(String ver, String service, String channel, String biz, String time, Object data) {
		super();
		this.ver = ver;
		this.service = service;
		this.channel = channel;
		this.biz = biz;
		this.time = time;
		this.data = data;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBiz() {
		return biz;
	}
	public void setBiz(String biz) {
		this.biz = biz;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	

}
