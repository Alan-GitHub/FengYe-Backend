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
			BaseResponse baseResponse, HttpServletResponse response, long curTime) {
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
			
			int retValue = 0; //���ظ��ͻ���
			switch(retCode) {
				case REGISTER_SUCCESS:
					{
						System.out.println("ע��ɹ���");
						retValue = 1;
					}
					break;
			
				case REGISTER_FAIL:
					{
						System.out.println("ע��ʧ��...");
						retValue = -1;
					}
					break;
				
				case REGISTER_USERNAME_EXIST:
					{
						//�û����Ѵ���
						System.out.println("�û����Ѵ���");
						retValue = -2;
					}
					break;

				default:
					System.out.println("ע�ᷢ��δ֪���...");
				
			}	
			
			//����JSON�ַ������ͻ���
			JSONObject obj = new JSONObject();
			obj.put("retValue", retValue);

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
		//��¼�ӿ�
		else if(ServiceNameEnum.LOGIN.getCode().equals(baseRequest.getService())) {
			System.out.println("�ͻ��˵�¼...");
			
			String name = jsonObject.getString("loginName").trim();
			String passwd = jsonObject.getString("loginPassword").trim();
			
			System.out.println("name=" + name);
			
			UserLoginVO userLoginInfo = new UserLoginVO(name, passwd);
			
			//
			if(!validationServiceParameters(userLoginInfo, baseRequest, baseResponse, response))
				return ;
			
			DataBaseReturnCode retCode = UserTableOperation.getInstance().userLogin(name, passwd);
			
			int loginStatus = 0;
			switch(retCode) {
				case LOGIN_SUCCESS:
					{
						System.out.println("��¼�ɹ�...");
						loginStatus = 1; //successful
					}
					break;
			
				case LOGIN_USER_NOEXIST:
					{
						System.out.println("��¼��������...");
						loginStatus = -1;  //login name is not exist
					}
					break;
			
				case LOGIN_PASSWD_ERROR:
					{
						System.out.println("��¼�������...");
						loginStatus = -2;  //password error
					}
					break;
			
				default:
					System.out.println("��¼����δ֪���...");
			}
			
			JSONObject jsonRet = new JSONObject();
			jsonRet.put("loginStatus", loginStatus);
			
			String str = JSONObject.toJSONString(jsonRet);
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
			String loginUser = jsonObject.getString("loginUser");
			
			InterestGroupDetailData groupDetailData = new InterestGroupDetailData();
			groupDetailData.getInterestGroupDetailData(groupName, loginUser);
			String strGroupDetailData = JSONObject.toJSONString(groupDetailData);
			
			DiscoverData discData = new DiscoverData();
			discData.getDiscoverData();
			String strDiscData = JSONObject.toJSONString(discData);
	
			JSONObject jsonObj1 = JSONObject.parseObject(strGroupDetailData);
			JSONObject jsonObj2 = JSONObject.parseObject(strDiscData);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.putAll(jsonObj1);
			jsonObj.putAll(jsonObj2);

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
		//��ע����ȡ����ע��Ȥ��
		else if(ServiceNameEnum.INTEREST_GROUP_REGARD_OR_NOT.getCode().equals(baseRequest.getService())) {

			System.out.println("��ע����ȡ����ע��Ȥ��...");
			
			String loginUser = jsonObject.getString("loginUser");
			String groupName = jsonObject.getString("groupName");
			String regardOrNot = jsonObject.getString("regardOrNot");
			
			System.out.println(loginUser);
			System.out.println(groupName);
			System.out.println(regardOrNot);
			
			InterestGroupDetailData groupDetailData = new InterestGroupDetailData();
			int retCode = groupDetailData.updateDataBaseRegardOrNotInterestGroup(loginUser, groupName, regardOrNot);
			
			JSONObject obj = new JSONObject();
			obj.put("retCode", retCode);
			
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
		//��������
		else if(ServiceNameEnum.PERSONALCENTER.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��������");
			String otherUser = jsonObject.getString("otherUser");
			String loginUser = jsonObject.getString("loginUser");
			
			System.out.println("otherUser=" + otherUser);
			System.out.println("loginUser=" + loginUser);
			
			if(otherUser == null)
				otherUser = loginUser;
			
			PersonalCenter pc = new PersonalCenter();
			pc.getPersonalCenterData(otherUser, loginUser);
			
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
			
			System.out.println("drawboardName=" + drawboardName);
			
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
			
			String otherUser = jsonObject.getString("otherUser");
			String loginUser = jsonObject.getString("loginUser");
			String drawName = jsonObject.getString("drawName");
			
			if(null == otherUser)
				otherUser = loginUser;

			PersonalCenterDetailDrawboard data = new PersonalCenterDetailDrawboard();
			data.getPersonalCenterDetailDrawboardData(otherUser, loginUser, drawName);
		
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
		//��������--��ϸ����--��ע����ȡ����ע
		else if(ServiceNameEnum.DRAWBOARD_REGARD_OR_NOT.getCode().equals(baseRequest.getService())) {
			System.out.println("��������--��ϸ����--��ע����ȡ����ע...");
			
			String loginUser = jsonObject.getString("loginUser");
			String drawOwnerUser = jsonObject.getString("drawOwnerUser");
			String drawName = jsonObject.getString("drawName");
			String regardOrNot = jsonObject.getString("regardOrNot");

			AttentionCommon common = new AttentionCommon();
			int retCode = common.updateDataBaseRegardOrNotDrawboard(drawName, drawOwnerUser, loginUser, regardOrNot);
		
			JSONObject obj = new JSONObject();
			obj.put("retCode", retCode);
			
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
		//��������--��ϸ����--�����Ʒ��Ϣ   ��ʱֻ���������Ϣ
		else if(ServiceNameEnum.WORKS_ADD_INFO.getCode().equals(baseRequest.getService())) {
			System.out.println("��������--��ϸ����--�����Ʒ��Ϣ...");
			String owner = jsonObject.getString("owner");
			String drawName = jsonObject.getString("drawName");
			String picURL = jsonObject.getString("picURL");
			String picDesc = jsonObject.getString("picDesc");

			WorksAddInfo worksInfo = new WorksAddInfo();
			picURL = picURL.replaceAll("/",	"_");
			int retCode = worksInfo.updateDataBaseAddInfoForWorks(owner, drawName, picURL, picDesc);
		
			JSONObject obj = new JSONObject();
			obj.put("retCode", retCode);
			
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
		//�û� -- ��ע����ȡ����ע
		else if(ServiceNameEnum.USER_REGARD_OR_NOT.getCode().equals(baseRequest.getService())) {
			
			System.out.println("�û� -- ��ע����ȡ����ע...");
			String loginUser = jsonObject.getString("loginUser");
			String regardedUser = jsonObject.getString("regardedUser");
			String regardOrNot = jsonObject.getString("regardOrNot");
			
			System.out.println(loginUser);
			System.out.println(regardedUser);
			System.out.println(regardOrNot);
			
			AttentionCommon common = new AttentionCommon();
			int retCode = common.updateDataBaseRegardOrNotUser(loginUser, regardedUser, regardOrNot, curTime);
			
			JSONObject obj = new JSONObject();
			obj.put("retCode", retCode);
			
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
		/* Operation View Button Action */
		//��Ʒ����  -- ϲ�����߲�ϲ������Ʒ
		else if(ServiceNameEnum.OPERATION_WORKS_LIKEORNOT.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��Ʒ����  -- ϲ�����߲�ϲ������Ʒ...");
			String picURL = jsonObject.getString("path");
			String username = jsonObject.getString("username");
			String isLike = jsonObject.getString("isLike");
			
			DetailWorksLikeOrNot detailWorks = new DetailWorksLikeOrNot();
			picURL = picURL.replaceAll("/", "_");
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
			picPath = picPath.replaceAll("/", "_");
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
			String loginUser = jsonObject.getString("loginUser");

			DetailWorksCollecSelectDrawName detailWorks = new DetailWorksCollecSelectDrawName();
			detailWorks.getLoginUserAllDrawboardName(loginUser);
			
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
			//ת���ͻ��˴�������ͼƬ���ָ�ʽ
			picURL = picURL.replaceAll("/", "_");
			colleAction.collectionPicture(originUsername, originDrawName, username, drawName, picURL, picDesc, curTime);
		
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
			//ת���ͻ��˴�������ͼƬ���ָ�ʽ
			picPath = picPath.replaceAll("/", "_");
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

			OperationViewCommentClicked operViewComment = new OperationViewCommentClicked();
			picPath = picPath.replaceAll("/", "_");
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
		//��ĳ��ͼƬ��Ʒ�������
		else if(ServiceNameEnum.OPERATION_WORKS_ADDCOMMENT.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��ĳ��ͼƬ��Ʒ�������...");
			
			String ownUser = jsonObject.getString("ownUser");
			String ownDrawboard = jsonObject.getString("ownDrawboard");
			String picPath = jsonObject.getString("path");
			String commentContent = jsonObject.getString("commentContent");
			String commentUser = jsonObject.getString("commentUser");

			OperationViewCommentClicked operViewComment = new OperationViewCommentClicked();
			picPath = picPath.replaceAll("/", "_");
			int retValue = operViewComment.operationViewCommentAddComment(commentUser, commentContent, ownUser, ownDrawboard, picPath, curTime);
		
			JSONObject obj = new JSONObject();
			obj.put("retCode", retValue);
			//��ʱȡ�ɷ�������ȡ���ݿ��е�����ʱ��
			obj.put("commentTime", curTime);
	
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
			System.out.println(loginUsername);
			System.out.println(privMsgUsername);
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
		//��Ϣ -- �ظ�˽������
		else if(ServiceNameEnum.MESSAGE_PRIVATE_DETAIL_REPLY.getCode().equals(baseRequest.getService())) {
			
			System.out.println("��Ϣ -- �ظ�˽������...");
			
			String loginUser = jsonObject.getString("loginUsername");
			String privMsgUser = jsonObject.getString("privMsgUsername");
			String replyContent = jsonObject.getString("replyContent");

			MessagePrivateDetail privContent = new MessagePrivateDetail();
			int retValue = privContent.privateMessageReply(replyContent, loginUser, privMsgUser, curTime);
			
			JSONObject obj = new JSONObject();
			obj.put("retCode", retValue);
			
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
