package com.alan.fengye.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.PersonalCenterAttentionInterestGroupCellData;
import com.alan.fengye.bean.WorksCellData;
import com.alan.fengye.dal.CommentsTableOperation;
import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.ForwardsTableOperation;
import com.alan.fengye.dal.InterestGroupTableOperation;
import com.alan.fengye.dal.LikesTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class DiscoverData {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private final static String PICREALPATH = "C:\\Users\\Alan\\Desktop\\p";
	
	private Vector<WorksCellData> worksUnitData;
	private Vector<PersonalCenterAttentionInterestGroupCellData> interestGroupUnitData;
			
	public DiscoverData()
	{
		this.worksUnitData = new Vector<WorksCellData>();
		this.interestGroupUnitData = new Vector<PersonalCenterAttentionInterestGroupCellData>();
	}
	
	public void getDiscoverData()
	{
		//get interest group unit data
		getInterestGroupUnit();
		
		//get works unit data
		getWorksUnit();
	}
	
	private void getInterestGroupUnit()
	{
		List<Map<String, Object>> list = InterestGroupTableOperation.getInstance().queryInterestGroupTable();
		
		BufferedImage bufferImage;
		for(Map<String, Object> map : list)
		{
			PersonalCenterAttentionInterestGroupCellData cellData = new PersonalCenterAttentionInterestGroupCellData();
			
			cellData.setInterestGroupName(String.valueOf(map.get("group_name")));
			cellData.setInterestGroupDesc(String.valueOf(map.get("description")));
			cellData.setCoverImageURL(PICDIRPREFIX + String.valueOf(map.get("cover_image_path")));
			
			bufferImage = getPicSize(String.valueOf(map.get("cover_image_path")));
			cellData.setCoverImageWidth(bufferImage.getWidth());
			cellData.setCoverImageHeight(bufferImage.getHeight());
			
			this.interestGroupUnitData.addElement(cellData);
		}
	}
	
	private void getWorksUnit()
	{
		List<Map<String, Object>> list = WorksTableOperation.getInstance().queryWorksTable();
		
		int drawID = 0;
		List<Map<String, Object>> templist;
		Map<String, Object> tempmap;
		for(Map<String, Object> map : list)
		{	
			WorksCellData workCell = new WorksCellData();
			
			workCell.setPicURL(String.valueOf(map.get("path")));
			
			//ÄÃµ½»­°åÃû
			drawID = Integer.parseInt(String.valueOf(map.get("drawboard_id")));
			templist = DrawboardTableOperation.getInstance().queryDrawboardTableWithID(drawID);
			tempmap = templist.get(0);
			workCell.setTemplateName(String.valueOf(tempmap.get("Name")));
			
			workCell.setDescriptionText(String.valueOf(map.get("description")));
			workCell.setUploadTime(Long.valueOf(String.valueOf(map.get("uploadtime"))));
			
			this.worksUnitData.addElement(workCell);
		}

		Map<String, Object> map;
		int worksId = 0;
		int userID = 0;
		for(WorksCellData cell : this.worksUnitData)
		{
			list = WorksTableOperation.getInstance().queryWorksTable(cell.getPicURL());
			map = list.get(0);
			worksId = Integer.parseInt(String.valueOf(map.get("id"))) ;
			userID = Integer.parseInt(String.valueOf(map.get("user_id"))) ;
			
			list = LikesTableOperation.getInstance().queryLikesTable(worksId);
			map = list.get(0);
			cell.setLikeCount(Integer.parseInt(String.valueOf(map.get("numbers"))));
				
			list = ForwardsTableOperation.getInstance().queryForwardsTableForCount(worksId);
			map = list.get(0);
			cell.setForwardCount(Integer.parseInt(String.valueOf(map.get("numbers"))) );
			
			list = CommentsTableOperation.getInstance().queryCommentsTable(worksId);
			map = list.get(0);
			cell.setCommentCount(Integer.parseInt(String.valueOf(map.get("numbers"))) );

			//set picture network url 
			BufferedImage bufferImage = getPicSize(cell.getPicURL().replaceAll("_", "/"));
			cell.setPicWidth(bufferImage.getWidth());
			cell.setPicHeight(bufferImage.getHeight());
			cell.setPicURL(PICDIRPREFIX + cell.getPicURL().replaceAll("_", "/"));
			
			//picture owner and head icon
			list = UserTableOperation.getInstance().queryUsersTable(userID);
			map = list.get(0);
			cell.setOwner(String.valueOf(map.get("username")));
			
			//this.unitData[i].setHeadIcon(this.getHeadIconURL(String.valueOf(map.get("head_icon"))));
			cell.setHeadIcon(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
		}
		
		//print
//		for(WorksCellData cell : this.worksUnitData)
//		{
//			System.out.println("getPicturePath=" + cell.getPicURL());
//			System.out.println("getPicWidth=" + cell.getPicWidth());
//			System.out.println("getPicHeight=" + cell.getPicHeight());
//		
//			System.out.println("getUploadTime=" + cell.getUploadTime());
//			System.out.println("getForwardCount=" + cell.getForwardCount());
//			System.out.println("getLikeCount=" + cell.getLikeCount());
//			System.out.println("getCommentCount=" + cell.getCommentCount());
//			
//			System.out.println("getDescription=" + cell.getDescriptionText());
//			System.out.println("getHeadIcon=" + cell.getHeadIcon());
//			System.out.println("getTemplate=" + cell.getTemplateName());
//			System.out.println("getOwner=" + cell.getOwner());
//			
//			System.out.println("\n");
//		}
		
	}
	
	private BufferedImage getPicSize(String path)
	{
		//Get the picture width and height
		File file = new File(PICREALPATH + "\\" + path);
		FileInputStream is = null;
		
	    try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		BufferedImage sourceImg = null;
		try {
			sourceImg = javax.imageio.ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sourceImg;
	}
	
	public Vector<WorksCellData> getWorksUnitData()
	{
		return this.worksUnitData;
	}
	
	public Vector<PersonalCenterAttentionInterestGroupCellData> getInterestGroupUnitData()
	{
		return this.interestGroupUnitData;
	}
	
}
