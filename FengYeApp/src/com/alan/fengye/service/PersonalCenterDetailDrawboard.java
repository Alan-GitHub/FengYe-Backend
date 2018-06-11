package com.alan.fengye.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.PersonalCenterDrawboardCell;
import com.alan.fengye.bean.WorksCellData;
import com.alan.fengye.dal.CommentsTableOperation;
import com.alan.fengye.dal.DrawboardRegardTableOperation;
import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.ForwardsTableOperation;
import com.alan.fengye.dal.LikesTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class PersonalCenterDetailDrawboard {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private final static String PICREALPATH = "C:\\Users\\Alan\\Desktop\\p";
	
	private Vector<WorksCellData> detailDrawbData;
	private boolean isAttention;
	
	public PersonalCenterDetailDrawboard()
	{
		this.detailDrawbData = new Vector<WorksCellData>();
		isAttention = false;
	} 
	
	public void getPersonalCenterDetailDrawboardData(String otherUser, String loginUser, String drawName)
	{
		//��ǰ�û����û�ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(otherUser);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		//�õ�����ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(userID, drawName);
		map = list.get(0);
		int drawboardID = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		list = WorksTableOperation.getInstance().queryWorksTableForSpecifyDrawb(drawboardID);
		
		//��ȡ����
		List<Map<String, Object>> listTemp;
		int worksID = 0;
		for(int i = 0; i < list.size(); i++)
		{
			WorksCellData detailDrawb = new WorksCellData();
			
			map = list.get(i);

			//�õ�ͼƬ·��
			detailDrawb.setPicURL(PICDIRPREFIX + String.valueOf(map.get("path")).replaceAll("_", "/"));

			//�õ�ͼƬ������
			detailDrawb.setDescriptionText(String.valueOf(map.get("description")));
			
			//����ͼƬ�Ŀ��
			BufferedImage bufferImage = getPicSize(String.valueOf(map.get("path")).replaceAll("_", "/"));
			detailDrawb.setPicWidth(bufferImage.getWidth());
			detailDrawb.setPicHeight(bufferImage.getHeight());
			
			//�õ�ͼƬ�ϴ�ʱ��
			detailDrawb.setUploadTime(Long.valueOf(String.valueOf(map.get("uploadtime"))));
			
			//�õ�ͼƬID
			worksID = Integer.parseInt(String.valueOf(map.get("id")));
			
			//�õ���������
			listTemp = DrawboardTableOperation.getInstance().queryDrawboardTableWithID(drawboardID);
			map = listTemp.get(0);
			detailDrawb.setTemplateName(String.valueOf(map.get("Name")));
			
			//�õ���Ʒ��ϲ����
			listTemp = LikesTableOperation.getInstance().queryLikesTable(worksID);
			map = listTemp.get(0);
			detailDrawb.setLikeCount(Integer.parseInt(String.valueOf(map.get("numbers"))));
			
			//�õ���Ʒ��ת����
			listTemp = ForwardsTableOperation.getInstance().queryForwardsTableForCount(worksID);
			map = listTemp.get(0);
			detailDrawb.setForwardCount(Integer.parseInt(String.valueOf(map.get("numbers"))) );
			
			//�õ���Ʒ��������
			listTemp = CommentsTableOperation.getInstance().queryCommentsTable(worksID);
			map = listTemp.get(0);
			detailDrawb.setCommentCount(Integer.parseInt(String.valueOf(map.get("numbers"))) );
			
			//�õ��û���
			listTemp = UserTableOperation.getInstance().queryUsersTable(userID);
			map = listTemp.get(0);
			detailDrawb.setOwner(String.valueOf(map.get("username")));
			
			//�õ��û�ͷ��
			detailDrawb.setHeadIcon(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
			
			this.detailDrawbData.addElement(detailDrawb);
		}
		
		//���һ�����鿴��ģ���Ƿ񱻵�¼�û�����ע
		//��¼�û����û�ID
		list = UserTableOperation.getInstance().queryUsersTable(loginUser);
		map = list.get(0);
		int loginUserID = Integer.parseInt(String.valueOf(map.get("id")));

		list = DrawboardRegardTableOperation.getInstance().queryDrawboardRegardTableWithDrawIDAndUserID(drawboardID, loginUserID);
		
		if(list.size() > 0)
			this.isAttention = true;
		else
			this.isAttention = false;
	}
	
	private BufferedImage getPicSize(String picURL)
	{
		//Get the picture width and height
		File file = new File(PICREALPATH + "\\" + picURL);
		FileInputStream is = null;
		
	    try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		BufferedImage sourceImg = null;
		try {
			sourceImg = javax.imageio.ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sourceImg;
	}
	
	public int updateDatabaseAddPicture(String username, String drawName, String filePath, String picDesc, long curTime) 
	{
		int retValue = 0;
		
		//�õ���¼�û����û�ID
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		int userID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//�õ�����ID
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserIDAndDrawName(userID, drawName);
		map = list.get(0);
		int drawID = Integer.parseInt(String.valueOf(map.get("id")));
		
		//����works��
		retValue = WorksTableOperation.getInstance().updateWorksTable(userID, drawID, filePath, picDesc, curTime);
		
		return retValue;
	}
	
	public Vector<WorksCellData> getDetailDrawbData(){
		
		return this.detailDrawbData;
	}
	
	public boolean getIsAttention(){
		
		return this.isAttention;
	}
}
