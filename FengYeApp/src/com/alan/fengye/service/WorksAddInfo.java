package com.alan.fengye.service;

import java.util.List;
import java.util.Map;

import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class WorksAddInfo {

	public WorksAddInfo() {}
	
	public int updateDataBaseAddInfoForWorks(String ownUser, String ownDrawbard, String picURL, String picDesc)
	{
		int retValue = 0;
		
		//�õ���Ʒ�����û���ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(ownUser);
		Map<String, Object> map = list.get(0);
		int ownUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//�õ���Ʒ���������ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(ownUserID, ownDrawbard);
		map = list.get(0);
		int ownDrawID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//�õ�ͼƬ����ƷID
		list = WorksTableOperation.getInstance().queryWorksTableWithPathAndDrawID(picURL, ownDrawID);
		map = list.get(0);
		int worksID = Integer.parseInt(String.valueOf(map.get("id")));
		
		retValue = WorksTableOperation.getInstance().updateWorksTableWithDesc(worksID, picDesc);
		
		return retValue;
	}
}
