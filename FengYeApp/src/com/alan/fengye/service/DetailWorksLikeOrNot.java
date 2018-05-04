package com.alan.fengye.service;

import java.util.List;
import java.util.Map;

import com.alan.fengye.dal.LikesTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class DetailWorksLikeOrNot {

	private int retValue;
	private int likeNum;
	
	public DetailWorksLikeOrNot(){
		
		this.retValue = 0;
		this.likeNum = 0;
	}
	
	public void updateWorksLikeOrNot(String username, String picURL, String isLike)
	{
		//拿到登录用户的用户ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到图片作品的ID
		list = WorksTableOperation.getInstance().queryWorksTableWithPath(picURL);
		map = list.get(0);
		int worksID = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		//存储操作返回值
		this.retValue = LikesTableOperation.getInstance().updateLikesTable(userID, worksID, isLike);
		
		//重新获取作品的喜欢数量
		list = LikesTableOperation.getInstance().queryLikesTable(worksID);
		map = list.get(0);
		this.likeNum = Integer.parseInt(String.valueOf(map.get("numbers")));
	}

	public int getRetValue(){
		
		return this.retValue;
	}
	
	public int getLikeNum() {
		
		return this.likeNum;
	}
}
