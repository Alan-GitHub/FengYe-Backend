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
		//1、创建一个DiskFileItemFactory工厂    
        DiskFileItemFactory factory = new DiskFileItemFactory();    
        //2、创建一个文件上传解析器    
        ServletFileUpload upload = new ServletFileUpload(factory);      
        
        // 1. 得到 FileItem 的集合 items    
        List<FileItem> items = null;
        try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        String username = "";
        String drawName = "";
        //头像 或者 作品本身
        String picType = ""; 
        
        // 2. 遍历 items:    
        for (FileItem item : items) {   
   
            if(item.isFormField()) {  //表单普通域
            	//取到用户名
            	if(item.getFieldName().equalsIgnoreCase("data[username]"))  //比较参数名
            	{
            		try {
						username = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            	//取到画板名
            	else if(item.getFieldName().equalsIgnoreCase("data[drawname]"))
            	{
            		try {
						drawName = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
            	}
            	//取到图片类型
            	else if(item.getFieldName().equalsIgnoreCase("data[picType]"))  //比较参数名
            		picType = item.getString();  
            } else {
    
            	//本次上传的是头像
            	if(picType.equalsIgnoreCase("PicTypeHeadIcon")) {
                	
                	saveHeadIconPicToServer(username, item, response);
                }
            	//本次上传的是作品图片
            	else if(picType.equalsIgnoreCase("PicTypeWorks")) {
                	
                	saveWorksPicToServer(username, drawName, item, response, curTime);
                }
            }
        }
	}
	
	//保存头像图片到服务器
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
        
        //输入流
        InputStream in = null;
		try {
			in = item.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    

        //输出流
        OutputStream out = null;
        try {
			String fileFullPath = picDir + "\\" + extractFileName;  //文件最终上传的位置     
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

        System.out.println("上传完成"); 
        
        //更新Users数据表
        PersonalCenter pc = new PersonalCenter();
        int ret = pc.updateUserHeadIcon(username, clientSubmitFileName);
        
        //以JSON形式返回
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
	
	//保存作品图片到服务器
	public void saveWorksPicToServer(String username, String drawName, FileItem item, HttpServletResponse response, long curTime)
	{
		String fileName = item.getName();
		
		String picDir = picBaseDir + "\\" + username;
        File dir = new File(picDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        //输入流
        InputStream in = null;
		try {
			in = item.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//输出流
        OutputStream out = null;
        try {
				
			String fileFullPath = picDir + "\\" + fileName;  //文件最终上传的位置     
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

        System.out.println("上传完成"); 
        
        //更新数据库
        String filenameWithUsername = username + "_" + fileName;
        String picDesc = "";
        
        PersonalCenterDetailDrawboard detailDraw = new PersonalCenterDetailDrawboard();
        int ret = detailDraw.updateDatabaseAddPicture(username, drawName, filenameWithUsername, picDesc, curTime);
        
        //以JSON形式返回
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
