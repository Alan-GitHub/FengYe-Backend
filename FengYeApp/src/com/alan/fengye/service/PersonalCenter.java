package com.alan.fengye.service;

import java.util.List;
import java.util.Map;

import com.alan.fengye.bean.PersonalCenterHeaderData;
import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.InterestGroupRegardTableOperation;
import com.alan.fengye.dal.LikesTableOperation;
import com.alan.fengye.dal.UserRegardTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class PersonalCenter {

	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	
	private PersonalCenterHeaderData headerData;
	
	public PersonalCenter()
	{
		this.headerData = new PersonalCenterHeaderData();
	}
	
	public void getPersonalCenterData(String username)
	{
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		this.headerData.setHeadIconURL(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
		this.headerData.setUsername(String.valueOf(map.get("username")));
		
		//拿到当前用户的画板数量
		list = DrawboardTableOperation.getInstance().queryDrawboardTableForCountWithUserID(userID);
		map = list.get(0);
		this.headerData.setDrawboardNum(Integer.parseInt(String.valueOf(map.get("numbers"))) );
		
		//拿到当前用户上传的作品数量
		list = WorksTableOperation.getInstance().queryWorksTableForCountWithUserID(userID);
		map = list.get(0);
		this.headerData.setCollectionNum(Integer.parseInt(String.valueOf(map.get("numbers"))) );
		
		//拿到当前用户喜欢的作品数量
		list = LikesTableOperation.getInstance().queryLikesTableForCountWithUserID(userID);
		map = list.get(0);
		this.headerData.setLikeNum(Integer.parseInt(String.valueOf(map.get("numbers"))) );
		
		//拿到当前用户关注的数量
		int attenNums = 0;
		list = UserRegardTableOperation.getInstance().queryUserRegardTableForCountWithSelfUserID(userID);
		map = list.get(0);
		attenNums += Integer.parseInt(String.valueOf(map.get("numbers")));
		
		list = InterestGroupRegardTableOperation.getInstance().queryInterestGroupRegardTableForCountWithUserID(userID);
		map = list.get(0);
		attenNums += Integer.parseInt(String.valueOf(map.get("numbers")));
		this.headerData.setAttentionNum(attenNums);
		
		//拿到当前用户的粉丝量
		list = UserRegardTableOperation.getInstance().queryUserRegardTableForCountWithRegardUserID(userID);
		map = list.get(0);
		this.headerData.setFansNum(Integer.parseInt(String.valueOf(map.get("numbers"))));
	}
	
	public int updateUserHeadIcon(String username, String headIconURL) {
		
		int ret = 0;
		
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		String path = String.valueOf(map.get("head_icon"));
	
		if(path.isEmpty()) {
			//路径没有，第一次上传头像
			ret = UserTableOperation.getInstance().updateUsersTable(userID, headIconURL);
		} else if(path.equalsIgnoreCase(headIconURL)) {

			ret = 2; //路径存在，不用替换
		} else {
			//应该永远不会到这里来
			ret = -1;
		}

		return ret;
	}
	
	public PersonalCenterHeaderData getHeaderData()
	{
		return this.headerData;
	}
}
