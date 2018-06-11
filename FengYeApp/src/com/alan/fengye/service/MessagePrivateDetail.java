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
		//�õ���¼�û�ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(loginUsername);
		Map<String, Object> map = list.get(0);
		int loginUserID = Integer.parseInt(String.valueOf(map.get("id")));
		String loginUserHeadIcon = PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/");
		
		//�õ�˽���ҵ��û�ID
		list = UserTableOperation.getInstance().queryUsersTable(privMsgUsername);
		map = list.get(0);
		int privMsgUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//�õ�ĳ�������û����¼�û����е���������
		list = MessagePrivateTableOperation.getInstance().queryMessagePrivateTableWithSendAndRecvUserID(loginUserID, privMsgUserID);
		
		//�õ�����������
		List<Map<String, Object>> listTemp;
		int sendUserID = 0;
		for(int i = 0; i < list.size(); i++)
		{
			CommentMessageData privContentData = new CommentMessageData();
			
			map = list.get(i);
			
			//��ȡ˽������
			privContentData.setCommentContent(String.valueOf(map.get("content")));
			
			//��ȡ˽�ŷ��͵�ʱ��
			privContentData.setCommentTime(String.valueOf(map.get("time")));
			
			//�õ�������Ϣ�û����û���
			sendUserID = Integer.parseInt(String.valueOf(map.get("send_user_id")));
			listTemp = UserTableOperation.getInstance().queryUsersTable(sendUserID);
			map = listTemp.get(0);
			privContentData.setCommentUsername(String.valueOf(map.get("username")));
			
			//��ȡ�ҵ�ͷ��
			privContentData.setMyHeadIconUrl(loginUserHeadIcon);
			
			this.privContent.addElement(privContentData);
		}
	}
	
	public int privateMessageReply(String replyContent, String loginUser, String privMsgUser, long curTime)
	{
		int retValue = 0;
		//�õ���¼�û�ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(loginUser);
		Map<String, Object> map = list.get(0);
		int loginUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//�õ�˽���û�ID
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
