package com.alan.fengye.service;

import java.util.List;
import java.util.Map;

import com.alan.fengye.bean.InterestGroupDetailUnit;
import com.alan.fengye.dal.DrawboardRegardTableOperation;
import com.alan.fengye.dal.InterestGroupRegardTableOperation;
import com.alan.fengye.dal.InterestGroupTableOperation;
import com.alan.fengye.dal.UserTableOperation;

public class InterestGroupDetailData {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	
	InterestGroupDetailUnit groupDetail;
	
	public InterestGroupDetailData(){
		
		this.groupDetail = new InterestGroupDetailUnit();
	}
	
	public void getInterestGroupDetailData(String groupName, String loginUser) {

		//拿到兴趣组id
		List<Map<String, Object>> list = InterestGroupTableOperation.getInstance().queryInterestGroupTable(groupName);
		Map<String, Object> map = list.get(0);
		int groupId = Integer.parseInt(String.valueOf(map.get("id"))) ;
	
		if(loginUser != null && loginUser.length() != 0)
		{
			//拿到登录用户id
			list = UserTableOperation.getInstance().queryUsersTable(loginUser);
			map = list.get(0);
			int loginUserID = Integer.parseInt(String.valueOf(map.get("id")));
			
			list = InterestGroupRegardTableOperation.getInstance().queryInterestGroupRegardTableWithGroupIDAndUserID(groupId, loginUserID);
			if(list.size() > 0)
				this.groupDetail.setIsAttention(true);
			else
				this.groupDetail.setIsAttention(false);
		}
	
		list = InterestGroupRegardTableOperation.getInstance().queryInterestGroupRegardTable(groupId);
		this.groupDetail.setNumsOfRegardUser(list.size());

		for(Map<String, Object> map1 : list)
		{
			int userId = Integer.parseInt(String.valueOf(map1.get("user_id"))) ;
	
			List<Map<String, Object>> list1 = UserTableOperation.getInstance().queryUsersTable(userId);
			this.groupDetail.getHeadIconOfRegardUser().addElement(PICDIRPREFIX + String.valueOf(list1.get(0).get("head_icon")).replaceAll("_", "/"));
		}
	}
	
	public int updateDataBaseRegardOrNotInterestGroup(String loginUser, String groupName, String yesOrNot)
	{
		int retValue = 0;
		
		//拿到登录用户的用户ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(loginUser);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到兴趣组id
		list = InterestGroupTableOperation.getInstance().queryInterestGroupTable(groupName);
		map = list.get(0);
		int groupId = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		if(yesOrNot.equals("1"))
		{
			retValue = InterestGroupRegardTableOperation.getInstance().updateInterestGroupRegardTableAddItem(groupId, userID);
		}else{
			
			retValue = InterestGroupRegardTableOperation.getInstance().updateInterestGroupRegardTableDelItem(groupId, userID);
		}
		
		return retValue;
	}
	
	public InterestGroupDetailUnit getGroupDetail() {
		
		return this.groupDetail;
	}
}
