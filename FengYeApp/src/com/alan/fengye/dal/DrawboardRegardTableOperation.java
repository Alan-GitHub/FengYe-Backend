package com.alan.fengye.dal;

import java.util.List;
import java.util.Map;

public class DrawboardRegardTableOperation {

	private static DrawboardRegardTableOperation instance = null;
	
	public DrawboardRegardTableOperation() {}
	
	public static DrawboardRegardTableOperation getInstance()
	{
		if(null == instance)
		{
			instance = new DrawboardRegardTableOperation();
		}
		
		return instance;
	}
	
	public List<Map<String, Object>> queryDrawboardRegardTableWithUserID(int userID)
	{
		String sql = "select * from drawboard_regard where user_id='" + userID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
}
