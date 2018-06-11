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
		
		//保存画板所属用户的头像和用户名
	    String headIconURL = PICDIRPREFIX + String.valueOf(map.get("head_icon")).replaceAll("_", "/");
		String ownerUsername = String.valueOf(map.get("username"));
	
		int userID = Integer.parseInt(String.valueOf(map.get("id"))) ;
		
		list = DrawboardTableOperation.getInstance().queryDrawboardTableWithUserID(userID);
		
		//获取数据
		List<Map<String, Object>> listTemp;
		int drawboardID = 0;
		for(int i = 0; i < list.size(); i++)
		{
			PersonalCenterDrawboardCell drawboardCell = new PersonalCenterDrawboardCell();
			
			map = list.get(i);
			
			//拿到画板名字和描述
			drawboardCell.setDrawboardName(String.valueOf(map.get("Name")));
			drawboardCell.setDescriptionText(String.valueOf(map.get("description")));
			
			//拿到画板ID
			drawboardID = Integer.parseInt(String.valueOf(map.get("id"))) ;
			
			//拿到画板所属用户的头像和用户名
			drawboardCell.setOwnerHeadIcon(headIconURL);
			drawboardCell.setOwnerUserName(ownerUsername);
	
			//拿到画板的封面图像---画板内部第一张图片
			listTemp = WorksTableOperation.getInstance().queryWorksTableForConver(drawboardID);
			if(listTemp.size() != 0)
			{
				map = listTemp.get(0);
				drawboardCell.setCoverImageURL(PICDIRPREFIX + String.valueOf(map.get("path")).replaceAll("_", "/"));
				
				//画板封面图像的宽和高
				BufferedImage bufferImage;
				bufferImage = getPicSize(String.valueOf(map.get("path")).replaceAll("_", "\\\\"));
				drawboardCell.setCoverImageWidth(bufferImage.getWidth());
				drawboardCell.setCoverImageHeight(bufferImage.getHeight());
			}
			
			//计算画板包含的图片数量
			listTemp = WorksTableOperation.getInstance().queryWorksTableForCount(drawboardID);
			map = listTemp.get(0);
			drawboardCell.setPicNums(Integer.parseInt(String.valueOf(map.get("numbers"))));
			
			//计算画板的关注量(画板的粉丝量) --- 留待日后实现
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
