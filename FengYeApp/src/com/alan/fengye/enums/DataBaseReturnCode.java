package com.alan.fengye.enums;

public enum DataBaseReturnCode {
	//User table
	//1. Register
	REGISTER_SUCCESS,
	REGISTER_USERNAME_EXIST,
	REGISTER_FAIL,
	
	//2. Login
	LOGIN_SUCCESS,
	LOGIN_USER_NOEXIST,
	LOGIN_PASSWD_ERROR,
	
	//�������ݳɹ�
	DRAWBOARD_INSERT_SUCCESS,
	//��������ʧ��
	DRAWBOARD_INSERT_FAILURE
}
