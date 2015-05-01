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


public  class GroupChatMessage  {
 
  private String ConvId;
private String Msgcontent;
protected ArrayList<UserEntity> Receivers =new ArrayList<UserEntity> ();



public GroupChatMessage() {

  }
 

public String getConvId(String ConvName) {
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Query gaeQuery = new Query("conversation");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	
	for (Entity entity : pq.asIterable()) {
		String ConversationName =entity.getProperty("ConversationName").toString();

		if (ConversationName.equals(ConvName)){
			Long x= entity.getKey().getId();
	    	ConvId = x.toString();
			return ConvId;
			
		}
	}
	return ConvId;
	
	

}

public void setConvId(String msgId) {
	
	ConvId = msgId;
}

public String getMsgName() {
return Msgcontent;
}

public void setMsgName(String msgName) {
Msgcontent = msgName;
}
	
public ArrayList GetReceivers(String Sender){
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Query gaeQuery = new Query("Receivers");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	
	for (Entity entity : pq.asIterable()) {
		String Convid =entity.getProperty("ConversationId").toString();
		String reciver =entity.getProperty("reciver").toString();

		if (Convid.equals(this.ConvId)&& !(reciver.equals(Sender))){ 
			// !reciver.equals(Sender) user who send the msg wont be in receiver list 
			
			UserEntity user =new UserEntity();
			user.setemail(reciver);
			Receivers.add(user);
		}
		}
	return Receivers;
	
}

public Boolean saveMessage(String SenderName) {
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
	employee.setProperty("ReceiverName", this.ConvId);

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

public void AddReceiver(String Rname,String ConvId) {
DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Transaction txn2 = datastore.beginTransaction();
	Query gaeQuery2 = new Query("Receivers");
	PreparedQuery pq2 = datastore.prepare(gaeQuery2);
	List<Entity> list2 = pq2.asList(FetchOptions.Builder.withDefaults());
	System.out.println("Size of Receivers" + list2.size());
	try{
    Entity employee2 = new Entity("Receivers", list2.size() + 3);
	System.out.println("Freind mail= " + Rname);

	employee2.setProperty("ConversationId", ConvId);
	employee2.setProperty("reciver", Rname);
	datastore.put(employee2);
	txn2.commit();
}finally{
	if (txn2.isActive()) {
        txn2.rollback();
    }	
}
}

public String  CreateConversation(String ConvName,String OwnerEmail){
   
  DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Transaction txn = datastore.beginTransaction();
	Query gaeQuery = new Query("conversation");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
	System.out.println("Size conversation= " + list.size());
	try {
	Entity employee = new Entity("conversation", list.size() + 2);
Integer Convid=list.size() + 2 ;
     ConvId =Convid.toString(); 
	employee.setProperty("ConversationName", ConvName);
	datastore.put(employee);
	txn.commit();
	
	//AddReceiver(OwnerEmail, Convid.toString()) ;
	return  Convid.toString();

	}finally{
		if (txn.isActive()) {
	        txn.rollback();
	    }}
}

public void Notify(String ReceiverId,String ConvId){ // convid == sender 
	String notifyType="MessageCommand";
	for(int i=0;i<Receivers.size();i++)
	{
		Receivers.get(i).Update(this.ConvId,ReceiverId,notifyType);
	}
	
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