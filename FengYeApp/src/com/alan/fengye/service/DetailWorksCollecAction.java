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
		
		//�õ���¼�û����û�ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//�õ������ɼ�����ͼƬ�Ļ���ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(userID, drawName);
		map = list.get(0);
		int toDrawID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//�������ݵ�works����   ת���൱���Լ��ӱ���������ͼƬ�ϴ�����̨
		this.retValue = WorksTableOperation.getInstance().updateWorksTable(userID, toDrawID, picURL, picDesc);
		
		if(this.retValue == 0)
			return;
		
		////////////// �������ݵ�forwards����   ��¼ת���¼� ////////////////////
		
		//1. �õ�ͼƬ��Ʒԭ�������û����û�ID
		list = UserTableOperation.getInstance().queryUsersTable(origUsername);
		map = list.get(0);
		int origUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//2. �õ�ԭ�������Ļ���ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(origUserID, origDrawName);
		map = list.get(0);
		int origDrawID = Integer.parseInt(String.valueOf(map.get("id")));
	
		//3. �õ�ͼƬ��ת��֮ǰ����ƷID
		list = WorksTableOperation.getInstance().queryWorksTableWithPathAndDrawID(picURL, origDrawID);
		map = list.get(0);
		int worksID = Integer.parseInt(String.valueOf(map.get("id")));
	
		//4. �ٲ�������
		this.retValue = ForwardsTableOperation.getInstance().updateForwardsTable(worksID, origDrawID, toDrawID);
		
		//���»�ȡת�ɵ�����
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
