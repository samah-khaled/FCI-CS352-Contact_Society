package com.FCI.SWE.ServicesModels;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public  class Notification {

	public String Sender;
	public String Receiver;
	public String NotificationType;
	public String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return Sender;
	}

	public void setSender(String sender) {
		Sender = sender;
	}

	public String getReceiver() {
		return Receiver;
	}

	public void setReceiver(String receiver) {
		Receiver = receiver;
	}

	public String getNotificationType() {
		return NotificationType;
	}

	public void setNotificationType(String notificationType) {
		NotificationType = notificationType;
	}

	public Notification() {

	}

	
	public static Notification getNotification(String id){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notification");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		for(Entity entity : list){
			String entityId =entity.getProperty("ConversationId").toString();
			if(entityId.equals(id)){
				Notification notification = new Notification();
				notification.setSender(entity.getProperty("ConversationId").toString());
				notification.setReceiver(entity.getProperty("ReceiverName").toString());
				notification.setNotificationType(entity.getProperty("NotifyType").toString());
				return notification;
			}
		}
		return null;

	}
	public static String  GetAllNotifications(String UserMail){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notification");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		JSONArray array = new JSONArray();
		for(Entity entity : list){
			if(entity.getProperty("ReceiverName").toString().equals(UserMail)){
				JSONObject object = new JSONObject();
				object.put("ConversationId", entity.getProperty("ConvId_ReqSender").toString());
				object.put("ReceiverName",UserMail);
             	object.put("NotifyType", entity.getProperty("NotifyType").toString());
				array.add(object);
			}
		}
		return array.toJSONString();
		
	}

	public static Notification ParserNotificationInfo(String jsonString) {
		JSONParser parser= new JSONParser();
		try {
			JSONObject object =(JSONObject) parser.parse(jsonString);
			Notification not =new Notification();
		not.Sender=object.get("ConversationId").toString();
		not.NotificationType =object.get("NotifyType").toString();
		not.Receiver=object.get("ReceiverName").toString();
		
		return not;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
	}

