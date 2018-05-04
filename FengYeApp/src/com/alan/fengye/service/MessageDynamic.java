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
		//�õ��û�ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		String myHeadIcon = PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/");
		
		//�õ���ע�ҵ������û�
		list = UserRegardTableOperation.getInstance().queryUserRegardTableWithRegardUserID(userID);
		
		List<Map<String, Object>> listTemp;
		int regardUserID = 0;
		for(int i = 0; i < list.size(); i++)
		{
			CommentMessageData dynMsgData = new CommentMessageData();
			
			map = list.get(i);
			
			//��ȡ��עʱ��
			dynMsgData.setCommentTime(String.valueOf(map.get("regard_time")));
			
			//��ȡ��ע�ҵ��û����û�ID
			regardUserID = Integer.parseInt(String.valueOf(map.get("self_user_id")));
			
			//�õ���ע�ҵ��û������ֺ�ͷ��
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
