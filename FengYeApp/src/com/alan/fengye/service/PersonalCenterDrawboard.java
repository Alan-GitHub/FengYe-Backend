package com.alan.fengye.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.alan.fengye.bean.PersonalCenterDrawboardCell;
import com.alan.fengye.dal.DrawboardRegardTableOperation;
import com.alan.fengye.dal.DrawboardTableOperation;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.dal.WorksTableOperation;

public class PersonalCenterDrawboard {
	
	private final static String PICDIRPREFIX = "http://192.168.1.101:8080/photo/";
	private final static String PICREALPATH = "C:\\Users\\Alan\\Desktop\\p";

	private Vector<PersonalCenterDrawboardCell> selfDrawboardCell;
	
	public PersonalCenterDrawboard()
	{
		this.selfDrawboardCell = new Vector<PersonalCenterDrawboardCell>();
	}
	
	public void getPersonalCenterDrawboardData(String username)
	{
		List<Map<String, Object>> list = UserTableOperation.getInstance().queryUsersTable(username);
		Map<String, Object> map = list.get(0);
		
		//���滭�������û���ͷ����û���
	    String headIconURL = PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/");
		String ownerUsername = String.valueOf(map.get("username"));
	
		int userID = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserID(userID);
		
		//��ȡ����
		List<Map<String, Object>> listTemp;
		int drawboardID = 0;
		for(int i = 0; i < list.size(); i++)
		{
			PersonalCenterDrawboardCell drawboardCell = new PersonalCenterDrawboardCell();
			
			map = list.get(i);
			
			//�õ��������ֺ�����
			drawboardCell.setDrawboardName(String.valueOf(map.get("Name")));
			drawboardCell.setDescriptionText(String.valueOf(map.get("description")));
			
			//�õ�����ID
			drawboardID = Integer.parseInt(String.valueOf(map.get("id"))) ;
			
			//�õ����������û���ͷ����û���
			drawboardCell.setOwnerHeadIcon(headIconURL);
			drawboardCell.setOwnerUserName(ownerUsername);
	
			//�õ�����ķ���ͼ��---�����ڲ���һ��ͼƬ
			listTemp = WorksTableOperation.getInstance().queryWorksTableForConver(drawboardID);
			if(listTemp.size() != 0)
			{
				map = listTemp.get(0);
				drawboardCell.setCoverImageURL(PICDIRPREFIX + String.valueOf(map.get("path")).replaceAll("_", "/"));
				
				//�������ͼ��Ŀ�͸�
				BufferedImage bufferImage;
				bufferImage = getPicSize(String.valueOf(map.get("path")).replaceAll("_", "\\\\"));
				drawboardCell.setCoverImageWidth(bufferImage.getWidth());
				drawboardCell.setCoverImageHeight(bufferImage.getHeight());
			}
			
			//���㻭�������ͼƬ����
			listTemp = WorksTableOperation.getInstance().queryWorksTableForCount(drawboardID);
			map = listTemp.get(0);
			drawboardCell.setPicNums(Integer.parseInt(String.valueOf(map.get("numbers"))));
			
			//���㻭��Ĺ�ע��(����ķ�˿��) --- �����պ�ʵ��
			listTemp = DrawboardRegardTableOperation.getInstance().queryDrawboardRegardTableWithDrawIDForCount(drawboardID);
			map = listTemp.get(0);
			drawboardCell.setAttentionNum(Integer.parseInt(String.valueOf(map.get("numbers"))));
			
			this.selfDrawboardCell.addElement(drawboardCell);
		}
		
		//print
//		for(PersonalCenterDrawboardCell cell : this.selfDrawboardCell)
//		{
//			System.out.println("\n-------------- PersonalCenterDrawboard -------");
//			System.out.println(cell.getCoverImageURL());
//			System.out.println(cell.getDrawboardName());
//			System.out.println(cell.getDescriptionText());
//			System.out.println(cell.getOwnerHeadIcon());
//			System.out.println(cell.getOwnerUserName());
//			System.out.println(cell.getPicNums());
//		}
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
	
	public Vector<PersonalCenterDrawboardCell> getSelfDrawboardCell()
	{
		return this.selfDrawboardCell;
	}

}
