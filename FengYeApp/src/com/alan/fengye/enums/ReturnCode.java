package com.alan.fengye.enums;

public enum ReturnCode {
	SECCESS("000","成功"),
	FAIL("999","失败"),
	SERVICEEXCEPTION("999999","服务器处理异常"),
	//controller控制层错误代码
	TIMEOUT("111","连接超时"),
	BASEPARAMNULL("112","基本参数为空"),
	SERVECPARAMNULL("112","业务参数为空"),
	
	//service业务层错误代码
	USER_EXSIST_ERROR("222","用户已存在错误"),
	INTERFACE_EXSIST_ERROR("223","接口不存在");
	private String code;
	private String desc;
	

	private ReturnCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
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

	

}
