package com.alan.fengye.dal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.alan.fengye.enums.DataBaseReturnCode;

public class UserTableOperation {
	
	private static UserTableOperation instance = null;
	
	//私有构造方法
	private UserTableOperation() {}
	
	//单例对象
	public static UserTableOperation getInstance() {
		
		if(null == instance) {
			instance = new UserTableOperation();
		}
		
		return instance;
	}
	
	public DataBaseReturnCode userRegister(String name, String passwd){
		
		int ret = 0;
		
		String sql = "select count(*) as numbers from users where username='" +  name + "';";
		
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		Map<String, Object> map = list.get(0);
		
		if(Integer.parseInt( String.valueOf(map.get("numbers"))) > 0 ){
			
			return DataBaseReturnCode.REGISTER_USERNAME_EXIST;
		}
		else{  //用户名不存在
			
			sql = "insert into users (username, password) values('" + name + "', '" + passwd + "');";
			ret = OperateDataBase.getInstance().generalUpdate(sql);	
			if(ret > 0) //插入用户成功，注册成功
			{
				return DataBaseReturnCode.REGISTER_SUCCESS;
			}
			else
			{
				return DataBaseReturnCode.REGISTER_FAIL;
			}
		}
	}
	
	public DataBaseReturnCode userLogin(String name, String passwd){
		
		String sql = "select count(*) as numbers from users where username='" + name + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		Map<String, Object> map = list.get(0);

		//用户名不存在
		if(Integer.parseInt(String.valueOf(map.get("numbers"))) == 0){
			
			return DataBaseReturnCode.LOGIN_USER_NOEXIST;
		}
		else{  //用户名存在
			sql = "select count(*) as numbers from users where username='" + name + "' && password='" + passwd + "';";
			
			list = OperateDataBase.getInstance().generalQuery(sql);	
			map = list.get(0);
			
			if(Integer.parseInt(String.valueOf(map.get("numbers"))) > 0) //查询成功
			{
				return DataBaseReturnCode.LOGIN_SUCCESS;
			}
			else
			{
				return DataBaseReturnCode.LOGIN_PASSWD_ERROR;
			}
		}
	}
	
	public List<Map<String, Object>> queryUsersTable(int userId)
	{
		String sql = "select * from users where id='" + userId + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryUsersTable(String username)
	{
		String sql = "select * from users where username='" + username + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public int updateUsersTable(int userID, String headIconURL)
	{
		String sql = "update users set head_icon ='" + headIconURL + "' where id ='" + userID + "';";
		
		int ret = OperateDataBase.getInstance().generalUpdate(sql);
		
		return ret;
	}
}
