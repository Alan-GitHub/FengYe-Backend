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
	
	public List<Map<String, Object>> queryDrawboardRegardTableWithDrawIDForCount(int DrawID)
	{
		String sql = "select count(*) as numbers from drawboard_regard where drawboard_id='" + DrawID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryDrawboardRegardTableWithDrawIDAndUserID(int drawID, int userID)
	{
		String sql = "select * from drawboard_regard where drawboard_id='" + drawID + "' && user_id='" + userID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public int updateDrawboardRegardTableAddItem(int drawID, int userID)
	{
		String sql = "insert into drawboard_regard (drawboard_id, user_id) values('" 
				+ drawID + "', '" + userID + "');";

		int ret = OperateDataBase.getInstance().generalUpdate(sql);	
		
		return ret;
	}
	
	public int updateDrawboardRegardTableDelItem(int drawID, int userID)
	{
		String sql = "delete from drawboard_regard where drawboard_id='" + drawID + "' && user_id='" + userID + "';";

		int ret = OperateDataBase.getInstance().generalUpdate(sql);	
		
		return ret;
	}
}
