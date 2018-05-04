package com.alan.fengye.bll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alan.fengye.dal.OperateDataBase;
import com.alan.fengye.enums.DataBaseReturnCode;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")

//@WebServlet( 
//		urlPatterns = {"/Servlet/ServletDemo", "/Servlet/Alan"},
//		initParams = { @WebInitParam(name = "username", value = "张三"),
//					   @WebInitParam(name = "password", value = "111")}
//		)

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		request.setCharacterEncoding("utf-8");
		int ret = 0;
		String name = request.getParameter("registerName");
		String passwd = request.getParameter("registerPassword");
		System.out.println(")))))))"+name);
		System.out.println(")))))))"+passwd);
		
		String sql = "select count(*) as numbers from users where username='" +  name + "';";
		
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		Map<String, Object> map = list.get(0);
		
		if(Integer.parseInt( String.valueOf(map.get("numbers"))) > 0 ){
			
			//用户名已存在
			System.out.println("用户名已存在");
			response.getWriter().append("用户名已存在...");
		}
		else{  //用户名不存在，可以使用
			
			sql = "insert into users (username, password) values('" + name + "', '" + passwd + "');";
			
			ret = OperateDataBase.getInstance().generalUpdate(sql);	
			
			if(ret > 0) //插入数据成功，注册成功
			{
				System.out.println("注册成功！");
				response.getWriter().append("注册成功！...");
			}
			
			else
			{
				System.out.println("注册失败...");
				response.getWriter().append("注册失败...");
			}
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
//		String sql = null;
//		
//		OperateDataBase odb = OperateDataBase.getInstance();
//		
//		try {
//			odb.sqlQuery(sql);
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
