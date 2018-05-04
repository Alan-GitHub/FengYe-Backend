package com.alan.fengye.service;

import java.util.List;
import java.util.Map;

import com.alan.fengye.dal.LikesTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class DetailWorksLikeOrNot {

	private int retValue;
	private int likeNum;
	
	public DetailWorksLikeOrNot(){
		
		this.retValue = 0;
		this.likeNum = 0;
	}
	
	public void updateWorksLikeOrNot(String username, String picURL, String isLike)
	{
		//�õ���¼�û����û�ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//�õ�ͼƬ��Ʒ��ID
		list = WorksTableOperation.getInstance().queryWorksTableWithPath(picURL);
		map = list.get(0);
		int worksID = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		//�洢��������ֵ
		this.retValue = LikesTableOperation.getInstance().updateLikesTable(userID, worksID, isLike);
		
		//���»�ȡ��Ʒ��ϲ������
		list = LikesTableOperation.getInstance().queryLikesTable(worksID);
		map = list.get(0);
		this.likeNum = Integer.parseInt(String.valueOf(map.get("numbers")));
	}

	public int getRetValue(){
		
		return this.retValue;
	}
	
	public int getLikeNum() {
		
		return this.likeNum;
	}
}
