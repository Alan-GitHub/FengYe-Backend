package com.alan.fengye.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alan.fengye.enums.DataBaseReturnCode;
import com.alibaba.fastjson.JSONObject;

public class ServiceForPictureUpload {
	
	private String picBaseDir = "C:\\Users\\Alan\\Desktop\\p";
	
	public void savePicToServer(HttpServletRequest request, HttpServletResponse response, long curTime)
	{
		//1������һ��DiskFileItemFactory����    
        DiskFileItemFactory factory = new DiskFileItemFactory();    
        //2������һ���ļ��ϴ�������    
        ServletFileUpload upload = new ServletFileUpload(factory);      
        
        // 1. �õ� FileItem �ļ��� items    
        List<FileItem> items = null;
        try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        String username = "";
        String drawName = "";
        //ͷ�� ���� ��Ʒ����
        String picType = ""; 
        
        // 2. ���� items:    
        for (FileItem item : items) {   
   
            if(item.isFormField()) {  //����ͨ��
            	//ȡ���û���
            	if(item.getFieldName().equalsIgnoreCase("data[username]"))  //�Ƚϲ�����
            	{
            		try {
						username = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            	//ȡ��������
            	else if(item.getFieldName().equalsIgnoreCase("data[drawname]"))
            	{
            		try {
						drawName = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
            	}
            	//ȡ��ͼƬ����
            	else if(item.getFieldName().equalsIgnoreCase("data[picType]"))  //�Ƚϲ�����
            		picType = item.getString();  
            } else {
    
            	//�����ϴ�����ͷ��
            	if(picType.equalsIgnoreCase("PicTypeHeadIcon")) {
                	
                	saveHeadIconPicToServer(username, item, response);
                }
            	//�����ϴ�������ƷͼƬ
            	else if(picType.equalsIgnoreCase("PicTypeWorks")) {
                	
                	saveWorksPicToServer(username, drawName, item, response, curTime);
                }
            }
        }
	}
	
	//����ͷ��ͼƬ��������
	public void saveHeadIconPicToServer(String username, FileItem item, HttpServletResponse response)
	{
		String clientSubmitFileName = username + "_" + item.getName();
		
		String filePathStr = clientSubmitFileName;
        filePathStr = filePathStr.replaceAll("_", "\\\\");
        
        int pos = filePathStr.lastIndexOf("\\");
        String extractFilePath = filePathStr.substring(0, pos);
        String extractFileName = filePathStr.substring(pos+1);
        
        System.out.println(extractFilePath);
        System.out.println(extractFileName);
        
        String picDir = picBaseDir + "\\" + extractFilePath;
        System.out.println(picDir);
        File dir = new File(picDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        //������
        InputStream in = null;
		try {
			in = item.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    

        //�����
        OutputStream out = null;
        try {
			String fileFullPath = picDir + "\\" + extractFileName;  //�ļ������ϴ���λ��     
			out = new FileOutputStream(fileFullPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        
        byte[] buffer = new byte[1024];    
        int len = 0; 
        try {
			while ((len = in.read(buffer)) != -1)   
				out.write(buffer, 0, len);    
	
			out.close();
			in.close();
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        

        System.out.println("�ϴ����"); 
        
        //����Users���ݱ�
        PersonalCenter pc = new PersonalCenter();
        int ret = pc.updateUserHeadIcon(username, clientSubmitFileName);
        
        //��JSON��ʽ����
        JSONObject obj = new JSONObject();
		obj.put("retCode", ret);
		String str = JSONObject.toJSONString(obj);
		
        response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json; charset=utf-8");  

	    PrintWriter printWriter = null;  
	    try {
	    	printWriter = response.getWriter();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} 
	    printWriter.append(str); 
	    printWriter.close();
	}
	
	//������ƷͼƬ��������
	public void saveWorksPicToServer(String username, String drawName, FileItem item, HttpServletResponse response, long curTime)
	{
		String fileName = item.getName();
		
		String picDir = picBaseDir + "\\" + username;
        File dir = new File(picDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        //������
        InputStream in = null;
		try {
			in = item.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//�����
        OutputStream out = null;
        try {
				
			String fileFullPath = picDir + "\\" + fileName;  //�ļ������ϴ���λ��     
			out = new FileOutputStream(fileFullPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    

        //
        byte[] buffer = new byte[1024];    
        int len = 0;
        try {
			while ((len = in.read(buffer)) != -1)   
				out.write(buffer, 0, len);    
	
			out.close();
			in.close();
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        

        System.out.println("�ϴ����"); 
        
        //�������ݿ�
        String filenameWithUsername = username + "_" + fileName;
        String picDesc = "";
        
        PersonalCenterDetailDrawboard detailDraw = new PersonalCenterDetailDrawboard();
        int ret = detailDraw.updateDatabaseAddPicture(username, drawName, filenameWithUsername, picDesc, curTime);
        
        //��JSON��ʽ����
        JSONObject obj = new JSONObject();
		obj.put("retCode", ret);
		String str = JSONObject.toJSONString(obj);
		
        response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json; charset=utf-8");  

	    PrintWriter printWriter = null;  
	    try {
	    	printWriter = response.getWriter();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} 
	    printWriter.append(str); 
	    printWriter.close();
	}
}
