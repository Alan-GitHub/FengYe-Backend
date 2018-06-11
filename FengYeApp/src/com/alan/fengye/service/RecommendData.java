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
		int worksId = 0;
		int userID = 0;
		for(Map<String, Object> map : list)
		{
			WorksCellData cellData = new WorksCellData();
			
			//路径
			cellData.setPicURL(String.valueOf(map.get("path")));
			
			//拿到画板名
			drawID = Integer.parseInt(String.valueOf(map.get("drawboard_id")));
			templist = DrawboardTableOperation.getInstance().queryDrawboardTableWithID(drawID);
			tempmap = templist.get(0);
			cellData.setTemplateName(String.valueOf(tempmap.get("Name")));
			
			//描述
			cellData.setDescriptionText(String.valueOf(map.get("description")));
			
			//上传时间
			cellData.setUploadTime(Long.valueOf(String.valueOf(map.get("uploadtime"))));
			
			//作品id
			worksId = Integer.parseInt(String.valueOf(map.get("id"))) ;
			//作品所属者的id
			userID = Integer.parseInt(String.valueOf(map.get("user_id"))) ;
			
			//该作品的喜欢数量
			templist = LikesTableOperation.getInstance().queryLikesTable(worksId);
			tempmap = templist.get(0);
			cellData.setLikeCount(Integer.parseInt(String.valueOf(tempmap.get("numbers"))));
			
			//该作品的转发数量
			templist = ForwardsTableOperation.getInstance().queryForwardsTableForCount(worksId);
			tempmap = templist.get(0);
			cellData.setForwardCount(Integer.parseInt(String.valueOf(tempmap.get("numbers"))) );
			
			//该作品的评论数量
			templist = CommentsTableOperation.getInstance().queryCommentsTable(worksId);
			tempmap = templist.get(0);
			cellData.setCommentCount(Integer.parseInt(String.valueOf(tempmap.get("numbers"))) );

			//set picture network url 
			BufferedImage bufferImage = getPicSize(cellData);
			cellData.setPicWidth(bufferImage.getWidth());
			cellData.setPicHeight(bufferImage.getHeight());
			cellData.setPicURL(PICDIRPREFIX + cellData.getPicURL().replaceAll("_", "/"));
			
			//picture owner and head icon
			templist = UserTableOperation.getInstance().queryUsersTable(userID);
			tempmap = templist.get(0);
			cellData.setOwner(String.valueOf(tempmap.get("username")));
			cellData.setHeadIcon(PICDIRPREFIX + String.valueOf(tempmap.get("head_icon")).replaceAll("_", "/"));
			
			this.unitData.addElement(cellData);
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
