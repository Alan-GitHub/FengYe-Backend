package com.alan.fengye.service;

import java.util.List;
import java.util.Map;

import com.alan.fengye.bean.PersonalCenterHeaderData;
import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.InterestGroupRegardTableOperation;
import com.alan.fengye.dal.LikesTableOperation;
import com.alan.fengye.dal.UserRegardTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class PersonalCenter {

	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	
	private PersonalCenterHeaderData headerData;
	
	public PersonalCenter()
	{
		this.headerData = new PersonalCenterHeaderData();
	}
	
	public void getPersonalCenterData(String otherUser, String loginUser)
	{
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(otherUser);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		this.headerData.setHeadIconURL(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
		this.headerData.setUsername(String.valueOf(map.get("username")));
		
		//�õ���ǰ�û��Ļ�������
		list = DrawboardTableOperation.getInstance().queryDrawboardTableForCountWithUserID(userID);
		map = list.get(0);
		this.headerData.setDrawboardNum(Integer.parseInt(String.valueOf(map.get("numbers"))) );
		
		//�õ���ǰ�û��ϴ�����Ʒ����
		list = WorksTableOperation.getInstance().queryWorksTableForCountWithUserID(userID);
		map = list.get(0);
		this.headerData.setCollectionNum(Integer.parseInt(String.valueOf(map.get("numbers"))) );
		
		//�õ���ǰ�û�ϲ������Ʒ����
		list = LikesTableOperation.getInstance().queryLikesTableForCountWithUserID(userID);
		map = list.get(0);
		this.headerData.setLikeNum(Integer.parseInt(String.valueOf(map.get("numbers"))) );
		
		//�õ���ǰ�û���ע������
		int attenNums = 0;
		list = UserRegardTableOperation.getInstance().queryUserRegardTableForCountWithSelfUserID(userID);
		map = list.get(0);
		attenNums += Integer.parseInt(String.valueOf(map.get("numbers")));
		
		list = InterestGroupRegardTableOperation.getInstance().queryInterestGroupRegardTableForCountWithUserID(userID);
		map = list.get(0);
		attenNums += Integer.parseInt(String.valueOf(map.get("numbers")));
		this.headerData.setAttentionNum(attenNums);
		
		//�õ���ǰ�û��ķ�˿��
		list = UserRegardTableOperation.getInstance().queryUserRegardTableForCountWithRegardUserID(userID);
		map = list.get(0);
		this.headerData.setFansNum(Integer.parseInt(String.valueOf(map.get("numbers"))));
		
		//�õ���¼�û��Ƿ��ע�˴��û�
		list = UserTableOperation.getInstance().queryUsersTable(loginUser);
		map = list.get(0);
		int loginUserID = Integer.parseInt(String.valueOf(map.get("id")));
		
		list = UserRegardTableOperation.getInstance().queryUserRegardTableWithRegardIDAndSelfID(userID, loginUserID);
		if(list.size() > 0)
			this.headerData.setIsAttention(true);
		else
			this.headerData.setIsAttention(false);
	}
	
	public int updateUserHeadIcon(String username, String headIconURL) {
		
		int ret = 0;
		
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		String path = String.valueOf(map.get("head_icon"));

		if(path.equalsIgnoreCase(headIconURL)) {

			ret = 2; //·�����ڣ������滻
		} else {
			//·��û�У���һ���ϴ�ͷ��
			ret = UserTableOperation.getInstance().updateUsersTable(userID, headIconURL);
		}  

		return ret;
	}
	
	public PersonalCenterHeaderData getHeaderData()
	{
		return this.headerData;
	}
}
