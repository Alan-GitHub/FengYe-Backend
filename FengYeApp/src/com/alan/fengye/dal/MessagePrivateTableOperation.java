package com.alan.fengye.dal;

import java.util.List;
import java.util.Map;

public class MessagePrivateTableOperation {
	
	private static MessagePrivateTableOperation instance = null;
	
	public MessagePrivateTableOperation() {}
	
	public static MessagePrivateTableOperation getInstance()
	{
		if(null == instance)
		{
			instance = new MessagePrivateTableOperation();
		}
		
		return instance;
	}
	
	public List<Map<String, Object>> queryMessagePrivateTableWithReceiveUserID(int receiveUserID)
	{
		String sql = "select * from message_private where receive_user_id='" + receiveUserID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryMessagePrivateTableWithReceiveUserIDReturnSendUserIDNums(int receiveUserID)
	{
		String sql = "select distinct send_user_id from message_private where receive_user_id='" + receiveUserID + "';";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryMessagePrivateTableWithSendAndRecvUserID(int sendID, int recvID)
	{
		
		String sql = "select  * from message_private where (send_user_id='" + sendID 
				+ "' or send_user_id = '" + recvID + "') and (receive_user_id='" + sendID 
				+ "' or receive_user_id='" + recvID + "') order by time;";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public List<Map<String, Object>> queryMessagePrivateTableWithSendAndRecvUserIDGetLastestRecord(int sendID, int recvID)
	{
		
		String sql = "select  * from message_private where (send_user_id='" + sendID 
				+ "' and receive_user_id = '" + recvID + "') or (send_user_id='" + recvID 
				+ "' and receive_user_id='" + sendID + "') order by time desc limit 0,1;";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}
	
	public int updateMessagePrivateTableAddReplyContent(String replyContent, int loginUserID, int privMsgUserID, long curTime) 
	{
		
		String sql = "insert into message_private (content, time, send_user_id, receive_user_id) values('" 
					+ replyContent + "', '" + curTime + "', '" + loginUserID + "', '"+ privMsgUserID + "');";


		int ret = OperateDataBase.getInstance().generalUpdate(sql);	
		
		return ret;
	}

}
