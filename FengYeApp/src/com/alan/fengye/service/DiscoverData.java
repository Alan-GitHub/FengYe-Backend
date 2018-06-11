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
		int worksId = 0;
		int userID = 0;
		List<Map<String, Object>> templist;
		Map<String, Object> tempmap;
		for(Map<String, Object> map : list)
		{	
			WorksCellData workCell = new WorksCellData();
			
			//路径
			workCell.setPicURL(String.valueOf(map.get("path")));
			
			//拿到画板名
			drawID = Integer.parseInt(String.valueOf(map.get("drawboard_id")));
			templist = DrawboardTableOperation.getInstance().queryDrawboardTableWithID(drawID);
			tempmap = templist.get(0);
			workCell.setTemplateName(String.valueOf(tempmap.get("Name")));
			
			//描述
			workCell.setDescriptionText(String.valueOf(map.get("description")));
			
			//上传时间
			workCell.setUploadTime(Long.valueOf(String.valueOf(map.get("uploadtime"))));
			
			//作品id
			worksId = Integer.parseInt(String.valueOf(map.get("id"))) ;
			//作品所属用户id
			userID = Integer.parseInt(String.valueOf(map.get("user_id"))) ;
			
			//作品的喜欢数量
			templist = LikesTableOperation.getInstance().queryLikesTable(worksId);
			tempmap = templist.get(0);
			workCell.setLikeCount(Integer.parseInt(String.valueOf(tempmap.get("numbers"))));
			
			//作品的转发数量
			templist = ForwardsTableOperation.getInstance().queryForwardsTableForCount(worksId);
			tempmap = templist.get(0);
			workCell.setForwardCount(Integer.parseInt(String.valueOf(tempmap.get("numbers"))) );
			
			templist = CommentsTableOperation.getInstance().queryCommentsTable(worksId);
			tempmap = templist.get(0);
			workCell.setCommentCount(Integer.parseInt(String.valueOf(tempmap.get("numbers"))) );

			//set picture network url 
			BufferedImage bufferImage = getPicSize(workCell.getPicURL().replaceAll("_", "/"));
			workCell.setPicWidth(bufferImage.getWidth());
			workCell.setPicHeight(bufferImage.getHeight());
			workCell.setPicURL(PICDIRPREFIX + workCell.getPicURL().replaceAll("_", "/"));
			
			//picture owner and head icon
			templist = UserTableOperation.getInstance().queryUsersTable(userID);
			tempmap = templist.get(0);
			workCell.setOwner(String.valueOf(tempmap.get("username")));
			workCell.setHeadIcon(PICDIRPREFIX + String.valueOf(tempmap.get("head_icon")).replaceAll("_", "/"));
			
			this.worksUnitData.addElement(workCell);
		}
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
