package com.alan.fengye.vo;

import com.alan.fengye.util.GeneratBase;
import com.alan.fengye.util.NotNull;

public class UserVo extends GeneratBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull(value="注册名不能为空")
	private  String registerName;
	@NotNull(value="注册密码不能为空")
	private String  registerPassword;
	
	
	public UserVo(String registerName, String registerPassword) {
		super();
		this.registerName = registerName;
		this.registerPassword = registerPassword;
	}
	public UserVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	public String getRegisterPassword() {
		return registerPassword;
	}
	public void setRegisterPassword(String registerPassword) {
		this.registerPassword = registerPassword;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
