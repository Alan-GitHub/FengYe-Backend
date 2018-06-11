package com.alan.fengye.service;

import java.util.List;
import java.util.Map;

import com.alan.fengye.dal.DrawboardRegardTableOperation;
import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.UserRegardTableOperation;
import com.alan.fengye.dal.UserTableOperation;

public class AttentionCommon {

	public AttentionCommon() {}
	
	public int updateDataBaseRegardOrNotUser(String loginUser, String otherUser, String yesOrNot, long curTime)
	{
		int retValue = 0;
		
		//拿到登录用户的ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(loginUser);
		Map<String, Object> map = list.get(0);
		int loginUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到被关注用户的ID
		list = UserTableOperation.getInstance().queryUsersTable(otherUser);
		map = list.get(0);
		int otherUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		if(yesOrNot.equals("1"))
		{
			retValue = UserRegardTableOperation.getInstance().updateUserRegardTableAddItem(loginUserID, otherUserID, curTime);
		}else{
			
			retValue = UserRegardTableOperation.getInstance().updateUserRegardTableDelItem(loginUserID, otherUserID);
		}
	
		return retValue;
	}
	
	public int updateDataBaseRegardOrNotDrawboard(String drawName, String drawOwnerUser, String loginUser, String yesOrNot)
	{
		int retValue = 0;
		
		//拿到被关注画板所属用户的ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(drawOwnerUser);
		Map<String, Object> map = list.get(0);
		int drawOwnerUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到画板id
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(drawOwnerUserID, drawName);
		map = list.get(0);
		int drawID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到登录用户的ID
		list = UserTableOperation.getInstance().queryUsersTable(loginUser);
		map = list.get(0);
		int loginUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		if(yesOrNot.equals("1"))
		{
			retValue = DrawboardRegardTableOperation.getInstance().updateDrawboardRegardTableAddItem(drawID, loginUserID);
		}else{
			
			retValue = DrawboardRegardTableOperation.getInstance().updateDrawboardRegardTableDelItem(drawID, loginUserID);
		}
		
		return retValue;
	}
}
