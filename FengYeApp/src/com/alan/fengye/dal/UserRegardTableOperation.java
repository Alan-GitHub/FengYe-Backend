package com.alan.fengye.dal;

import java.util.List;
import java.util.Map;

public class UserRegardTableOperation {

	private static UserRegardTableOperation instance = null;
	
	public UserRegardTableOperation() {}
	
	public static UserRegardTableOperation getInstance()
	{
		if(null == instance)
		{
			instance = new UserRegardTableOperation();
		}
		
		return instance;
	}
	
	public List<Map<String, Object>> queryUserRegardTableWithSelfUserID(int selfUserID)
	{
		String sql = "select * from user_regard where self_user_id='" + selfUserID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryUserRegardTableForCountWithSelfUserID(int selfUserID)
	{
		String sql = "select count(*) as numbers from user_regard where self_user_id='" + selfUserID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryUserRegardTableWithRegardUserID(int regardUserID)
	{
		String sql = "select * from user_regard where regard_user_id='" + regardUserID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryUserRegardTableForCountWithRegardUserID(int regardUserID)
	{
		String sql = "select count(*) as numbers from user_regard where regard_user_id='" + regardUserID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
}
