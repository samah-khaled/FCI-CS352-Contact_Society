package com.FCI.SWE.ServicesModels;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class FriendRequest {

	public FriendRequest() {

	}
	
	public String GetRequests(String Receiver){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("RequestFriend");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		JSONArray array = new JSONArray();
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		for(Entity entity : list){
			if(entity.getProperty("Reciever").toString().equals(Receiver)&&
					entity.getProperty("status").toString().equals("binding")){
				JSONObject obj = new JSONObject();
				obj.put("Sender", entity.getProperty("Sender").toString());
				obj.put("status", entity.getProperty("status").toString());
				array.add(obj);
			}
		}
		return array.toJSONString();
		
	}

}

