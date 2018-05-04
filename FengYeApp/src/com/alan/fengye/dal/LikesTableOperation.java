package com.alan.fengye.dal;

import java.util.List;
import java.util.Map;

public class LikesTableOperation {

	private static LikesTableOperation instance = null;
	
	public LikesTableOperation() {}
	
	public static LikesTableOperation getInstance()
	{
		if(null == instance)
		{
			instance = new LikesTableOperation();
		}
		
		return instance;
	}
	
	public List<Map<String, Object>> queryLikesTable(int worksId)
	{
		String sql = "select count(*) as numbers from likes where works_id='" + worksId + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryLikesTableWithUserID(int userID)
	{
		String sql = "select * from likes where user_id='" + userID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryLikesTableWithWorksID(int worksID)
	{
		String sql = "select * from likes where works_id='" + worksID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryLikesTableForCountWithUserID(int userID)
	{
		String sql = "select count(*) as numbers from likes where user_id='" + userID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public int updateLikesTable(int userID, int workID, String isLike)
	{
		String sql = "";
		
		if(isLike.equalsIgnoreCase("yes"))  //喜欢某作品
			sql = "insert into likes (works_id, user_id) values('" + workID + "', '" + userID + "');";
		else  //取消喜欢
		    sql = "delete from likes where works_id='" + workID + "' && user_id='" + userID + "';";

		int ret = OperateDataBase.getInstance().generalUpdate(sql);	
		
		return ret;
	}
	
	
}
