package com.alan.fengye.service;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.CommentMessageData;
import com.alan.fengye.bean.PersonalCenterAttentionDrawboardCellData;
import com.alan.fengye.dal.CommentsTableOperation;
import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class OperationViewCommentClicked {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private Vector<CommentMessageData> allComments;
	
	public OperationViewCommentClicked()
	{
		this.allComments = new Vector<CommentMessageData>();
	}
	
	public void getOperationViewCommentClickedData(String username, String drawName, String picPath)
	{
		//�õ��û�ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//�õ�����ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(userID, drawName);
		map = list.get(0);
		int drawID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//�õ�ͼƬ����ƷID
		list = WorksTableOperation.getInstance().queryWorksTableWithPathAndDrawID(picPath, drawID);
		map = list.get(0);
		int worksID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//�õ���ͼƬ���е�������Ŀ
		list = CommentsTableOperation.getInstance().queryCommentsTableWithWorksID(worksID);
		
		List<Map<String, Object>> listTemp;
		for(int i = 0; i < list.size(); i++)
		{
			CommentMessageData commentData = new CommentMessageData();
			
			map = list.get(i);
			
			//��ȡ��������
			commentData.setCommentContent(String.valueOf(map.get("comment_content")));
			
			//��ȡ����ʱ��
			commentData.setCommentTime(String.valueOf(map.get("comment_time")));

			//�õ���Ʒ�����û������ֺ�ͷ��
			int worksOwnUserID = Integer.parseInt(String.valueOf(map.get("user_id")));
			listTemp = UserTableOperation.getInstance().queryUsersTable(worksOwnUserID);
			map = listTemp.get(0);
			commentData.setCommentUsername(String.valueOf(map.get("username")));
			commentData.setCommentUserHeadIconUrl(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
			
			this.allComments.addElement(commentData);
		}
		
		//print
//		for(CommentMessageData comm : this.allComments)
//		{
//			System.out.println("\n-------------- OperationViewCommentClicked -------");
//			System.out.println(comm.getCommentUserHeadIconUrl());
//			System.out.println(comm.getCommentUsername());
//			System.out.println(comm.getCommentContent());
//			System.out.println(comm.getCommentTime());
//		}
	}
	
	public Vector<CommentMessageData> getAllComments(){
		
		return this.allComments;
	}
}
