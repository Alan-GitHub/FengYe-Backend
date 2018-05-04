package com.alan.fengye.service;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.UserTableOperation;

public class DetailWorksCollecSelectDrawName {

	private Vector<String> drawName;
	
	public DetailWorksCollecSelectDrawName() {
		
		this.drawName = new Vector<String>();
	}
	
	public void getLoginUserAllDrawboardName(String username)
	{
		//拿到登录用户的用户ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserID(userID);
		
		//获取画板名
		for(int i = 0; i < list.size(); i++)
		{
			map = list.get(i);
			
			//拿到画板名字和描述
			String name = String.valueOf(map.get("Name"));
			
			this.drawName.addElement(name);
		}
				
	}
	
	public Vector<String> getDrawName()
	{
		return this.drawName;
	}
}
