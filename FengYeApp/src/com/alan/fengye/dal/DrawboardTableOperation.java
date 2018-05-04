package com.alan.fengye.dal;

import java.util.List;
import java.util.Map;

import com.alan.fengye.enums.DataBaseReturnCode;

public class DrawboardTableOperation {

	private static DrawboardTableOperation instance = null;
	
	public DrawboardTableOperation() {}
	
	public static DrawboardTableOperation getInstance()
	{
		if(null == instance)
		{
			instance = new DrawboardTableOperation();
		}
		
		return instance;
	}
	
	public List<Map<String, Object>> queryDrawboardTable(int id)
	{
		String sql = "select * from drawboard where id='" + id + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryDrawboardTableWithUserID(int UserID)
	{
		String sql = "select * from drawboard where user_id='" + UserID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryDrawboardTableWithID(int id)
	{
		String sql = "select * from drawboard where id='" + id + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryDrawboardTableWithUserIDAndDrawName(int UserID, String drawName)
	{
		String sql = "select * from drawboard where user_id='" + UserID + "' && Name='" + drawName + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryDrawboardTableForCountWithUserID(int UserID)
	{
		String sql = "select count(*) as numbers from drawboard where user_id='" + UserID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list; 
	}
	
	public DataBaseReturnCode insertDrawboardTable(int userID, String dbName, String dbDesc)
	{
		String sql = "insert into drawboard (user_id, Name, description) values('" + userID + "', '" + dbName + "', '" + dbDesc + "');";

		int ret = OperateDataBase.getInstance().generalUpdate(sql);	
		
		if(ret > 0) //≤Â»Îª≠∞Â≥…π¶
		{
			return DataBaseReturnCode.DRAWBOARD_INSERT_SUCCESS;
		}
		else
		{
			return DataBaseReturnCode.DRAWBOARD_INSERT_FAILURE;
		}
	}
}
