package com.alan.fengye.bean;

import java.util.Vector;

public class InterestGroupDetailUnit {
	
	private int numsOfRegardUser;
	private Vector<String> headIconOfRegardUser;
	private boolean isAttention;
	
	public InterestGroupDetailUnit() {
		
		this.numsOfRegardUser = 0;
		this.headIconOfRegardUser = new Vector<String>();
		this.isAttention = false;
	}
	
	public int getNumsOfRegardUser(){
		
		return this.numsOfRegardUser;
	}
	
	public void setNumsOfRegardUser(int nums) {
		
		this.numsOfRegardUser = nums;
	}
	
	public Vector<String> getHeadIconOfRegardUser() {
		
		return this.headIconOfRegardUser;
	}
	
	public boolean getIsAttention(){
		
		return this.isAttention;
	}
	
	public void setIsAttention(boolean isAttention) {
		
		this.isAttention = isAttention;
	}
}
