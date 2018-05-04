package com.alan.fengye.dal;

import java.util.List;
import java.util.Map;

public class AssociationTableOperation_Del {

	private static AssociationTableOperation_Del instance = null;
	
	public AssociationTableOperation_Del() {}
	
	public static AssociationTableOperation_Del getInstance()
	{
		if(null == instance)
		{
			instance = new AssociationTableOperation_Del();
		}
		
		return instance;
	}
	
	public List<Map<String, Object>> queryAssociationTable(int worksId)
	{
		String sql = "select * from association where works_id='" + worksId + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
}

