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
	
	public List<Map<String, Object>> queryUserRegardTableWithRegardIDAndSelfID(int regardID, int selfID)
	{
		String sql = "select * from user_regard where regard_user_id='" + regardID + "' && self_user_id='" + selfID +"';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public int updateUserRegardTableAddItem(int loginUserID, int otherUserID, long curTime)
	{
		String sql = "insert into user_regard (regard_user_id, self_user_id, regard_time) values('" 
				+ otherUserID + "', '" + loginUserID + "', '" + curTime + "');";

		int ret = OperateDataBase.getInstance().generalUpdate(sql);	
		
		return ret;
	}
	
	public int updateUserRegardTableDelItem(int loginUserID, int otherUserID)
	{
		String sql = "delete from user_regard where regard_user_id='" + otherUserID + "' && self_user_id='" + loginUserID + "';";

		int ret = OperateDataBase.getInstance().generalUpdate(sql);	
		
		return ret;
	}
}
