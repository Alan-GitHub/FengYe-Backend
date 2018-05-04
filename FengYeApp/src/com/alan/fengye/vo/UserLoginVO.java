package com.alan.fengye.vo;

import com.alan.fengye.util.GeneratBase;
import com.alan.fengye.util.NotNull;

public class UserLoginVO extends GeneratBase {
	
	@NotNull("登录名不能为空")
	private String loginName;
	@NotNull("登录密码不能为空")
	private String loginPassword;
	
	public UserLoginVO() {}
	
	public UserLoginVO(String name, String passwd) {
		
		this.loginName = name;
		this.loginPassword = passwd;
	}
	
	public void setLoginName(String name) {
		
		this.loginName = name;
	}
	
	public String getLoginName() {
	
		return this.loginName;
	}
	
	public void setLoginPassword(String passwd) {
		
		this.loginPassword = passwd;
	}
	
	public String getLoginPassword() {
		
		return this.loginPassword;
	}
}
