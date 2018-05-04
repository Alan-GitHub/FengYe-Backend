package com.alan.fengye.service;

import java.util.List;
import java.util.Map;

import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.ForwardsTableOperation;
import com.alan.fengye.dal.LikesTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class DetailWorksCollecAction {
	
	private int retValue;
	private int forwardNum;
	
	public DetailWorksCollecAction() {
		
		this.retValue = 0;
		this.forwardNum = 0;
	}
	
	public void collectionPicture(String origUsername, String origDrawName, String username, String drawName, String picURL, String picDesc) {
		
		//拿到登录用户的用户ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到收留采集来的图片的画板ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(userID, drawName);
		map = list.get(0);
		int toDrawID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//插入数据到works表中   转采相当于自己从别人那里拷贝图片上传到后台
		this.retValue = WorksTableOperation.getInstance().updateWorksTable(userID, toDrawID, picURL, picDesc);
		
		if(this.retValue == 0)
			return;
		
		////////////// 插入数据到forwards表中   记录转采事件 ////////////////////
		
		//1. 拿到图片作品原先所属用户的用户ID
		list = UserTableOperation.getInstance().queryUsersTable(origUsername);
		map = list.get(0);
		int origUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//2. 拿到原来所属的画板ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(origUserID, origDrawName);
		map = list.get(0);
		int origDrawID = Integer.parseInt(String.valueOf(map.get("id")));
	
		//3. 拿到图片被转采之前的作品ID
		list = WorksTableOperation.getInstance().queryWorksTableWithPathAndDrawID(picURL, origDrawID);
		map = list.get(0);
		int worksID = Integer.parseInt(String.valueOf(map.get("id")));
	
		//4. 再插入数据
		this.retValue = ForwardsTableOperation.getInstance().updateForwardsTable(worksID, origDrawID, toDrawID);
		
		//重新获取转采的数量
		list = ForwardsTableOperation.getInstance().queryForwardsTableForCount(worksID);
		map = list.get(0);
		this.forwardNum = Integer.parseInt(String.valueOf(map.get("numbers")));
	}
	
	public int getRetValue(){
		
		return this.retValue;
	}
	
	public int getForwardNum() {
		
		return this.forwardNum;
	}
}
