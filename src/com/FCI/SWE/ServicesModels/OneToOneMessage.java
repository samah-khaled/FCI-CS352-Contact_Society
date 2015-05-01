package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class OneToOneMessage {
UserEntity Receiver; 
	private int MsgId;
	private String Msgcontent;

	public OneToOneMessage() {
		// TODO Auto-generated constructor stub
	}
	public int getMsgId() {
		return MsgId;
		}

		public void setMsgId(int msgId) {
		MsgId = msgId;
		}

		public String getMsgName() {
		return Msgcontent;
		}

		public void setMsgName(String msgName) {
		Msgcontent = msgName;
		}

	public Boolean saveMessage(String SenderName, String ReceiverName) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("Messages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size table msg = " + list.size());
		try {
		Entity employee = new Entity("Messages", list.size() + 2);
		employee.setProperty("Mesg", this.Msgcontent);
		employee.setProperty("SenderName", SenderName);
		employee.setProperty("ReceiverName", ReceiverName);
		datastore.put(employee);
	// notify 
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;

	}

	
	public UserEntity getReceiver() {
		return Receiver;
	}
	public void setReceiver(UserEntity receiver) {
		Receiver = receiver;
	}
	
	public void Notify(String Sender, String ReceiverId) {
		String notifyType="MessageCommand";
		
  	Receiver.Update(Sender,ReceiverId ,notifyType);
	
		
	}
	
	
	public static String GetAllMessages(String ID) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Messages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		JSONArray array = new JSONArray();
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		for(Entity entity : list){
			if(entity.getProperty("ReceiverName").toString().equals(ID)){
				JSONObject obj = new JSONObject();
				obj.put("Mesg", entity.getProperty("Mesg").toString());
				obj.put("SenderName", entity.getProperty("SenderName").toString());
				array.add(obj);
			}
		}
		return array.toJSONString();
		
	}
	
}
