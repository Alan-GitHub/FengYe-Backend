package com.alan.fengye.bean;

public class WorksCellData {

	//picture property
	private String picURL;
	private int picWidth;
	private int picHeight;
	
	//property
	private long uploadTime;
	private int forwardCount;
	private int likeCount;
	private int commentCount;
	private String descriptionText;
	
	//picture owner
	private String headIcon;
	private String templateName;
	private String owner;
	
	
	
	
	public WorksCellData()
	{	
		this.picURL = "";
		this.picWidth = 0;
		this.picHeight = 0;
		
		this.uploadTime = 0;
		this.forwardCount = 0;
		this.likeCount = 0;
		this.commentCount = 0;
		this.descriptionText = "";

		this.headIcon = "";
		this.templateName = "";
		this.owner = "";
	}

	//method
	public String getPicURL()
	{
		return this.picURL;
	}
	
	public void setPicURL(String picURL)
	{
		this.picURL = picURL;
	}
	
	public int getPicWidth()
	{
		return this.picWidth;
	}
	
	public void setPicWidth(int picWidth)
	{
		this.picWidth = picWidth;
	}
	
	public int getPicHeight()
	{
		return this.picHeight;
	}
	
	public void setPicHeight(int picHeight)
	{
		this.picHeight = picHeight;
	}
	
	public long getUploadTime()
	{
		return this.uploadTime;
	}
	
	public void setUploadTime(long uploadTime)
	{
		this.uploadTime = uploadTime;
	}
	
	public int getForwardCount() 
	{
		return this.forwardCount;
	}
	
	public void setForwardCount(int forwardCount)
	{
		this.forwardCount = forwardCount;
	}
	
	public int getLikeCount()
	{
		return this.likeCount;
	}
	
	public void setLikeCount(int likeCount)
	{
		this.likeCount = likeCount;
	}
	
	public int getCommentCount()
	{
		return this.commentCount;
	}
	
	public void setCommentCount(int commentCount)
	{
		this.commentCount = commentCount;
	}
	
	public String getDescriptionText()
	{
		return this.descriptionText;
	}
	
	public void setDescriptionText(String descriptionText)
	{
		this.descriptionText = descriptionText;
	}
	
	public String getHeadIcon()
	{
		return this.headIcon;
	}
	
	public void setHeadIcon(String headIcon)
	{
		this.headIcon = headIcon;
	}
	
	public String getTemplateName()
	{
		return this.templateName;
	}
	
	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}
	
	public String getOwner()
	{
		return this.owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
}
