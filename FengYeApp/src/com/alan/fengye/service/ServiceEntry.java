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
		//注册接口
		if(ServiceNameEnum.REGISTER.getCode().equals(baseRequest.getService())) {
			System.out.println("客户端注册...");
			
			String name = jsonObject.getString("registerName").trim();
			String passwd = jsonObject.getString("registerPassword").trim();
	
			UserVo userRegisterInfo = new UserVo(name, passwd);
			
			//检测业务层参数是否为空
			if(!validationServiceParameters(userRegisterInfo, baseRequest, baseResponse, response))
				return ;
			////////
			DataBaseReturnCode retCode = UserTableOperation.getInstance().userRegister(name, passwd);
			
			int retValue = 0; //返回给客户端
			switch(retCode) {
				case REGISTER_SUCCESS:
					{
						System.out.println("注册成功！");
						retValue = 1;
					}
					break;
			
				case REGISTER_FAIL:
					{
						System.out.println("注册失败...");
						retValue = -1;
					}
					break;
				
				case REGISTER_USERNAME_EXIST:
					{
						//用户名已存在
						System.out.println("用户名已存在");
						retValue = -2;
					}
					break;

				default:
					System.out.println("注册发生未知情况...");
				
			}	
			
			//返回JSON字符串给客户端
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
		//登录接口
		else if(ServiceNameEnum.LOGIN.getCode().equals(baseRequest.getService())) {
			System.out.println("客户端登录...");
			
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
						System.out.println("登录成功...");
						loginStatus = 1; //successful
					}
					break;
			
				case LOGIN_USER_NOEXIST:
					{
						System.out.println("登录名不存在...");
						loginStatus = -1;  //login name is not exist
					}
					break;
			
				case LOGIN_PASSWD_ERROR:
					{
						System.out.println("登录密码错误...");
						loginStatus = -2;  //password error
					}
					break;
			
				default:
					System.out.println("登录发生未知情况...");
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
		//推荐接口
		else if(ServiceNameEnum.RECOMMEND.getCode().equals(baseRequest.getService())) {
			System.out.println("请求推荐数据...");
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
		//发现接口
		else if(ServiceNameEnum.DISCOVER.getCode().equals(baseRequest.getService())) {
			System.out.println("请求发现数据...");
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
		//选择兴趣组
		else if(ServiceNameEnum.INTERESTGROUPSELECTED.getCode().equals(baseRequest.getService())) {

			System.out.println("选择兴趣组数据...");
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
		//关注或者取消关注兴趣组
		else if(ServiceNameEnum.INTEREST_GROUP_REGARD_OR_NOT.getCode().equals(baseRequest.getService())) {

			System.out.println("关注或者取消关注兴趣组...");
			
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
		//个人中心
		else if(ServiceNameEnum.PERSONALCENTER.getCode().equals(baseRequest.getService())) {
			
			System.out.println("个人中心");
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
		//个人中心 --- 自己的粉丝数据
		else if(ServiceNameEnum.PERSONALCENTER_FANS.getCode().equals(baseRequest.getService())) {
			
			System.out.println("个人中心---粉丝数据");
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
		//个人中心--画板
		else if(ServiceNameEnum.PERSONALCENTER_DRAWBOARD.getCode().equals(baseRequest.getService())) {
			System.out.println("个人中心--画板...");
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
		//个人中心--增加画板
		else if(ServiceNameEnum.PERSONALCENTER_ADDDRAWBOARD.getCode().equals(baseRequest.getService())) {
			System.out.println("个人中心--增加画板...");
			
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
		//个人中心--详细画板
		else if(ServiceNameEnum.PERSONALCENTER_DETAILDRAWBOARD.getCode().equals(baseRequest.getService())) {
			System.out.println("个人中心--详细画板...");
			
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
		//个人中心--详细画板--关注或者取消关注
		else if(ServiceNameEnum.DRAWBOARD_REGARD_OR_NOT.getCode().equals(baseRequest.getService())) {
			System.out.println("个人中心--详细画板--关注或者取消关注...");
			
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
		//个人中心--详细画板--添加作品信息   暂时只添加描述信息
		else if(ServiceNameEnum.WORKS_ADD_INFO.getCode().equals(baseRequest.getService())) {
			System.out.println("个人中心--详细画板--添加作品信息...");
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
		//个人中心--采集(自己上传的作品)
		else if(ServiceNameEnum.PERSONALCENTER_COLLECTION.getCode().equals(baseRequest.getService())) {
			
			System.out.println("个人中心--采集(自己上传的作品)");
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
		//个人中心--喜欢
		else if(ServiceNameEnum.PERSONALCENTER_LIKE.getCode().equals(baseRequest.getService())) {
			
			System.out.println("个人中心--喜欢");
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
		//个人中心--关注
		else if(ServiceNameEnum.PERSONALCENTER_ATTENTION.getCode().equals(baseRequest.getService())) {
			
			System.out.println("个人中心--关注...");
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
		//用户 -- 关注或者取消关注
		else if(ServiceNameEnum.USER_REGARD_OR_NOT.getCode().equals(baseRequest.getService())) {
			
			System.out.println("用户 -- 关注或者取消关注...");
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
		//作品详情  -- 喜欢或者不喜欢该作品
		else if(ServiceNameEnum.OPERATION_WORKS_LIKEORNOT.getCode().equals(baseRequest.getService())) {
			
			System.out.println("作品详情  -- 喜欢或者不喜欢该作品...");
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
		//喜欢某作品的所有用户
		else if(ServiceNameEnum.OPERATION_WORKS_ALLLIKE.getCode().equals(baseRequest.getService())) {
			
			System.out.println("喜欢某作品的所有用户...");
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
		//采集某个作品到自己画板 -- 选择画板名字
		else if(ServiceNameEnum.OPERATION_WORKS_COLLECTION_SELECT_DRAWNAME.getCode().equals(baseRequest.getService())) {
			
			System.out.println("采集某个作品到自己画板-- 选择画板名字...");
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
		//采集某个作品到自己画板 -- 执行动作
		else if(ServiceNameEnum.OPERATION_WORKS_COLLECTION_ACTION.getCode().equals(baseRequest.getService())) {
			
			System.out.println("采集某个作品到自己画板 -- 执行动作...");
			String username = jsonObject.getString("username");
			String drawName = jsonObject.getString("drawName");
			String picURL = jsonObject.getString("picURL");
			String picDesc = jsonObject.getString("picDesc");
			String originUsername = jsonObject.getString("originUsername");
			String originDrawName = jsonObject.getString("originDrawName");
			
			DetailWorksCollecAction colleAction = new DetailWorksCollecAction();
			//转换客户端传过来的图片名字格式
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
		//转发某作品的所有画板
		else if(ServiceNameEnum.OPERATION_WORKS_ALLFORWARD.getCode().equals(baseRequest.getService())) {
			
			System.out.println("转发某作品的所有画板...");
			
			String username = jsonObject.getString("username");
			String drawName = jsonObject.getString("drawName");
			String picPath = jsonObject.getString("path");

			OperationViewForwardClicked operViewForward = new OperationViewForwardClicked();
			//转换客户端传过来的图片名字格式
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
		//某个图片作品的所有评论
		else if(ServiceNameEnum.OPERATION_WORKS_ALLCOMMENT.getCode().equals(baseRequest.getService())) {
			
			System.out.println("某个图片作品的所有评论...");
			
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
		//给某个图片作品添加评论
		else if(ServiceNameEnum.OPERATION_WORKS_ADDCOMMENT.getCode().equals(baseRequest.getService())) {
			
			System.out.println("给某个图片作品添加评论...");
			
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
			//临时取巧方案：获取数据库中的评论时间
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
		//消息 -- 动态
		else if(ServiceNameEnum.MESSAGE_DYNAMIC.getCode().equals(baseRequest.getService())) {
			
			System.out.println("消息 -- 动态...");
			
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
		//消息 -- 私有
		else if(ServiceNameEnum.MESSAGE_PRIVATE.getCode().equals(baseRequest.getService())) {
			
			System.out.println("消息 -- 私有...");
			
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
		//消息 -- 详细私信内容
		else if(ServiceNameEnum.MESSAGE_PRIVATE_DETAIL.getCode().equals(baseRequest.getService())) {
			
			System.out.println("消息 -- 详细私信内容...");
			
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
		//消息 -- 回复私信内容
		else if(ServiceNameEnum.MESSAGE_PRIVATE_DETAIL_REPLY.getCode().equals(baseRequest.getService())) {
			
			System.out.println("消息 -- 回复私信内容...");
			
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
			System.out.println("接口不存在...");
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
