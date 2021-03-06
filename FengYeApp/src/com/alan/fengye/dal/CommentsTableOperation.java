package com.alan.fengye.dal;

import java.util.List;
import java.util.Map;

public class CommentsTableOperation {

	private static CommentsTableOperation instance = null;
	
	public CommentsTableOperation() {}
	
	public static CommentsTableOperation getInstance()
	{
		if(null == instance)
		{
			instance = new CommentsTableOperation();
		}
		
		return instance;
	}
	
	public List<Map<String, Object>> queryCommentsTable(int worksId)
	{
		String sql = "select count(*) as numbers from comments where works_id='" + worksId + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryCommentsTableWithWorksID(int worksId)
	{
		String sql = "select * from comments where works_id='" + worksId + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public int updateCommentsTableAddComment(String commentContent, int commentUserID, int workID, long curTime) 
	{
		
		String sql = "insert into comments (works_id, comment_content, comment_time, user_id) values('" 
					+ workID + "', '" + commentContent + "', '" + curTime + "', '"+ commentUserID + "');";

		int ret = OperateDataBase.getInstance().generalUpdate(sql);	
		
		return ret;
	}
}
