package com.alan.fengye.service;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.PersonalCenterAttentionUserCellData;
import com.alan.fengye.dal.LikesTableOperation;
import com.alan.fengye.dal.UserRegardTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class OperationViewLikeClicked {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private Vector<PersonalCenterAttentionUserCellData> allLikeUsers;
	
	public OperationViewLikeClicked()
	{
		this.allLikeUsers = new Vector<PersonalCenterAttentionUserCellData>();
	}
	
	public void getOperationViewLikeClickedData(String path)
	{
		//拿到图片作品ID
		List<Map<String, Object>> list = WorksTableOperation.getInstance().queryWorksTableWithPath(path);
		Map<String, Object> map = list.get(0);
		int worksID = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		list = LikesTableOperation.getInstance().queryLikesTableWithWorksID(worksID);
		
		//获取喜欢该作品的用户的数据
		int fansID;
		List<Map<String, Object>> listTemp;
		for(int i = 0; i < list.size(); i++)
		{
			PersonalCenterAttentionUserCellData fansCell = new PersonalCenterAttentionUserCellData();	
			
			map = list.get(i);
			fansID = Integer.parseInt(String.valueOf(map.get("user_id"))) ;
			
			listTemp = UserTableOperation.getInstance().queryUsersTable(fansID);
			map = listTemp.get(0);

			//拿到粉丝的头像URL和用户名
			fansCell.setUserHeadIcon(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
			fansCell.setUserName(String.valueOf(map.get("username")));
	
			//拿到粉丝的粉丝量
			listTemp = UserRegardTableOperation.getInstance().queryUserRegardTableForCountWithRegardUserID(fansID);
			map = listTemp.get(0);
			fansCell.setFansNums(Integer.parseInt(String.valueOf(map.get("numbers"))));
			
			this.allLikeUsers.addElement(fansCell);		
		}
		
		//print
//		for(PersonalCenterAttentionUserCellData cell : this.allLikeUsers)
//		{
//			System.out.println("\n-------------- PersonalCenterFans -------");
//			System.out.println(cell.getFansNums());
//			System.out.println(cell.getUserName());
//			System.out.println(cell.getUserHeadIcon());
//		}
	}
	
	public Vector<PersonalCenterAttentionUserCellData> getAllLikeUsers()
	{
		return this.allLikeUsers;
	}

}
