package com.alan.fengye.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class WorksTableOperation {

	private static WorksTableOperation instance = null;
	
	public WorksTableOperation() {}
	
	public static WorksTableOperation getInstance()
	{
		if(null == instance)
		{
			instance = new WorksTableOperation();
		}
		
		return instance;
	}
	
	public List<Map<String, Object>> queryWorksTable()
	{
		String sql = "select * from works;";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryWorksTable(int userID)
	{
		String sql = "select * from works where user_id='" + userID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryWorksTableForCountWithUserID(int userID)
	{
		String sql = "select count(*) as numbers from works where user_id='" + userID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryWorksTableWithID(int id)
	{
		String sql = "select * from works where id='" + id + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryWorksTableWithPath(String path)
	{
		String sql = "select * from works where path='" + path + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	//不考虑一个画板中出现多张同样的图片情况
	public List<Map<String, Object>> queryWorksTableWithPathAndDrawID(String path, int drawID)
	{
		String sql = "select * from works where path='" + path + "' && drawboard_id='" + drawID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryWorksTable(String picURL)
	{
		String sql = "select * from works where path='" + picURL + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryWorksTableForConver(int drawboardID)
	{
		String sql = "select * from works where drawboard_id='" + drawboardID + "' limit 0,1;";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryWorksTableForSpecifyDrawb(int drawboardID)
	{
		String sql = "select * from works where drawboard_id='" + drawboardID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryWorksTableForCount(int drawboardID)
	{
		String sql = "select count(*) as numbers from works where drawboard_id='" + drawboardID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public int updateWorksTable(int userID, int drawID, String picURL, String picDesc, long curTime)
	{
		String sql = "insert into works (path, drawboard_id, description, uploadtime, user_id) "
								+ "values('" + picURL + "', '" + drawID + "', '" + picDesc + "', '" + curTime + "', '" + userID + "');";
	
		int ret = OperateDataBase.getInstance().generalUpdate(sql);	
		
		return ret;
	}
	
	public int updateWorksTableWithDesc(int worksID, String picDesc)
	{
		String sql = "update works set description ='" + picDesc + "' where id ='" + worksID + "';";
		int ret = OperateDataBase.getInstance().generalUpdate(sql);	
		
		return ret;
	}
}
