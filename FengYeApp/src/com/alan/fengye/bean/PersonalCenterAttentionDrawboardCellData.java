package com.alan.fengye.bean;

public class PersonalCenterAttentionDrawboardCellData {

	private String coverImageURL;
	private int coverImageWidth;
	private int coverImageHeight;
	private String drawboardName;
	private String descriptionText;
	private String ownerHeadIcon;
	private String ownerUserName;
	
	private int picNums;
	private int attentionNum;
	
	public PersonalCenterAttentionDrawboardCellData() {
		
		this.coverImageURL = "";
		this.coverImageWidth = 0;
		this.coverImageHeight = 0;
		this.ownerUserName = "";
		this.drawboardName = "";
		this.descriptionText = "";
		this.ownerHeadIcon = "";
		this.picNums = 0;
		this.attentionNum = 0;
	}
	
	//method
	public String getCoverImageURL() {
		
		return this.coverImageURL;
	}
	
	public void setCoverImageURL(String imageURL) {
		
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
	
	public String getOwnerUserName() {
		
		return this.ownerUserName;
	}
	
	public void setOwnerUserName(String ownerUserName) {
		
		this.ownerUserName = ownerUserName;
	}
	
	public String getDrawboardName() {
		
		return this.drawboardName;
	}
	
	public void setDrawboardName(String drawboardName) {
		
		this.drawboardName = drawboardName;
	}
	
	public String getDescriptionText() {
		
		return this.descriptionText;
	}
	
	public void setDescriptionText(String descriptionText) {
		
		this.descriptionText = descriptionText;
	}
	
	public String getOwnerHeadIcon() {
		
		return this.ownerHeadIcon;
	}
	
	public void setOwnerHeadIcon(String ownerHeadIcon) {
		
		this.ownerHeadIcon = ownerHeadIcon;
	}
	
	public int getPicNums() {
		
		return this.picNums;
	}
	
	public void setPicNums(int picNums) {
		
		this.picNums = picNums;
	}
	
	public int getAttentionNum() {
		
		return this.attentionNum;
	}
	
	public void setAttentionNum(int attentionNum) {
		
		this.attentionNum = attentionNum;
	}
}
