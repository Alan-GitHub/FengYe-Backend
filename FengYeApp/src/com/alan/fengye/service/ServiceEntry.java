package com.alan.fengye.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.alan.fengye.bean.WorksCellData;
import com.alan.fengye.dal.OperateDataBase;
import com.alan.fengye.dal.UserTableOperation;
import com.alan.fengye.enums.DataBaseReturnCode;
import com.alan.fengye.enums.ReturnCode;
import com.alan.fengye.enums.ServiceNameEnum;
import com.alan.fengye.util.BaseRequest;
import com.alan.fengye.util.BaseResponse;
import com.alan.fengye.util.CommonAnnotationUtils;
import com.alan.fengye.util.GeneratBase;
import com.alan.fengye.util.JsonReader;
import com.alan.fengye.util.ServiceException;
import com.alan.fengye.vo.UserLoginVO;
import com.alan.fengye.vo.UserVo;
import com.alibaba.fastjson.JSONObject;

public class ServiceEntry {
	
	public void handleRequest(JSONObject jsonObject, BaseRequest baseRequest, 
			BaseResponse baseResponse, HttpServletResponse response) {
		//ע��ӿ�
		if(ServiceNameEnum.REGISTER.getCode().equals(baseRequest.getService())) {
			System.out.println("�ͻ���ע��...");
			
			String name = jsonObject.getString("registerName").trim();
			String passwd = jsonObject.getString("registerPassword").trim();
	
			UserVo userRegisterInfo = new UserVo(name, passwd);
			
			//���ҵ�������Ƿ�Ϊ��
			if(!validationServiceParameters(userRegisterInfo, baseRequest, baseResponse, response))
				return ;
			////////
			DataBaseReturnCode retCode = UserTableOperation.getInstance().userRegister(name, passwd);
			
			switch(retCode) {
			case REGISTER_SUCCESS:
			{
				System.out.println("ע��ɹ���");
				try {
					response.getWriter().append("Register successful...");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
			
			case REGISTER_USERNAME_EXIST:
			{
				//�û����Ѵ���
				System.out.println("�û����Ѵ���");
				try {
					response.getWriter().append("The username has existed...");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
				
			case REGISTER_FAIL:
			{
				System.out.println("ע��ʧ��...");
				try {
					response.getWriter().append("Register failure...");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			break;
				
			default:
				System.out.println("ע�ᷢ��δ֪���...");
				
			}	
		}
		//��¼�ӿ�
		else if(ServiceNameEnum.LOGIN.getCode().equals(baseRequest.getService())) {
			
			System.out.println("�ͻ��˵�¼...");
			
			String name = jsonObject.getString("loginName").trim();
			String passwd = jsonObject.getString("loginPassword").trim();
			
			UserLoginVO userLoginInfo = new UserLoginVO(name, passwd);
			
			//
			if(!validationServiceParameters(userLoginInfo, baseRequest, baseResponse, response))
				return ;
			
			DataBaseReturnCode retCode = UserTableOperation.getInstance().userLogin(name, passwd);
			
			JSONObject jsonRet = new JSONObject();
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
			
			switch(retCode) {
			case LOGIN_SUCCESS:
			{
				System.out.println("��¼�ɹ�...");
				jsonRet.put("loginStatus", 0); //successful
				try {
					response.getWriter().append(jsonRet.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
			
			case LOGIN_USER_NOEXIST:
			{
				System.out.println("��¼��������...");
				jsonRet.put("loginStatus", 1);  //login name is not exist
				try {
					response.getWriter().append(jsonRet.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
			
			case LOGIN_PASSWD_ERROR:
			{
				System.out.println("��¼�������...");
				jsonRet.put("loginStatus", 2);  //password error
				try {
					response.getWriter().append(jsonRet.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
			
			default:
				System.out.println("��¼����δ֪���...");
			}
			
		}
		//�Ƽ��ӿ�
		else if(ServiceNameEnum.RECOMMEND.getCode().equals(baseRequest.getService())) {
			System.out.println("�����Ƽ�����...");
			RecommendData data = new RecommendData();
			data.getRecommendData();
			
			String str = JSONObject.toJSONString(data);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		//���ֽӿ�
		else if(ServiceNameEnum.DISCOVER.getCode().equals(baseRequest.getService())) {
			System.out.println("����������...");
			DiscoverData data = new DiscoverData();
			data.getDiscoverData();
			
			String str = JSONObject.toJSONString(data);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		//ѡ����Ȥ��
		else if(ServiceNameEnum.INTERESTGROUPSELECTED.getCode().equals(baseRequest.getService())) {

			System.out.println("ѡ����Ȥ������...");
			String groupName = jsonObject.getString("clickedInrstGroupName");
			
			InterestGroupDetailData groupDetailData = new InterestGroupDetailData();
			groupDetailData.getInterestGroupDetailData(groupName);
			String strGroupDetailData = JSONObject.toJSONString(groupDetailData);
			
			DiscoverData discData = new DiscoverData();
			discData.getDiscoverData();
			String strDiscData = JSONObject.toJSONString(discData);
	
			JSONObject jsonObj1 = JSONObject.parseObject(strGroupDetailData);
			JSONObject jsonObj2 = JSONObject.parseObject(strDiscData);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.putAll(jsonObj1);
			jsonObj.putAll(jsonObj2);
			
//			System.out.println(jsonObj.toString());
			
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(jsonObj.toString());
		    out.close();
		}
		//��������
		else if(ServiceNameEnum.PERSONALCENTER.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��������");
			String username = jsonObject.getString("username");
			
			System.out.println("username=" + username);
			
			PersonalCenter pc = new PersonalCenter();
			pc.getPersonalCenterData(username);
			
			String str = JSONObject.toJSONString(pc);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}	
		//�������� --- �Լ��ķ�˿����
		else if(ServiceNameEnum.PERSONALCENTER_FANS.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��������---��˿����");
			String username = jsonObject.getString("username");
			
			System.out.println("username=" + username);
			
			PersonalCenterFans fansData = new PersonalCenterFans();
			fansData.getPersonalCenterFansData(username);
				
			String str = JSONObject.toJSONString(fansData);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}	
		//��������--����
		else if(ServiceNameEnum.PERSONALCENTER_DRAWBOARD.getCode().equals(baseRequest.getService())) {
			System.out.println("��������--����...");
			String username = jsonObject.getString("username");
			
			System.out.println("username=" + username);
			
			PersonalCenterDrawboard data = new PersonalCenterDrawboard();
			data.getPersonalCenterDrawboardData(username);
			
			String str = JSONObject.toJSONString(data);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}	
		//��������--���ӻ���
		else if(ServiceNameEnum.PERSONALCENTER_ADDDRAWBOARD.getCode().equals(baseRequest.getService())) {
			System.out.println("��������--���ӻ���...");
			
			String username = jsonObject.getString("username");
			String drawboardName = jsonObject.getString("drawboardName");
			String drawboardDesc = jsonObject.getString("drawboardDesc");
			
			PersonalCenterAddDrawboard pcAddDrawboard = new PersonalCenterAddDrawboard();
			DataBaseReturnCode retCode= pcAddDrawboard.addDrawboard(username, drawboardName, drawboardDesc);
			
			JSONObject obj = new JSONObject();
			if(retCode == DataBaseReturnCode.DRAWBOARD_INSERT_SUCCESS)
			{
				obj.put("retCode", 0);
			}else if(retCode == DataBaseReturnCode.DRAWBOARD_INSERT_FAILURE)
			{
				obj.put("retCode", 1);
			}
			
			String str = JSONObject.toJSONString(obj);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}	
		//��������--��ϸ����
		else if(ServiceNameEnum.PERSONALCENTER_DETAILDRAWBOARD.getCode().equals(baseRequest.getService())) {
			System.out.println("��������--��ϸ����...");
			String username = jsonObject.getString("username");
			String drawboardName = jsonObject.getString("drawboardName");
			
			System.out.println("username=" + username);
			System.out.println("drawboardName=" + drawboardName);

			PersonalCenterDetailDrawboard data = new PersonalCenterDetailDrawboard();
			data.getPersonalCenterDetailDrawboardData(username, drawboardName);
		
			String str = JSONObject.toJSONString(data);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}			
		//��������--�ɼ�(�Լ��ϴ�����Ʒ)
		else if(ServiceNameEnum.PERSONALCENTER_COLLECTION.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��������--�ɼ�(�Լ��ϴ�����Ʒ)");
			String username = jsonObject.getString("username");
			
			PersonalCenterCollection pcCollec = new PersonalCenterCollection();
			pcCollec.getCollectionCellData(username);
			
			String str = JSONObject.toJSONString(pcCollec);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}	
		//��������--ϲ��
		else if(ServiceNameEnum.PERSONALCENTER_LIKE.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��������--ϲ��");
			String username = jsonObject.getString("username");
			
			System.out.println("username=" + username);
			
			PersonalCenterLike likeCell = new PersonalCenterLike();
			likeCell.getLikeCellData(username); 
			
			String str = JSONObject.toJSONString(likeCell);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}		
		//��������--��ע
		else if(ServiceNameEnum.PERSONALCENTER_ATTENTION.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��������--��ע...");
			String username = jsonObject.getString("username");
			
			PersonalCenterAttention data = new PersonalCenterAttention();
			data.getPersonalCenterAttentionData(username);
			
			String str = JSONObject.toJSONString(data);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		/* Operation View Button Action */
		//��Ʒ����  -- ϲ�����߲�ϲ������Ʒ
		else if(ServiceNameEnum.OPERATION_WORKS_LIKEORNOT.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��Ʒ����  -- ϲ�����߲�ϲ������Ʒ...");
			String picURL = jsonObject.getString("path");
			String username = jsonObject.getString("username");
			String isLike = jsonObject.getString("isLike");
			
			DetailWorksLikeOrNot detailWorks = new DetailWorksLikeOrNot();
			detailWorks.updateWorksLikeOrNot(username, picURL, isLike);
			
			String str = JSONObject.toJSONString(detailWorks);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		//ϲ��ĳ��Ʒ�������û�
		else if(ServiceNameEnum.OPERATION_WORKS_ALLLIKE.getCode().equals(baseRequest.getService())) {
			
			System.out.println("ϲ��ĳ��Ʒ�������û�...");
			String picPath = jsonObject.getString("path");

			OperationViewLikeClicked operViewLike = new OperationViewLikeClicked();
			operViewLike.getOperationViewLikeClickedData(picPath);
			
			String str = JSONObject.toJSONString(operViewLike);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		//�ɼ�ĳ����Ʒ���Լ����� -- ѡ�񻭰�����
		else if(ServiceNameEnum.OPERATION_WORKS_COLLECTION_SELECT_DRAWNAME.getCode().equals(baseRequest.getService())) {
			
			System.out.println("�ɼ�ĳ����Ʒ���Լ�����-- ѡ�񻭰�����...");
			String username = jsonObject.getString("username");

			DetailWorksCollecSelectDrawName detailWorks = new DetailWorksCollecSelectDrawName();
			detailWorks.getLoginUserAllDrawboardName(username);
			
			String str = JSONObject.toJSONString(detailWorks);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		//�ɼ�ĳ����Ʒ���Լ����� -- ִ�ж���
		else if(ServiceNameEnum.OPERATION_WORKS_COLLECTION_ACTION.getCode().equals(baseRequest.getService())) {
			
			System.out.println("�ɼ�ĳ����Ʒ���Լ����� -- ִ�ж���...");
			String username = jsonObject.getString("username");
			String drawName = jsonObject.getString("drawName");
			String picURL = jsonObject.getString("picURL");
			String picDesc = jsonObject.getString("picDesc");
			String originUsername = jsonObject.getString("originUsername");
			String originDrawName = jsonObject.getString("originDrawName");
			
			DetailWorksCollecAction colleAction = new DetailWorksCollecAction();
			colleAction.collectionPicture(originUsername, originDrawName, username, drawName, picURL, picDesc);
		
			String str = JSONObject.toJSONString(colleAction);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		//ת��ĳ��Ʒ�����л���
		else if(ServiceNameEnum.OPERATION_WORKS_ALLFORWARD.getCode().equals(baseRequest.getService())) {
			
			System.out.println("ת��ĳ��Ʒ�����л���...");
			
			String username = jsonObject.getString("username");
			String drawName = jsonObject.getString("drawName");
			String picPath = jsonObject.getString("path");

			OperationViewForwardClicked operViewForward = new OperationViewForwardClicked();
			operViewForward.getOperationViewForwardClickedData(username, drawName, picPath);
			
			String str = JSONObject.toJSONString(operViewForward);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		//ĳ��ͼƬ��Ʒ����������
		else if(ServiceNameEnum.OPERATION_WORKS_ALLCOMMENT.getCode().equals(baseRequest.getService())) {
			
			System.out.println("ĳ��ͼƬ��Ʒ����������...");
			
			String username = jsonObject.getString("username");
			String drawName = jsonObject.getString("drawName");
			String picPath = jsonObject.getString("path");
			
			System.out.println(username);
			System.out.println(drawName);
			System.out.println(picPath);

			OperationViewCommentClicked operViewComment = new OperationViewCommentClicked();
			operViewComment.getOperationViewCommentClickedData(username, drawName, picPath);
			
			String str = JSONObject.toJSONString(operViewComment);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		//��Ϣ -- ��̬
		else if(ServiceNameEnum.MESSAGE_DYNAMIC.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��Ϣ -- ��̬...");
			
			String username = jsonObject.getString("username");

			MessageDynamic dynMsg = new MessageDynamic();
			dynMsg.getMessageDynamicData(username);
			
			String str = JSONObject.toJSONString(dynMsg);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		//��Ϣ -- ˽��
		else if(ServiceNameEnum.MESSAGE_PRIVATE.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��Ϣ -- ˽��...");
			
			String username = jsonObject.getString("username");

			MessagePrivate privMsg = new MessagePrivate();
			privMsg.getMessagePrivateData(username);
			
			String str = JSONObject.toJSONString(privMsg);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		//��Ϣ -- ��ϸ˽������
		else if(ServiceNameEnum.MESSAGE_PRIVATE_DETAIL.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��Ϣ -- ��ϸ˽������...");
			
			String loginUsername = jsonObject.getString("loginUsername");
			String privMsgUsername = jsonObject.getString("privMsgUsername");

			MessagePrivateDetail privContent = new MessagePrivateDetail();
			privContent.getMessagePrivateDetailData(loginUsername, privMsgUsername);
			
			String str = JSONObject.toJSONString(privContent);
		    response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {
				out = response.getWriter();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
		    out.append(str); 
		    out.close();
		}
		else {
			System.out.println("�ӿڲ�����...");
			baseResponse.setReturnCode(ReturnCode.INTERFACE_EXSIST_ERROR.getCode());
			baseResponse.setMsg(ReturnCode.INTERFACE_EXSIST_ERROR.getDesc());
			baseResponse.setReturnBiz(baseRequest.getBiz());
			JsonReader.responseJson(response, baseResponse);
		}
	}
	
	/*
	 * ret:
	 * true  -- OK
	 * false -- NOK
	 * */
	private boolean validationServiceParameters(GeneratBase baseObject, BaseRequest baseRequest,
			BaseResponse baseResponse, HttpServletResponse response) {
		try {
			CommonAnnotationUtils.doValidator(baseObject);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			System.out.println(((ServiceException)e).getDesc());
			
			baseResponse.setReturnCode(ReturnCode.SERVECPARAMNULL.getCode());
			baseResponse.setMsg(ReturnCode.SERVECPARAMNULL.getDesc());
			baseResponse.setReturnBiz(baseRequest.getBiz());
			JsonReader.responseJson(response, baseResponse);
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
