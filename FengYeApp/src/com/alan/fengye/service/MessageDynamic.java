package com.alan.fengye.service;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.CommentMessageData;
import com.alan.fengye.dal.UserRegardTableOperation;
import com.alan.fengye.dal.UserTableOperation;

public class MessageDynamic {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private Vector<CommentMessageData> allDynMsg;
	
	public MessageDynamic()
	{
		this.allDynMsg = new Vector<CommentMessageData>();
	}
	
	public void getMessageDynamicData(String username)
	{
		//拿到用户ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		String myHeadIcon = PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/");
		
		//拿到关注我的所有用户
		list = UserRegardTableOperation.getInstance().queryUserRegardTableWithRegardUserID(userID);
		
		List<Map<String, Object>> listTemp;
		int regardUserID = 0;
		for(int i = 0; i < list.size(); i++)
		{
			CommentMessageData dynMsgData = new CommentMessageData();
			
			map = list.get(i);
			
			//获取关注时间
			dynMsgData.setCommentTime(String.valueOf(map.get("regard_time")));
			
			//获取关注我的用户的用户ID
			regardUserID = Integer.parseInt(String.valueOf(map.get("self_user_id")));
			
			//拿到关注我的用户的名字和头像
			listTemp = UserTableOperation.getInstance().queryUsersTable(regardUserID);
			map = listTemp.get(0);
			dynMsgData.setCommentUsername(String.valueOf(map.get("username")));
			dynMsgData.setCommentUserHeadIconUrl(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
			
			dynMsgData.setMyHeadIconUrl(myHeadIcon);
			
			this.allDynMsg.addElement(dynMsgData);
		}
		
		//print
//		for(CommentMessageData dynMsg : this.allDynMsg)
//		{
//			System.out.println("\n-------------- MessageDynamic -------");
//			System.out.println(dynMsg.getCommentUserHeadIconUrl());
//			System.out.println(dynMsg.getCommentUsername());
//			System.out.println(dynMsg.getCommentTime());
//			System.out.println(dynMsg.getMyHeadIconUrl());
//		}
	}
	
	public Vector<CommentMessageData> getAllDynMsg(){
		
		return this.allDynMsg;
	}
}
