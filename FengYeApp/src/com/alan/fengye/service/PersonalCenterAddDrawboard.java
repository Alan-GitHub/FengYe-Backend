package com.alan.fengye.service;

import java.util.List;
import java.util.Map;

import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.enums.DataBaseReturnCode;

public class PersonalCenterAddDrawboard {

	public PersonalCenterAddDrawboard() {}
	
	public DataBaseReturnCode addDrawboard(String username, String dbName, String dbDesc)
	{
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
	
		int userID = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		return DrawboardTableOperation.getInstance().insertDrawboardTable(userID, dbName, dbDesc);
	}
}
