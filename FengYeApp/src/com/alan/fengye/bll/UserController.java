package com.alan.fengye.bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alan.fengye.enums.ReturnCode;
import com.alan.fengye.enums.ServiceNameEnum;
import com.alan.fengye.service.ServiceEntry;
import com.alan.fengye.service.ServiceForPictureUpload;
import com.alan.fengye.util.BaseController;
import com.alan.fengye.util.BaseRequest;
import com.alan.fengye.util.BaseResponse;
import com.alan.fengye.util.CommonAnnotationUtils;
import com.alan.fengye.util.JsonReader;
import com.alan.fengye.util.ServiceException;
import com.alan.fengye.util.TimeUtils;
import com.alan.fengye.vo.UserVo;
import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final BaseController baseController=new BaseController();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		//������Ʒ��ʱ�򣬿��Խ���ǰʱ��new Date().getTime()�������ݿ�
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		//String date = df.format(new Date().getTime());// new Date()Ϊ��ȡ��ǰϵͳʱ�䣬Ҳ��ʹ�õ�ǰʱ���

		//���ɵ�ǰʱ��
		Calendar time = Calendar.getInstance();
		
		//�ж��Ƿ��Ǳ��ļ�����
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		//�����ļ��ϴ�
		if(isMultipart){

			ServiceForPictureUpload picUpload = new ServiceForPictureUpload();
			picUpload.savePicToServer(request, response, time.getTimeInMillis());
			return;
		}
		
		//�������
		BaseResponse baseResponse=new BaseResponse(ReturnCode.SERVICEEXCEPTION.getCode(),ReturnCode.SERVICEEXCEPTION.getDesc());
		BaseRequest baseRequest=null;
		Long newTime=null;
		JSONObject object=null;
		try {
			object=JsonReader.requerstJson(request);
			baseRequest=new BaseRequest(object.getString("ver"),object.getString("service"),
					object.getString("channel"),object.getString("biz"),object.getString("time"));
			CommonAnnotationUtils.doValidator(baseRequest);
			newTime=new TimeUtils().getTime(baseRequest.getTime());
		} catch (ServiceException|IOException e) {
			baseResponse.setReturnCode(ReturnCode.BASEPARAMNULL.getCode());
			baseResponse.setMsg(ReturnCode.BASEPARAMNULL.getDesc());
			baseResponse.setReturnBiz(baseRequest.getBiz());
			JsonReader.responseJson(response, baseResponse);
			System.out.println(((ServiceException)e).getDesc());
			e.printStackTrace();
		}
		if(time.getTimeInMillis()>newTime) {
			ServiceEntry serviceEntry = new ServiceEntry();
			serviceEntry.handleRequest(JSONObject.parseObject(object.getString("data")), 
					baseRequest, baseResponse, response, time.getTimeInMillis());
		}else {
			
			baseResponse.setReturnCode(ReturnCode.TIMEOUT.getCode());
			baseResponse.setMsg(ReturnCode.TIMEOUT.getDesc());
			baseResponse.setReturnBiz(baseRequest.getBiz());
			JsonReader.responseJson(response, baseResponse);
		}
	}
}
