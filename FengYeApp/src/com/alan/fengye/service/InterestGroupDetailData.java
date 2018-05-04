package com.alan.fengye.service;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import com.alan.fengye.bean.InterestGroupDetailUnit;
import com.alan.fengye.bean.PersonalCenterAttentionInterestGroupCellData;
import com.alan.fengye.dal.InterestGroupRegardTableOperation;
import com.alan.fengye.dal.InterestGroupTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class InterestGroupDetailData {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	
	InterestGroupDetailUnit groupDetail;
	
	public InterestGroupDetailData(){
		
		this.groupDetail = new InterestGroupDetailUnit();
	}
	
	public void getInterestGroupDetailData(String groupName) {

		List<Map<String, Object>> list = InterestGroupTableOperation.getInstance().queryInterestGroupTable(groupName);
		Map<String, Object> map = list.get(0);
		int groupId = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		list = InterestGroupRegardTableOperation.getInstance().queryInterestGroupRegardTable(groupId);
		this.groupDetail.setNumsOfRegardUser(list.size());
		
		for(Map<String, Object> map1 : list)
		{
			int userId = Integer.parseInt(String.valueOf(map1.get("user_id"))) ;
			
			List<Map<String, Object>> list1 = UserTableOperation.getInstance().queryUsersTable(userId);
			this.groupDetail.getHeadIconOfRegardUser().addElement(PICDIRPREFIX + String.valueOf(list1.get(0).get("head_icon")).replaceAll("_", "/"));
		}
	}
	
	public InterestGroupDetailUnit getGroupDetail() {
		
		return this.groupDetail;
	}
}
