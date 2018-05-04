package com.alan.fengye.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

public class JsonReader{

	public static JSONObject requerstJson(HttpServletRequest request) throws IOException, UnsupportedEncodingException {  
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8")); 
		JSONObject object=null;
		try {
			
	        String line = null;  
	        StringBuilder sb = new StringBuilder();  
	        while ((line = br.readLine()) != null) {  
	            sb.append(line);  
	        } 
	      //将json字符串转换为json对象  
	      object=JSONObject.parseObject(sb.toString());
		} catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (br != null) {  
	        	br.close();
	        }  
	    }  
        return object;
	}
	public static void responseJson( HttpServletResponse response,Object object) {
		String str = JSONObject.toJSONString(object);
	    response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json; charset=utf-8");  
	    PrintWriter out = null;  
	    try {  
	        out = response.getWriter();  
	        out.append(str); 
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	    }  
	}
}
