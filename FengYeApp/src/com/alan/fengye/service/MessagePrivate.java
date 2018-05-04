package com.alan.fengye.service;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.CommentMessageData;
import com.alan.fengye.dal.MessagePrivateTableOperation;
import com.alan.fengye.dal.UserTableOperation;

public class MessagePrivate {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private Vector<CommentMessageData> allPrivMsg;
	
	public MessagePrivate()
	{
		this.allPrivMsg = new Vector<CommentMessageData>();
	}
	
	public void getMessagePrivateData(String username)
	{
		//拿到用户ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));

		//拿到所有的留言用户(可能包含重复用户)
		list = MessagePrivateTableOperation.getInstance().queryMessagePrivateTableWithReceiveUserID(userID);
		
		//拿到各项子数据
		List<Map<String, Object>> listTemp;
		Map<String, Object> mapTemp;
		int sendUserID = 0;
		for(int i = 0; i < list.size(); i++)
		{
			map = list.get(i);
			
			//拿到发送私信用户的用户ID
			sendUserID = Integer.parseInt(String.valueOf(map.get("send_user_id")));
			
			if(i+1 < list.size())
			{
				mapTemp = list.get(i+1);
				int nextSendUserID = Integer.parseInt(String.valueOf(mapTemp.get("send_user_id")));
				if(sendUserID == nextSendUserID)
					continue;
			}
				
			CommentMessageData privMsgData = new CommentMessageData();
			
			//获取私信内容
			privMsgData.setCommentContent(String.valueOf(map.get("content")));
			
			//获取私信发送的时间
			privMsgData.setCommentTime(String.valueOf(map.get("time")));
		
			//拿到私信我的用户的名字和头像
			listTemp = UserTableOperation.getInstance().queryUsersTable(sendUserID);
			map = listTemp.get(0);
			privMsgData.setCommentUsername(String.valueOf(map.get("username")));
			privMsgData.setCommentUserHeadIconUrl(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
			
			this.allPrivMsg.addElement(privMsgData);
		}
		
		//print
//		for(CommentMessageData privMsg : this.allPrivMsg)
//		{
//			System.out.println("\n-------------- MessagePrivate -------");
//			System.out.println(privMsg.getCommentUserHeadIconUrl());
//			System.out.println(privMsg.getCommentUsername());
//			System.out.println(privMsg.getCommentContent());
//			System.out.println(privMsg.getCommentTime());
//		}
	}
	
	public Vector<CommentMessageData> getAllPrivMsg(){
		
		return this.allPrivMsg;
	}

}
