package com.alan.fengye.dal;

import java.util.List;
import java.util.Map;

public class InterestGroupTableOperation {


	private static InterestGroupTableOperation instance = null;
	
	public InterestGroupTableOperation() {}
	
	public static InterestGroupTableOperation getInstance()
	{
		if(null == instance)
		{
			instance = new InterestGroupTableOperation();
		}
		
		return instance;
	}
	
	public List<Map<String, Object>> queryInterestGroupTable()
	{
		String sql = "select * from interest_group;";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryInterestGroupTable(String groupName)
	{
		String sql = "select * from interest_group where group_name='" + groupName + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryInterestGroupTable(int id)
	{
		String sql = "select * from interest_group where id='" + id + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
}
