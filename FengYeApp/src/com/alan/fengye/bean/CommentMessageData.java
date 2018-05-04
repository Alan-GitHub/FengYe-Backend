package com.alan.fengye.bean;

public class CommentMessageData {

	private String commentUserHeadIconUrl;
	private String commentUsername;
	private String commentContent;
	private String commentTime;
	private String myHeadIconUrl;
	
	public CommentMessageData(){
		
		this.commentUserHeadIconUrl = "";
		this.commentUsername = "";
		this.commentContent = "";
		this.commentTime = "";
		this.myHeadIconUrl = "";
	}
	
	public String getCommentUserHeadIconUrl()
	{
		return this.commentUserHeadIconUrl;
	}
	
	public void setCommentUserHeadIconUrl(String headIconUrl)
	{
		this.commentUserHeadIconUrl = headIconUrl;
	}
	
	public String getCommentUsername()
	{
		return this.commentUsername;
	}
	
	public void setCommentUsername(String username)
	{
		this.commentUsername = username;
	}
	
	public String getCommentContent()
	{
		return this.commentContent;
	}
	
	public void setCommentContent(String content)
	{
		this.commentContent = content;
	}
	
	public String getCommentTime()
	{
		return this.commentTime;
	}
	
	public void setCommentTime(String time)
	{
		this.commentTime = time;
	}
	
	public String getMyHeadIconUrl()
	{
		return this.myHeadIconUrl;
	}
	
	public void setMyHeadIconUrl(String headIcon)
	{
		this.myHeadIconUrl = headIcon;
	}
}
