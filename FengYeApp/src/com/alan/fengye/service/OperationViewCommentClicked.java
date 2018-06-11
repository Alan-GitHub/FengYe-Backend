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
		//拿到用户ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到画板ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(userID, drawName);
		map = list.get(0);
		int drawID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到图片的作品ID
		list = WorksTableOperation.getInstance().queryWorksTableWithPathAndDrawID(picPath, drawID);
		map = list.get(0);
		int worksID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到该图片所有的评论条目
		list = CommentsTableOperation.getInstance().queryCommentsTableWithWorksID(worksID);
		
		List<Map<String, Object>> listTemp;
		for(int i = 0; i < list.size(); i++)
		{
			CommentMessageData commentData = new CommentMessageData();
			
			map = list.get(i);
			
			//获取评论内容
			commentData.setCommentContent(String.valueOf(map.get("comment_content")));
			
			//获取评论时间
			commentData.setCommentTime(String.valueOf(map.get("comment_time")));

			//拿到作品所属用户的名字和头像
			int worksOwnUserID = Integer.parseInt(String.valueOf(map.get("user_id")));
			listTemp = UserTableOperation.getInstance().queryUsersTable(worksOwnUserID);
			map = listTemp.get(0);
			commentData.setCommentUsername(String.valueOf(map.get("username")));
			commentData.setCommentUserHeadIconUrl(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
			
			this.allComments.addElement(commentData);
		}
	}
	
	public int operationViewCommentAddComment(String commentUser, String commentContent, String ownUser, String ownDrawbard, String picURL, long curTime)
	{
		int retValue = 0;
		
		//拿到作品所属用户的ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(ownUser);
		Map<String, Object> map = list.get(0);
		int ownUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到作品所属画板的ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(ownUserID, ownDrawbard);
		map = list.get(0);
		int ownDrawID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到图片的作品ID
		list = WorksTableOperation.getInstance().queryWorksTableWithPathAndDrawID(picURL, ownDrawID);
		map = list.get(0);
		int worksID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//拿到评论作品的用户的ID
		list = UserTableOperation.getInstance().queryUsersTable(commentUser);
		map = list.get(0);
		int commentUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		retValue = CommentsTableOperation.getInstance().updateCommentsTableAddComment(commentContent, commentUserID, worksID, curTime);
		
		return retValue;
	}
	
	public Vector<CommentMessageData> getAllComments(){
		
		return this.allComments;
	}
}
