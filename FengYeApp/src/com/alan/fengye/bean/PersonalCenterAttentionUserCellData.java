package com.alan.fengye.bean;

public class PersonalCenterAttentionUserCellData {

	private String userHeadIcon;
	private String userName;
	private int fansNums;
	
	public PersonalCenterAttentionUserCellData() {
		
		this.userHeadIcon = "";
		this.userName = "";
		this.fansNums = 0;
	}
	
	//method
	public String getUserHeadIcon() {
		
		return this.userHeadIcon;
	}
	
	public void setUserHeadIcon(String userHeadIcon) {
		
		this.userHeadIcon = userHeadIcon;
	}
	
	public String getUserName() {
		
		return this.userName;
	}
	
	public void setUserName(String userName) {
		
		this.userName = userName;
	}
	
	public int getFansNums() {
		
		return this.fansNums;
	}
	
	public void setFansNums(int fansNums) {
		
		this.fansNums = fansNums;
	}
}
