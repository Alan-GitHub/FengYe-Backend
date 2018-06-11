package com.alan.fengye.bean;

public class PersonalCenterHeaderData {
	
	private int drawboardNum;
	private int collectionNum;
	private int likeNum;
	private int attentionNum;
	private int fansNum;
	
	private String headIconURL;
	private String username;
	
	private boolean isAttention;
	
	public PersonalCenterHeaderData() 
	{
		this.drawboardNum = 0;
		this.collectionNum = 0;
		this.likeNum = 0;
		this.attentionNum = 0;
		this.fansNum = 0;
		this.headIconURL = "";
		this.username = "";
		this.isAttention = false;
	}
	
	public int getDrawboardNum()
	{
		return this.drawboardNum;
	}
	
	public void setDrawboardNum(int drawboardNum)
	{
		this.drawboardNum = drawboardNum;
	}
	
	public int getCollectionNum()
	{
		return this.collectionNum;
	}
	
	public void setCollectionNum(int collectionNum)
	{
		this.collectionNum = collectionNum;
	}
	
	public int getLikeNum()
	{
		return this.likeNum;
	}
	
	public void setLikeNum(int likeNum)
	{
		this.likeNum = likeNum;
	}
	
	public int getAttentionNum()
	{
		return this.attentionNum;
	}
	
	public void setAttentionNum(int attentionNum)
	{
		this.attentionNum = attentionNum;
	}
	
	public int getFansNum()
	{
		return this.fansNum;
	}
	
	public void setFansNum(int fansNum)
	{
		this.fansNum = fansNum;
	}
	
	public String getHeadIconURL()
	{
		return this.headIconURL;
	}
	
	public void setHeadIconURL(String headIconURL)
	{
		this.headIconURL = headIconURL;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public void setUsername(String name)
	{
		this.username = name;
	}
	
	public boolean getIsAttention()
	{
		return this.isAttention;
	}
	
	public void setIsAttention(boolean isAttention)
	{
		this.isAttention = isAttention;
	}
}
