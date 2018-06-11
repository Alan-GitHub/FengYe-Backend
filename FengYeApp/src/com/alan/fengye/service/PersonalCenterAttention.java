package com.alan.fengye.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.PersonalCenterAttentionDrawboardCellData;
import com.alan.fengye.bean.PersonalCenterAttentionInterestGroupCellData;
import com.alan.fengye.bean.PersonalCenterAttentionUserCellData;
import com.alan.fengye.dal.DrawboardRegardTableOperation;
import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.InterestGroupRegardTableOperation;
import com.alan.fengye.dal.InterestGroupTableOperation;
import com.alan.fengye.dal.UserRegardTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class PersonalCenterAttention {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private final static String PICREALPATH = "C:\\Users\\Alan\\Desktop\\p";
	
	private Vector<PersonalCenterAttentionInterestGroupCellData> interestGroupUnitData;
	private Vector<PersonalCenterAttentionDrawboardCellData> drawboardUnitData;
	private Vector<PersonalCenterAttentionUserCellData> userCellData;
	
	public PersonalCenterAttention()
	{
		this.interestGroupUnitData = new Vector<PersonalCenterAttentionInterestGroupCellData>();
		this.drawboardUnitData = new Vector<PersonalCenterAttentionDrawboardCellData>();
		this.userCellData = new Vector<PersonalCenterAttentionUserCellData>();
	}
	
	public void getPersonalCenterAttentionData(String username)
	{
		//get interest group unit data
		getInterestGroupUnitData(username);
		
		//get drawboard unit data
		getDrawboardUnitData(username);
		
		//get regard user data
		getUserCellData(username);
	}
	
	private void getUserCellData(String username) 
	{
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id"))) ;

		list = UserRegardTableOperation.getInstance().queryUserRegardTableWithSelfUserID(userID);

		//获取关注用户的数据
		int regardUserID;
		List<Map<String, Object>> listTemp;
		for(int i = 0; i < list.size(); i++)
		{
			PersonalCenterAttentionUserCellData userCell = new PersonalCenterAttentionUserCellData();	
			
			map = list.get(i);
			regardUserID = Integer.parseInt(String.valueOf(map.get("regard_user_id"))) ;
			
			listTemp = UserTableOperation.getInstance().queryUsersTable(regardUserID);
			map = listTemp.get(0);

			//拿到关注用户的头像URL和用户名
			userCell.setUserHeadIcon(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
			userCell.setUserName(String.valueOf(map.get("username")));
	
			//拿到关注用户的粉丝量
			listTemp = UserRegardTableOperation.getInstance().queryUserRegardTableForCountWithRegardUserID(regardUserID);
			map = listTemp.get(0);
			userCell.setFansNums(Integer.parseInt(String.valueOf(map.get("numbers"))));
			
			this.userCellData.addElement(userCell);		
		}
	}
	
	private void getDrawboardUnitData(String username)
	{
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id"))) ;
	
		list = DrawboardRegardTableOperation.getInstance().queryDrawboardRegardTableWithUserID(userID);
		
		//获取画板数据
		int drawID;
		List<Map<String, Object>> listTemp;
		for(int i = 0; i < list.size(); i++)
		{
			PersonalCenterAttentionDrawboardCellData drawboardCell = new PersonalCenterAttentionDrawboardCellData();
			
			map = list.get(i);
			drawID = Integer.parseInt(String.valueOf(map.get("drawboard_id"))) ;
			
			listTemp = DrawboardTableOperation.getInstance().queryDrawboardTable(drawID);
			map = listTemp.get(0);

			//拿到画板的名字和描述
			drawboardCell.setDrawboardName(String.valueOf(map.get("Name")));
			drawboardCell.setDescriptionText(String.valueOf(map.get("description")));
			
			//拿到画板所属用户的名字和头像
			int drawOwnerUserID = Integer.parseInt(String.valueOf(map.get("user_id")));
			listTemp = UserTableOperation.getInstance().queryUsersTable(drawOwnerUserID);
			map = listTemp.get(0);
			drawboardCell.setOwnerUserName(String.valueOf(map.get("username")));
			drawboardCell.setOwnerHeadIcon(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
			
			//拿到画板的封面图像---画板内部第一张图片
			listTemp = WorksTableOperation.getInstance().queryWorksTableForConver(drawID);
			map = listTemp.get(0);
			drawboardCell.setCoverImageURL(PICDIRPREFIX + String.valueOf(map.get("path")).replaceAll("_", "/"));
			//画板封面图像的宽和高
			BufferedImage bufferImage;
			bufferImage = getPicSize(String.valueOf(map.get("path")).replaceAll("_", "\\\\"));
			drawboardCell.setCoverImageWidth(bufferImage.getWidth());
			drawboardCell.setCoverImageHeight(bufferImage.getHeight());
			
			//画板包含的图片数量
			listTemp = WorksTableOperation.getInstance().queryWorksTableForCount(drawID);
			map = listTemp.get(0);
			drawboardCell.setPicNums(Integer.parseInt(String.valueOf(map.get("numbers"))));
			
			//画板被关注的数量(画板的粉丝量) --- 预留给后续实现
			
			this.drawboardUnitData.addElement(drawboardCell); 
		}		
	}
	
	//拿到关注的兴趣组数据完成
	private void getInterestGroupUnitData(String username)
	{
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id"))) ;

		list = InterestGroupRegardTableOperation.getInstance().queryInterestGroupRegardTableWithUserID(userID);
		
		//获取兴趣组数据
		int groupID;
		List<Map<String, Object>> listTemp;
		for(int i = 0; i < list.size(); i++)
		{
			PersonalCenterAttentionInterestGroupCellData itGroup = new PersonalCenterAttentionInterestGroupCellData();
			
			map = list.get(i);
			groupID = Integer.parseInt(String.valueOf(map.get("group_id"))) ;
			
			listTemp = InterestGroupTableOperation.getInstance().queryInterestGroupTable(groupID);
			map = listTemp.get(0);

			BufferedImage bufferImage;
			itGroup.setInterestGroupName(String.valueOf(map.get("group_name")));
			itGroup.setInterestGroupDesc(String.valueOf(map.get("description")));
			itGroup.setCoverImageURL(PICDIRPREFIX + String.valueOf(map.get("cover_image_path")));
				
			bufferImage = getPicSize(String.valueOf(map.get("cover_image_path")));
			itGroup.setCoverImageWidth(bufferImage.getWidth());
			itGroup.setCoverImageHeight(bufferImage.getHeight());
		
			this.interestGroupUnitData.addElement(itGroup);
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
	
	public Vector<PersonalCenterAttentionInterestGroupCellData> getInterestGroupUnitData() 
	{
		return this.interestGroupUnitData;
	}
	
	public Vector<PersonalCenterAttentionDrawboardCellData> getDrawboardUnitData()
	{
		return this.drawboardUnitData;
	}
	
	public Vector<PersonalCenterAttentionUserCellData> getUserCellData()
	{
		return this.userCellData;
	}
}
