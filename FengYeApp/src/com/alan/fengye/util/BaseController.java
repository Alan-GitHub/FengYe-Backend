package com.alan.fengye.util;

import java.util.UUID;

import com.alan.fengye.enums.ReturnCode;

public class BaseController {
	/** 
	* ���ָ����Ŀ��UUID 
	* @param number int ��Ҫ��õ�UUID���� 
	* @return String[] UUID���� 
	*/ 
	public static String getUUID(int number){ 
	if(number < 1){ 
	return null; 
	} 
	String[] retArray = new String[number]; 
	for(int i=0;i<number;i++){ 
	retArray[i] = getUUID(); 
	} 
	return retArray.toString(); 
	}

	/** 
	* ���һ��UUID 
	* @return String UUID 
	*/ 
	public static String getUUID(){ 
	String uuid = UUID.randomUUID().toString(); 
	//ȥ����-������ 
	return uuid.replaceAll("-", "");
	}
	public static ServiceException biudingSeccess(Object data) {
		return new ServiceException(ReturnCode.SECCESS.getCode(),data);
	}
	
	public static ServiceException biudingServiceException() {
		return  new ServiceException(ReturnCode.SERVICEEXCEPTION.getCode(),ReturnCode.SERVICEEXCEPTION.getDesc());
	}
	public static ServiceException biudingFailException() {
		return  new ServiceException(ReturnCode.FAIL.getCode(),ReturnCode.FAIL.getDesc());
	}
	public static ServiceException biudingTimeOutException() {
		return  new ServiceException(ReturnCode.TIMEOUT.getCode(),ReturnCode.TIMEOUT.getDesc());
	}
}
