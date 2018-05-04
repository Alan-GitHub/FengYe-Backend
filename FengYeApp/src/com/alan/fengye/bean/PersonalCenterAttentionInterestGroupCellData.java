package com.alan.fengye.bean;

public class PersonalCenterAttentionInterestGroupCellData {
	
	private String interestGroupName;
	private String interestGroupDesc;
	private String coverImageURL;
	private int coverImageWidth;
	private int coverImageHeight;
	
	public PersonalCenterAttentionInterestGroupCellData()
	{
		this.interestGroupName = "";
		this.interestGroupDesc = "";
		this.coverImageURL = "";
		this.coverImageWidth = 0;
		this.coverImageHeight = 0;
	}
	
	//method
	public String getInterestGroupName()
	{
		return this.interestGroupName;
	}
	
	public void setInterestGroupName(String groupName)
	{
		this.interestGroupName = groupName;
	}
	
	public String getInterestGroupDesc()
	{
		return this.interestGroupDesc;
	}
	
	public void setInterestGroupDesc(String groupDesc)
	{
		this.interestGroupDesc = groupDesc;
	}
	
	public String getCoverImageURL()
	{
		return this.coverImageURL;
	}
	
	public void setCoverImageURL(String coverImageUrl)
	{
		this.coverImageURL = coverImageUrl;
	}
	
	public int getCoverImageWidth()
	{
		return this.coverImageWidth;
	}
	
	public void setCoverImageWidth(int imageWidth)
	{
		this.coverImageWidth = imageWidth;
	}
	
	public int getCoverImageHeight()
	{
		return this.coverImageHeight;
	}
	
	public void setCoverImageHeight(int imageHeight)
	{
		this.coverImageHeight = imageHeight;
	}
}
