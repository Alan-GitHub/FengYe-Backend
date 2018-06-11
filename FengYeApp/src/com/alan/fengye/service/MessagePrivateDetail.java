package com.alan.fengye.service;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.CommentMessageData;
import com.alan.fengye.dal.MessagePrivateTableOperation;
import com.alan.fengye.dal.UserTableOperation;

public class MessagePrivateDetail {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private Vector<CommentMessageData> privContent;
	
	public MessagePrivateDetail()
	{
		this.privContent = new Vector<CommentMessageData>();
	}
	
	public void getMessagePrivateDetailData(String loginUsername, String privMsgUsername)
	{
		//拿到登录用户ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(loginUsername);
		Map<String, Object> map = list.get(0);
		int loginUserID = Integer.parseInt(String.valueOf(map.get("id")));
		String loginUserHeadIcon = PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/");
		
		//拿到私信我的用户ID
		list = UserTableOperation.getInstance().queryUsersTable(privMsgUsername);
		map = list.get(0);
		int privMsgUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到某个留言用户与登录用户所有的留言内容
		list = MessagePrivateTableOperation.getInstance().queryMessagePrivateTableWithSendAndRecvUserID(loginUserID, privMsgUserID);
		
		//拿到各项子数据
		List<Map<String, Object>> listTemp;
		int sendUserID = 0;
		for(int i = 0; i < list.size(); i++)
		{
			CommentMessageData privContentData = new CommentMessageData();
			
			map = list.get(i);
			
			//获取私信内容
			privContentData.setCommentContent(String.valueOf(map.get("content")));
			
			//获取私信发送的时间
			privContentData.setCommentTime(String.valueOf(map.get("time")));
			
			//拿到发送消息用户的用户名
			sendUserID = Integer.parseInt(String.valueOf(map.get("send_user_id")));
			listTemp = UserTableOperation.getInstance().queryUsersTable(sendUserID);
			map = listTemp.get(0);
			privContentData.setCommentUsername(String.valueOf(map.get("username")));
			
			//获取我的头像
			privContentData.setMyHeadIconUrl(loginUserHeadIcon);
			
			this.privContent.addElement(privContentData);
		}
	}
	
	public int privateMessageReply(String replyContent, String loginUser, String privMsgUser, long curTime)
	{
		int retValue = 0;
		//拿到登录用户ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(loginUser);
		Map<String, Object> map = list.get(0);
		int loginUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到私信用户ID
		list = UserTableOperation.getInstance().queryUsersTable(privMsgUser);
		map = list.get(0);
		int privMsgUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		retValue = MessagePrivateTableOperation.getInstance().updateMessagePrivateTableAddReplyContent(replyContent, loginUserID, privMsgUserID, curTime);
	
		return retValue;
	}
	
	public Vector<CommentMessageData> getPrivContent(){
		
		return this.privContent;
	}
}
