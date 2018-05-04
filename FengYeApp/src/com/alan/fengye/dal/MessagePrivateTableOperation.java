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
	
	public List<Map<String, Object>> queryMessagePrivateTableWithSendAndRecvUserID(int sendID, int recvID)
	{
		
		String sql = "select  * from message_private where (send_user_id='" + sendID 
				+ "' or send_user_id = '" + recvID + "') and (receive_user_id='" + sendID 
				+ "' or receive_user_id='" + recvID + "') order by time;";
		List<Map<String, Object>> list = OperateDataBase.getInstance().generalQuery(sql);
		
		return list;
	}

}
