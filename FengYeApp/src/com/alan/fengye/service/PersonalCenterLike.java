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

public class PersonalCenterLike {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private final static String PICREALPATH = "C:\\Users\\Alan\\Desktop\\p";
	
	private Vector<WorksCellData> likeCellData;
			
	public PersonalCenterLike() 
	{
		this.likeCellData = new Vector<WorksCellData>();
	}
	
	public void getLikeCellData(String username)
	{
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		list = LikesTableOperation.getInstance().queryLikesTableWithUserID(userID);
		
		int worksID;
		int drawID = 0;
		List<Map<String, Object>> listTemp;
		for(int i = 0; i < list.size(); i++)
		{
			WorksCellData cellData = new WorksCellData();
			
			map = list.get(i);
			
			//拿到图片的id
			worksID = Integer.parseInt(String.valueOf(map.get("works_id")));
			listTemp = WorksTableOperation.getInstance().queryWorksTableWithID(worksID);
			map = listTemp.get(0);
			
			//拿到图片的描述、上传时间
			cellData.setDescriptionText(String.valueOf(map.get("description")));
			cellData.setUploadTime(Long.valueOf(String.valueOf(map.get("uploadtime"))));
			
			//拿到图片网络URL和宽高
			cellData.setPicURL(PICDIRPREFIX + String.valueOf(map.get("path")).replaceAll("_", "/"));
			BufferedImage bufferImage = getPicSize(String.valueOf(map.get("path")).replaceAll("_", "\\\\"));
			cellData.setPicWidth(bufferImage.getWidth());
			cellData.setPicHeight(bufferImage.getHeight());
			
			//拿到该图片的上传者ID
			userID = Integer.parseInt(String.valueOf(map.get("user_id")));
			
			//拿到图片所属的画板的名字
			drawID = Integer.parseInt(String.valueOf(map.get("drawboard_id")));
			listTemp = DrawboardTableOperation.getInstance().queryDrawboardTableWithID(drawID);
			map = listTemp.get(0);
			cellData.setTemplateName(String.valueOf(map.get("Name")));
			
			//拿到该图片的喜欢数量
			listTemp = LikesTableOperation.getInstance().queryLikesTable(worksID);
			map = listTemp.get(0);
			cellData.setLikeCount(Integer.parseInt(String.valueOf(map.get("numbers"))));
			
			//拿到该图片的转发量
			listTemp = ForwardsTableOperation.getInstance().queryForwardsTableForCount(worksID);
			map = listTemp.get(0);
			cellData.setForwardCount(Integer.parseInt(String.valueOf(map.get("numbers"))) );
			
			//拿到该图片的评论量
			listTemp = CommentsTableOperation.getInstance().queryCommentsTable(worksID);
			map = listTemp.get(0);
			cellData.setCommentCount(Integer.parseInt(String.valueOf(map.get("numbers"))) );

			//拿到该图片上传者的用户名和头像
			listTemp = UserTableOperation.getInstance().queryUsersTable(userID);
			map = listTemp.get(0);
			cellData.setOwner(String.valueOf(map.get("username")));
			cellData.setHeadIcon(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
			
			this.likeCellData.addElement(cellData);
		}

		//print
//		for(WorksCellData cell : this.likeCellData)
//		{
//			System.out.println("\n-------------- PersonalCenterLike -------");
//			System.out.println(cell.getPicURL());
//			System.out.println(cell.getPicWidth());
//			System.out.println(cell.getPicHeight());
//			System.out.println(cell.getUploadTime());
//			System.out.println(cell.getForwardCount());
//			System.out.println(cell.getLikeCount());
//			System.out.println(cell.getCommentCount());
//			System.out.println(cell.getDescriptionText());
//			System.out.println(cell.getHeadIcon());
//			System.out.println(cell.getTemplateName());
//			System.out.println(cell.getOwner());
//		}
	}
	
	private BufferedImage getPicSize(String picPath)
	{
		//Get the picture width and height
		File file = new File(PICREALPATH + "\\" + picPath);
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
	
	public Vector<WorksCellData> getLikeCellData()
	{
		return this.likeCellData; 
	}
}
