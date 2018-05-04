package com.alan.fengye.dal;

import java.util.List;
import java.util.Map;

public class ForwardsTableOperation {

	private static ForwardsTableOperation instance = null;
	
	public ForwardsTableOperation() {}
	
	public static ForwardsTableOperation getInstance()
	{
		if(null == instance)
		{
			instance = new ForwardsTableOperation();
		}
		
		return instance;
	}
	
	public List<Map<String, Object>> queryForwardsTableForCount(int worksId)
	{
		String sql = "select count(*) as numbers from (select distinct works_id,from_drawboard_id,to_drawboard_id from forwards where works_id='" + worksId + "') A;";
		 
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryForwardsTable(int worksId)
	{
		String sql = "select distinct works_id,from_drawboard_id,to_drawboard_id from forwards where works_id='" + worksId + "';";
		 
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public int updateForwardsTable(int workID, int fromDrawID, int toDrawID)
	{
		String sql = "insert into forwards (works_id, from_drawboard_id, to_drawboard_id) "
				+ "values('" + workID + "', '" + fromDrawID + "', '" + toDrawID + "');";

		int ret = OperateDataBase.getInstance().generalUpdate(sql);	
		
		return ret;
	}
}
