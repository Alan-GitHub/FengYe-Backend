package com.alan.fengye.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.WorksCellData;
import com.alan.fengye.dal.CommentsTableOperation;
import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.ForwardsTableOperation;
import com.alan.fengye.dal.LikesTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class RecommendData {
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private final static String PICREALPATH = "C:\\Users\\Alan\\Desktop\\p";
	private Vector<WorksCellData> unitData;
			
	public RecommendData() 
	{
		this.unitData = new Vector<WorksCellData>();
	}
	
	public void getRecommendData()
	{
		List<Map<String, Object>> list = WorksTableOperation.getInstance().queryWorksTable();

		int drawID = 0;
		List<Map<String, Object>> templist;
		Map<String, Object> tempmap;
		for(Map<String, Object> map : list)
		{
			WorksCellData cellData = new WorksCellData();
			
			cellData.setPicURL(String.valueOf(map.get("path")));
			
			//ÄÃµ½»­°åÃû
			drawID = Integer.parseInt(String.valueOf(map.get("drawboard_id")));
			templist = DrawboardTableOperation.getInstance().queryDrawboardTableWithID(drawID);
			tempmap = templist.get(0);
			cellData.setTemplateName(String.valueOf(tempmap.get("Name")));
			
			cellData.setDescriptionText(String.valueOf(map.get("description")));
			cellData.setUploadTime(Long.valueOf(String.valueOf(map.get("uploadtime"))));
			
			this.unitData.addElement(cellData);
		}

		Map<String, Object> map;
		int worksId = 0;
		int userID = 0;
		for(WorksCellData cell : this.unitData)
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
			BufferedImage bufferImage = getPicSize(cell);
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
	}
	
	private BufferedImage getPicSize(WorksCellData unitData)
	{
		//Get the picture width and height
		File file = new File(PICREALPATH + "\\" + unitData.getPicURL().replaceAll("_", "/"));
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
	
	public Vector<WorksCellData> getUnitData()
	{
		return this.unitData;
	}
	
}
