package com.alan.fengye.bean;

import java.util.Vector;

public class InterestGroupDetailUnit {
	
	private int numsOfRegardUser;
	private Vector<String> headIconOfRegardUser;
	
	public InterestGroupDetailUnit() {
		
		this.numsOfRegardUser = 0;
		this.headIconOfRegardUser = new Vector<String>();
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
}
