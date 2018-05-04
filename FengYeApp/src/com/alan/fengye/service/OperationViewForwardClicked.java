package com.alan.fengye.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.PersonalCenterAttentionDrawboardCellData;
import com.alan.fengye.bean.PersonalCenterAttentionInterestGroupCellData;
import com.alan.fengye.bean.PersonalCenterAttentionUserCellData;
import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.ForwardsTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class OperationViewForwardClicked {

	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private final static String PICREALPATH = "C:\\Users\\Alan\\Desktop\\p";
	private Vector<PersonalCenterAttentionDrawboardCellData> allForwards;
	
	public OperationViewForwardClicked()
	{
		this.allForwards = new Vector<PersonalCenterAttentionDrawboardCellData>();
	}
	
	public void getOperationViewForwardClickedData(String username, String drawName, String picPath)
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
		
		//��ȡת�ɸ�ͼƬ�����л���
		list = ForwardsTableOperation.getInstance().queryForwardsTable(worksID);
		
		List<Map<String, Object>> listTemp;
		int toDrawID = 0;
		for(int i = 0; i < list.size(); i++)
		{
			PersonalCenterAttentionDrawboardCellData forwardCell = new PersonalCenterAttentionDrawboardCellData();
			
			map = list.get(i);
			toDrawID = Integer.parseInt(String.valueOf(map.get("to_drawboard_id"))) ;
			
			listTemp = DrawboardTableOperation.getInstance().queryDrawboardTable(toDrawID);
			map = listTemp.get(0);

			//�õ���������ֺ�����
			forwardCell.setDrawboardName(String.valueOf(map.get("Name")));
			forwardCell.setDescriptionText(String.valueOf(map.get("description")));
			
			//�õ����������û������ֺ�ͷ��
			int drawOwnerUserID = Integer.parseInt(String.valueOf(map.get("user_id")));
			listTemp = UserTableOperation.getInstance().queryUsersTable(drawOwnerUserID);
			map = listTemp.get(0);
			forwardCell.setOwnerUserName(String.valueOf(map.get("username")));
			forwardCell.setOwnerHeadIcon(PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/"));
			
			//�õ�����ķ���ͼ��---�����ڲ���һ��ͼƬ
			listTemp = WorksTableOperation.getInstance().queryWorksTableForConver(drawID);
			map = listTemp.get(0);
			forwardCell.setCoverImageURL(PICDIRPREFIX + String.valueOf(map.get("path")));
			//�������ͼ��Ŀ�͸�
			BufferedImage bufferImage;
			bufferImage = getPicSize(String.valueOf(map.get("path")));
			forwardCell.setCoverImageWidth(bufferImage.getWidth());
			forwardCell.setCoverImageHeight(bufferImage.getHeight());
			
			//���������ͼƬ����
			listTemp = WorksTableOperation.getInstance().queryWorksTableForCount(drawID);
			map = listTemp.get(0);
			forwardCell.setPicNums(Integer.parseInt(String.valueOf(map.get("numbers"))));
			
			//���屻��ע������(����ķ�˿��) --- Ԥ��������ʵ��
			
			this.allForwards.addElement(forwardCell); 
		}
	}
	
	private BufferedImage getPicSize(String path)
	{
		//Get the picture width and height
		File file = new File(PICREALPATH + "\\" + path);
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
	
	public Vector<PersonalCenterAttentionDrawboardCellData> getAllForwards()
	{
		return this.allForwards;
	}
}
