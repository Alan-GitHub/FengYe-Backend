package com.alan.fengye.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.PersonalCenterDrawboardCell;
import com.alan.fengye.bean.WorksCellData;
import com.alan.fengye.dal.CommentsTableOperation;
import com.alan.fengye.dal.DrawboardRegardTableOperation;
import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.ForwardsTableOperation;
import com.alan.fengye.dal.LikesTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class PersonalCenterDetailDrawboard {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private final static String PICREALPATH = "C:\\Users\\Alan\\Desktop\\p";
	
	private Vector<WorksCellData> detailDrawbData;
	private boolean isAttention;
	
	public PersonalCenterDetailDrawboard()
	{
		this.detailDrawbData = new Vector<WorksCellData>();
		isAttention = false;
	} 
	
	public void getPersonalCenterDetailDrawboardData(String otherUser, String loginUser, String drawName)
	{
		//当前用户的用户ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(otherUser);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		//拿到画板ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(userID, drawName);
		map = list.get(0);
		int drawboardID = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		list = WorksTableOperation.getInstance().queryWorksTableForSpecifyDrawb(drawboardID);
		
		//获取数据
		List<Map<String, Object>> listTemp;
		int worksID = 0;
		for(int i = 0; i < list.size(); i++)
		{
			WorksCellData detailDrawb = new WorksCellData();
			
			map = list.get(i);

			//拿到图片路径
			detailDrawb.setPicURL(PICDIRPREFIX + String.valueOf(map.get("path")).replaceAll("_", "/"));

			//拿到图片的描述
			detailDrawb.setDescriptionText(String.valueOf(map.get("description")));
			
			//计算图片的宽高
			BufferedImage bufferImage = getPicSize(String.valueOf(map.get("path")).replaceAll("_", "/"));
			detailDrawb.setPicWidth(bufferImage.getWidth());
			detailDrawb.setPicHeight(bufferImage.getHeight());
			
			//拿到图片上传时间
			detailDrawb.setUploadTime(Long.valueOf(String.valueOf(map.get("uploadtime"))));
			
			//拿到图片ID
			worksID = Integer.parseInt(String.valueOf(map.get("id")));
			
			//拿到画板名字
			listTemp = DrawboardTableOperation.getInstance().queryDrawboardTableWithID(drawboardID);
			map = listTemp.get(0);
			detailDrawb.setTemplateName(String.valueOf(map.get("Name")));
			
			//拿到作品的喜欢量
			listTemp = LikesTableOperation.getInstance().queryLikesTable(worksID);
			map = listTemp.get(0);
			detailDrawb.setLikeCount(Integer.parseInt(String.valueOf(map.get("numbers"))));
			
			//拿到作品的转发量
			listTemp = ForwardsTableOperation.getInstance().queryForwardsTableForCount(worksID);
			map = listTemp.get(0);
			detailDrawb.setForwardCount(Integer.parseInt(String.valueOf(map.get("numbers"))) );
			
			//拿到作品的评论量
			listTemp = CommentsTableOperation.getInstance().queryCommentsTable(worksID);
			map = listTemp.get(0);
			detailDrawb.setCommentCount(Integer.parseInt(String.valueOf(map.get("numbers"))) );
			
			//拿到用户名
			listTemp = UserTableOperation.getInstance().queryUsersTable(userID);
			map = listTemp.get(0);
			detailDrawb.setOwner(String.valueOf(map.get("username")));
			
			//拿到用户头像
			detailDrawb.setHeadIcon(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
			
			this.detailDrawbData.addElement(detailDrawb);
		}
		
		//最后一步：查看该模板是否被登录用户所关注
		//登录用户的用户ID
		list = UserTableOperation.getInstance().queryUsersTable(loginUser);
		map = list.get(0);
		int loginUserID = Integer.parseInt(String.valueOf(map.get("id")));

		list = DrawboardRegardTableOperation.getInstance().queryDrawboardRegardTableWithDrawIDAndUserID(drawboardID, loginUserID);
		
		if(list.size() > 0)
			this.isAttention = true;
		else
			this.isAttention = false;
	}
	
	private BufferedImage getPicSize(String picURL)
	{
		//Get the picture width and height
		File file = new File(PICREALPATH + "\\" + picURL);
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
	
	public int updateDatabaseAddPicture(String username, String drawName, String filePath, String picDesc, long curTime) 
	{
		int retValue = 0;
		
		//拿到登录用户的用户ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到画板ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(userID, drawName);
		map = list.get(0);
		int drawID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//插入works表
		retValue = WorksTableOperation.getInstance().updateWorksTable(userID, drawID, filePath, picDesc, curTime);
		
		return retValue;
	}
	
	public Vector<WorksCellData> getDetailDrawbData(){
		
		return this.detailDrawbData;
	}
	
	public boolean getIsAttention(){
		
		return this.isAttention;
	}
}
