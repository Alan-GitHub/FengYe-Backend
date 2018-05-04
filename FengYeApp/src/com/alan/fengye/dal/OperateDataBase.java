package com.alan.fengye.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class OperateDataBase {
	
	private static String url = "jdbc:mysql://localhost:3306/fengyeapp";
	private static String username = "alan";
	private static String password = "alan";
	
	public static OperateDataBase instance = null;
	
	private OperateDataBase() {}
	
	public static OperateDataBase getInstance() {
		
		if(null == instance) {
			instance = new OperateDataBase();
		}
		
		return instance;
	}
	
	//Query Database...
	public List<Map<String, Object>> generalQuery(String sql)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			
			while(rs.next())
			{
				Map<String, Object> rowData = new HashMap<String, Object>();
				for(int i = 1; i <= columnCount; i++)
				{
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(rowData);
			}

			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	//ret value:
	//返回变化的行数
	public int generalUpdate(String sql){
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement st = null;
		int ret = 0;
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			
			st = conn.createStatement();
			
			//executeUpdate的返回值是一个整数， 表示更新的条目数（更新的记录数量）
			//sql语句为 INSERT、UPDATE 或 DELETE  三者之一。
			ret = st.executeUpdate(sql);
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Function sqlExecuteQuery call failed!");
			e.printStackTrace();
		}

		return ret;
	}
}
