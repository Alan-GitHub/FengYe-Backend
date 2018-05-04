package com.alan.fengye.bean;

public class PersonalCenterDrawboardCell {
	
	private String coverImageURL;
	private int coverImageWidth;
	private int coverImageHeight;
	private String drawboardName;
	private String descriptionText;
	private String ownerHeadIcon;
	private String ownerUserName;
	
	private int picNums;
	private int attentionNum;
	
	public PersonalCenterDrawboardCell()
	{
		this.coverImageURL = "";
		this.coverImageWidth = 0;
		this.coverImageHeight = 0;
		this.drawboardName = "";
		this.descriptionText = "";
		this.ownerHeadIcon = "";
		this.ownerUserName = "";	
		this.picNums = 0;
		this.attentionNum = 0;
	}
	
	public String getCoverImageURL()
	{
		return this.coverImageURL;
	}
	
	public void setCoverImageURL(String imageURL)
	{
		this.coverImageURL = imageURL;
	}
	
	public int getCoverImageWidth() {
		
		return this.coverImageWidth;
	}
	
	public void setCoverImageWidth(int coverImageWidth) {
		
		this.coverImageWidth = coverImageWidth;
	}
	
	public int getCoverImageHeight() {
		
		return this.coverImageHeight;
	}
	
	public void setCoverImageHeight(int coverImageHeight) {
		
		this.coverImageHeight = coverImageHeight;
	}
	
	public String getDrawboardName()
	{
		return this.drawboardName;
	}
	
	public void setDrawboardName(String drawboardName)
	{
		this.drawboardName = drawboardName;
	}
	
	public String getDescriptionText()
	{
		return this.descriptionText;
	}
	
	public void setDescriptionText(String Text)
	{
		this.descriptionText = Text;
	}
	
	public int getPicNums()
	{
		return this.picNums;
	}
	
	public void setPicNums(int picNums)
	{
		this.picNums = picNums;
	}
	
	public int getAttentionNum()
	{
		return this.attentionNum;
	}
	
	public void setAttentionNum(int attentionNum)
	{
		this.attentionNum = attentionNum;
	}
	
	public String getOwnerHeadIcon()
	{
		return this.ownerHeadIcon;
	}
	
	public void setOwnerHeadIcon(String headIcon)
	{
		this.ownerHeadIcon = headIcon;
	}
	
	public String getOwnerUserName()
	{
		return this.ownerUserName;
	}
	
	public void setOwnerUserName(String userName)
	{
		this.ownerUserName = userName;
	}

}
