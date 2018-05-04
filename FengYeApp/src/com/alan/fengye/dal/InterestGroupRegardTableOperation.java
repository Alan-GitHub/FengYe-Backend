package com.alan.fengye.dal;

import java.util.List;
import java.util.Map;

public class InterestGroupRegardTableOperation {

	private static InterestGroupRegardTableOperation instance = null;
	
	public InterestGroupRegardTableOperation() {}
	
	public static InterestGroupRegardTableOperation getInstance()
	{
		if(null == instance)
		{
			instance = new InterestGroupRegardTableOperation();
		}
		
		return instance;
	}
	
	public List<Map<String, Object>> queryInterestGroupRegardTable(int groupID)
	{
		String sql = "select * from interest_group_regard where group_id=" + groupID + ";";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryInterestGroupRegardTableWithUserID(int userID)
	{
		String sql = "select * from interest_group_regard where user_id=" + userID + ";";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryInterestGroupRegardTableForCountWithUserID(int userID)
	{
		String sql = "select count(*) as numbers from interest_group_regard where user_id=" + userID + ";";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
}
